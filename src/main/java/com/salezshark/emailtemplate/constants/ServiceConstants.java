package com.salezshark.emailtemplate.constants;

/**
 * @author Nibhash
 *
 */
public class ServiceConstants {

	public static final String MODULE_TYPE_LEADS = "Leads";
	public static final String MODULE_TYPE_CONTACTS = "Contacts";
	public static final String MODULE_TYPE_ACCOUNTS = "Accounts";
	public static final String MODULE_TYPE_OPPORTUNITY = "Potentials";
	public static final String DATA_TYPE_ACTIVITY_TYPE = "activityType";
	public static final String DATA_TYPE_CONTACT_TYPE = "contactType";
	public static final String DATA_TYPE_LEAD = "activityType";
	public static final String DATA_VALUE_LEAD = "Lead";
	public static final String DATA_VALUE_CONTACT = "Contact";
	public static final String DATA_VALUE_ACCOUNT = "Account";
	public static final String DATA_VALUE_OPPORTUNITY = "Potential";
	public static final String DATA_VALUE_TYPE_OPPORTUNITY = "Opportunity";

	public static final String EXCEPTION_OCCURRED_IN= "Exception occurred in"; 
	public static final int RESPONSE_CODE_INTERNAL_SERVER_ERROR = 500;
	public static final String RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR = "Internal server error";
	public static final Boolean RESPONSE_STATUS_FAILURE = false;
	public static final Boolean RESPONSE_STATUS_SUCCESS = true;
	public static final int RESPONSE_CODE_FAILURE = 410;

	//	Validation Failure
	public static final int RESPONSE_CODE_VALIDATION_FAILED = 422;
	public static final String RESPONSE_MESSAGE_VALIDATION_FAILED = "Validation Failed";
	
	public static final String EMAIL_TEMPLATE_FILTER_TYPE_PUBLIC = "public";
	public static final String EMAIL_TEMPLATE_FILTER_TYPE_PRIVATE = "private";
	public static final String EMAIL_TEMPLATE_FILTER_TYPE_SHARE = "share";
	public static final String EMAIL_TEMPLATE_FILTER_VALUE_PUBLIC = "PB";
	public static final String EMAIL_TEMPLATE_FILTER_VALUE_PRIVATE = "PV";
	public static final String EMAIL_TEMPLATE_FILTER_VALUE_SHARE = "SH";
	public static final String EMAIL_TEMPLATE_FILTER_VALUE_FAVOURITE = "FV";
	public static final String EMAIL_TEMPLATE_FILTER_VALUE_BY_ME = "me";
	public static final String USER_CACHED_BY_DSMID = "userCachedByDsmId";
	public static final String SHARE_COUNT_TEMPLATE = "shareCount";
	public static final String DEFAULT_SORT_BY = "createdDate";
	
	public static final int RECORD_SIZE = 10;
	public static final int CACHE_SIZE = 100;
}
