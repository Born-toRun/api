package kr.borntorun.api.config.hazelcast;

import java.io.InputStream;
import java.io.OutputStream;

import org.jetbrains.annotations.NotNull;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.InputChunked;
import com.esotericsoftware.kryo.io.OutputChunked;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import com.esotericsoftware.kryo.serializers.EnumNameSerializer;
import com.esotericsoftware.kryo.util.DefaultClassResolver;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import com.esotericsoftware.kryo.util.Pool;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

public class Kryo5Serializer implements StreamSerializer<Object> {

	private static final Pool<Kryo> pool = new KryoPool();

	@Override
	public void write(@NotNull ObjectDataOutput out, @NotNull Object object) {
		Kryo kryo = pool.obtain();
		OutputChunked output = new OutputChunked((OutputStream)out);
		kryo.writeClassAndObject(output, object);
		output.endChunk();
		output.flush();
		pool.free(kryo);
	}

	@NotNull
	@Override
	public Object read(@NotNull ObjectDataInput in) {
		Kryo kryo = pool.obtain();
		Object object = kryo.readClassAndObject(new InputChunked((InputStream)in));
		pool.free(kryo);
		return object;
	}

	@Override
	public int getTypeId() {
		return 1;
	}

	@Override
	public void destroy() {
		pool.clean();
	}

	static class KryoPool extends Pool<Kryo> {

		private final ClassResolver classResolver = new ClassResolver();

		public KryoPool() {
			super(true, true);
		}

		@Override
		protected Kryo create() {
			// TODO: resolver
			Kryo kryo = new Kryo(classResolver, null);
			kryo.setReferences(true);
			kryo.register(PageImpl.class);
			kryo.register(Sort.class);
			kryo.register(Pageable.class);
			kryo.setDefaultSerializer(CompatibleFieldSerializer.class);
			kryo.addDefaultSerializer(Enum.class, EnumNameSerializer.class);
			kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
			kryo.setRegistrationRequired(false);
			return kryo;
		}
	}

	static class ClassResolver extends DefaultClassResolver {

		@Override
		protected Class<?> getTypeByName(String className) {
			Class<?> type = super.getTypeByName(className);
			if (type != null) {
				return type;
			}
			try {
				return Class.forName(className, false, Thread.currentThread().getContextClassLoader());
			} catch (ClassNotFoundException ex) {
				return null;
			}
		}
	}
}
