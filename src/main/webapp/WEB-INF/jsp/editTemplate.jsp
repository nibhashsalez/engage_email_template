<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<link href="../static/styles/automation.css" type="text/css"
	rel="stylesheet" />
<link href="../static/styles/workFlow.css" type="text/css"
	rel="stylesheet" />
<script type="text/javascript" src="../static/scripts/js/jquery.min.js"></script>
<script src="https://cdn.ckeditor.com/4.14.0/standard/ckeditor.js"></script>
<script type="text/javascript" src="../static/scripts/js/ckeditor.js"></script>
<head>
<style>
</style>
</head>
<body>


	<div class="template_editor">
		<div class="automation_create_rule_header">
			<div class="create-rule-left-sec editor_module_div">
				<h3 class="subHeading">
					<span onclick="window.history.back();"> <img
						src="../static/images/administrator/ic_back.svg" alt="back img"></span>
					<span id="template-name">${templateDetail.data.name}</span> 
					<div>
					
					<!-- Need to change with folderId TODO -->
					<input type="hidden" id="selected-folder-id"
						value="${templateDetail.data.folderId}" />
				</h3>
				<p>
					<span class="" style="margin-right: 10px;"> </span><small
						class="light_text">Module:</small>
				</p>
				<div class="inline_block">
					<select id="moduleListDropDown" onInput="removeError();">
						<c:forEach items="${moduleList.data}" var="module">
							<option value="${module.id}">${module.value}</option>
						</c:forEach>
					</select>
				</div>
				<span class="module_description" id="subject-border"> <small
					class="light_text">Subject:</small> <a
					data-reveal-id="updateSubject"> <small class="dark_text"
						id="template-subject">${templateDetail.data.subject} </small> <img
						title="update module description" class="showEditIcon"
						src="../static/images/administrator/module-img/ic_more_expand.svg"
						alt="edit icon" id="edit-icon">

				</a>

				</span>

			</div>
			<div class="create-rule-right-sec">
				<a href="javascript:;" data-reveal-id="cancelTemplate"
					class="primary_btn cancel_btn close-reveal-modal">Cancel</a> <a
					href="javascript:;" class="blue_color  primary_btn blue_hover"
					onClick="showPreview()">Preview</a> <a href="javascript:;"
					id="openSaveTempalePopup" onClick="validateFields();"
					
					class="blue_bg  primary_btn blue_hover">save Template</a>
			</div>
		</div>
		<div class="templateEditorBody">
		<div class="ckEditorTemplateBlankText">* you can not save blank template !</div>
			<div class="editor_text_body">
				<textarea name="editor1" cols="40" rows="35"></textarea>
			</div>
		</div>
	</div>


<div id="template-data" style="opacity:0;height:2px;">${templateDetail.data.data}</div>


