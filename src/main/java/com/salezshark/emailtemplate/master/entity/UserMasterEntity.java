package com.salezshark.emailtemplate.master.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.salezshark.emailtemplate.utility.BooleanConverter;
import com.salezshark.emailtemplate.utility.LocalDateTimeConverter;

@Entity
@Table(name = "user_master")
public class UserMasterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_MASTER_ID")
	private long userMasterId;

	@Column(name = "USER_ID")
	private long userId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "IS_DELETED")
	@Convert(converter = BooleanConverter.class)
	private Boolean isDeleted;

	@Column(name = "CONFIRMATION_STATUS")
	@Convert(converter = BooleanConverter.class)
	private Boolean confirmationStatus;

	@Column(name = "PERMANENT_DELETED")
	@Convert(converter = BooleanConverter.class)
	private Boolean permanentDeleted;

	@Column(name = "REPORT_TO")
	private long reportTo;

	@Column(name = "ORGANIZATION_ID")
	private long organizationId;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "RESET_CODE")
	private String resetCode;

	@Column(name = "RESET_CODE_TIME")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime resetCodeTime;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(name = "MOBILE")
	private String mobile;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "DATA_SOURCE_MAPPING_ID", referencedColumnName = "data_source_mapping_id")
	private DataSourceMappingEntity dataSourceMappingId;

	public String getUserFullName() {
		String name = "";
		if (this.firstName != null) {
			name = name + this.firstName;
		}
		if (this.firstName != null && this.lastName != null) {
			name = name + " ";
		}
		if (this.lastName != null) {
			name = name + this.lastName + " ";
		}
		return name;
	}
	
	public long getUserMasterId() {
		return userMasterId;
	}

	public void setUserMasterId(long userMasterId) {
		this.userMasterId = userMasterId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getConfirmationStatus() {
		return confirmationStatus;
	}

	public void setConfirmationStatus(Boolean confirmationStatus) {
		this.confirmationStatus = confirmationStatus;
	}

	public Boolean getPermanentDeleted() {
		return permanentDeleted;
	}

	public void setPermanentDeleted(Boolean permanentDeleted) {
		this.permanentDeleted = permanentDeleted;
	}

	public long getReportTo() {
		return reportTo;
	}

	public void setReportTo(long reportTo) {
		this.reportTo = reportTo;
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResetCode() {
		return resetCode;
	}

	public void setResetCode(String resetCode) {
		this.resetCode = resetCode;
	}

	public LocalDateTime getResetCodeTime() {
		return resetCodeTime;
	}

	public void setResetCodeTime(LocalDateTime resetCodeTime) {
		this.resetCodeTime = resetCodeTime;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public DataSourceMappingEntity getDataSourceMappingId() {
		return dataSourceMappingId;
	}

	public void setDataSourceMappingId(DataSourceMappingEntity dataSourceMappingId) {
		this.dataSourceMappingId = dataSourceMappingId;
	}

}
