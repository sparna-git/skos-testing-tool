package fr.sparna.rdf.skos.testtool.fix;

import org.eclipse.rdf4j.common.lang.service.ServiceRegistry;

public class FixPolicyRegistry extends ServiceRegistry<Class, FixPolicy> {

	/**
	 * Internal helper class to avoid continuous synchronized checking.
	 */
	private static class FixPolicyRegistryHolder {
		public static final FixPolicyRegistry instance = new FixPolicyRegistry();
	}

	/**
	 * Gets the default SailRegistry.
	 * 
	 * @return The default registry.
	 */
	public static FixPolicyRegistry getInstance() {
		return FixPolicyRegistryHolder.instance;
	}

	public FixPolicyRegistry() {
		super(FixPolicy.class);
	}

	@Override
	protected Class getKey(FixPolicy policy) {
		return policy.getClass();
	}
	
	
}