<div id="cancelTemplate" class="deleteTemplateUpdate reveal-modal"
		aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
		<div class="bodyPart">
		<div class="inline_block">
			<img alt="deleteimg" src="../static/images/administrator/popup-delete.svg">
		</div>
		<div class="inline_block">
			<p>Are you sure, you want to discard changes?</p>
		</div>	
		</div>
		<div class="modal-body">
			<input type="hidden" name="template-delete-Id"
				id="template-delete-Id" />
		</div>
		<div class="footer_section">
			<ul>
				<li><a href="javascript:;"
					class="primary_btn cancel_btn close-reveal-modal"
					aria-label="Close">Cancel</a></li>
				<li><a href="/email/template/viewTemplateList.html" class="blue_bg _whiteBg primary_btn blue_hover"
						id="deleteBtn">Ok</a></li>
			</ul>
		</div>

		<a class="close-reveal-modal" aria-label="Close">&#215;</a>
	</div>



	<!--delete popup checking  -->
	<div id="deleteTemplate" class="reveal-modal"
		aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
		<div class="bodyPart">
			<p>Are you sure, you want to delete the templates ?</p>
		</div>
		<div class="modal-body">
			<input type="hidden" name="template-delete-Id"
				id="template-delete-Id" />
		</div>
		<div class="footer_section">
			<ul>
				<li><a href="javascript:;"
					class="primary_btn cancel_btn close-reveal-modal"
					aria-label="Close">Cancel</a></li>
				<li><button class="blue_bg _whiteBg primary_btn blue_hover"
						id="deleteBtn">Ok</button></li>
			</ul>
		</div>

		<a class="close-reveal-modal" aria-label="Close">&#215;</a>
	</div>

	<!-- delete custom template popup html here  -->


	<div id="updateSubject" class="reveal-modal" data-reveal
		aria-labelledby="modalTitle" role="dialog" aria-hidden="true"
		role="dialog">
		<h2 id="modalTitle">Update Subject</h2>
		<div class="bodyPart">
			<div class="create_template_info">
				<div class="inline_block">
					<p>DESCRIPTION</p>
				</div>
				<div class="inline_block">
					<p>
						<textarea class="" rows="4" id="textarea-subject"
							onInput="removeError();" placeholder="Write if any...">${templateDetail.data.subject}</textarea>
					</p>
				</div>
			</div>
		</div>
		<div class="footer_section">
			<div class="inline_block">
				<ul>
					<li><a href="javascript:;"
						class="primary_btn cancel_btn close-reveal-modal"
						aria-label="Close">Cancel</a></li>
					<li><a href="javascript:;"
						class="blue_bg _whiteBg primary_btn blue_hover"
						id="update-subect-btn" onClick="updateSubject();">ok</a></li>
				</ul>
			</div>
		</div>

		<a class="close-reveal-modal" aria-label="Close">&#215;</a>
	</div>

	<!-- delete custom template popup html here  -->

	<div id="saveTemplatePopup" class="reveal-modal" data-reveal
		aria-labelledby="modalTitle" role="dialog" aria-hidden="true"
		role="dialog">
		<h2 id="modalTitle">Save Template</h2>
		<div class="bodyPart">
			<div class="save_template_popup inline_block">
				<span>move to folder</span>
			</div>
			<div class="inline_block searchFolder selectedDefault" id="disableWholeDiv">
				<span id="select_folder_name">Select Folder</span>
				<div class="shareTermp_seelctGrp" id="shareTemp_popup">
					<div class="createFolderDiv">
						<div class="inline_block">
							<input type="text" placeholder="create new folder"
								onInput="removeError();" id="folder-name">
						</div>
						<div class="inline_block" id="create_new_folder"
							onClick="createFolder();">+</div>
					</div>
					<ul class="custom_scroll">
						<li><label class="custom_cehckbox"> None <input
								type="radio" name="folderRadioBtn"
								onClick="makePublicDisabled(-1)"> <span
								class="checkmark"></span>
						</label></li>
						<div id="folderListDynamic"></div>
						<div id="folder-list">
							<c:forEach items="${folderList.data}" var="folder">
								<li><label class="custom_cehckbox"> ${folder.value}
										<input type="radio" name="folderRadioBtn"
										id="folderRadioBtn${folder.id}"
										onClick="makePublicDisabled(${folder.id})"
										folder-id="${folder.id}"> <span class="checkmark"></span>
								</label></li>
							</c:forEach>
						</div>

					</ul>
				</div>
			</div>
			<div class="selection_save_popup">
				<h3>
					<span>OR</span>
				</h3>
				<div class="save_make_public clearfix">
					<div class="inline_block">Do you want to make it Public?</div>
					<div class="inline_block">
						<label class="switch inline_block"> <input type="checkbox"
							onClick="disableFolderDropDown();" id="public"> <span
							class="slider round"></span>
						</label>
					</div>
				</div>
			</div>

		</div>
		<div class="footer_section">
			<ul>
				<li><a href="javascript:;"
					class="primary_btn cancel_btn close-reveal-modal"
					aria-label="Close">Cancel</a></li>
				<li><a href="javascript:;"
					class="blue_bg _whiteBg primary_btn blue_hover"
					onClick="updateTemplate();" id="save-template-btn">Update</a></li>
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


	<script type="text/javascript" src="../static/scripts/custom_select.js"></script>
	<script type="text/javascript" src="../static/scripts/js/foundation.js"></script>
	<script type="text/javascript"
		src="../static/scripts/js/foundation.reveal.js"></script>


	<script>
	var templateData=$('#template-data').html();
	console.log(templateData);
	$("#template-data").empty();
 	console.log("details page data",templateData);
	setTimeout( () => (
	CKEDITOR.instances.editor1.insertHtml(templateData)
	),1500)
		var url_string = window.location.href;
		var url = new URL(url_string);
		var moduleId = url.searchParams.get("moduleId");
		var categorySelected=$('#category-name').val();
		document.getElementById("moduleListDropDown").value = moduleId;
		var folderSelected=$('#selected-folder-id').val();
		//Todo after folder id recieved
		if(folderSelected!='' && typeof folderSelected!=='undefined'){
			$('#folderRadioBtn'+folderSelected).prop('checked',true);
			var folderNameValue=$('#folderRadioBtn'+folderSelected).prop('checked',true).parent('label').text();
			$('#select_folder_name').text(folderNameValue);
			}
		if(categorySelected=='public')
			{
			$('#public').attr('checked', true);
			$("#disableWholeDiv").css("pointer-events","none");
			}
		else
			{
			$('#public').attr('checked', false);
			$("#disableWholeDiv").css("pointer-events","auto");
			}

		function makePublicDisabled(value) {
			if(value=='-1' || typeof value=='undefined'){
				$('#public').attr('disabled', false);
			}
			else
				{
				$('#public').attr('disabled', true);
				}
		}

		function disableFolderDropDown(){
			if($("#public").is(":checked")) {
					  $("#disableWholeDiv").css("pointer-events","none");
					  $('#select_folder_name').text("Select folder or create folder");
					  
				}
			else
				{
				$("#disableWholeDiv").css("pointer-events","auto");
				}
			}

		function createFolder() {
			var folderName = $('#folder-name').val();
			if (folderName.trim() != '') {
				var data = {
					"name" : folderName
				}
				createNewFolder(data);
			} else {
				$('#folder-name').addClass("errorClass");
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
																$(
																		'#folder-list')
																		.remove();
																markup = "<li><label class='custom_cehckbox'>"
																		+ folderName
																		+ "<input type='radio' name='folderRadioBtn' folder-id= "+folderId+" > "
																		+ "<span class='checkmark'></span></label></li>"

																$(
																		'#folder-name')
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

						},
						error : function(e) {
							location.reload();
						}
					});
		}

		function redirectToListingPage() {
			window.location.href = "/viewTemplateList.html";
		}
		//TODO VALIDATION
		function validateFields() {
			
			var oEditor = CKEDITOR.instances.editor1;
			var data = oEditor.getData();
			 		 
			if (data == '' && data.trim() == ''){
		 		$(".ckEditorTemplateBlankText").show();  
		 		$('#openSaveTempalePopup').removeAttr("data-reveal-id",
				"saveTemplatePopup");
				setTimeout(function() {
					$('.ckEditorTemplateBlankText').hide(); 
					 }
				 , 2000);
			}
			else{
				$('#openSaveTempalePopup').attr("data-reveal-id",
				"saveTemplatePopup");
				}
			
			var moduleId = $("#moduleListDropDown").val();
			var subject = $('#template-subject').text();

			if (moduleId == null) {
				$("#moduleListDropDown").addClass("errorClass");
			}
			if (subject.trim() == '') {
				$("#subject-border").addClass("errorClass");
			}
			if (moduleId != '' && subject.trim() != '') {
				$('#open-save-popup').attr("data-reveal-id",
						"saveTemplatePopup");
			}
		}

		function updateSubject() {
			var subject = $('#textarea-subject').val();
			if (subject.trim() != '') {
				$('#template-subject').text(subject);
				$('.reveal-modal').trigger('reveal:close');
				
			} else {
				$('#textarea-subject').addClass("errorClass");
			}

		}
		function removeError() {
			$('#textarea-subject').removeClass('errorClass');
			$('#select_folder_name').removeClass("errorClass");
			$('#public').removeClass("errorClass");
			$('#moduleListDropDown').removeClass("errorClass");
			$('#subject-border').removeClass("errorClass");
			$('#folder-name').removeClass("errorClass");

		}

		function showPreview() {
			var oEditor = CKEDITOR.instances.editor1;
			var contents = oEditor.getData();
			var mywin = window.open("", '_blank');
			$(mywin.document.body).html(contents);
		}
		function updateTemplate() {
			var url_string = window.location.href;
			var url = new URL(url_string);
			var templateId = url.searchParams.get("templateId");
			if(templateId.trim()!='')
				{
			var oEditor = CKEDITOR.instances.editor1;
			var data = oEditor.getData();
			var folderId = $('input[name=folderRadioBtn]:checked').attr(
			'folder-id');
			if(folderId=='-1' || typeof folderId=='undefined'){
				folderId="";
				}
			var templateName = $('#template-name').text();
			var templateSubject = $('#template-subject').text();
			var moduleId = $("#moduleListDropDown").val();
			var filter="";
			var isCheckedPublic=$("#public").is(":checked");
			if((folderId=='' && isCheckedPublic==false))
			{
			 filter="";
			 folderId="";
			}
		else {
		 if ((folderId=='') && (isCheckedPublic==true )) {
				filter="PB"			
		 }
		 if((folderId!='') && (isCheckedPublic==false ))
			 {
			    filter="PV";
			 }
		}
			
		var data = {
				"templateId":templateId,
				"moduleId" : moduleId,
				"templateName" : templateName,
				"subject" : templateSubject,
				"data" : data,
				"filter" : filter,
				"folderId" : folderId
			}
		updateTemplateAjax(data);
	}
		}

	function updateTemplateAjax(data){
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : '<c:url value="/template/edit"/>',
			data : JSON.stringify(data),
			success : function(response) {
				//alert(response.message);
				//window.location.href="/email/template/viewTemplateList.html"
 				$('.reveal-modal').trigger('reveal:close');
             	setTimeout(function() { 
             		window.location.href="/email/template/viewTemplateList.html";
	            }, 2000);
				$("#afterActionPopupText").append("Update template successfully");
        		$('.ShowAfterAction').show();
			},
			error : function(e) {
				location.reload();
			}
		});
		}
		$(document).ready(function() {
			CKEDITOR.replace('editor1', {
				height : 340
			});

/* 			$("#saveTemplatePopup").click(function() {
	            if ($(".shareTermp_seelctGrp").is(":visible")) {
	                $(".shareTermp_seelctGrp").slideUp();
	            }
	        }); */
			 $(".searchFolder > span").click( function(e){
			     e.stopPropagation();
			     $(".shareTermp_seelctGrp").slideToggle();
			     
			 });
 			$(".createFolderDiv").click(function(e){
 				 e.stopPropagation();
 				$(".shareTermp_seelctGrp").css("display", "block");
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
				$("#shareTemp_popup").slideUp();
			});

			$(document).on("click", "#shareTemp_popup ul li input[type='radio']", function() {
				if ($(this).is(":checked")) {

					var select_folder = $(this).parent('label').text();
				}
				$("#select_folder_name").text(select_folder);
				$("#shareTemp_popup").slideUp();
			});

			
		});
	</script>

</body>
</html>
