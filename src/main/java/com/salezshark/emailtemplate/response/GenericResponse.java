/**
 * 
 */
package com.salezshark.emailtemplate.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.salezshark.emailtemplate.bean.TemplateDetailBean;

/**
 * @author smoyeen
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericResponse  implements Serializable {

	private boolean success;
	private String message;
	private int statusCode;
	private Object data;
	private boolean isNext;

	public GenericResponse() {
	}

	/**
	 * cons
	 * 
	 * @param data
	 * @param message
	 * @param status
	 * @param statusCode
	 */
	public GenericResponse(Object data, String message, Boolean status, int statusCode) {
		this.data = data;
		this.success = status;
		this.statusCode = statusCode;
		this.message = message;
	}

	public GenericResponse(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
	}

	public GenericResponse(List<TemplateDetailBean> templateDetailBeans, Boolean isNext) {
		this.data=templateDetailBeans;
		this.isNext=isNext;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isNext() {
		return isNext;
	}

	public void setNext(boolean isNext) {
		this.isNext = isNext;
	}

}
