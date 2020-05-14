package com.salezshark.emailtemplate.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salezshark.emailtemplate.entity.TemplateFiltersEntity;

/**
 * 
 * @author nibhash
 *
 */

@Repository
public interface TemplateFilterRepository extends CrudRepository<TemplateFiltersEntity, Long> {

//	@Query("select tf from TemplateFiltersEntity tf")
	List<TemplateFiltersEntity> findAll();
	
}
