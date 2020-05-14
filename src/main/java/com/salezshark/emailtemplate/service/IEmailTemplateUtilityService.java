package com.salezshark.emailtemplate.service;

import javax.servlet.http.HttpServletRequest;

import com.salezshark.emailtemplate.exception.EmailTemplateException;
import com.salezshark.emailtemplate.response.GenericResponse;

public interface IEmailTemplateUtilityService {
	/**
	 * getAllModules
	 * @param request
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse getAllModules(HttpServletRequest request) throws EmailTemplateException;
	/**
	 * getAllFilters
	 * @param request
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse getAllFilters(HttpServletRequest request)throws EmailTemplateException;
	/**
	 * getAllModuleMapByDsmId
	 * @param request
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse getAllModuleMapByDsmId(HttpServletRequest request) throws EmailTemplateException;
	/**
	 * getAllFolders
	 * @param request
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse getAllFolders(HttpServletRequest request) throws EmailTemplateException;
	/**
	 * getAllUsersByDsmIdOrName : get either all users or by user name
	 * @param request
	 * @param name
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse getAllUsersByDsmIdOrName(HttpServletRequest request, String name) throws EmailTemplateException;

}
