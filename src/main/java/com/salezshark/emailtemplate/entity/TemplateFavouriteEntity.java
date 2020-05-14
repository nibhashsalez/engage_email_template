// Generated with g9.

package com.salezshark.emailtemplate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.salezshark.emailtemplate.bean.SubscriberManagementBean;
import com.salezshark.emailtemplate.request.EmailTemplateRequest;

@Entity
@Table(name = "template_favourite")
public class TemplateFavouriteEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "template_id")
	private EmailTemplateDetailsEntity templateDetailsEntity;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "dsm_id")
	private Long dsmId;

	/** Default constructor. */
	public TemplateFavouriteEntity() {
	}
	/**
	 * cons
	 * @param emailTemplateRequest
	 * @param subscriberManagementBean
	 */
	public TemplateFavouriteEntity(EmailTemplateRequest emailTemplateRequest, SubscriberManagementBean subscriberManagementBean) {
		this.templateDetailsEntity = new EmailTemplateDetailsEntity(emailTemplateRequest.getTemplateId());
		this.userId = subscriberManagementBean.getUserMasterEntity().getUserId();
		this.dsmId = subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public Long getTemplateId() {
//		return templateId;
//	}
//
//	public void setTemplateId(Long templateId) {
//		this.templateId = templateId;
//	}

	public Long getUserId() {
		return userId;
	}

	public EmailTemplateDetailsEntity getTemplateDetailsEntity() {
		return templateDetailsEntity;
	}
	public void setTemplateDetailsEntity(EmailTemplateDetailsEntity templateDetailsEntity) {
		this.templateDetailsEntity = templateDetailsEntity;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getDsmId() {
		return dsmId;
	}
	public void setDsmId(Long dsmId) {
		this.dsmId = dsmId;
	}
}
