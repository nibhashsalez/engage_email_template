// Generated with g9.

package com.salezshark.emailtemplate.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.salezshark.emailtemplate.bean.SubscriberManagementBean;
import com.salezshark.emailtemplate.request.EmailTemplateRequest;
import com.salezshark.emailtemplate.utility.LocalDateTimeConverter;

@Entity
@Table(name = "folder_master")
public class FolderMasterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "folder_name")
	private String folderName;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@Column(name = "created_date")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime createdDate;
	
	@Column(name = "updated_date")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime updatedDate;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "updated_by")
	private Long updatedBy;
	
	@Column(name = "dsm_id")
	private Long dsmId;

	/** Default constructor. */
	public FolderMasterEntity() {
	}
	/**
	 * cons
	 * @param emailTemplateRequest
	 * @param subscriberManagementBean
	 */
	public FolderMasterEntity(EmailTemplateRequest emailTemplateRequest, SubscriberManagementBean subscriberManagementBean) {
		this.folderName = emailTemplateRequest.getName();
		this.dsmId = subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId();
		this.isActive = Boolean.TRUE;
		this.createdBy = subscriberManagementBean.getUserMasterEntity().getUserId();
		this.updatedBy = subscriberManagementBean.getUserMasterEntity().getUserId();
		this.createdDate = LocalDateTime.now();
		this.updatedDate = LocalDateTime.now();
	}
	/**
	 * cons
	 * @param folderId
	 */
	public FolderMasterEntity(Long folderId) {
		this.id = folderId;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getDsmId() {
		return dsmId;
	}

	public void setDsmId(Long dsmId) {
		this.dsmId = dsmId;
	}
}
