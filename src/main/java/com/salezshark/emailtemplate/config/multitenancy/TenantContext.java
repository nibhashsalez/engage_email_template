package com.salezshark.emailtemplate.config.multitenancy;

import org.springframework.stereotype.Component;

/**
 * @author nibhash
 * @version 1.0
 */

@Component
public class TenantContext {

	public static final String DEFAULT_TENANT = "ss_template_builder";

	private static ThreadLocal<String> currentTenant = new ThreadLocal<String>() {
		@Override
		protected String initialValue() {
			return DEFAULT_TENANT;
		}
	};

	public static void setCurrentTenant(String tenant) {
		currentTenant.set(tenant);
	}
	public static String getCurrentTenant() {
		return currentTenant.get();
	}
	public static void clear() {
		currentTenant.remove();
	}
}
