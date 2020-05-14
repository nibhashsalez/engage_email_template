
  package com.salezshark.emailtemplate.repository;
  
  import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import
  org.springframework.stereotype.Repository;

import com.salezshark.emailtemplate.entity.EmailTemplateDetailsEntity;
import com.salezshark.emailtemplate.entity.TemplateUserActivityEntity;
  
  @Repository("templateUserActivityRepository") 
  public interface TemplateUserActivityRepository extends
  										CrudRepository<TemplateUserActivityEntity, Long>,
  										JpaSpecificationExecutor<TemplateUserActivityEntity>,
  										PagingAndSortingRepository<TemplateUserActivityEntity, Long> {
	  /**
	   * findAllBySharedWithUserIdAndTemplateDetailsEntityAndDsmId
	   * @param sharedWithId
	   * @param templateDetailsEntity
	   * @param dsmId
	   * @return
	   */
	  List<TemplateUserActivityEntity> findAllBySharedWithUserIdAndTemplateDetailsEntityAndDsmId(Long sharedWithId, EmailTemplateDetailsEntity templateDetailsEntity, Long dsmId);
	 /**
	  * getAllSahredUsers
	  * @param templateId
	  * @param sharedWithUserId
	  * @param dsmId
	  * @return
	  */
//	  @Query("select ua from TemplateUserActivityEntity ua where ua.templateDetailsEntity.templateId = :templateId"
//	 		+ " and ua.sharedWithUserId = :sharedWithUserId and ua.templateFilter = 'SH' and ua.dsmId = :dsmId")
//	  List<TemplateUserActivityEntity> getAllSahredUsers(@Param("templateId") Long templateId,  @Param("sharedWithUserId") Long sharedWithUserId, @Param("dsmId") Long dsmId);
	  
	  @Query("select ua from TemplateUserActivityEntity ua where ua.templateDetailsEntity.templateId = :templateId and ua.templateFilter = 'SH' and ua.dsmId = :dsmId")
		  List<TemplateUserActivityEntity> getAllSahredUsers(@Param("templateId") Long templateId, @Param("dsmId") Long dsmId);
	  /**
	   * deleteUserAvtivityTemplate
	   * @param templateIds
	   * @param dsmId
	   * @param loginUserId
	   */
	  @Modifying
	  @Query("delete from TemplateUserActivityEntity ua where ua.dsmId = :dsmId and ua.templateDetailsEntity.templateId in (:templateIds)")
	  int deleteUserAvtivityTemplate(@Param("templateIds") List<Long> templateIds, @Param("dsmId") Long dsmId);
  }
 