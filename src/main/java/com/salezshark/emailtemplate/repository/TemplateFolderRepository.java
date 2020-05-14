package com.salezshark.emailtemplate.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salezshark.emailtemplate.entity.EmailTemplateDetailsEntity;
import com.salezshark.emailtemplate.entity.FolderMasterEntity;
import com.salezshark.emailtemplate.entity.TemplateFolderEntity;

/**
 * 
 * @author nibhash
 *
 */

@Repository
public interface TemplateFolderRepository extends 
										  CrudRepository<TemplateFolderEntity, Long>, 
										  JpaSpecificationExecutor<TemplateFolderEntity>,
										  PagingAndSortingRepository<TemplateFolderEntity, Long> {

	/**
	 * deleteTemplateFromFolder
	 * @param templateIds
	 * @param dsmId
	 * @return
	 */
	@Modifying
	@Query("delete from TemplateFolderEntity tf where tf.templateDetailsEntity.templateId in (:templateIds) and tf.dsmId = :dsmId")
	int deleteTemplateFromFolder(@Param("templateIds") List<Long> templateIds, @Param("dsmId")  Long dsmId);
	/**
	 * findAllByFolderMasterEntityAndDsmId
	 * @param folderMasterEntity
	 * @param dsmId
	 * @return
	 */
	List<TemplateFolderEntity> findAllByFolderMasterEntityAndDsmId(FolderMasterEntity folderMasterEntity, Long dsmId, Pageable pageable);
	/**
	 * getTemplateCountByFolderId
	 * @param folderId
	 * @param dsmId
	 * @return
	 */
	@Query("select count(tf.templateDetailsEntity.templateId) from TemplateFolderEntity tf where tf.folderMasterEntity.id = :folderId and tf.dsmId = :dsmId")
	Long getTemplateCountByFolderId(@Param("folderId") Long folderId, @Param("dsmId") Long dsmId);
	/**
	 * findByTemplateDetailsEntityAndCreatedByAndDsmId
	 * @param emailTemplateDetailsEntity
	 * @param loginUserId
	 * @param dataSourceMappingId
	 * @return
	 */
	List<TemplateFolderEntity> findByTemplateDetailsEntityAndCreatedByAndDsmId(EmailTemplateDetailsEntity emailTemplateDetailsEntity, Long loginUserId, Long dataSourceMappingId);
	/**
	 * deleteTemplateFromUserFolder
	 * @param templateIds
	 * @param dsmId
	 * @param folderId
	 * @return
	 */
	@Modifying
	@Query("delete from TemplateFolderEntity tf where tf.templateDetailsEntity.templateId in (:templateIds) and tf.dsmId = :dsmId and tf.folderMasterEntity.id = :folderId")
	int deleteTemplateFromUserFolder(@Param("templateIds") List<Long> templateIds, @Param("folderId")  Long folderId, @Param("dsmId")  Long dsmId);
	/**
	 * deleteTemplateIdsFromUserFolder
	 * @param templateIds
	 * @param folderId
	 * @param dsmId
	 * @return
	 */
	@Modifying
	@Query("delete from TemplateFolderEntity tf where tf.templateDetailsEntity.templateId in (:templateIds) and tf.dsmId = :dsmId and tf.folderMasterEntity.id = :folderId")
	int deleteTemplateIdsFromUserFolder(@Param("templateIds") List<Long> templateIds, @Param("folderId")  Long folderId, @Param("dsmId")  Long dsmId);
	/**
	 * findAllByFolderMasterEntityAndDsmId
	 * @param folderMasterEntity
	 * @param dsmId
	 * @return
	 */
	List<TemplateFolderEntity> findAllByFolderMasterEntityAndDsmId(FolderMasterEntity folderMasterEntity, Long dsmId);
}
