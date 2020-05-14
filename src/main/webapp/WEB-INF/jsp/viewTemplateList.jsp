<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<link href="../static/styles/automation.css" type="text/css"
	rel="stylesheet" />
<link href="../static/styles/workFlow.css" type="text/css"
	rel="stylesheet" />
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="../static/scripts/js/jquery.min.js"></script>

<script type="text/javascript"
	src="../static/scripts/js/foundation.min.js"></script>
<script type="text/javascript"
	src="../static/scripts/js/foundation.reveal.js"></script>
<script type="text/javascript"
	src="../static/scripts/js/custom_select.js"></script>

<!-- custom template html start here -->
<%-- <form:form method="post" action="createTemplate.htm" modelAttribute=""
	autocomplete="off" id="tempalteForm" onsubmit=""> --%>

<div class="large0 medium-9 columns contentbar template_customized">
	<div class="cus_template_header">
		<div class="cus_template_headTop">
			<div class="inline_block">
				<h3 class="subHeading">
					<!-- <span onclick="window.history.back();"><img
						src="../static/images/administrator/ic_back.svg" alt="back img">
						</span> -->
					Template
				</h3>
				<input type="hidden" id="next-page" />
			</div>
			<div class="inline_block" style="text-align: right;">
				<a href="javascript:;" data-reveal-id="createNewTemplate"
					class="primary_btn blue_bg blue_color">+ new template</a>
			</div>
		</div>
		<div class="cus_template_headBottom">
			<div class="inline_block">
				<p>
					Total Template: <b>${templateList.totalTemplate}</b>
				</p>
			</div>
			<div class="inline_block cus_temp_rgt_module">
				<div class="onSelectTemp">
					<div class="showing_icons" id="showingIcons">
						<a href="javascript:;" data-reveal-id="deleteTemplate"> Ok<img
							title="delete" src="../static/images/administrator/delete.svg"
							alt="action img">
						</a> <a href="javascript:;" data-reveal-id="saveTemplatePopup"> <img
							title="move to folder"
							src="../static/images/administrator/ic_move_to_fldr.svg"
							alt="action img" style="width: 13px">
						</a> <a href="javascript:;" data-reveal-id="shareTemplate"> Ok <img
							title="share" src="../static/images/administrator/ic_share.svg"
							alt="action img">
						</a>
					</div>
				</div>
				<div class="cus_temp_module inline_block">
					<p>Module</p>
					<div class="inline_block module_list">
						<select name="moduleList" id="moduleList"
							onchange="getTemplatesByModule(this.value);">
							<option value="">All Module</option>
							<c:forEach items="${moduleList.data}" var="module">
								<option value="${module.id}">${module.value}</option>
							</c:forEach>
						</select>

					</div>
				</div>
				<div class="cus_temp_search inline_block">
					<span> <input type="text" name="templateSearch"
						id="templateSearch" placeholder="Search by name"> <i
						class="fa fa-search" aria-hidden="true" id="templateSearchEvent"></i>
					</span>
				</div>
			</div>
		</div>
	</div>

	<div class="cus_template_body " id="tRow">
		<div class="cus_template_body_left">
			<div class="containerOne">
				<label> <input type="checkbox"
					id="parentCheckbox" name="parentCheckbox" /> <span
					class="checkmark"></span> Name of the template
				</label>
			</div>
			<div id="templateListDynamicDiv"></div>

		</div>
	</div>


	<!-- TODO update count -->
	<div class="cus_template_body_rgt">
		<div class="cus_temp_reg_secTop">
			

			<div class="cus_temp_all filter_all_temp active"
				onClick="getTemplatesByFilter(this.id)" id="">
				<div>
					<img src="../static/images/administrator/all_template.svg"
						alt="action img">
				</div>
				<p>
					<a> <span>All</span> <small>${templateList.totalTemplate}</small>
					</a>
				</p>
			</div>

			<div class="cus_temp_favorate filter_all_temp" id="FV"
				onClick="getTemplatesByFilter(this.id)">
				<div>
					<img src="../static/images/administrator/ic_sent.svg"
						alt="action img">
				</div>
				<p>
					<a> <span>Favorite</span> <small id="fav-count-dynamic">${templateList.favCount}</small>
					</a>
				</p>
			</div>

			<div class="cus_temp_createdBy filter_all_temp" id="ME"
				onClick="getTemplatesByFilter(this.id)">
				<div>
					<img src="../static/images/administrator/ic_draft.svg"
						alt="action img">
				</div>
				<p>
					<a> <span>Created By Me</span> <small
						id="created-count-dynamic">${templateList.createdByMeCount}</small>
					</a>
				</p>
			</div>

			<div class="cus_temp_createdBy filter_all_temp" id="SH"
				onClick="getTemplatesByFilter(this.id)">
				<div>
					<img src="../static/images/administrator/ic_scheduled.svg"
						alt="action img">
				</div>
				<p>
					<a> <span>Share with me</span><small id="shared-count-dynamic">${templateList.sharedCount}</small>
					</a>
				</p>
			</div>

			<div class="cus_temp_favorate filter_all_temp" id="PB"
				onClick="getTemplatesByFilter(this.id)">
				<div>
					<img src="../static/images/administrator/ic_public.svg"
						alt="action img">
				</div>
				<p>
					<a> <span>Public</span> <small id="public-count-dynamic">${templateList.publicCount}</small>
					</a>
				</p>
			</div>


		</div>
		<div class="cus_temp_reg_secBottom">
			<h3 style:margin-bottom:10px>Folders</h3>
			<div class="cus_member_checkboxGrp" id="shareTemp_popup">
				<div class="createFolderDiv">
					<div class="inline_block">
						<input type="text" placeholder="create new folder"
							onInput="removeError();" id="folder-name">
					</div>
					<div class="inline_block" id="create_new_folder"
						onClick="createFolder();">+</div>
				</div>
				<ul class="custom_scroll">

					<div id="folderListDynamic"></div>
					<div id="folder-list">
						<c:forEach items="${folderList.data}" var="folder">
							<li><a href="javascript:;"
								onClick="getTemplatesByFolderId('${folder.id}');"
								folder-id="${folder.id}">${folder.value}</a>
								<div class="showing_icons">
									<a href="javascript:;" data-reveal-id="editFolder" folder-old-id="${folder.id}" folder-existing-name="${folder.value}"
										onClick="getEditFolderId(this);"><img title="edit"
										src="../static/images/administrator/ic_edit.svg"
										alt="action img"></a> <a href="javascript:;"
										data-reveal-id="deleteTemplateFolder"
										onClick="getDeleteFolderId(${folder.id});"><img
										title="delete" src="../static/images/administrator/delete.svg"
										alt="action img"></a>
								</div></li>
							<%-- 		<li><label class="custom_cehckbox"> ${folder.value}
									<input type="radio" name="folderRadioBtn"
									folder-id="${folder.id}"> <span class="checkmark"></span>
							</label></li> --%>
						</c:forEach>
					</div>

				</ul>
			</div>

		</div>
		<!-- Removed After discussion -->
		<%-- 		<div class="cus_temp_reg_secBottom">
			<h3>Members</h3>
			<div class="cus_temp_reg_secBottom_search">
				<span> <input type="text" placeholder="Search by name">
					<i class="fa fa-search" aria-hidden="true" id="searchEvent"></i>
				</span>
			</div>
			<div class="cus_member_checkboxGrp">
				<ul class="custom_scroll">
					<c:forEach items="${userList.data}" var="user">
						<li><label class="custom_cehckbox"> <input
								type="checkbox" id="checkbox2" name="checkbox2"
								value="${user.id}"> <span class="checkmark"></span>
								${user.value}
						</label></li>
					</c:forEach>
				</ul>
			</div>
		</div> --%>
	</div>

