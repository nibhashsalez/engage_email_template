package com.salezshark.emailtemplate.config;

import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.ENABLE_LAZY_LOAD_NO_TRANS;
import static org.hibernate.cfg.AvailableSettings.FORMAT_SQL;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
import static org.hibernate.cfg.AvailableSettings.MULTI_TENANT;
import static org.hibernate.cfg.AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER;
import static org.hibernate.cfg.AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS;

import java.util.HashMap;

import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author smoyeen
 * @version 4.0
 * @since 4.0
 */
@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(basePackages = "com.salezshark.emailtemplate.repository", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
@EntityScan("com.salezshark.emailtemplate.entity")
public class TenantJPAConfiguration {

	@Autowired
	private Environment env;

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(MultiTenantConnectionProvider provider,
			CurrentTenantIdentifierResolver resolver) {

		HashMap<String, Object> properties = new HashMap<>();
		properties.put(DIALECT, env.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.put(USE_NEW_ID_GENERATOR_MAPPINGS,
				env.getProperty("spring.jpa.properties.hibernate.id.new_generator_mappings"));
		properties.put(FORMAT_SQL, env.getProperty("spring.jpa.properties.hibernate.format_sql"));
		properties.put(ENABLE_LAZY_LOAD_NO_TRANS,
				env.getProperty("spring.jpa.properties.hibernate.enable_lazy_load_no_trans"));
		properties.put(HBM2DDL_AUTO, env.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.put(SHOW_SQL, env.getProperty("spring.jpa.show-sql"));
		/* configure multi tenant* properties */
		properties.put(MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
		properties.put(MULTI_TENANT_CONNECTION_PROVIDER, provider);
		properties.put(MULTI_TENANT_IDENTIFIER_RESOLVER, resolver);

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(tenantDataSource());
		em.setPackagesToScan("com.salezshark.emailtemplate.entity");
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Bean
	@Primary
	public DataSource tenantDataSource() {

		HikariConfig config = new HikariConfig();
		// Base datasource configuration
		config.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		config.setJdbcUrl(env.getProperty("spring.datasource.tenant.url"));
		config.setUsername(env.getProperty("spring.datasource.username"));
		config.setPassword(env.getProperty("spring.datasource.password"));
		// Connection pool configuration
		config.setConnectionTestQuery(env.getProperty("spring.datasource.hikari.connection-test-query"));
		config.setConnectionTimeout(env.getProperty("spring.datasource.hikari.connection-timeout", Integer.class));
		config.setMinimumIdle(env.getProperty("spring.datasource.hikari.minimum-idle", Integer.class));
		config.setMaximumPoolSize(env.getProperty("spring.datasource.hikari.maximum-pool-size", Integer.class));
		config.setIdleTimeout(env.getProperty("spring.datasource.hikari.idle-timeout", Integer.class));
		config.setMaxLifetime(env.getProperty("spring.datasource.hikari.max-lifetime", Integer.class));
		config.setPoolName("TENANT DATABASE");
		return new HikariDataSource(config);
	}

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager(MultiTenantConnectionProvider provider, CurrentTenantIdentifierResolver resolver) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory(provider, resolver).getObject());
		return transactionManager;
	}
}
