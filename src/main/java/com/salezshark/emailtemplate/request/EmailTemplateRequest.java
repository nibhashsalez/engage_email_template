package com.salezshark.emailtemplate.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class EmailTemplateRequest {

	private Long templateId;
	private Long moduleId;
	private String templateName;
	private String subject;
	private String data;
	private String attachments;
	private String filter;
	private String searchText;
	private int page;
	private int size;
	private String usersIdList;
	private String templateIdList;
	private List<Long> templateIds;
	private List<Long> sharedUserIds;
	private String name;
	private Long folderId;
	private Boolean unfavorite = false;

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Long> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(List<Long> templateIds) {
		this.templateIds = templateIds;
	}

	public List<Long> getSharedUserIds() {
		return sharedUserIds;
	}

	public void setSharedUserIds(List<Long> sharedUserIds) {
		this.sharedUserIds = sharedUserIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public Boolean getUnfavorite() {
		return unfavorite;
	}

	public void setUnfavorite(Boolean unfavorite) {
		this.unfavorite = unfavorite;
	}

	public String getUsersIdList() {
		return usersIdList;
	}

	public void setUsersIdList(String usersIdList) {
		this.usersIdList = usersIdList;
	}

	public String getTemplateIdList() {
		return templateIdList;
	}

	public void setTemplateIdList(String templateIdList) {
		this.templateIdList = templateIdList;
	}

}
