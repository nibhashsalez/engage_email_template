package com.salezshark.emailtemplate.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.salezshark.emailtemplate.master.entity.UserMasterEntity;


/**
 * @author nibhash
 *
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserBean {

    private Long userId;
    private String userName;
    private String email;
    private Long organizationId;
//    private String organizatonName;
    private List<Long> favTemplateIds = new ArrayList<Long>();
    
    /**
     * cons
     */
    public UserBean() {}
    /**
     * cons
     * @param userMasterEntity
     * @param userFavTemplateIds
     */
	public UserBean(UserMasterEntity userMasterEntity, List<Long> userFavTemplateIds) {
		this.userId = userMasterEntity.getUserId();
		this.userName = StringUtils.isNoneBlank(userMasterEntity.getUserFullName()) ? userMasterEntity.getUserFullName() : null;
		this.email = StringUtils.isNoneBlank(userMasterEntity.getEmailId()) ? userMasterEntity.getEmailId() : null;
		this.organizationId = userMasterEntity.getOrganizationId();
		this.favTemplateIds = !CollectionUtils.isEmpty(userFavTemplateIds) ? userFavTemplateIds : null;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	/*
	 * public String getOrganizatonName() { return organizatonName; }
	 * 
	 * public void setOrganizatonName(String organizatonName) { this.organizatonName
	 * = organizatonName; }
	 */

	public List<Long> getFavTemplateIds() {
		return favTemplateIds;
	}

	public void setFavTemplateIds(List<Long> favTemplateIds) {
		this.favTemplateIds = favTemplateIds;
	}
}