</div>




<!-- delete custom template popup html here  -->

<div id="deleteTemplate" class="deleteTemplateUpdate reveal-modal" data-reveal
	aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
	<div class="bodyPart">
	<div class="inline_block">
		<img alt="deleteimg" src="../static/images/administrator/popup-delete.svg">
	</div>
	<div class="inline_block">
			<p>Are you sure, you want to delete the templates ?</p>
	
	</div>
	</div>
	<div class="modal-body">
		<input type="hidden" name="template-delete-Id" id="template-delete-Id" />
	</div>
	<div class="footer_section">
		<ul>
			<li><a href="javascript:;"
				class="primary_btn cancel_btn close-reveal-modal" aria-label="Close">Cancel</a></li>
			<li><a class="blue_bg _whiteBg primary_btn blue_hover"
					id="deleteBtn">Ok</a></li>
		</ul>
	</div>

	<a class="close-reveal-modal" aria-label="Close">&#215;</a>
</div>

<!-- Delete fodler popup -->

<div id="deleteTemplateFolder" class="deleteTemplateUpdate reveal-modal" data-reveal
	aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
	<div class="bodyPart">
		<div class="inline_block">
			<img alt="deleteimg" src="../static/images/administrator/popup-delete.svg">
		</div>
		<div class="inline_block">
		<p>Are you sure, you want to delete folder ?</p>
		</div>
	</div>
	<div class="modal-body-deleteFolder">
		<input type="hidden" id="folder-delete-Id" />
	</div>
	<div class="footer_section">
		<ul>
			<li><a href="javascript:;"
				class="primary_btn cancel_btn close-reveal-modal" aria-label="Close">Cancel</a></li>
			<li><a class="blue_bg _whiteBg primary_btn blue_hover"
					id="deleteFolderBtn">Ok</a></li>
		</ul>
	</div>

	<a class="close-reveal-modal" aria-label="Close">&#215;</a>
</div>


<!--  share template pop-up -->
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
				class="primary_btn cancel_btn close-reveal-modal" aria-label="Close">Cancel</a></li>
			<li class="shareTemp" id="shareTemp"><a href="javascript:;"
				class="blue_bg _whiteBg primary_btn blue_hover" id="shareBtn">share</a></li>
		</ul>
	</div>
	<a class="close-reveal-modal" aria-label="Close">&#215;</a>
</div>


