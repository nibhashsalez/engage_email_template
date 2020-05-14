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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.salezshark.emailtemplate.bean.SubscriberManagementBean;
import com.salezshark.emailtemplate.utility.LocalDateTimeConverter;

@Entity
@Table(name = "template_folder")
public class TemplateFolderEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "template_id")
    private EmailTemplateDetailsEntity templateDetailsEntity;
	
	@ManyToOne
    @JoinColumn(name = "folder_id")
    private FolderMasterEntity folderMasterEntity;
	
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
	public TemplateFolderEntity() {
	}
	/**
	 * cons
	 * @param emailTemplateDetailsEntity
	 * @param folderEntity
	 * @param subscriberManagementBean
	 */
	public TemplateFolderEntity(EmailTemplateDetailsEntity templateDetailsEntity, FolderMasterEntity folderMasterEntity, SubscriberManagementBean subscriberManagementBean) {
		this.templateDetailsEntity = templateDetailsEntity;
		this.folderMasterEntity = folderMasterEntity;
		this.dsmId = subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId();
		this.createdBy = subscriberManagementBean.getUserMasterEntity().getUserId();
		this.updatedBy = subscriberManagementBean.getUserMasterEntity().getUserId();
		this.createdDate = LocalDateTime.now();
		this.updatedDate = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmailTemplateDetailsEntity getTemplateDetailsEntity() {
		return templateDetailsEntity;
	}

	public void setTemplateDetailsEntity(EmailTemplateDetailsEntity templateDetailsEntity) {
		this.templateDetailsEntity = templateDetailsEntity;
	}

	public FolderMasterEntity getFolderMasterEntity() {
		return folderMasterEntity;
	}
	public void setFolderMasterEntity(FolderMasterEntity folderMasterEntity) {
		this.folderMasterEntity = folderMasterEntity;
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
