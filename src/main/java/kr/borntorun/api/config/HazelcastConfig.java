package kr.borntorun.api.config;

import java.util.Collections;
import java.util.HashMap;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryConfig;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.config.EntryListenerConfig;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.GlobalSerializerConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.spi.properties.ClusterProperty;

import kr.borntorun.api.config.hazelcast.HazelcastMapEventLogger;
import kr.borntorun.api.config.hazelcast.Kryo5Serializer;
import kr.borntorun.api.config.hazelcast.SpringCloudDiscoveryStrategyFactory;

@Configuration
public class HazelcastConfig {

	@Value("${spring.cache.port}")
	private int port;
	private final DiscoveryClient discoveryClient;
	private final Registration registration;

	public HazelcastConfig(final DiscoveryClient discoveryClient,
	  final ObjectProvider<Registration> registration) {
		this.discoveryClient = discoveryClient;
		this.registration = registration.getIfAvailable();
	}

	@Bean
	public Config config() {
		final Config config = new Config();
		config.setInstanceName("borntorun");
		configureNetwork(config.getNetworkConfig());
		configureDefaultMapConfig(config.getMapConfig("default"));
		configureSerialization(config.getSerializationConfig());
		return config;
	}

	private void configureNetwork(final NetworkConfig networkConfig) {
		final JoinConfig joinConfig = networkConfig.getJoin();
		networkConfig.setPort(port);
		if (registration == null) {
			System.setProperty("hazelcast.local.localAddress", "127.0.0.1");
		} else {
			joinConfig.getMulticastConfig().setEnabled(false);
			configureDiscovery(joinConfig.getDiscoveryConfig(), registration.getServiceId(), port);
		}
	}

	private void configureDiscovery(final DiscoveryConfig discoveryConfig, final String serviceId, final int port) {
		System.setProperty(ClusterProperty.DISCOVERY_SPI_ENABLED.getName(), "true");
		System.setProperty(ClusterProperty.WAIT_SECONDS_BEFORE_JOIN.getName(), "1");
		System.setProperty(ClusterProperty.MERGE_FIRST_RUN_DELAY_SECONDS.getName(), "10");

		final HashMap<String, String> properties = new HashMap<>();
		properties.put("serviceId", serviceId);
		properties.put("port", String.valueOf(port));
		discoveryConfig.addDiscoveryStrategyConfig(new DiscoveryStrategyConfig()
		  .setDiscoveryStrategyFactory(new SpringCloudDiscoveryStrategyFactory(discoveryClient))
		  .setProperties(Collections.unmodifiableMap(properties)));
	}

	private void configureDefaultMapConfig(final MapConfig mapConfig) {
		mapConfig.setTimeToLiveSeconds(60)
		  .setEvictionConfig(new EvictionConfig()
			.setEvictionPolicy(EvictionPolicy.LRU)
			.setMaxSizePolicy(MaxSizePolicy.USED_HEAP_PERCENTAGE)
			.setSize(10))
		  .addEntryListenerConfig(new EntryListenerConfig()
			.setImplementation(new HazelcastMapEventLogger())
			.setLocal(false)
			.setIncludeValue(false));
	}

	private void configureSerialization(final SerializationConfig serializationConfig) {
		serializationConfig.setGlobalSerializerConfig(new GlobalSerializerConfig()
		  .setImplementation(new Kryo5Serializer())
		  .setOverrideJavaSerialization(true));
	}
}
