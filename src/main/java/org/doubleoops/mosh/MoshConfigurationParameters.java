package org.doubleoops.mosh;

import java.util.Optional;

import org.junit.platform.engine.ConfigurationParameters;

/**
 * Mosh-specific configuration parameters for the test engine.
 * 
 * Currently has no actual parameters in it.
 */
final class MoshConfigurationParameters implements ConfigurationParameters {
	
	@Override
	public Optional<String> get(String key) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Boolean> getBoolean(String key) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}
