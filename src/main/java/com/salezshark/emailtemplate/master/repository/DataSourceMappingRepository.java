package com.salezshark.emailtemplate.master.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salezshark.emailtemplate.master.entity.DataSourceMappingEntity;


/**
 * @author nibhash
 *
 */
@Repository
public interface DataSourceMappingRepository extends CrudRepository<DataSourceMappingEntity, Integer> {
	
	/**
	 * findByDataSourceMappingIdAndIsActiveTrue
	 * @param dataSourceMappingId
	 * @return
	 */
	DataSourceMappingEntity findByDataSourceMappingIdAndIsActiveTrue(Long dataSourceMappingId);
	/**
	 * findAllByIsActiveTrue
	 * @return
	 */
	List<DataSourceMappingEntity> findAllByIsActiveTrue();
}
