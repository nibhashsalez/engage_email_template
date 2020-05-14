package com.salezshark.emailtemplate.service;

import javax.servlet.http.HttpServletRequest;

import com.salezshark.emailtemplate.exception.EmailTemplateException;
import com.salezshark.emailtemplate.request.EmailTemplateRequest;
import com.salezshark.emailtemplate.response.GenericResponse;

public interface IEmailTemplateService {

	/**
	 * saveTemplate
	 * 
	 * @param emailTemplateRequest
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse saveTemplate(EmailTemplateRequest emailTemplateRequest) throws EmailTemplateException;

	/**
	 * getAllEmailTemplates
	 * 
	 * @param emailTemplateRequest
	 * @return
	 */
	GenericResponse getAllEmailTemplates(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request)
			throws EmailTemplateException;

	/**
	 * favouriteTemplate
	 * 
	 * @param emailTemplateRequest
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse favouriteTemplate(EmailTemplateRequest emailTemplateRequest) throws EmailTemplateException;

	/**
	 * shareEmailTemplate
	 * 
	 * @param emailTemplateRequest
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse shareEmailTemplate(EmailTemplateRequest emailTemplateRequest) throws EmailTemplateException;

	/**
	 * createFolder
	 * 
	 * @param emailTemplateRequest
	 * @param request
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse createFolder(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request)
			throws EmailTemplateException;

	/**
	 * moveToFolder
	 * 
	 * @param emailTemplateRequest
	 * @param request
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse moveToFolder(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request)
			throws EmailTemplateException;

	/**
	 * getTemplateDetailById
	 * 
	 * @param templateId
	 * @param request
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse getTemplateDetailById(Long templateId, HttpServletRequest request) throws EmailTemplateException;

	/**
	 * 
	 * @param emailTemplateRequest
	 * @param request
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse deleteTemplate(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request)
			throws EmailTemplateException;

	/**
	 * getAllTemplatesByFolder
	 * 
	 * @param emailTemplateRequest
	 * @param request
	 * @return
	 * @throws EmailTemplateException
	 */
//	List<TemplateDetailBean> getAllTemplatesByFolder(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request,int size, int page) throws EmailTemplateException;
	/**
	 * editTemplate
	 * 
	 * @param emailTemplateRequest
	 * @param request
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse editTemplate(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request)
			throws EmailTemplateException;

	/**
	 * editFolder
	 * 
	 * @param emailTemplateRequest
	 * @param request
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse editFolder(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request)
			throws EmailTemplateException;

	/**
	 * deleteFolder
	 * 
	 * @param emailTemplateRequest
	 * @param request
	 * @return
	 * @throws EmailTemplateException
	 */
	GenericResponse deleteFolder(EmailTemplateRequest emailTemplateRequest, HttpServletRequest request)
			throws EmailTemplateException;
}
