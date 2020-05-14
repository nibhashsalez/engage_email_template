package com.salezshark.emailtemplate.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.salezshark.emailtemplate.utility.LocalDateTimeConverter;

@Entity
@Table(name = "email_template_details")
public class EmailTemplateDetailsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "template_id")
	private Long templateId;
	
	@Column(name = "template_name")
	private String templateName;
	
	@Column(name = "template_subject" )
	private String templateSubject;
	
	@Lob
	@Column(name = "template_data", columnDefinition = "blob")
	private String templateData;
	
	@Column(name = "template_attachments")
	private String templateAttachments;
	
	/*
	 * @Column(name = "template_filter_id") 
	 * private Long templateFilterId;
	 */
	
	@Column(name = "template_module_id")
	private Long templateModuleId;
	
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
	
	@Column(name = "template_type")
	private String templateType;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "templateDetailsEntity")
    private List<TemplateUserActivityEntity> templateUserActivities;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "templateDetailsEntity")
    private List<TemplateFolderEntity> templateFolderEntities;

	/**
	 * cons
	 */
	public EmailTemplateDetailsEntity() {}
	/**
	 * cons
	 * @param templateId
	 */
	public EmailTemplateDetailsEntity(Long templateId) {
		this.templateId = templateId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateSubject() {
		return templateSubject;
	}

	public void setTemplateSubject(String templateSubject) {
		this.templateSubject = templateSubject;
	}

	public String getTemplateData() {
		return templateData;
	}

	public void setTemplateData(String templateData) {
		this.templateData = templateData;
	}

	public String getTemplateAttachments() {
		return templateAttachments;
	}

	public void setTemplateAttachments(String templateAttachments) {
		this.templateAttachments = templateAttachments;
	}

	/*
	 * public Long getTemplateFilterId() { return templateFilterId; }
	 * 
	 * public void setTemplateFilterId(Long templateFilterId) {
	 * this.templateFilterId = templateFilterId; }
	 */

	public Long getTemplateModuleId() {
		return templateModuleId;
	}

	public void setTemplateModuleId(Long templateModuleId) {
		this.templateModuleId = templateModuleId;
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

	public List<TemplateUserActivityEntity> getTemplateUserActivities() {
		return templateUserActivities;
	}

	public void setTemplateUserActivities(List<TemplateUserActivityEntity> templateUserActivities) {
		this.templateUserActivities = templateUserActivities;
	}

	public Long getDsmId() {
		return dsmId;
	}

	public void setDsmId(Long dsmId) {
		this.dsmId = dsmId;
	}
	public List<TemplateFolderEntity> getTemplateFolderEntities() {
		return templateFolderEntities;
	}
	public void setTemplateFolderEntities(List<TemplateFolderEntity> templateFolderEntities) {
		this.templateFolderEntities = templateFolderEntities;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
}
