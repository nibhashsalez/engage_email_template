package com.salezshark.emailtemplate.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salezshark.emailtemplate.entity.FolderMasterEntity;

/**
 * 
 * @author nibhash
 *
 */

@Repository
public interface FolderMasterRepository extends CrudRepository<FolderMasterEntity, Long> {

	/**
	 * findByFolderIdAndIsActiveTrueAndDsmId
	 * @param dataSourceMappingId
	 * @return
	 */
	FolderMasterEntity findByIdAndIsActiveTrueAndDsmId(Long folderId, Long dataSourceMappingId);
	/**
	 * findAllByIsActiveTrueAndDsmIdAndCreatedBy
	 * @param dataSourceMappingId
	 * @param userId
	 * @return
	 */
	List<FolderMasterEntity> findAllByIsActiveTrueAndDsmIdAndCreatedBy(Long dataSourceMappingId, Long userId, Sort sortBy);
}
