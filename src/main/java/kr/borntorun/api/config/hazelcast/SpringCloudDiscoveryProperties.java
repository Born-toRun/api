package kr.borntorun.api.config.hazelcast;

import static com.hazelcast.config.properties.PropertyTypeConverter.INTEGER;
import static com.hazelcast.config.properties.PropertyTypeConverter.STRING;

import com.hazelcast.config.properties.PropertyDefinition;
import com.hazelcast.config.properties.PropertyTypeConverter;
import com.hazelcast.config.properties.SimplePropertyDefinition;
import com.hazelcast.config.properties.ValueValidator;

class SpringCloudDiscoveryProperties {

	public static final PropertyDefinition SERVICE_ID = property("serviceId", STRING);
	public static final PropertyDefinition PORT = property("port", INTEGER);

	private SpringCloudDiscoveryProperties() {
	}

	private static PropertyDefinition property(String key, PropertyTypeConverter typeConverter) {
		return property(key, typeConverter, null);
	}

	private static PropertyDefinition property(String key, PropertyTypeConverter typeConverter,
	  ValueValidator valueValidator) {
		return new SimplePropertyDefinition(key, true, typeConverter, valueValidator);
	}
}
