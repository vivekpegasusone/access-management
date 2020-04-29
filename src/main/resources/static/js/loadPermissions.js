function findPermissionsForApplication(applicationId){ 
    var url='/access-management/application/permissions?applicationId='+applicationId;
    $.ajax({
    	type: "get",
        url: url,
        cache: false,
        success: function(response){
            $("#errors").html(jQuery.trim(response));
            //if errors not present
            if(jQuery.trim(response)==''){
            	alert("No permissions found for the application.");
            	addPermissions(response);
            } else {
            	addPermissions(response);
            }
        },
        error: function( ){
        	alert("No permissions found for the application.")
        }
    });
}

function addPermissions(permissions) {
	$('#permission').empty();
	$('#permission').append($("<option></option>").attr("value", "0").text("--Select Permission--"));	
	for (i = 0; i < permissions.length ; i++) {
		if(permissions[i].key == $('#permissionId').val()) {
			$('#permission').append($("<option></option>").attr("selected", "selected").attr("value", permissions[i].key).text(permissions[i].value));	
		} else {
			$('#permission').append($("<option></option>").attr("value", permissions[i].key).text(permissions[i].value));
		}
	}
}

$('#appRolePermission').change(function(){   
	var applicationId = $(this).val();
	loadPermissions(applicationId);
})

$(window).bind("load", function() {
	var applicationId = $('#appRolePermission').val();
	loadPermissions(applicationId);
});

function loadPermissions(applicationId) {
	if(typeof applicationId != "undefined" && applicationId != "-") {
		findPermissionsForApplication(applicationId);
	} else {
		addPermissions(new Array());
	}
}