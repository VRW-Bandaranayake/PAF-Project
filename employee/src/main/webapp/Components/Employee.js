$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {

	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateItemForm();

	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	
	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "EmployeeAPI",
		type : type,
		data : $("#formItem").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onItemSaveComplete(response.responseText, status);
		}
	});
});

function onItemSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}


// UPDATE==========================================
$(document)
		.on(
				"click",
				".btnUpdate",
				function(event) {

					$("#hidItemIDSave").val(
							$(this).closest("tr").find('#hidItemIDUpdate')
									.val());
					$("#docId").val(
							$(this).closest("tr").find('td:eq(0)').text());
					$("#docName").val(
							$(this).closest("tr").find('td:eq(1)').text());
					$("#docGender").val(
							$(this).closest("tr").find('td:eq(2)').text());
					$("#docDob").val(
							$(this).closest("tr").find('td:eq(3)').text());
					$("#docAge").val(
							$(this).closest("tr").find('td:eq(4)').text());
					$("#docSalary").val(
							$(this).closest("tr").find('td:eq(5)').text());

				});

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "EmployeeAPI",
		type : "DELETE",
		data : "docId=" + $(this).data("itemid"),
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
		}
	});
});



function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}


function validateItemForm() {

	// CODE
	if ($("#docId").val().trim() == "") {
		return "Insert Id.";
	}
	if ($("#docName").val().trim() == "") {
		return "Insert Name.";
	}
	// GENDER
	if ($("#docGender").val() == "0") {
		return "Select Gender.";
	}
	if ($("#docDob").val() == "0") {
		return "Select Dob.";
	}


	if ($("#docAge").val() == "0") {
		return "Select Age.";
	}

	if ($("#docSalary").val() == "0") {
		return "Select Salary.";
	}

	return true;
}