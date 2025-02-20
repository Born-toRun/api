package kr.borntorun.api.config.hazelcast;

import org.springframework.data.domain.PageImpl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PageSerializer extends Serializer<PageImpl> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void write(Kryo kryo, Output output, PageImpl object) {
		try {
			String json = objectMapper.writeValueAsString(object);
			output.writeString(json);
		} catch (JsonProcessingException e) {
			throw new KryoException("cannot serialize PageImpl!");
		}

	}

	@Override
	public PageImpl read(Kryo kryo, Input input, Class<? extends PageImpl> type) {
		String page = input.readString();
		try {
			return objectMapper.readValue(page, new TypeReference<>() {
			});
		} catch (JsonProcessingException ex) {
			throw new KryoException("cannot deserialize PageImpl!");
		}
	}
}
