package com.salezshark.emailtemplate.exception;

public class EmailTemplateException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private Throwable coreException;
	
	public EmailTemplateException(Exception exception, String msg) {
		super(msg);
		coreException = exception;
	}

	/**
	 * @return the coreException
	 */
	public Throwable getCoreException() {
		return coreException;
	}

	/**
	 * @param coreException the coreException to set
	 */
	public void setCoreException(Throwable coreException) {
		this.coreException = coreException;
	}

	public EmailTemplateException(String msg) {
		super(msg);
	}
}
