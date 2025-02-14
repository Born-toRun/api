package kr.borntorun.api.config.hazelcast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.client.discovery.DiscoveryClient;

import com.hazelcast.config.properties.PropertyDefinition;
import com.hazelcast.logging.ILogger;
import com.hazelcast.spi.discovery.DiscoveryNode;
import com.hazelcast.spi.discovery.DiscoveryStrategy;
import com.hazelcast.spi.discovery.DiscoveryStrategyFactory;

public class SpringCloudDiscoveryStrategyFactory implements DiscoveryStrategyFactory {

  private static final Collection<PropertyDefinition> PROPERTY_DEFINITIONS;

  static {
    List<PropertyDefinition> propertyDefinitions = new ArrayList<>();
    propertyDefinitions.add(SpringCloudDiscoveryProperties.SERVICE_ID);
    propertyDefinitions.add(SpringCloudDiscoveryProperties.PORT);
    PROPERTY_DEFINITIONS = Collections.unmodifiableCollection(propertyDefinitions);
  }

  private final DiscoveryClient discoveryClient;

  public SpringCloudDiscoveryStrategyFactory(final DiscoveryClient discoveryClient) {
    this.discoveryClient = discoveryClient;
  }

  @Override
  public Class<? extends DiscoveryStrategy> getDiscoveryStrategyType() {
    return SpringCloudDiscoveryStrategy.class;
  }

  @Override
  public DiscoveryStrategy newDiscoveryStrategy(final DiscoveryNode discoveryNode, final ILogger logger, final Map<String, Comparable> properties) {
    return new SpringCloudDiscoveryStrategy(discoveryClient, logger, properties);
  }

  @Override
  public Collection<PropertyDefinition> getConfigurationProperties() {
    return PROPERTY_DEFINITIONS;
  }
}
