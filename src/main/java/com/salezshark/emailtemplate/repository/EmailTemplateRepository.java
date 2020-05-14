package com.salezshark.emailtemplate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salezshark.emailtemplate.entity.EmailTemplateDetailsEntity;

@Repository("emailTemplateRepository")
public interface EmailTemplateRepository extends 
										CrudRepository<EmailTemplateDetailsEntity, Long>, 
										JpaSpecificationExecutor<EmailTemplateDetailsEntity>,
										PagingAndSortingRepository<EmailTemplateDetailsEntity, Long>{
	/**
	 * findByTemplateIdAndIsActiveTrueAndDsmId
	 * @param templateId
	 * @param dataSourceMappingId
	 * @return
	 */
	EmailTemplateDetailsEntity findByTemplateIdAndIsActiveTrueAndDsmId(Long templateId, Long dataSourceMappingId);
	/**
	 * findByTemplateIdInAndIsActiveTrueAndDsmId
	 * @param templateIds
	 * @param dataSourceMappingId
	 * @return
	 */
	List<EmailTemplateDetailsEntity> findByTemplateIdInAndIsActiveTrueAndDsmId(List<Long> templateIds, Long dataSourceMappingId);
	/**
	 * 
	 * @param templateIds
	 * @param dataSourceMappingId
	 * @return
	 */
	@Modifying
	@Query("update EmailTemplateDetailsEntity et set et.isActive = false where et.templateId in (:templateIds) and et.dsmId = :dsmId")
	int deleteTemplatesById(@Param("templateIds") List<Long> templateIds, @Param("dsmId")Long dsmId);
	
//	
//	@Query("select et from EmailTemplateDetailsEntity et where et.isAvtive = true and et.")
//	List<EmailTemplateDetailsEntity> getAllTemplates(List<Long> templateIds, Long dataSourceMappingId);
}
