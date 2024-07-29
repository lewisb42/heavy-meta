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
		// TODO: discover classes that extend MetaTestBase
		/* TODO: ??? 2nd selector for vanilla @Test's???
		 * Can we use this selection process to gather those
		 * in, but use the results as class/methodname combos
		 * for running metatests?
		 */ 
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
