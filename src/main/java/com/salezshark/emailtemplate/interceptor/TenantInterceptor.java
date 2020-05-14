package com.salezshark.emailtemplate.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.salezshark.emailtemplate.bean.SubscriberManagementBean;
import com.salezshark.emailtemplate.config.multitenancy.TenantContext;
import com.salezshark.emailtemplate.master.service.IUserDetailService;
import com.salezshark.emailtemplate.utility.DESedeEncryption;

/**
 * @author nibhash
 *
 */
@Component
public class TenantInterceptor extends HandlerInterceptorAdapter {

	private final static Logger LOGGER = LoggerFactory.getLogger(TenantInterceptor.class);

	/*
	 * @Autowired private IDataSourceMappingService dataSourceMappingService;
	 */
	@Autowired
	private SubscriberManagementBean subscriberManagementBean;

	@Autowired
	private IUserDetailService userDetailService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			if (request.getRequestURI().endsWith("/email")){
				return true;
			}
			if(StringUtils.isEmpty(request.getHeader("dsmID"))) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.getWriter().write("{\"error\": \"please provide valid dsmID \"}");
				response.getWriter().flush();
				return false;
			}
			String decryptedDsmId = DESedeEncryption.decipher(DESedeEncryption.secretKey, request.getHeader("dsmID"));/*AESHelper.decrypt(request.getHeader("dsmID"));*/
			String dsmIdAndUserId[] = decryptedDsmId.split("_");
			Long dataSourceMappingId = Long.parseLong(dsmIdAndUserId[0]);
			/*
			 * String tenantSchema =
			 * dataSourceMappingService.getDataSourceMappingById(dataSourceMappingId);
			 * LOGGER.debug("Set TenantContext: {}", tenantSchema); if(!
			 * TenantContext.getCurrentTenant().equalsIgnoreCase(tenantSchema)) {
			 * TenantContext.setCurrentTenant(tenantSchema); }
			 */
			Long userId = Long.parseLong(dsmIdAndUserId[1]);
			subscriberManagementBean.setDsmID(request.getHeader("dsmID"));
			subscriberManagementBean.setUserMasterEntity(userDetailService.findByUserIdAndDsmId(userId, dataSourceMappingId));
			return true;
		}
		catch (Exception e) {
			LOGGER.error("error :", e.getMessage());
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		LOGGER.debug("Clear TenantContext: {}", TenantContext.getCurrentTenant());
		TenantContext.clear();
	}
}
