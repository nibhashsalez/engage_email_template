package com.salezshark.emailtemplate.utility;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.salezshark.emailtemplate.bean.SubscriberManagementBean;
import com.salezshark.emailtemplate.constants.ServiceConstants;
import com.salezshark.emailtemplate.entity.EmailTemplateDetailsEntity;
import com.salezshark.emailtemplate.entity.TemplateFavouriteEntity;
import com.salezshark.emailtemplate.entity.TemplateFolderEntity;
import com.salezshark.emailtemplate.entity.TemplateUserActivityEntity;
import com.salezshark.emailtemplate.exception.EmailTemplateException;
import com.salezshark.emailtemplate.request.EmailTemplateRequest;

/**
 * @author Nibhash
 * 
 */
@Component
public class SpecificationUtility {
	
	/**
	 * getTemplateUserSpecification
	 * @param emailTemplateRequest
	 * @param predicates
	 * @param subscriberManagementBean
	 * @param shareFlag
	 * @return
	 * @throws EmailTemplateException
	 */
	public static Specification<TemplateUserActivityEntity> getTemplateUserSpecification(EmailTemplateRequest emailTemplateRequest,
			List<Predicate> predicates, SubscriberManagementBean subscriberManagementBean, Boolean totalCountFlag) throws EmailTemplateException {
		return userActivityTemplateSpecification(emailTemplateRequest, predicates, subscriberManagementBean, totalCountFlag);
	}
	/**
	 * getFavTemplateSpecification
	 * @param emailTemplateRequest
	 * @param predicates
	 * @param subscriberManagementBean
	 * @param favFlag
	 * @return
	 * @throws EmailTemplateException
	 */
	public static Specification<TemplateFavouriteEntity> getFavTemplateSpecification(EmailTemplateRequest emailTemplateRequest,
			List<Predicate> predicates, SubscriberManagementBean subscriberManagementBean) throws EmailTemplateException {
		return favTemplateSpecification(emailTemplateRequest, predicates, subscriberManagementBean);
	}
	/**
	 * getCreatedByMeSpecification
	 * @param emailTemplateRequest
	 * @param predicates
	 * @param subscriberManagementBean
	 * @return
	 * @throws EmailTemplateException
	 */
	public static Specification<EmailTemplateDetailsEntity> getCreatedByMeSpecification(EmailTemplateRequest emailTemplateRequest,
			List<Predicate> predicates, SubscriberManagementBean subscriberManagementBean) throws EmailTemplateException {
		return createdByMeSpecification(emailTemplateRequest, predicates, subscriberManagementBean);
	}
	/**
	 * getPublicTemplateSpecification
	 * @param emailTemplateRequest
	 * @param predicates
	 * @param subscriberManagementBean
	 * @return
	 * @throws EmailTemplateException
	 */
	public static Specification<EmailTemplateDetailsEntity> getPublicTemplateSpecification(EmailTemplateRequest emailTemplateRequest,
			List<Predicate> predicates, SubscriberManagementBean subscriberManagementBean) throws EmailTemplateException {
		return publicTemplateSpecification(emailTemplateRequest, predicates, subscriberManagementBean);
	}
	
