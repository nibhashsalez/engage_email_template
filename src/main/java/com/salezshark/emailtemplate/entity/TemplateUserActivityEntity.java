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
@Table(name = "template_user_activity")
public class TemplateUserActivityEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "template_filter")
	private String templateFilter;
	
	@Column(name = "shared_with")
	private Long sharedWithUserId;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "dsm_id")
	private Long dsmId;
	
	@Column(name = "created_date")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime createdDate;
	
	@ManyToOne
    @JoinColumn(name = "template_id")
    private EmailTemplateDetailsEntity templateDetailsEntity;

	/** Default constructor. */
	public TemplateUserActivityEntity() {
	}

	/**
	 * cons
	 * @param templateFilter
	 * @param sharedWithUserId
	 * @param createdBy
	 * @param templateDetailsEntity
	 */
	public TemplateUserActivityEntity(String templateFilter, Long sharedWithUserId, SubscriberManagementBean subscriberManagementBean, EmailTemplateDetailsEntity templateDetailsEntity) {
		this.templateFilter = templateFilter;
		this.sharedWithUserId = sharedWithUserId;
		this.createdBy = subscriberManagementBean.getUserMasterEntity().getUserId();
		this.templateDetailsEntity = templateDetailsEntity;
		this.createdDate = LocalDateTime.now();
		this.dsmId = subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId();
	}
	/**
	 * cons
	 * @param templateFilter
	 * @param createdBy
	 * @param templateDetailsEntity
	 */
	public TemplateUserActivityEntity(String templateFilter, SubscriberManagementBean subscriberManagementBean, EmailTemplateDetailsEntity templateDetailsEntity) {
		this.templateFilter = templateFilter;
		this.createdBy = subscriberManagementBean.getUserMasterEntity().getUserId();
		this.templateDetailsEntity = templateDetailsEntity;
		this.createdDate = LocalDateTime.now();
		this.dsmId = subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTemplateFilter() {
		return templateFilter;
	}

	public void setTemplateFilter(String templateFilter) {
		this.templateFilter = templateFilter;
	}

	public Long getSharedWithUserId() {
		return sharedWithUserId;
	}

	public void setSharedWithUserId(Long sharedWithUserId) {
		this.sharedWithUserId = sharedWithUserId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public EmailTemplateDetailsEntity getTemplateDetailsEntity() {
		return templateDetailsEntity;
	}

	public void setTemplateDetailsEntity(EmailTemplateDetailsEntity templateDetailsEntity) {
		this.templateDetailsEntity = templateDetailsEntity;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Long getDsmId() {
		return dsmId;
	}

	public void setDsmId(Long dsmId) {
		this.dsmId = dsmId;
	}
}
