package com.salezshark.emailtemplate.master.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.salezshark.emailtemplate.constants.ServiceConstants;

/**
 * @author nibhash
 *
 */
@Entity
@Table(name = "master_data")
public class MasterDataEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "master_id")
	private Long masterId;
	
	@Column(name = "master_data_id")
	private Long masterDataId;

	@Column(name = "data_value")
	private String dataValue;

	@Column(name = "data_type")
	private String dataType;

	@Column(name = "master_parent_id")
	private Long masterParentId;
	
	@Column(name = "order_by")
	private Long orderBy;
	
	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public Long getMasterDataId() {
		return masterDataId;
	}

	public void setMasterDataId(Long masterDataId) {
		this.masterDataId = masterDataId;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Long getMasterParentId() {
		return masterParentId;
	}

	public void setMasterParentId(Long masterParentId) {
		this.masterParentId = masterParentId;
	}

	public Long getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Long orderBy) {
		this.orderBy = orderBy;
	}
	public String getModifiedDataValue() {
		/*
		 * if(StringUtils.isNotBlank(this.dataValue) &&
		 * this.dataValue.equalsIgnoreCase(ServiceConstants.DATA_VALUE_OPPORTUNITY)) {
		 * return ServiceConstants.DATA_VALUE_TYPE_OPPORTUNITY; }
		 */
		return this.dataValue;
	}
}