<div id="createNewTemplate" class="reveal-modal" data-reveal
	aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
	<h2 id="modalTitle">Create New Template</h2>
	<div class="bodyPart">
		<div class="create_template_info">
			<div class="inline_block">
				<p>MODULE<span class="StrickStar">*</span></p>
			</div>
			<div class="inline_block">
				<!--  <p class="custom-select"> -->
				<select id="module-list-dropDown" class="select_option"
					oninput="removeError()">
					<option value="">Select Module</option>
					<c:forEach items="${moduleList.data}" var="module">
						<option value="${module.id}">${module.value}</option>
					</c:forEach>
				</select>

				<!-- </p> -->
			</div>
		</div>
		<div class="create_template_info">
			<div class="inline_block">
				<p>Template NAME<span class="StrickStar">*</span></p>
			</div>
			<div class="inline_block">
				<input type="text" placeholder="Enter the template name"
					class="inputt" id="template-name" onInput="removeError()">
			</div>
		</div>
		<div class="create_template_info">
			<div class="inline_block">
				<p>Template Subject<span class="StrickStar">*</span></p>
			</div>
			<div class="inline_block">
				<input type="text" placeholder="Enter the template subject"
					id="template-subject" class="inputt" onInput="removeError()">
			</div>
		</div>
	</div>
	<div class="footer_section">
		<ul>
			<li><a href="javascript:;"
				class="primary_btn cancel_btn close-reveal-modal" aria-label="Close">Cancel</a></li>
			<li><a href="javascript:;" onClick="addTemplate()" id="nextBtn"
				class="blue_bg _whiteBg primary_btn blue_hover">Next</a></li>
		</ul>
	</div>

	<a class="close-reveal-modal" aria-label="Close">&#215;</a>
</div>


<div id="saveTemplatePopup" class="reveal-modal" data-reveal
	aria-labelledby="modalTitle" role="dialog" aria-hidden="true"
	role="dialog">
	<h2 id="modalTitle">Move templates to folder</h2>
	<div class="bodyPart">
		<div class="save_template_popup inline_block">
			<span>move to folder<span class="StrickStar">*</span></span>
		</div>
		<div class="modalBodyMoveFolder">
			<input type="hidden" id="single-template-moveId" />
		</div>
		<div class="inline_block searchFolder selectedDefault">
			<span id="select_folder_name">Select Folder</span>
			<div class="shareTermp_seelctGrp" id="shareTemp_popup">

				<ul class="custom_scroll">
					<li><label class="custom_cehckbox"> Select Folder <input
							type="radio" name="folderRadioBtn" folder-id=""> 
					</label></li>
					<c:forEach items="${folderList.data}" var="folder">
						<li><label class="custom_cehckbox"> ${folder.value} <input
								type="radio" name="folderRadioBtn" folder-id="${folder.id}">
								<span class="checkmark"></span>
						</label></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div class="footer_section">
		<ul>
			<li><a href="javascript:;"
				class="primary_btn cancel_btn close-reveal-modal" aria-label="Close">Cancel</a></li>
			<li><a href="javascript:;"
				class="blue_bg _whiteBg primary_btn blue_hover"
				onClick="moveTemplateToFolder();" id="move-tempalte-btn">save</a></li>
		</ul>
	</div>
	<a class="close-reveal-modal" aria-label="Close">&#215;</a>
</div>

<div id="afterShareActionPopup" class=" afterActionPopup reveal-modal"
	data-reveal aria-labelledby="modalTitle" role="dialog"
	aria-hidden="true" role="dialog">
	<a class="close-reveal-modal" aria-label="Close">&#215;</a>

	<div class="bodyPart">

		<div class="inline_block">
			<span class="checkmark">
				<div class="checkmark_stem"></div>
				<div class="checkmark_kick"></div>
			</span>
		</div>
		<div class="inline_block">Share template successfully.</div>

	</div>
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

  
<div id="editFolder" class="reveal-modal" data-reveal
	aria-labelledby="modalTitle" role="dialog" aria-hidden="true"
	role="dialog">
	<h2 id="modalTitle">Rename Folder</h2>
	<div class="bodyPart">
		<div class="create_template_info">
			<div class="inline_block">
				<p>New Folder Name<span class="StrickStar">*</span> </p>
			</div>
			<span class="folderNameBody"> <input type="hidden"
				id="folderNameId" />
			</span>

			<div class="inline_block">

				<input type="text" id="folder-new-name" placeholder="Enter new folder name"
					 class="inputt" onInput="removeError();">
			</div>
		</div>
	</div>
	<div class="footer_section">

		<div class="inline_block">
			<ul>
				<li><a href="javascript:;"
					class="primary_btn cancel_btn close-reveal-modal"
					aria-label="Close">Cancel</a></li>
				<li><a href="javascript:;" onClick="editFolderName()"
					class="blue_bg _whiteBg primary_btn blue_hover">ok</a></li>
			</ul>

		</div>
	</div>
	<a class="close-reveal-modal" aria-label="Close">&#215;</a>
</div>



