package org.doubleoops.mosh;

import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.platform.engine.EngineDiscoveryRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.UniqueId;

/**
 * Executor for the mosh tool.
 */
public class MoshEngine {

	private static final UniqueId ID = UniqueId.parse("mosh-engine");
	/**
	 * Called by main() to set the tool in motion.
	 */
	public void run() {
		JupiterTestEngine jupiterEngine = new JupiterTestEngine();
		EngineDiscoveryRequest discoveryRequest = null;
		TestDescriptor testDescriptor = jupiterEngine.discover(discoveryRequest, ID);
		
	}
}
