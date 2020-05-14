package com.salezshark.emailtemplate.master.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.salezshark.emailtemplate.utility.BooleanConverter;

@Entity
@Table(name = "data_source_mapping")
public class DataSourceMappingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "data_source_mapping_id")
	private Long dataSourceMappingId;

	@Column(name = "user_name")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "driver_class_name")
	private String driverClassName;

	@Column(name = "url")
	private String url;

	@Column(name = "is_active")
	@Convert(converter = BooleanConverter.class)
	private Boolean isActive;

	@Column(name = "tenant_id")
	private String tenantId;

	public Long getDataSourceMappingId() {
		return dataSourceMappingId;
	}

	public void setDataSourceMappingId(Long dataSourceMappingId) {
		this.dataSourceMappingId = dataSourceMappingId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
