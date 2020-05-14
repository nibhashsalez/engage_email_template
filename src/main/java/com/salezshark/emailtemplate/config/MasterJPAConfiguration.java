package com.salezshark.emailtemplate.config;

import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.ENABLE_LAZY_LOAD_NO_TRANS;
import static org.hibernate.cfg.AvailableSettings.FORMAT_SQL;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(basePackages = "com.salezshark.emailtemplate.master.repository", entityManagerFactoryRef = "masterEntityManagerFactory", transactionManagerRef = "masterTransactionManager")
@EntityScan("com.salezshark.emailtemplate.master.entity")
public class MasterJPAConfiguration {

	@Autowired
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory() {

		HashMap<String, Object> properties = new HashMap<>();
		properties.put(DIALECT, env.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.put(USE_NEW_ID_GENERATOR_MAPPINGS,
				env.getProperty("spring.jpa.properties.hibernate.id.new_generator_mappings"));
		properties.put(FORMAT_SQL, env.getProperty("spring.jpa.properties.hibernate.format_sql"));
		properties.put(ENABLE_LAZY_LOAD_NO_TRANS,
				env.getProperty("spring.jpa.properties.hibernate.enable_lazy_load_no_trans"));
		properties.put(HBM2DDL_AUTO, env.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.put(SHOW_SQL, env.getProperty("spring.jpa.show-sql"));

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(masterDataSource());
		em.setPackagesToScan("com.salezshark.emailtemplate.master.entity");
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setJpaPropertyMap(properties);
		return em;
	}

	@Bean
	public DataSource masterDataSource() {
		HikariConfig config = new HikariConfig();
		// Base datasource configuration
		config.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		config.setJdbcUrl(env.getProperty("spring.datasource.master.url"));
		config.setUsername(env.getProperty("spring.datasource.username"));
		config.setPassword(env.getProperty("spring.datasource.password"));
		// Connection pool configuration
		config.setConnectionTestQuery(env.getProperty("spring.datasource.hikari.connection-test-query"));
		config.setConnectionTimeout(env.getProperty("spring.datasource.hikari.connection-timeout", Integer.class));
		config.setMinimumIdle(env.getProperty("spring.datasource.hikari.minimum-idle", Integer.class));
		config.setMaximumPoolSize(env.getProperty("spring.datasource.hikari.maximum-pool-size", Integer.class));
		config.setIdleTimeout(env.getProperty("spring.datasource.hikari.idle-timeout", Integer.class));
		config.setMaxLifetime(env.getProperty("spring.datasource.hikari.max-lifetime", Integer.class));
		config.setPoolName("MASTER DATABASE");
		return new HikariDataSource(config);
	}

	@Bean
	public PlatformTransactionManager masterTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(masterEntityManagerFactory().getObject());
		return transactionManager;
	}
}
