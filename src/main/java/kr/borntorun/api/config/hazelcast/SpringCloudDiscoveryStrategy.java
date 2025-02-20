package kr.borntorun.api.config.hazelcast;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import com.hazelcast.cluster.Address;
import com.hazelcast.logging.ILogger;
import com.hazelcast.spi.discovery.AbstractDiscoveryStrategy;
import com.hazelcast.spi.discovery.DiscoveryNode;
import com.hazelcast.spi.discovery.SimpleDiscoveryNode;

class SpringCloudDiscoveryStrategy extends AbstractDiscoveryStrategy {

	private final DiscoveryClient discoveryClient;
	private final String serviceId;
	private final int port;

	public SpringCloudDiscoveryStrategy(final DiscoveryClient discoveryClient, final ILogger logger,
	  final Map<String, Comparable> properties) {
		super(logger, properties);
		this.discoveryClient = discoveryClient;
		this.port = Objects.requireNonNull(getOrNull(SpringCloudDiscoveryProperties.PORT));
		this.serviceId = Objects.requireNonNull(getOrNull(SpringCloudDiscoveryProperties.SERVICE_ID));
	}

	@Override
	public Iterable<DiscoveryNode> discoverNodes() {
		final List<DiscoveryNode> nodes = new ArrayList<>();
		for (final ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
			try {
				nodes.add(new SimpleDiscoveryNode(new Address(instance.getHost(), port)));
			} catch (UnknownHostException e) {
				getLogger().finest(e.getMessage());
			}
		}
		return nodes;
	}
}
