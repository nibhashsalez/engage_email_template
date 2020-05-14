package com.salezshark.emailtemplate.config.multitenancy;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author nibhash
 * @version 1.0
 */

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {

	private final static Logger LOGGER = LoggerFactory.getLogger(MultiTenantConnectionProviderImpl.class);
	private static final long serialVersionUID = 1L;

	private static final String USE_DATABASE = "USE ";

	@Autowired
	private DataSource commonDataSource;

	public MultiTenantConnectionProviderImpl(DataSource commonDataSource) {
		this.commonDataSource = commonDataSource;
	}

	@Override
	public Connection getAnyConnection() throws SQLException {
		return commonDataSource.getConnection();
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {

		connection.close();
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {

		LOGGER.debug("Get connection for tenant {}", tenantIdentifier);
		final Connection connection = getAnyConnection();
		try {
			if (tenantIdentifier != null) {
				connection.createStatement().execute(USE_DATABASE + tenantIdentifier);
			} else {
				connection.createStatement().execute(USE_DATABASE + TenantContext.DEFAULT_TENANT);
			}
		} catch (SQLException exception) {
			LOGGER.error("Error occurred on setting schema {}, Exception {}", tenantIdentifier, exception.getMessage());
			throw new HibernateException("Error occurred on setting schema " + tenantIdentifier, exception);
		}
		return connection;
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {

		LOGGER.debug("Release connection for tenant {}", tenantIdentifier);
		try {
			connection.createStatement().execute(USE_DATABASE + TenantContext.DEFAULT_TENANT);
		} catch (SQLException exception) {
			LOGGER.error("Error occurred on setting schema {}, Exception {}", tenantIdentifier, exception.getMessage());
			throw new HibernateException("Error occurred on setting schema " + tenantIdentifier, exception);
		}
		connection.close();
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		return null;
	}
}
