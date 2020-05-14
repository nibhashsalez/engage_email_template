package com.salezshark.emailtemplate.master.service;

import com.salezshark.emailtemplate.master.entity.UserMasterEntity;


/**
 * @author nibhash
 *
 */
public interface IUserDetailService {
	UserMasterEntity findByUserIdAndDsmId(Long userId, Long dataSourceMappingId);
}
