package com.salezshark.emailtemplate.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salezshark.emailtemplate.master.entity.MasterDataEntity;
import com.salezshark.emailtemplate.master.entity.UserMasterEntity;

/**
 * 
 * @author nibhash
 *
 */

@Repository
public interface MasterDataRepository extends CrudRepository<MasterDataEntity, Long> {

//	@Query("select md from MasterDataEntity md where md.dataSourceMappingEntity.dataSourceMappingId = :dsmID and LOWER(md.dataType) in (:dataTypes)")
	@Query("select md from MasterDataEntity md where LOWER(md.dataType) in (:dataTypes)")
	List<MasterDataEntity> findByDataTypeInIgnoreCase(@Param("dataTypes") List<String> dataTypes);

	@Query("select user from UserMasterEntity user where user.dataSourceMappingId.dataSourceMappingId = :dsmId and user.isDeleted = false")
	List<UserMasterEntity> getAllUsersByDsmId(@Param("dsmId")Long dsmId);
	
	@Query("select user from UserMasterEntity user where user.dataSourceMappingId.dataSourceMappingId = :dsmId "
			+ "and user.isDeleted = false and LOWER(CONCAT(user.firstName, ' ', user.lastName)) like '%' || LOWER(:name) || '%'")
	List<UserMasterEntity> getAllUsersByDsmIdOrName(@Param("dsmId") Long dsmId, @Param("name") String name);
}
