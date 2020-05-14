// Generated with g9.

package com.salezshark.emailtemplate.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "template_filters")
public class TemplateFiltersEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "template_filter_id")
	private Long templateFilterId;
	
	@Column(name = "template_filter_name")
	private String templateFilterName;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "updated_by")
	private Long updatedBy;
	
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	/** Default constructor. */
	public TemplateFiltersEntity() {
		super();
	}

	public Long getTemplateFilterId() {
		return templateFilterId;
	}

	public void setTemplateFilterId(Long templateFilterId) {
		this.templateFilterId = templateFilterId;
	}

	public String getTemplateFilterName() {
		return templateFilterName;
	}

	public void setTemplateFilterName(String templateFilterName) {
		this.templateFilterName = templateFilterName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
}
