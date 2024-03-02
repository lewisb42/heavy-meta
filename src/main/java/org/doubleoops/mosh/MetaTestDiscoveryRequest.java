package org.doubleoops.mosh;

import java.util.Collections;
import java.util.List;

import org.junit.platform.engine.ConfigurationParameters;
import org.junit.platform.engine.DiscoveryFilter;
import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.EngineDiscoveryRequest;

/**
 * Discover meta-tests in the project.
 */
class MetaTestDiscoveryRequest implements EngineDiscoveryRequest {

	@Override
	public <T extends DiscoverySelector> List<T> getSelectorsByType(Class<T> selectorType) {
		return Collections.emptyList();
	}

	@Override
	public <T extends DiscoveryFilter<?>> List<T> getFiltersByType(Class<T> filterType) {
		return Collections.emptyList();
	}

	@Override
	public ConfigurationParameters getConfigurationParameters() {
		// TODO Auto-generated method stub
		return new MoshConfigurationParameters();
	}

}
