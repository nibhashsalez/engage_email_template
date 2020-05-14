package com.salezshark.emailtemplate.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salezshark.emailtemplate.bean.TemplateDetailBean;
import com.salezshark.emailtemplate.constants.ServiceConstants;
import com.salezshark.emailtemplate.exception.EmailTemplateException;
import com.salezshark.emailtemplate.request.EmailTemplateRequest;
import com.salezshark.emailtemplate.response.GenericResponse;
import com.salezshark.emailtemplate.service.IEmailTemplateService;
import com.salezshark.emailtemplate.service.IEmailTemplateUtilityService;

/**
 * @author nibhash
 *
 */
@CrossOrigin
@Controller
@RequestMapping("/template")
public class EmailTemplateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailTemplateController.class);
	private static final String classname = EmailTemplateController.class.getName();

	@Autowired
	private IEmailTemplateService emailTemplateService;

	@Autowired
	private IEmailTemplateUtilityService emailTemplateUtiltyService;

	/**
	 * addTemplate
	 * 
	 * @param emailTemplateRequest
	 * @return
	 */
	@PostMapping(value = "/add")
	@ResponseBody
	public GenericResponse addTemplate(@RequestBody EmailTemplateRequest emailTemplateRequest) {
		LOGGER.info("Executing " + classname + ".addTemplate");
		GenericResponse genericResponse = null;
		if (StringUtils.isEmpty(emailTemplateRequest.getTemplateName())) {
			return new GenericResponse(null,
					ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": template name is blank!",
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
		}
		if (StringUtils.isEmpty(emailTemplateRequest.getSubject())) {
			return new GenericResponse(null,
					ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": template subject is blank !",
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
		}
		if (null == emailTemplateRequest.getModuleId()) {
			return new GenericResponse(null,
					ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": module id is blank!",
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
		}
		if (StringUtils.isEmpty(emailTemplateRequest.getData())) {
			return new GenericResponse(null,
					ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": template data is blank!",
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
		}
		if (StringUtils.isEmpty(emailTemplateRequest.getFilter())) {
			emailTemplateRequest.setFilter(ServiceConstants.EMAIL_TEMPLATE_FILTER_TYPE_PRIVATE);
		}
		try {
			genericResponse = emailTemplateService.saveTemplate(emailTemplateRequest);
		} catch (Exception exception) {
			exception.printStackTrace();
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".addTemplate " + exception);
		}
		LOGGER.info("Executed " + classname + ".addTemplate");
		return genericResponse;
	}

	/**
	 * favouriteEmailTemplate : favourite/unfavourite template
	 * 
	 * @param emailTemplateRequest
	 * @return
	 */
	@PostMapping(value = "/favourite")
	@ResponseBody
	public GenericResponse favouriteEmailTemplate(@RequestBody EmailTemplateRequest emailTemplateRequest) {
		LOGGER.info("Executing " + classname + ".favouriteTemplates");
		GenericResponse genericResponse = null;
		try {
			if (null == emailTemplateRequest.getTemplateId()) {
				return new GenericResponse(null,
						ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": template id is blank!",
						ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
			}
			genericResponse = emailTemplateService.favouriteTemplate(emailTemplateRequest);
		} catch (Exception exception) {
			exception.printStackTrace();
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".favouriteTemplate " + exception);
		}
		LOGGER.info("Executed " + classname + ".favouriteTemplate");
		return genericResponse;
	}

	/**
	 * shareEmailTemplate
	 * 
	 * @param emailTemplateRequest
	 * @return
	 */
	@PostMapping(value = "/share")
	@ResponseBody
	public GenericResponse shareEmailTemplate(@RequestBody EmailTemplateRequest emailTemplateRequest) {
		LOGGER.info("Executing " + classname + ".shareEmailTemplate");
		GenericResponse genericResponse = null;

		if (StringUtils.isEmpty(emailTemplateRequest.getUsersIdList())) {
			return new GenericResponse(null,
					ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": shared user list is blank!",
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
		}

		if (StringUtils.isEmpty(emailTemplateRequest.getTemplateIdList())) {
			return new GenericResponse(null,
					ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": template ids are blank!",
					ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
		}

		try {
			genericResponse = emailTemplateService.shareEmailTemplate(emailTemplateRequest);
		} catch (Exception exception) {
			exception.printStackTrace();
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".shareEmailTemplate " + exception);
		}
		LOGGER.info("Executed " + classname + ".shareEmailTemplate");
		return genericResponse;
	}

	/**
	 * getAllEmailTemplates : private/public/shared with all filters and also folder
	 * template
	 * 
	 * @param emailTemplateRequest
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/all")
	@ResponseBody
	public GenericResponse getAllEmailTemplates(@RequestBody EmailTemplateRequest emailTemplateRequest,
			HttpServletRequest request) {
		LOGGER.info("Executing " + classname + ".getAllEmailTemplates");
		GenericResponse genericResponse = null;
		try {
			genericResponse = emailTemplateService.getAllEmailTemplates(emailTemplateRequest, request);
		} catch (Exception exception) {
			exception.printStackTrace();
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".getAllFilters " + exception);
		}
		LOGGER.info("Executed " + classname + ".getAllEmailTemplates");
		return genericResponse;
	}

	/**
	 * createFolder
	 * 
	 * @param emailTemplateRequest
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/create/folder")
	@ResponseBody
	public GenericResponse createFolder(@RequestBody EmailTemplateRequest emailTemplateRequest,
			HttpServletRequest request) {
		LOGGER.info("Executing " + classname + ".createFolder");
		GenericResponse genericResponse = null;
		try {
			if (StringUtils.isEmpty(emailTemplateRequest.getName())) {
				return new GenericResponse(null,
						ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": folder name is blank!",
						ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
			}
			genericResponse = emailTemplateService.createFolder(emailTemplateRequest, request);
		} catch (Exception exception) {
			exception.printStackTrace();
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".createFolder " + exception);
		}
		LOGGER.info("Executed " + classname + ".createFolder");
		return genericResponse;
	}

	/**
	 * editFolder
	 * 
	 * @param emailTemplateRequest
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/folder/edit")
	@ResponseBody
	public GenericResponse editFolder(@RequestBody EmailTemplateRequest emailTemplateRequest,
			HttpServletRequest request) {
		LOGGER.info("Executing " + classname + ".editFolder");
		GenericResponse genericResponse = null;
		try {
			if (null == emailTemplateRequest.getFolderId()) {
				return new GenericResponse(null,
						ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": folder id is blank!",
						ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
			}
			if (StringUtils.isEmpty(emailTemplateRequest.getName())) {
				return new GenericResponse(null,
						ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": folder name is blank!",
						ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
			}
			genericResponse = emailTemplateService.editFolder(emailTemplateRequest, request);
		} catch (Exception exception) {
			exception.printStackTrace();
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".moveToFolder " + exception);
		}
		LOGGER.info("Executed " + classname + ".moveToFolder");
		return genericResponse;
	}

	/**
	 * deleteFolder
	 * 
	 * @param emailTemplateRequest
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/folder/delete")
	@ResponseBody
	public GenericResponse deleteFolder(@RequestBody EmailTemplateRequest emailTemplateRequest,
			HttpServletRequest request) {
		LOGGER.info("Executing " + classname + ".deleteFolder");
		GenericResponse genericResponse = null;
		try {
			if (null == emailTemplateRequest.getFolderId()) {
				return new GenericResponse(null,
						ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": folder id is blank!",
						ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
			}
			genericResponse = emailTemplateService.deleteFolder(emailTemplateRequest, request);
		} catch (Exception exception) {
			exception.printStackTrace();
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".deleteFolder " + exception);
		}
		LOGGER.info("Executed " + classname + ".moveToFolder");
		return genericResponse;
	}

	/**
	 * moveToFolder : move existing template(s) to folder
	 * 
	 * @param emailTemplateRequest
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/moveTo/folder")
	@ResponseBody
	public GenericResponse moveToFolder(@RequestBody EmailTemplateRequest emailTemplateRequest,
			HttpServletRequest request) {
		LOGGER.info("Executing " + classname + ".moveToFolder");
		GenericResponse genericResponse = null;
		try {
			if (StringUtils.isEmpty(emailTemplateRequest.getTemplateIdList())) {
				return new GenericResponse(null,
						ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": template ids are blank!",
						ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
			}
			if (null == emailTemplateRequest.getFolderId()) {
				return new GenericResponse(null,
						ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": folder id is blank!",
						ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
			}
			genericResponse = emailTemplateService.moveToFolder(emailTemplateRequest, request);
		} catch (Exception exception) {
			exception.printStackTrace();
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".moveToFolder " + exception);
		}
		LOGGER.info("Executed " + classname + ".moveToFolder");
		return genericResponse;
	}

	/**
	 * getTemplateDetailById
	 * 
	 * @param emailTemplateRequest
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/detail")
	@ResponseBody
	public GenericResponse getTemplateDetailById(@RequestBody EmailTemplateRequest emailTemplateRequest,
			HttpServletRequest request) {
		LOGGER.info("Executing " + classname + ".getTemplateDetailById");
		GenericResponse genericResponse = null;
		try {
			if (null == emailTemplateRequest.getTemplateId()) {
				return new GenericResponse(null,
						ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": template id is blank!",
						ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
			}
			genericResponse = emailTemplateService.getTemplateDetailById(emailTemplateRequest.getTemplateId(), request);
		} catch (Exception exception) {
			exception.printStackTrace();
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".getTemplateDetailById " + exception);
		}
		LOGGER.info("Executed " + classname + ".getAllEmailTemplates");
		return genericResponse;
	}

	/**
	 * deleteTemplate
	 * 
	 * @param emailTemplateRequest
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/delete")
	@ResponseBody
	public GenericResponse deleteTemplate(@RequestBody EmailTemplateRequest emailTemplateRequest,
			HttpServletRequest request) {
		LOGGER.info("Executing " + classname + ".deleteTemplate");
		GenericResponse genericResponse = null;
		try {
			if (StringUtils.isBlank(emailTemplateRequest.getTemplateIdList())) {
				return new GenericResponse(null,
						ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": template id is blank!",
						ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
			}
			genericResponse = emailTemplateService.deleteTemplate(emailTemplateRequest, request);
		} catch (Exception exception) {
			exception.printStackTrace();
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".deleteTemplate " + exception);
		}
		LOGGER.info("Executed " + classname + ".deleteTemplate");
		return genericResponse;
	}

	@PostMapping(value = "/edit")
	@ResponseBody
	public GenericResponse editTemplate(@RequestBody EmailTemplateRequest emailTemplateRequest,
			HttpServletRequest request) {
		LOGGER.info("Executing " + classname + ".editTemplate");
		GenericResponse genericResponse = null;
		try {
			if (null == emailTemplateRequest.getTemplateId()) {
				return new GenericResponse(null,
						ServiceConstants.RESPONSE_MESSAGE_VALIDATION_FAILED + ": template id is blank!",
						ServiceConstants.RESPONSE_STATUS_FAILURE, ServiceConstants.RESPONSE_CODE_VALIDATION_FAILED);
			}
			genericResponse = emailTemplateService.editTemplate(emailTemplateRequest, request);
		} catch (Exception exception) {
			exception.printStackTrace();
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".editTemplate " + exception);
		}
		LOGGER.info("Executed " + classname + ".editTemplate");
		return genericResponse;
	}

	@RequestMapping("/viewTemplateList.html")
	public String welcome(Map<String, Object> map, HttpServletRequest request,
			EmailTemplateRequest emailTemplateRequest) {
		GenericResponse moduleResponse = null;
		GenericResponse filterResponse = null;
		GenericResponse userResponse = null;
		GenericResponse templateResponse = null;
		GenericResponse folderResponse = null;
		try {
			moduleResponse = emailTemplateUtiltyService.getAllModules(request);
			filterResponse = emailTemplateUtiltyService.getAllFilters(request);
			userResponse = emailTemplateUtiltyService.getAllUsersByDsmIdOrName(request, "");
			templateResponse = emailTemplateService.getAllEmailTemplates(emailTemplateRequest, request);
			folderResponse = emailTemplateUtiltyService.getAllFolders(request);
			map.put("moduleList", moduleResponse);
			map.put("filterList", filterResponse);
			map.put("userList", userResponse);
			map.put("templateList", templateResponse);
			map.put("folderList", folderResponse);

		} catch (EmailTemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "viewTemplateList";
	}

	@RequestMapping("/addTemplate.html")
	public String addTemplate(Map<String, Object> map, HttpServletRequest request,
			EmailTemplateRequest emailTemplateRequest) {
		// TODO REMOVE BEAN AND GET THROUGH URL
		GenericResponse moduleResponse = null;
		GenericResponse folderResponse = null;
		try {
			moduleResponse = emailTemplateUtiltyService.getAllModules(request);
			folderResponse = emailTemplateUtiltyService.getAllFolders(request);
			TemplateDetailBean templateDetailBean = new TemplateDetailBean();
			if (StringUtils.isNotEmpty(request.getParameter("templateName"))) {
				templateDetailBean.setName(request.getParameter("templateName"));
			}
			if (StringUtils.isNotEmpty(request.getParameter("templateSubject"))) {
				templateDetailBean.setSubject(request.getParameter("templateSubject"));
			}
			if (StringUtils.isNotEmpty(request.getParameter("moduleId"))) {
				templateDetailBean.setModuleId(Long.parseLong(request.getParameter("moduleId")));
			}
			map.put("moduleList", moduleResponse);
			map.put("templateDetail", templateDetailBean);
			map.put("folderList", folderResponse);

		} catch (EmailTemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "addTemplate";
	}

	@RequestMapping("/editTemplate.html")
	public String editTemplate(@RequestParam(value = "templateId") Long templateId, Map<String, Object> map,
			HttpServletRequest request) {
		GenericResponse moduleResponse = null;
		GenericResponse templateResponse = null;
		GenericResponse folderResponse = null;
		try {
			if (templateId != null) {
				moduleResponse = emailTemplateUtiltyService.getAllModules(request);
				templateResponse = emailTemplateService.getTemplateDetailById(templateId, request);
				folderResponse = emailTemplateUtiltyService.getAllFolders(request);
				map.put("templateDetail", templateResponse);
				map.put("moduleList", moduleResponse);
				map.put("folderList", folderResponse);
			}

		} catch (EmailTemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "editTemplate";
	}

	@RequestMapping("/templateDetails.html")
	public String getTemplateDetails(@RequestParam(value = "templateId") Long templateId, Map<String, Object> map,
			HttpServletRequest request) {
		GenericResponse templateResponse = null;
		GenericResponse userResponse = null;
		try {
			if (templateId != null) {
				userResponse = emailTemplateUtiltyService.getAllUsersByDsmIdOrName(request, "");
				templateResponse = emailTemplateService.getTemplateDetailById(templateId, request);

				map.put("templateDetail", templateResponse);
				map.put("userList", userResponse);
			}

		} catch (EmailTemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "viewTemplateDetails";
	}
}
