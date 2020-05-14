package com.salezshark.emailtemplate.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salezshark.emailtemplate.master.entity.UserMasterEntity;

/**
 * @author Nibhash
 *
 */
@Repository
public interface UserDetailsRepository extends CrudRepository<UserMasterEntity, Long> {
	/**
	 * findByIdAndIsActiveTrueAndIsDeletedFalse : 
	 * find user by user id (isActive true and isDeleted false) 
	 * @param id
	 * @return
	 */
	@Query(value = "select user from UserMasterEntity user where user.userId= :userId and user.dataSourceMappingId.dataSourceMappingId = :dataSourceMappingId "
			+ "and user.isDeleted = false and user.confirmationStatus= true")
	UserMasterEntity findByUserIdAndDsmId(@Param("userId") Long userId, @Param("dataSourceMappingId") Long dataSourceMappingId);

	/**
	 * findAllUserByDsmId
	 * @param isActive
	 * @param dataSourceMappingId
	 * @return
	 */
	@Query(value = "select user from UserMasterEntity user where user.isDeleted= :isDeleted and user.permanentDeleted= :permanentDeleted "
			+ " and user.confirmationStatus= :confirmationStatus and user.dataSourceMappingId.dataSourceMappingId = :dataSourceMappingId")
	List<UserMasterEntity> findAllUserByDsmId(@Param("permanentDeleted") Boolean permanentDeleted, @Param("isDeleted") Boolean isDeleted, 
			@Param("confirmationStatus") Boolean confirmationStatus, @Param("dataSourceMappingId") Long dataSourceMappingId);
}