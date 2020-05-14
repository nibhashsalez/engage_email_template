package com.salezshark.emailtemplate.master.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salezshark.emailtemplate.master.entity.UserMasterEntity;
import com.salezshark.emailtemplate.master.repository.UserDetailsRepository;
import com.salezshark.emailtemplate.master.service.IUserDetailService;

/**
 * @author Nibhash
 *
 */
@Service(value = "userService")
public class UserDetailsServiceImpl implements IUserDetailService {
	
	@Autowired
    private UserDetailsRepository userDetailsRepository;

	@Override
	public UserMasterEntity findByUserIdAndDsmId(Long userId, Long dataSourceMappingId) {
		return userDetailsRepository.findByUserIdAndDsmId(userId, dataSourceMappingId);
	}
}