package com.salezshark.emailtemplate.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.salezshark.emailtemplate.constants.ServiceConstants;
import com.salezshark.emailtemplate.response.GenericResponse;
import com.salezshark.emailtemplate.service.IEmailTemplateUtilityService;

/**
 * @author nibhash
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/template")
public class UtilityController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilityController.class);
	private static final String classname = UtilityController.class.getName();

	@Autowired
	private IEmailTemplateUtilityService emailTemplateUtiltyService;
	
	/**
	 * getAllModules
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/modules")
	@ResponseBody
	public GenericResponse getAllModules(HttpServletRequest request) {
		LOGGER.info("Executing " + classname + ".getAllModules");
		GenericResponse genericResponse = null;
		try {
			genericResponse = emailTemplateUtiltyService.getAllModules(request);
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".getAllModules " + e);
			e.printStackTrace();
		} 
		LOGGER.info("Executed " + classname + ".getAllModules");
		return genericResponse;
	}
	/**
	 * getAllFilters
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/filters")
	@ResponseBody
	public GenericResponse getAllFilters(HttpServletRequest request) {
		LOGGER.info("Executing " + classname + ".getAllFilters");
		GenericResponse genericResponse = null;
		try {
			genericResponse = emailTemplateUtiltyService.getAllFilters(request);
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".getAllFilters " + e);
			e.printStackTrace();
		} 
		LOGGER.info("Executed " + classname + ".getAllFilters");
		return genericResponse;
	}
	/**
	 * getAllUsersByDsmIdOrName
	 * @param request
	 * @param name
	 * @return
	 */
	@GetMapping(value = "/users")
	@ResponseBody
	public GenericResponse getAllUsersByDsmIdOrName(HttpServletRequest request, @RequestParam(required = false) String name) {
		LOGGER.info("Executing " + classname + ".getAllUsers");
		GenericResponse genericResponse = null;
		try {
			genericResponse = emailTemplateUtiltyService.getAllUsersByDsmIdOrName(request, name);
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".getAllUsers " + e);
			e.printStackTrace();
		} 
		LOGGER.info("Executed " + classname + ".getAllUsers");
		return genericResponse;
	}
	
	@GetMapping(value = "/folders")
	@ResponseBody
	public GenericResponse getAllFolders(HttpServletRequest request) {
		LOGGER.info("Executing " + classname + ".getAllUsers");
		GenericResponse genericResponse = null;
		try {
			genericResponse = emailTemplateUtiltyService.getAllFolders(request);
		} catch (Exception e) {
			LOGGER.error(ServiceConstants.EXCEPTION_OCCURRED_IN + classname + ".getAllFolders " + e);
			e.printStackTrace();
		} 
		LOGGER.info("Executed " + classname + ".getAllFolders");
		return genericResponse;
	}
}
