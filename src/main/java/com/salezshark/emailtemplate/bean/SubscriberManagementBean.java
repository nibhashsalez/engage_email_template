package com.salezshark.emailtemplate.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.salezshark.emailtemplate.master.entity.UserMasterEntity;


/**
 * @author nibhash
 *
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SubscriberManagementBean {

    private String dsmID;
    private UserMasterEntity userMasterEntity;

    public String getDsmID() {
		return dsmID;
	}

	public void setDsmID(String dsmID) {
		this.dsmID = dsmID;
	}

	public UserMasterEntity getUserMasterEntity() {
		return userMasterEntity;
	}

	public void setUserMasterEntity(UserMasterEntity userMasterEntity) {
		this.userMasterEntity = userMasterEntity;
	}

	public Long getLoginUserId() {
        return this.userMasterEntity.getUserId();
    }
}