	/**
	 * createdByMeSpecification : when created by me filter is applied.....
	 * @param emailTemplateRequest
	 * @param predicates
	 */
	public static Specification<EmailTemplateDetailsEntity> createdByMeSpecification(
			EmailTemplateRequest emailTemplateRequest, List<Predicate> predicates, SubscriberManagementBean subscriberManagementBean) {

		Specification<EmailTemplateDetailsEntity> templateDetailsSpecification = new Specification<EmailTemplateDetailsEntity>() {

			public static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<EmailTemplateDetailsEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				predicates.add(criteriaBuilder.equal(root.get("createdBy"), subscriberManagementBean.getLoginUserId()));
				predicates.add(criteriaBuilder.equal(root.get("dsmId"), subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId()));
				predicates.add(criteriaBuilder.equal(root.get("isActive"), Boolean.TRUE));

				if (null != emailTemplateRequest.getModuleId() /* && !createdByMeCountFlag */) {
					predicates.add(criteriaBuilder.equal(root.get("templateModuleId"), emailTemplateRequest.getModuleId()));
				}
				if (StringUtils.isNoneBlank(emailTemplateRequest.getSearchText()) /* && !createdByMeCountFlag */) {
					predicates.add(criteriaBuilder.or(
							criteriaBuilder.like(criteriaBuilder.lower(root.get("templateName")),
									"%" + emailTemplateRequest.getSearchText().toLowerCase() + "%"),
							criteriaBuilder.like(criteriaBuilder.lower(root.get("templateSubject")),
									"%" + emailTemplateRequest.getSearchText().toLowerCase() + "%")));
				}
				query.orderBy(criteriaBuilder.desc(root.get("createdDate")));

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		return templateDetailsSpecification;
	}

	/**
	 * favTemplateSpecification : when favourite filter is applied.....
	 * @param emailTemplateRequest
	 * @param predicates
	 */
	public static Specification<TemplateFavouriteEntity> favTemplateSpecification(
			EmailTemplateRequest emailTemplateRequest, List<Predicate> predicates, SubscriberManagementBean subscriberManagementBean) {
		Specification<TemplateFavouriteEntity> favTemplateSpecification = new Specification<TemplateFavouriteEntity>() {

			public static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<TemplateFavouriteEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				predicates.add(criteriaBuilder.equal(root.get("userId"), subscriberManagementBean.getLoginUserId()));
				predicates.add(criteriaBuilder.equal(root.get("templateDetailsEntity").get("dsmId"), 
						subscriberManagementBean.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId()));
				predicates.add(criteriaBuilder.equal(root.get("templateDetailsEntity").get("isActive"), Boolean.TRUE));

				if (null != emailTemplateRequest.getModuleId()/* && !favFlag */) {
					predicates.add(criteriaBuilder.equal(root.get("templateDetailsEntity").get("templateModuleId"), emailTemplateRequest.getModuleId()));
				}
				if (StringUtils.isNoneBlank(emailTemplateRequest.getSearchText())/* && !favFlag */) {
					predicates
							.add(criteriaBuilder.or(
									criteriaBuilder.like(
											criteriaBuilder
													.lower(root.get("templateDetailsEntity").get("templateName")),
											"%" + emailTemplateRequest.getSearchText().toLowerCase() + "%"),
									criteriaBuilder.like(
											criteriaBuilder
													.lower(root.get("templateDetailsEntity").get("templateSubject")),
											"%" + emailTemplateRequest.getSearchText().toLowerCase() + "%")));
				}
				query.orderBy(criteriaBuilder.desc(root.get("templateDetailsEntity").get("createdDate")));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		return favTemplateSpecification;
	}

	/**
	 * userActivityTemplateSpecification : when no filter is applied.....
	 * 
	 * @param emailTemplateRequest
	 * @param predicates
	 * @return
	 */
	@Deprecated
	public static Specification<TemplateUserActivityEntity> userActivityTemplateSpecification1(
			EmailTemplateRequest emailTemplateRequest, List<Predicate> predicates,
			SubscriberManagementBean subscriberManagementBean, Boolean shareCountFlag) {

		Specification<TemplateUserActivityEntity> userActivitySpecification;
		userActivitySpecification = new Specification<TemplateUserActivityEntity>() {

			public static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<TemplateUserActivityEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				if ((StringUtils.isNoneBlank(emailTemplateRequest.getFilter()) && emailTemplateRequest.getFilter()
						.equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_SHARE)) || shareCountFlag) {
					predicates.add(criteriaBuilder.equal(root.get("sharedWithUserId"),
							subscriberManagementBean.getLoginUserId()));
				} else {
					predicates.add(criteriaBuilder.or(
							criteriaBuilder.equal(root.get("templateFilter"),
									ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_PUBLIC),
							criteriaBuilder.equal(root.get("sharedWithUserId"),
									subscriberManagementBean.getLoginUserId()),
							criteriaBuilder.or(criteriaBuilder.and(
									criteriaBuilder.equal(root.get("templateFilter"),
											ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_PRIVATE),
									criteriaBuilder.equal(root.get("createdBy"),
											subscriberManagementBean.getLoginUserId())))));
				}
				predicates.add(criteriaBuilder.equal(root.get("templateDetailsEntity").get("isActive"), Boolean.TRUE));
				predicates.add(
						criteriaBuilder.equal(root.get("templateDetailsEntity").get("dsmId"), subscriberManagementBean
								.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId()));

				if (null != emailTemplateRequest.getModuleId() && !shareCountFlag) {
					predicates.add(criteriaBuilder.equal(root.get("templateDetailsEntity").get("templateModuleId"),
							emailTemplateRequest.getModuleId()));
				}
				if (StringUtils.isNoneBlank(emailTemplateRequest.getSearchText()) && !shareCountFlag) {
					predicates
							.add(criteriaBuilder.or(
									criteriaBuilder.like(
											criteriaBuilder
													.lower(root.get("templateDetailsEntity").get("templateName")),
											"%" + emailTemplateRequest.getSearchText().toLowerCase() + "%"),
									criteriaBuilder.like(
											criteriaBuilder
													.lower(root.get("templateDetailsEntity").get("templateSubject")),
											"%" + emailTemplateRequest.getSearchText().toLowerCase() + "%")));
				}
				query.orderBy(criteriaBuilder.desc(root.get("templateDetailsEntity").get("createdDate")));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		return userActivitySpecification;
	}

	/**
	 * userActivityTemplateSpecification : when no filter is applied.....
	 * 
	 * @param emailTemplateRequest
	 * @param predicates
	 * @param subscriberManagementBean
	 * @param shareCountFlag
	 * @return
	 */
	public static Specification<TemplateUserActivityEntity> userActivityTemplateSpecification(
			EmailTemplateRequest emailTemplateRequest, List<Predicate> predicates, SubscriberManagementBean subscriberManagementBean, Boolean totalCountFlag) {

		Specification<TemplateUserActivityEntity> userActivitySpecification;
		userActivitySpecification = new Specification<TemplateUserActivityEntity>() {

			public static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<TemplateUserActivityEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				if ((StringUtils.isNoneBlank(emailTemplateRequest.getFilter()) && emailTemplateRequest.getFilter()
						.equalsIgnoreCase(ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_SHARE)) && totalCountFlag ) {
					predicates.add(criteriaBuilder.equal(root.get("sharedWithUserId"), subscriberManagementBean.getLoginUserId()));
				} else {
					predicates.add(criteriaBuilder.or(
							criteriaBuilder.equal(root.get("templateFilter"), ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_PUBLIC),
							criteriaBuilder.equal(root.get("sharedWithUserId"), subscriberManagementBean.getLoginUserId()),
							criteriaBuilder.or(criteriaBuilder.and(criteriaBuilder.equal(root.get("templateFilter"), ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_PRIVATE),
							criteriaBuilder.equal(root.get("createdBy"), subscriberManagementBean.getLoginUserId())))));

//					predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("templateFilter"), ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_PUBLIC),
//							criteriaBuilder.equal(root.get("sharedWithUserId"), subscriberManagementBean.getLoginUserId()),
//							criteriaBuilder.or(criteriaBuilder.and(
//									criteriaBuilder.equal(root.get("templateFilter"), ServiceConstants.EMAIL_TEMPLATE_FILTER_VALUE_PRIVATE),
//									criteriaBuilder.equal(root.get("createdBy"), subscriberManagementBean.getLoginUserId())))));
				}
				predicates.add(criteriaBuilder.equal(root.get("templateDetailsEntity").get("isActive"), Boolean.TRUE));
				predicates.add(criteriaBuilder.equal(root.get("templateDetailsEntity").get("dsmId"), subscriberManagementBean
								.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId()));

				if (null != emailTemplateRequest.getModuleId() && totalCountFlag ) {
					predicates.add(criteriaBuilder.equal(root.get("templateDetailsEntity").get("templateModuleId"), emailTemplateRequest.getModuleId()));
				}
				if (StringUtils.isNoneBlank(emailTemplateRequest.getSearchText()) && totalCountFlag ) {
					predicates
							.add(criteriaBuilder.or(
									criteriaBuilder.like(
											criteriaBuilder
													.lower(root.get("templateDetailsEntity").get("templateName")),
											"%" + emailTemplateRequest.getSearchText().toLowerCase() + "%"),
									criteriaBuilder.like(
											criteriaBuilder
													.lower(root.get("templateDetailsEntity").get("templateSubject")),
											"%" + emailTemplateRequest.getSearchText().toLowerCase() + "%")));
				}
				query.distinct(true);
				query.orderBy(criteriaBuilder.desc(root.get("templateDetailsEntity").get("createdDate")));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		return userActivitySpecification;
	}

	/**
	 * publicTemplateSpecification : when public filter is applied
	 * 
	 * @param emailTemplateRequest
	 * @param predicates
	 * @param subscriberManagementBean
	 * @param favCountFlag
	 * @return
	 */
	public static Specification<EmailTemplateDetailsEntity> publicTemplateSpecification(
			EmailTemplateRequest emailTemplateRequest, List<Predicate> predicates, SubscriberManagementBean subscriberManagementBean) {
		Specification<EmailTemplateDetailsEntity> publicTemplateSpecification = new Specification<EmailTemplateDetailsEntity>() {

			public static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<EmailTemplateDetailsEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				predicates.add(criteriaBuilder.equal(root.get("templateType"),
						ServiceConstants.EMAIL_TEMPLATE_FILTER_TYPE_PUBLIC));
				predicates.add(criteriaBuilder.equal(root.get("dsmId"), subscriberManagementBean.getUserMasterEntity()
						.getDataSourceMappingId().getDataSourceMappingId()));
				predicates.add(criteriaBuilder.equal(root.get("isActive"), Boolean.TRUE));

				if (null != emailTemplateRequest.getModuleId()/* && !peublicCountFlag */) {
					predicates.add(
							criteriaBuilder.equal(root.get("templateModuleId"), emailTemplateRequest.getModuleId()));
				}
				if (StringUtils.isNoneBlank(emailTemplateRequest.getSearchText())/* && !peublicCountFlag */) {
					predicates.add(criteriaBuilder.or(
							criteriaBuilder.like(criteriaBuilder.lower(root.get("templateName")),
									"%" + emailTemplateRequest.getSearchText().toLowerCase() + "%"),
							criteriaBuilder.like(criteriaBuilder.lower(root.get("templateSubject")),
									"%" + emailTemplateRequest.getSearchText().toLowerCase() + "%")));
				}
				query.orderBy(criteriaBuilder.desc(root.get("createdDate")));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		return publicTemplateSpecification;
	}

	/**
	 * folderTemplateSpecification : when folder filter is applied
	 * 
	 * @param emailTemplateRequest
	 * @param predicates
	 * @param subscriberManagementBean
	 * @param favCountFlag
	 * @return
	 */
	public static Specification<TemplateFolderEntity> folderTemplateSpecification(
			EmailTemplateRequest emailTemplateRequest, List<Predicate> predicates,
			SubscriberManagementBean subscriberManagementBean, Boolean favCountFlag) {
		Specification<TemplateFolderEntity> folderTemplateSpecification = new Specification<TemplateFolderEntity>() {

			public static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<TemplateFolderEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				predicates.add(criteriaBuilder.equal(root.get("folderMasterEntity").get("id"),
						emailTemplateRequest.getFolderId()));
				predicates
						.add(criteriaBuilder.equal(root.get("folderMasterEntity").get("dsmId"), subscriberManagementBean
								.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId()));
				predicates.add(criteriaBuilder.equal(root.get("templateDetailsEntity").get("isActive"), Boolean.TRUE));

				if (null != emailTemplateRequest.getModuleId()) {
					predicates.add(criteriaBuilder.equal(root.get("templateDetailsEntity").get("templateModuleId"),
							emailTemplateRequest.getModuleId()));
				}
				if (StringUtils.isNoneBlank(emailTemplateRequest.getSearchText())) {
					predicates
							.add(criteriaBuilder.or(
									criteriaBuilder.like(
											criteriaBuilder
													.lower(root.get("templateDetailsEntity").get("templateName")),
											"%" + emailTemplateRequest.getSearchText().toLowerCase() + "%"),
									criteriaBuilder.like(
											criteriaBuilder
													.lower(root.get("templateDetailsEntity").get("templateSubject")),
											"%" + emailTemplateRequest.getSearchText().toLowerCase() + "%")));
				}
				query.orderBy(criteriaBuilder.desc(root.get("templateDetailsEntity").get("createdDate")));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		return folderTemplateSpecification;
	}

	public static Specification<TemplateFolderEntity> folderTemplateSpecification(
			EmailTemplateRequest emailTemplateRequest, List<Predicate> predicates,
			SubscriberManagementBean subscriberManagementBean) {
		Specification<TemplateFolderEntity> folderTemplateSpecification = new Specification<TemplateFolderEntity>() {

			public static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<TemplateFolderEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				predicates.add(criteriaBuilder.equal(root.get("folderMasterEntity").get("id"),
						emailTemplateRequest.getFolderId()));
				predicates
						.add(criteriaBuilder.equal(root.get("folderMasterEntity").get("dsmId"), subscriberManagementBean
								.getUserMasterEntity().getDataSourceMappingId().getDataSourceMappingId()));
				predicates.add(criteriaBuilder.equal(root.get("templateDetailsEntity").get("isActive"), Boolean.TRUE));

				if (null != emailTemplateRequest.getModuleId()) {
					predicates.add(criteriaBuilder.equal(root.get("templateDetailsEntity").get("templateModuleId"),
							emailTemplateRequest.getModuleId()));
				}
				if (StringUtils.isNoneBlank(emailTemplateRequest.getSearchText())) {
					predicates
							.add(criteriaBuilder.or(
									criteriaBuilder.like(
											criteriaBuilder
													.lower(root.get("templateDetailsEntity").get("templateName")),
											"%" + emailTemplateRequest.getSearchText().toLowerCase() + "%"),
									criteriaBuilder.like(
											criteriaBuilder
													.lower(root.get("templateDetailsEntity").get("templateSubject")),
											"%" + emailTemplateRequest.getSearchText().toLowerCase() + "%")));
				}
				query.orderBy(criteriaBuilder.desc(root.get("templateDetailsEntity").get("createdDate")));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		return folderTemplateSpecification;
	}
}
