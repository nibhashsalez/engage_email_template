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
import com.salezshark.emailtemplate.entity.TemplateFavouriteEntity;

@Repository("favouriteTemplateRepository")
public interface FavouriteTemplateRepository extends 
											CrudRepository<TemplateFavouriteEntity, Long>, 
											JpaSpecificationExecutor<TemplateFavouriteEntity>,
											PagingAndSortingRepository<TemplateFavouriteEntity, Long>{
	/**
	 * findTemplateIdByUserIdAndDsmId
	 * @param userId
	 * @param dsmId
	 * @return
	 * 
	 */
	@Query("SELECT t.templateDetailsEntity.templateId FROM TemplateFavouriteEntity t where t.userId = :userId and t.dsmId = :dsmId") 
	List<Long> getTemplateIdByUserIdAndDsmId(@Param("userId") Long userId, @Param("dsmId") Long dsmId);
	/**
	 * findAllByDsmIdAndTemplateDetailsEntityAndUserId
	 * @param dsmId
	 * @param templateDetailsEntity
	 * @param userId
	 * @return
	 */
	List<TemplateFavouriteEntity> findAllByDsmIdAndTemplateDetailsEntityAndUserId(Long dsmId, EmailTemplateDetailsEntity templateDetailsEntity, Long userId);
	/**
	 * delelteFavoriteTemplate
	 * @param templateIds
	 * @param dataSourceMappingId
	 * @return
	 */
	@Modifying
	@Query("delete TemplateFavouriteEntity fe where fe.templateDetailsEntity.templateId in (:templateIds) and fe.dsmId = :dsmId")
	int delelteFavoriteTemplate(@Param("templateIds") List<Long> templateIds, @Param("dsmId") Long dsmId);
	
}
