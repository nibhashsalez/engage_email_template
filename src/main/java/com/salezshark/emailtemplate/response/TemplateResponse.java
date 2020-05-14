package com.salezshark.emailtemplate.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.salezshark.emailtemplate.bean.TemplateDetailBean;

/**
 * @author Nibhash
 * 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateResponse  extends GenericResponse implements Serializable{

	Object templateDetailBeans;
	Long totalTemplate;
	Long sharedCount;
	Long favCount;
	Long createdByMeCount;
	Long publicCount;

	/**
	 * cons
	 */
	public TemplateResponse() {

	}
	/**
	 * cons
	 * @param templateDetailBeans
	 * @param totalTemplateCount
	 * @param sharedCount
	 * @param favCount
	 * @param createdByMeCount
	 */
	public TemplateResponse(Object templateDetailBeans, Long totalTemplateCount, Long sharedCount, Long favCount, Long createdByMeCount, Long publicCount) {
		super.setData(templateDetailBeans);
		this.templateDetailBeans = templateDetailBeans;
		this.totalTemplate = totalTemplateCount;
		this.sharedCount = sharedCount;
		this.favCount = favCount;
		this.createdByMeCount = createdByMeCount;
		this.publicCount = publicCount;
	}

	public Object getTemplateDetailBeans() {
		return templateDetailBeans;
	}
	public void setTemplateDetailBeans(Object templateDetailBeans) {
		this.templateDetailBeans = templateDetailBeans;
	}
	public Long getSharedCount() {
		return sharedCount;
	}

	public void setSharedCount(Long sharedCount) {
		this.sharedCount = sharedCount;
	}

	public Long getFavCount() {
		return favCount;
	}

	public void setFavCount(Long favCount) {
		this.favCount = favCount;
	}

	public Long getCreatedByMeCount() {
		return createdByMeCount;
	}

	public void setCreatedByMeCount(Long createdByMeCount) {
		this.createdByMeCount = createdByMeCount;
	}

	public Long getTotalTemplate() {
		return totalTemplate;
	}

	public void setTotalTemplate(Long totalTemplate) {
		this.totalTemplate = totalTemplate;
	}
	public Long getPublicCount() {
		return publicCount;
	}
	public void setPublicCount(Long publicCount) {
		this.publicCount = publicCount;
	}
	@Override
	public String toString() {
		return "TemplateResponse [templateDetailBeans=" + templateDetailBeans + ", totalTemplate=" + totalTemplate
				+ ", sharedCount=" + sharedCount + ", favCount=" + favCount + ", createdByMeCount=" + createdByMeCount
				+ ", publicCount=" + publicCount + "]";
	}
	
}
