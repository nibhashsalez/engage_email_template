package com.salezshark.emailtemplate.utility;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.salezshark.emailtemplate.constants.ServiceConstants;


/**
 * @author Nibhash
 * 
 */
@Component
public class RepositoryUtils {
    public Pageable getPageable(int page, int size) {
        if(size <= 0) {
        	size = ServiceConstants.RECORD_SIZE;
        }
        return PageRequest.of(page, size);
    }
    
    public Pageable getPageable(int page, int size, String sortBy) {
        if(size <= 0) {
        	size = ServiceConstants.RECORD_SIZE;
        }
        if(StringUtils.isNoneBlank(sortBy)) {
        	sortBy = ServiceConstants.DEFAULT_SORT_BY;
        }
        return PageRequest.of(page, size, Sort.by(sortBy).descending());
    }
    public Sort getSortBy(String sortBy) {
        if(StringUtils.isNoneBlank(sortBy)) {
        	sortBy = ServiceConstants.DEFAULT_SORT_BY;
        }
        return Sort.by(sortBy).descending();
    }
}
