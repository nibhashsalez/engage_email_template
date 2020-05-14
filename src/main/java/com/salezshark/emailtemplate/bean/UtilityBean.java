/**
 * 
 */
package com.salezshark.emailtemplate.bean;

/**
 * @author nibhash
 *
 */
public class UtilityBean {

	private Long id;
	private String value;
	private Long count;

	/**
	 * cons
	 */
	public UtilityBean() {}

	/**
	 * cons
	 * @param id
	 * @param value
	 */
	public UtilityBean(Long id, String value) {
		this.id = id;
		this.value = value;
	}
	/**
	 * cons
	 * @param id
	 * @param value
	 * @param count
	 */
	public UtilityBean(Long id, String value, Long count) {
		this.id = id;
		this.value = value;
		this.count = null != count ? count : 0l;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
}
