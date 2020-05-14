<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>template details</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="../static/styles/automation.css" type="text/css"
	rel="stylesheet" />
<link href="../static/styles/workFlow.css" type="text/css"
	rel="stylesheet" />
<link href="../static/styles/font-awesome.css" type="text/css"
	rel="stylesheet">
<link href="../static/styles/foundation.css" type="text/css"
	rel="stylesheet">
<script type="text/javascript" src="../static/scripts/js/jquery.min.js"></script>

</head>
<body>
	<!-- custom template html start here -->

	<div class="large-10 medium-9 columns contentbar">
		<div class="template_details">
			<div class="temp_details_head">
				<div class="inline_block">
					<h3 class="subHeading">
						<a href ="/email/template/viewTemplateList.html"> <img
							src="../static/images/administrator/ic_back.svg" alt="back img">
						 </a> 
						 <input type="hidden" id="template-id" value="${templateDetail.data.templateId}" /> 
						<span> ${templateDetail.data.name} </span>
					</h3>
				</div>

				<!-- for private -->
				<div class="inline_block">
					<p>


					<c:if test="${templateDetail.data.category eq 'private'}">
						<p>

							<a href="javascript:;" data-reveal-id="deleteTemplate"
								class="primary_btn cancel_btn close-reveal-modal">Delete</a> <a
								href="javascript:;" data-reveal-id="shareTemplate"
								class="blue_color  primary_btn blue_hover">share</a> <a
								href="/email/template/editTemplate.html?templateId=${templateDetail.data.templateId}&&moduleId=${templateDetail.data.moduleId}"
								class="blue_bg  primary_btn blue_hover">edit</a>
						</p>
					</c:if>
					<c:if
						test="${templateDetail.data.category eq 'public' || templateDetail.data.category eq 'share' }">
						<p>
							<c:if test="${templateDetail.data.isDeletePermission eq true }">
								<a href="javascript:;" data-reveal-id="deleteTemplate"
									class="primary_btn cancel_btn close-reveal-modal"
									id="deleteBtn">delete</a>
							</c:if>
							<c:if test="${templateDetail.data.isEditPermission eq true }">
								<a
									href="/email/template/editTemplate.html?templateId=${templateDetail.data.templateId}&&moduleId=${templateDetail.data.moduleId}"
									class="blue_bg  primary_btn blue_hover">edit</a>
							</c:if>

						</p>
					</c:if>

				</div>

			</div>
			<div class="temp_details_body">
				<div class="temp_details_body_left">
					<h3>Template Info</h3>
					<div>
						<p>
							<span>Module</span> <span>${templateDetail.data.moduleName}</span>
						</p>
						<p>
							<span>Created by</span> <span>${templateDetail.data.createdByName}</span>
						</p>
						<p>
							<span>Last Updated</span> <span>
								${templateDetail.data.updatedDate} </span>
						</p>
						<p>
							<span>Created on</span> <span>${templateDetail.data.createdDate}</span>
						</p>
						<c:if test="${templateDetail.data.folder ne null}">
							<p>
								<span>Folder</span>
								<c:forEach var="entry" items="${templateDetail.data.folder}">
									<span>${entry.value}</span>
								</c:forEach>

							</p>
						</c:if>


						<p>
							<span>Category</span> <span>${templateDetail.data.category}</span>
						</p>
						<p>

							<c:if test="${templateDetail.data.sahredWithMap ne null}">
								<p>
									<span>Shared With</span>
									<c:forEach var="entry"
										items="${templateDetail.data.sahredWithMap}">
										<span>${entry.value}, </span>
									</c:forEach>

								</p>
							</c:if>
						</p>
						<p>
							<span>Subject</span> <span>${templateDetail.data.subject}</span>
						</p>
					</div>
				</div>
				<div class="temp_details_body_rgt custom_scroll"
					id="template-data-div">
					<div id="template-data">${templateDetail.data.data}</div>
				</div>
			</div>
		</div>
	</div>


	<!-- delete custom template popup html here  -->

	<div id="deleteTemplate" class="deleteTemplateUpdate reveal-modal" data-reveal
		aria-labelledby="modalTitle" role="dialog" aria-hidden="true"
		role="dialog">
		<div class="bodyPart">
			<div class="inline_block">
		<img alt="deleteimg" src="../static/images/administrator/popup-delete.svg">
	</div>
	<div class="inline_block">
			<p>Are you sure, you want to delete the templates ?</p>
	
	</div>
		</div>

		<div class="footer_section">
			<ul>
				<li><a href="javascript:;"
					class="primary_btn cancel_btn close-reveal-modal"
					aria-label="Close">Cancel</a></li>
				<li><a href="javascript:;"
					class="blue_bg _whiteBg primary_btn blue_hover"
					onClick="deleteTemplate();">Ok</a></li>
			</ul>
		</div>

		<a class="close-reveal-modal" aria-label="Close">&#215;</a>
	</div>

	<!-- share template popup here -->
	<div id="shareTemplate" class="reveal-modal" data-reveal
		aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
		<h2 id="modalTitle">Share Template</h2>
		<div class="bodyPart">
			<div class="share-modal-body">
				<input type="hidden" name="data-share" id="shareId" />
			</div>
			<span class="common_search"> <input type="text"
				name="userSearch" id="userSearch" placeholder="Search by name">
				<i class="fa fa-search" aria-hidden="true" id="searchEvent"></i>
			</span>
			<div class="shareTermp_seelctGrp">
				<ul class="custom_scroll">
					<div id="userListDynamic">
						<div>
							<div id="userList">
								<c:forEach items="${userList.data}" var="user">
									<li><label class="custom_cehckbox"> <input
											type="checkbox" name="checkbox2" data-user-id="${user.id}">
											<span class="checkmark"></span>${user.value}
									</label></li>
								</c:forEach>
							</div>
				</ul>
			</div>
		</div>
		<div class="footer_section">
			<ul>
				<li><a href="javascript:;"
					class="primary_btn cancel_btn close-reveal-modal"
					aria-label="Close">Cancel</a></li>
				<li class="shareTemp" id="shareTemp"><a href="javascript:;"
					class="blue_bg _whiteBg primary_btn blue_hover" id="shareBtn">share</a></li>
			</ul>
		</div>
		<a class="close-reveal-modal" aria-label="Close">&#215;</a>
	</div>


	<div class="ShowAfterAction afterActionPopup">
		<div id="popupBackShadow"></div>
		<div class="bodyPart">
			<div class="inline_block">
				<span class="checkmark">
					<div class="checkmark_stem"></div>
					<div class="checkmark_kick"></div>
				</span>
			</div>
			<div class="inline_block" id="afterActionPopupText"></div>
		</div>
	</div>

	<script type="text/javascript"
		src="../static/scripts/js/custom_select.js"></script>
	<script type="text/javascript" src="../static/scripts/js/foundation.js"></script>
	<script type="text/javascript"
		src="../static/scripts/js/foundation.reveal.js"></script>

	<script>
		var templateData = $('#template-data').html();
		$("#template-data").empty();
		$('#template-data-div').append(templateData);
		function deleteTemplate() {

			var singleDeleteId = $("#template-id").val();
			var templateIdList = "";
			if (singleDeleteId != '' || singleDeleteId.trim() != '') {
				templateIdList = singleDeleteId;
			}
			var data = {
				"templateIdList" : templateIdList
			}
			deleteTemplates(data);
		}

		function deleteTemplates(data) {
			$
					.ajax({
						type : "POST",
						contentType : "application/json",
						url : '<c:url value="/template/delete"/>',
						data : JSON.stringify(data),
						success : function(response) {
							//window.location.href = "/email/template/viewTemplateList.html";
							$('.reveal-modal').trigger('reveal:close');
							setTimeout(
									function() {
										window.location.href = "/email/template/viewTemplateList.html";
									}, 2000);
							$("#afterActionPopupText").append(
									"Delete template successfully");
							$('.ShowAfterAction').show();
						},
						error : function(e) {
							location.reload();
						}
					});
		}

		function getUsersBySearch(data) {
			$
					.ajax({
						type : "GET",
						contentType : "application/json",
						data : data,
						url : '<c:url value="/template/users"/>',
						success : function(response) {

							var markup = "";
							$('#userListDynamic').empty();
							$
									.each(
											response.data,
											function(i, data) {
												var userId = data.id;
												var userName = data.value;
												$('#userList').remove();
												markup = "<li class='userList'><label class='custom_cehckbox'> "
														+ "<input type='checkbox' name='checkbox2' data-user-id="
														 +userId
														 +">"
														+ "<span class='checkmark'></span>"
														+ userName
														+ "</label></li>"
												$('#userListDynamic').append(
														markup);
											});

						},
						error : function(e) {
							console.log(e);
						}
					});
		}
		function shareTemplates(data) {
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : '<c:url value="/template/share"/>',
				data : JSON.stringify(data),
				success : function(response) {
					//console.log(response);
					//window.location.href = "/email/template/viewTemplateList.html";
					$('.reveal-modal').trigger('reveal:close');
					setTimeout(function() {
						location.reload();
						//window.location.href = "/email/template/viewTemplateList.html";
					}, 3000);
					$("#afterActionPopupText").append(
							"Share template successfully");
					$('.ShowAfterAction').show();
				},
				error : function(e) {
					console.log(e);
				}
			});
		}

		function delaySearchTime(callback, ms) {
			var timer = 0;
			return function() {
				var context = this, args = arguments;
				clearTimeout(timer);
				timer = setTimeout(function() {
					callback.apply(context, args);
				}, ms || 0);
			};
		}

		$(document).ready(
				function() {
					$('.share_member_list small:lt(3)').show();

					$('#shareBtn').click(function() {
						var singleShareId = $("#template-id").val();
						var usersIdList = "";
						var templateIdList = "";
						if (singleShareId != '' || singleShareId.trim() != '') {
							templateIdList = singleShareId;
						}
						$('input[name="checkbox2"]:checked').each(function() {
							var id = $(this).attr('data-user-id');
							usersIdList += id + ",";
						});
						var data = {
							"templateIdList" : templateIdList,
							"usersIdList" : usersIdList
						}
						shareTemplates(data);
					});

					$("#userSearch").keyup(
							delaySearchTime(function(e) {
								var userSearchText = $("#userSearch").val();
								var data = {};
								if (userSearchText.trim() != ''
										|| !userSearchText.length === 0) {
									data = {
										name : userSearchText
									}
									getUsersBySearch(data);
								} else {
									getUsersBySearch(data);
								}

							}, 500));
				});

		$(".shareTemp").append("<span id='appendSpan'></span>");

		$('#shareTemplate li input').on('change', function() {

			var shareCheck = $("input:checked");

			if (shareCheck.length < 1) {

				$('#shareTemp').addClass("shareTemp");
				$(".shareTemp").append("<span id='appendSpan'></span>");
			} else {
				console.log("elselength", shareCheck);
				$('#shareTemp').removeClass("shareTemp");
				$('#shareTemp span').remove();
			}

		});

		$(document).on("change", ".userList", function() {
			var shareCheck = $("input:checked");

			if (shareCheck.length < 1) {

				$('#shareTemp').addClass("shareTemp");
				$(".shareTemp").append("<span id='appendSpan'></span>");
			} else {
				console.log("elselength", shareCheck);
				$('#shareTemp').removeClass("shareTemp");
				$('#shareTemp span').remove();
			}

		});
	</script>
</body>
</html>