<script>
	function singleDeleteTemplate(element) {
		var id = $(element).attr('data-template-deleteId');
		$(".modal-body #template-delete-Id").val(id);

	}

	function getDeleteFolderId(value)
	{
		$(".modal-body-deleteFolder #folder-delete-Id").val(value);
	}

	function getEditFolderId(element){
		var id = $(element).attr('folder-old-id');
		var name = $(element).attr('folder-existing-name');
		$(".folderNameBody #folderNameId").val(id);
		$("#folder-new-name").val(name);
		}

	function editFolderName()
	{
		var folderId=$('#folderNameId').val();
		var folderName=$('#folder-new-name').val();
		if (folderName == '') {
			$('#folder-new-name').addClass('errorClass');
		}
		if(folderName.trim()!='' && folderId){
          var data={
                  "name":folderName,
                  "folderId":folderId
                   }
           $.ajax({
   			type : "POST",
   			contentType : "application/json",
   			url : '<c:url value="/template/folder/edit"/>',
   			data : JSON.stringify(data),
   			success : function(response) {
 				$('.reveal-modal').trigger('reveal:close');
             	setTimeout(function() { 
            		location.reload();
	            }, 3000);
				$("#afterActionPopupText").append("Folder rename successfully");
        		$('.ShowAfterAction').show();
   			},
   			error : function(e) {
   				location.reload();
   			}
   		});
           
			}
		if(folderName.trim()=='')
			{
			$('#folder-new-name').addClass("errorClass");
			}

		}

	
	function MoveSingleTemplateToFolder(element) {
		var id = $(element).attr('single-moveId');
		$(".modalBodyMoveFolder #single-template-moveId").val(id);

	}
	function moveTemplateToFolder() {
		
		var folderId = $('input[name=folderRadioBtn]:checked')
				.attr('folder-id');
		if (folderId != '' && folderId != ''
				&& typeof folderId !== 'undefined') {
			//ToDo single templateId for move
			var singleTemplateId = $('#single-template-moveId').val();
			var templateIdList = "";
			if (singleTemplateId != '' || singleTemplateId.trim() != '') {
				templateIdList = singleTemplateId;
			} else {
				$('input[name="checkbox"]:checked').each(function() {
					var id = $(this).attr('template-id');
					templateIdList += id + ",";
				})
			}
			var data = {
				"folderId" : folderId,
				"templateIdList" : templateIdList
			}
			moveTemplates(data);
		} else {
			 	$('.searchFolder.selectedDefault').addClass("errorClass");
 		}

	}

	function moveTemplates(data) {
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : '<c:url value="/template/moveTo/folder"/>',
			data : JSON.stringify(data),
			success : function(response) {
				$('.reveal-modal').trigger('reveal:close');
             	setTimeout(function() { 
            		location.reload();
	            }, 3000);
				$("#afterActionPopupText").append("Template move successfully");
        		$('.ShowAfterAction').show();
 	            
				
			},
			error : function(e) {
				location.reload();
			}
		});
	}
	

	function getTemplatesByFolderId(value) {
		$('#templateSearch')
		.val('');
		parentCheckbox.checked = false;
		localStorage.setItem("filter","");
		localStorage.setItem("pageNo",0);
		
		var moduleId=localStorage.getItem("moduleId");
		if (moduleId!= '' && value != '') {
			data = {
				"folderId" : value,
				"moduleId" : moduleId
			}
		} else if (moduleId == '' && value != '') {
			data = {
				"folderId" : value
			}
		}
		localStorage.setItem("folderId", value);
		getTemplates(data);

	}
	function createFolder() {
		var folderName = $('#folder-name').val();
		if (folderName != '') {
			var data = {
				"name" : folderName
			}
			createNewFolder(data);
		} else {
			$('#folder-name').addClass("errorClass");
			setTimeout(function() { 
				$('#folder-name').removeClass("errorClass"); 
				}, 2000);
			
		}
	}
	function createNewFolder(data) {
		$
				.ajax({
					type : "POST",
					contentType : "application/json",
					url : '<c:url value="/template/create/folder"/>',
					data : JSON.stringify(data),
					success : function(response) {
						$
								.ajax({
									type : "GET",
									contentType : "application/json",
									url : "/email/template/folders",
									success : function(response) {
										var markup = "";
										$('#folderListDynamic').empty();
										$
												.each(
														response.data,
														function(i, data) {
															var folderId = data.id;
															var folderName = data.value;
															$('#folder-list')
																	.remove();
															
															markup = "<li><a href='javascript:;' "
																+ "onClick='getTemplatesByFolderId("
																	+ folderId
																	+ " );'>"
											
																	+ folderName
																	+ "</a>"
																	+ "<div class='showing_icons'>"
																	+ " <a href='javascript:;' data-reveal-id='editFolder' "
																	+ "folder-old-id="+ folderId +" folder-existing-name="+ folderName +" "
																	+ " onClick='getEditFolderId(this "
																	+ " );'>"
																	+ "<img title='edit' "
																	+ "src='../static/images/administrator/ic_edit.svg' "
																	+ " alt='action img'> </a> <a href='javascript:;' data-reveal-id='deleteTemplateFolder' "
																	+ "onClick='getDeleteFolderId( "
																	+ folderId
																	+ " );'>"
																	
																	+ "<img title='delete' src='../static/images/administrator/delete.svg' alt='action img'></a>"
																	+ "</div></li>"

															$('#folder-name')
																	.val('');
															$(
																	'#folderListDynamic')
																	.append(
																			markup);
														});
									},
									error : function(e) {
										console.log(e);
									}
								});
						location.reload();

					},
					error : function(e) {
						location.reload();
					}
				});
	}
	function addToFavorite(element) {
		$(element).toggleClass('addFavrate');
		var data = {};
		var templateId = $(element).attr('id');
		var unfavorite;
		if ($(element).hasClass('addFavrate')) {
			unfavorite = "false"
		} else {
			unfavorite = "true"
		}
		data = {
			"unfavorite" : unfavorite,
			"templateId" : templateId
		}
		addFavorite(data);
	}
	function addFavorite(data) {
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : '<c:url value="/template/favourite"/>',
			data : JSON.stringify(data),
			success : function(response) {
 				$('.reveal-modal').trigger('reveal:close');
             	setTimeout(function() { 
            		location.reload();
	            }, 2000);
	            if(data.unfavorite=='true')
		            {
				$("#afterActionPopupText").append("Template removed from favourite successfully");
		            }
	            else 
		            {
	            	$("#afterActionPopupText").append("Template added to  favourite successfully");
		            }
        		$('.ShowAfterAction').show();
			},
			error : function(e) {
				console.log(e);
			}
		});
	}

	function multiSelectMethod() {

		var allChecked = 0;
		var cList = document.getElementsByName('checkbox');
		var parentCheckbox = document.getElementById('parentCheckbox');

		for (i = 0; i < cList.length; i++) {
			if ((cList[i].checked))
				allChecked++;
		}

		if (allChecked == cList.length) {
			parentCheckbox.checked = true;
			$('#showingIcons').show();

		} else {
			parentCheckbox.checked = false;
			$('#showingIcons').hide();
		}
		if (allChecked > 0) {
			$('#showingIcons').show();

		} else if (allChecked == 0) {
			$('#showingIcons').hide();
		} else if (allChecked == 1) {
			$('#showingIcons').hide();

		}

	}
	function removeError() {
		$('#template-name').removeClass('errorClass');
		$('#module-list-dropDown').removeClass('errorClass');
		$('#template-subject').removeClass('errorClass');
		$('#folder-name').removeClass('errorClass');
		$('#folder-new-name').removeClass('errorClass');
		

	}

	function addTemplate() {
		var templateName = $('#template-name').val();
		var templateSubject = $('#template-subject').val();
		var moduleId = $('#module-list-dropDown').val();

		if (templateName.trim() == '') {
			$('#template-name').addClass('errorClass');
		}
		if (moduleId == '') {
			$('#module-list-dropDown').addClass('errorClass');
		}
		if (templateSubject.trim() == '') {
			$('#template-subject').addClass('errorClass');
		}

		if (templateName != '' && templateSubject != '' && moduleId != '') {

			var url = "/email/template/addTemplate.html?templateName="
					+ templateName + "&&moduleId=" + moduleId
					+ "&&templateSubject=" + templateSubject
			window.location.href = url;
		}
	}
	function shareSingleTemplate(element) {
		var id = $(element).attr('data-share-templateId');
		$(".share-modal-body #shareId").val(id);
	}

	function deleteTemplates(data) {
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : '<c:url value="/template/delete"/>',
			data : JSON.stringify(data),
			success : function(response) {
				console.log(response);
 				$('.reveal-modal').trigger('reveal:close');
             	setTimeout(function() { 
            		location.reload();
	            }, 3000);
				$("#afterActionPopupText").append("Delete template successfully");
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
											$('#userListDynamic')
													.append(markup);
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
				// $('#deleteTemplate').show();
				// location.reload();
 				$('.reveal-modal').trigger('reveal:close');
             	setTimeout(function() { 
            		location.reload();
	            }, 2000);
				$("#afterActionPopupText").append("Template share successfully");
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
	function getTemplatesByModule(value) {
		$(window).scrollTop(0); 
		localStorage.setItem("pageNo",0);
		var folderId=localStorage.getItem("folderId");
		$('#templateSearch')
		.val('');
		parentCheckbox.checked = false;
		var data = {};
		var filter = localStorage.getItem('filter');
		var folderId=localStorage.getItem('folderId');
		if(folderId!='' && value!='')
			{
              data={
                      "moduleId":value,
                      "folderId": folderId 
                      }
			}
		else if (filter != '' && value != '') {
			data = {
				"moduleId" : value,
				"filter" : filter
			}
		} else if (filter != '' && value == '') {
			data = {
				"filter" : filter
			}
		} else if (value != '') {
			data = {
				"moduleId" : value
			}
		}
		localStorage.setItem("moduleId", value);
		getTemplates(data);

	}

	function getTemplatesByFilter(value) {
		localStorage.setItem('pageNo',0);
		localStorage.setItem('folderId',"");
		$('#templateSearch')
		.val('');
		parentCheckbox.checked = false;
		var data = {};
		var moduleId = localStorage.getItem('moduleId');
		if (value != '' && moduleId != '') {
			data = {
				"moduleId" : moduleId,
				"filter" : value
			}
		} else if (value != '' && moduleId == '') {
			data = {
				"filter" : value
			}
		} else if (moduleId != '') {
			data = {
				"moduleId" : moduleId
			}
		}
		localStorage.setItem("filter", value);
		getTemplates(data);

	}

	function getTemplates(data) {
		$
				.ajax({
					type : "POST",
					contentType : "application/json",
					url : '<c:url value="/template/all"/>',
					data : JSON.stringify(data),
					success : function(response) {
						var pageNo=localStorage.getItem('pageNo');
						$('#showingIcons').hide();
						
						if (!$.trim(response)) {
							//Empty message show
						} else if(response.data==null)
								{
							    $('#showingIcons').hide();
							    $('#created-count-dynamic').text("0");
							    $('#fav-count-dynamic').text("0");
							    $('#public-count-dynamic').text("0");
							    $('#shared-count-dynamic').text("0");
							    $('#templateListDynamicDiv').empty();
								localStorage.setItem('pageNo',2000);
								}
						else {
							if(response.data!=null){
							 var nextPage=response.next;
							 $('#next-page').val(nextPage);
							if(response.createdByMeCount!=null)
								{
							var createdByMeCount=response.createdByMeCount;
							$('#created-count-dynamic').text(createdByMeCount);
								}
							else
								{
								$('#created-count-dynamic').text("0");
								}
							if(response.favCount!=null)
							{
								var favouriteCount=response.favCount;
								$('#fav-count-dynamic').text(favouriteCount);
							}
						else
							{
							$('#fav-count-dynamic').text("0");
							}
							if(response.publicCount!=null)
							{
								var publicCount=response.publicCount;
								$('#public-count-dynamic').text(publicCount);
							}
						else
							{
							$('#public-count-dynamic').text("0");
							}
							if(response.data.sharedCount!=null)
							{
								var sharedCount=response.sharedCount;
								$('#shared-count-dynamic').text(sharedCount);
							}
						else
							{
							$('#shared-count-dynamic').text("0");
							}
							}
							
							var markup = "";		
							var pageNo=localStorage.getItem('pageNo');
							if(pageNo==0)
								{
							$('#templateListDynamicDiv').empty();
							/* $('#templateListMainDiv').remove(); */
								}
							if(response.data!=null)
								{
							$
									.each(
											response.data,
											function(i, data) {
												var tId = data.templateId;
												var tName = data.name;
												var tCreatedBy = data.createdByName;
												var tCreatedDate = data.createdDate;
												var moduleName = data.moduleName;
                                                var isFavourite=data.isFavourite;
                                                var isDeletable=data.isDeletePermission;
                                                var isEditable=data.isEditPermission;
                                                var category=data.category;
                                                var privateImgMarkup="";
                                                var favImageMarkup="";
                                                var editImageMarkup="";
                                                var deleteImageMarkup="";
                                                var moduleId=data.moduleId;
                                                if(category=='private')
                                                    {
                                                   
                                                	privateImgMarkup  = " <img src='../static/images/administrator/private_img.svg' alt='private img'>"

                                                	     if(isFavourite==true)
                                                         {
                                                     	favImageMarkup="<i class='fa fa-star addFavrate' onClick='addToFavorite(this)' aria-hidden='true' id="+ tId+ "></i> "
                                                         }
                                                     else
                                                         {
                                                     	favImageMarkup="<i class='fa fa-star' onClick='addToFavorite(this)' aria-hidden='true' id="+ tId+ "></i> "
                                                         }
                                                    
                                                markup=	"<ul><li> <div class='inline_block'> <label class='containerOne'>"
													+ "<input type='checkbox' name='checkbox' template-id= "
													+ tId
													+ " onClick='multiSelectMethod();'>"
													+ "<span class='checkmark'></span>"
													+ favImageMarkup
													+ "</div>"
													+ "<div class='inline_block'>"
													+ "<h3><a href='/email/template/templateDetails.html?templateId="
													+ tId
													+ "'>"
													+ tName
													+ "</a>"
													+ privateImgMarkup
												    + "</h3>"
													+ "<p>"
													+ "Module: <b>"
													+ moduleName
													+ " </b> | created By: <b>"
													+ tCreatedBy
													+ "</b>"
													+ "at <b>"
													+ tCreatedDate
													+ "</b>"
													+ "</p></div>"
													+ "<div class='inline_block OnHover_show_icons' id='tooltipIcons'>"
													+ "<div class='showing_icons'>"
													+ "<a href='javascript:;' title='share'"
													+ "data-reveal-id= 'shareTemplate' "
													+ "data-share-templateId= "
													+ tId
													+ " "
													+ "onClick='shareSingleTemplate(this)'"
													+ "'>"
													+ "<img src='../static/images/administrator/ic_share.svg'"
												    + "alt='action img' id='shareSingleTemplate'></a>"
													+ "<a href='/email/template/editTemplate.html?templateId="
													+ tId
													+ "&&moduleId="
													+ moduleId
													+ " 'title='edit'>"
													+ "<img src='../static/images/administrator/ic_edit.svg'"
												    + "alt='action img'></a> <a href='javascript:;' title='delete'"

													+ "data-reveal-id='deleteTemplate'"
													+ " data-template-deleteId= "
													+ tId
													+ " "
													+ "onClick='singleDeleteTemplate(this)' "
													+ ">"
													+ "<img src='../static/images/administrator/delete.svg'"
												    + "alt='action img' id='deleteSingleTemplate'></a>"

												    + "<a href='javascript:;' data-reveal-id='saveTemplatePopup' "
												    + "single-moveId="
												    +tId
												    + " "
												    + "onClick='MoveSingleTemplateToFolder(this);' " 
												    + ">"
													+ "<img title='move to folder' src='../static/images/administrator/ic_move_to_fldr.svg' "
													+ "alt='action img' style='width: 13px'>"
												    + "</a>"

												    
													+ "</div>"
													+ "</div>"
													+ "</li></ul>"
                                                    }
                                                if(category=='public' || category=='share')
                                                    {

                                                    if(isFavourite==true)
                                                        {
                                                    	favImageMarkup="<i class='fa fa-star addFavrate' onClick='addToFavorite(this)' aria-hidden='true' id="+ tId+ "></i> "
                                                        }
                                                    else
                                                        {
                                                    	favImageMarkup="<i class='fa fa-star' onClick='addToFavorite(this)' aria-hidden='true' id="+ tId+ "></i> "
                                                        }
                                                          if(isEditable== true)
                                                              {
                                                        	  editImageMarkup="<a href='/email/template/editTemplate.html?templateId="
          													+ tId
        													+ "&&moduleId="
        													+ moduleId
        													+ " 'title='edit'>"
        													+ "<img src='../static/images/administrator/ic_edit.svg'"
        												    + "alt='action img'></a>"
                                                              }
                                                          if(isDeletable==true)
                                                              {
                                                        	  deleteImageMarkup="<a href='javascript:;' title='delete'"
          														+ "data-reveal-id='deleteTemplate'"
        														+ " data-template-deleteId= "
        														+ tId
        														+ " "
        														+ "onClick='singleDeleteTemplate(this)' "
        														+ ">"
        														+ "<img src='../static/images/administrator/delete.svg'"
        													    + "alt='action img' id='deleteSingleTemplate'></a>"
                                                              }
                                                    
                                                markup = "<ul><li> <div class='inline_block'> <label class='containerOne'>"
													+ "<input type='checkbox' name='checkbox' template-id= "
													+ tId
													+ " onClick='multiSelectMethod();'>"
													+ "<span class='checkmark'></span>"
													+ "</label> "
													+ favImageMarkup
													+ "</div>"
													+ "<div class='inline_block'>"
													+ "<h3><a href='/email/template/templateDetails.html?templateId="
													+ tId
													+ "'>"
													+ tName 
													+ "</a>"
													+ privateImgMarkup
													+ "</h3>"
													+ "<p>"
													+ "Module: <b>"
													+ moduleName
													+ " </b>| created By: <b>"
													+ tCreatedBy
													+ "</b>"
													+ "at <b>"
													+ tCreatedDate
													+ "</b>"
													+ "</p></div>"
													+ "<div class='inline_block OnHover_show_icons' id='tooltipIcons'>"
													+ "<div class='showing_icons'>"
													+ editImageMarkup
                                                    + deleteImageMarkup

												    + "<a href='javascript:;' data-reveal-id='saveTemplatePopup' "
												    + "single-moveId="
												    +tId
												    + " "
												     + "onClick='MoveSingleTemplateToFolder(this);' " 
												    + ">"
													+ "<img title='move to folder' src='../static/images/administrator/ic_move_to_fldr.svg' "
													+ "alt='action img' style='width: 13px'>"
												    + "</a>"
												    
													+ "</div>"
													+ "</div>"
													+ "</li></ul>"
                            
                                                    }
                                                $('#templateListDynamicDiv')
												.append(markup);
											
											
											});
						
						}

						}},
					error : function(e) {
						location.reload();
					}
				});

	}

	$(document).ready(function() {
		localStorage.clear();
		$(window).scrollTop(0);
		var pageNo=0
        $('#moduleList').val("All Module");
		$(".cus_member_checkboxGrp .custom_scroll li").click(function(){
			$(window).scrollTop(0);
			$(this).addClass('active').siblings('li').removeClass('active');
			$(this).parents('.cus_temp_reg_secBottom').siblings('.cus_temp_reg_secTop').find('.filter_all_temp.active').removeClass('active');
		});
		
		localStorage.setItem('pageNo',0);
		var data={};
        getTemplates(data);
        $(".searchFolder > span").click( function(e){
            e.stopPropagation();
            $(".shareTermp_seelctGrp").slideToggle();
            
        });
        $("#saveTemplatePopup").click(function() {
            if ($(".shareTermp_seelctGrp").is(":visible")) {
                $(".shareTermp_seelctGrp").slideUp();
            }
        });

		$("#shareTemp_popup ul li input[type='radio']").click(function() {
			if ($(this).is(":checked")) {

				var select_folder = $(this).parent('label').text();
			}
			$("#select_folder_name").text(select_folder);
			$(".shareTermp_seelctGrp").slideUp();
		});

		$(".filter_all_temp").click(function() {
			$(window).scrollTop(0);
			$(this).addClass('active').siblings().removeClass("active");
			$(this).parent('.cus_temp_reg_secTop').siblings('.cus_temp_reg_secBottom').find('li.active').removeClass('active');
		});
		$('#showingIcons').hide();

		$('#parentCheckbox').click(function() {
			$('#showingIcons').show();

			var cList = document.getElementsByName('checkbox');
			var parentCheckbox = document.getElementById('parentCheckbox');
			if (parentCheckbox.checked) {
				for (i = 0; i < cList.length; i++)
					cList[i].checked = true;
				$('#showingIcons').show();

			} else {
				for (i = 0; i < cList.length; i++)
					cList[i].checked = false;
				$('#showingIcons').hide();
			}
		});


		$('#deleteBtn').click(function() {
			
            var folderId=localStorage.getItem("folderId");
			var singleDeleteId = $("#template-delete-Id").val();
			var data={};
			var templateIdList = "";
			if (singleDeleteId != '' || singleDeleteId.trim() != '') {
				templateIdList = singleDeleteId;
			} else {
				$('input[name="checkbox"]:checked').each(function() {
					var id = $(this).attr('template-id');
					templateIdList += id + ",";
				})
			}
			if(!folderId){
			 data = {
				"templateIdList" : templateIdList
			}
			}
			else {
				data= {"templateIdList" : templateIdList,
				"folderId" : folderId
				}
				}

			
			deleteTemplates(data);
		});

$('#deleteFolderBtn').click(function() {
	 var folderId=$('#folder-delete-Id').val();
     if(folderId!='')
         {
         var data={"folderId":folderId}
   	  $.ajax({
 			type : "POST",
 			contentType : "application/json",
 			url : '<c:url value="/template/folder/delete"/>',
 			data : JSON.stringify(data),
 			success : function(response) {
 				$('.reveal-modal').trigger('reveal:close');
             	setTimeout(function() { 
            		location.reload();
	            }, 3000);
				$("#afterActionPopupText").append("Delete folder successfully");
        		$('.ShowAfterAction').show();
 			},
 			error : function(e) {
 				console.log(e);
 			}
 		});
         }
});

		$('#shareBtn').click(function() {
			var singleShareId = $("#shareId").val();
			var usersIdList = "";
			var templateIdList = "";
			if (singleShareId != '' || singleShareId.trim() != '') {
				templateIdList = singleShareId;
			} else {
				$('input[name="checkbox"]:checked').each(function() {
					var id = $(this).attr('template-id');
					templateIdList += id + ",";
				})
			}
			$('input[name="checkbox2"]:checked').each(function() {
				var id = $(this).attr('data-user-id');
				usersIdList += id + ",";
			});
			var data = {
				"templateIdList" : templateIdList,
				"usersIdList" : usersIdList
			};
			shareTemplates(data);
		});

		$("#templateSearch").keyup(delaySearchTime(function(e) {
			localStorage.setItem('pageNo',0);
			
			var searchText = $("#templateSearch").val();
			var filter = localStorage.getItem('filter');
			var moduleId=localStorage.getItem('moduleId');
			var folderId=localStorage.getItem('folderId');
			var data = {};
			if(searchText.trim() != ''){
				localStorage.setItem("searchText",searchText);
			if(filter!='' && moduleId!=''){
				data = {
					"searchText" : searchText,
					"filter": filter,
			        "moduleId":moduleId	
				}
			}
			else if(moduleId && folderId){
                 data={
                         "modulleId":moduleId,
                         "folderId":folderId,
                         "searchText" : searchText
                         }
				}
			else if(filter)
			{
			data = {
					"searchText" : searchText,
					"filter": filter
				}
			}
			else if(folderId)
				{
				data={
						"searchText" : searchText,
                        "folderId":folderId
                        }
				}
		else if(moduleId){
			data = {
					"searchText" : searchText,
					"moduleId":moduleId	
				}
			}
		else {
			   data={"searchText": searchText}
			 }
			getTemplates(data);
			}
			else 
				{
				if(moduleId && filter){
					data = {
						"filter": filter,
				        "moduleId":moduleId	
					}
				}
				else if(moduleId && folderId){
					data = {
							"folderId": folderId,
					        "moduleId":moduleId	
						}

					}
					else if(filter)
				{
				  data = {
						"filter": filter
					}
				}
				
			else if(moduleId){
				data = {
						"moduleId":moduleId	
					}
				}
			else if(folderId){
				data = {
						"folderId":folderId	
					}
				}
				getTemplates(data);
				}
		}, 500));

		$("#userSearch").keyup(delaySearchTime(function(e) {
			var userSearchText = $("#userSearch").val();
			var data = {};
			if (userSearchText.trim() != '' || !userSearchText.length === 0) {
				data = {
					name : userSearchText
				}
				getUsersBySearch(data);
			} else {
				getUsersBySearch(data);
			}

		}, 500));
		
		$(window).scroll(function() {
			   if(Math.round($(window).scrollTop()) == $(document).height() - $(window).height()) {  
				    var nextPage=$('#next-page').val();
					 pageNo=localStorage.getItem('pageNo');
					 pageNo=parseInt(pageNo)+1;
					 localStorage.setItem('pageNo',pageNo);
			         var filter=localStorage.getItem("filter");
			         var folderId=localStorage.getItem("folderId");
			         var moduleId=localStorage.getItem("moduleId");
			         var searchText=localStorage.getItem("folderId");
			         var data={};
			        
			         if(!folderId) 
				         {
			          data={
					           "page":pageNo,
					           "filter":filter,
					           "searchText":searchText,
					           "moduleId": moduleId,
					         }
				         }
			         else
				         {
                           data=  {
                                   "page":pageNo,
    					           "searchText":searchText,
    					           "moduleId": moduleId
                           }      
                          }
                     if(nextPage=='true'){
                     getTemplates(data);
                     }
				}
			});
	});

	$(".shareTemp").append("<span id='appendSpan'></span>");
	
	 $('#shareTemplate li input').on('change', function(){
		 
			var shareCheck = $("input:checked");

			 if(shareCheck.length < 1) {
				 
				 $('#shareTemp').addClass("shareTemp");
			     $(".shareTemp").append("<span id='appendSpan'></span>");
 			  } 
			  else {
			    	console.log("elselength", shareCheck);
			        $('#shareTemp').removeClass("shareTemp");
			        $('#shareTemp span').remove();
			    }

	});

	 $(document).on("change", ".userList", function(){
		 var shareCheck = $("input:checked");

		 if(shareCheck.length < 1) {
			 
			 $('#shareTemp').addClass("shareTemp");
		     $(".shareTemp").append("<span id='appendSpan'></span>");
 		  } 
		  else {
		    	console.log("elselength", shareCheck);
		        $('#shareTemp').removeClass("shareTemp");
		        $('#shareTemp span').remove();
		    }

	});		


 	 
	 
</script>

