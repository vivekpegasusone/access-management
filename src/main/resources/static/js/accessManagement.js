function findRolesForApplication(applicationId){ 
    var url='/access-management/application/roles?applicationId='+applicationId;
    $.ajax({
    	type: "get",
        url: url,
        cache: false,
        success: function(response){
            $("#errors").html(jQuery.trim(response));
            //if errors not present
            if(jQuery.trim(response)==''){
            	alert("No roles found for the application.");
            	addRoles(response);
            } else {
            	addRoles(response);
            }
        },
        error: function( ){
        	alert("No roles found for the application.")
        }
    });
}

function addRoles(roles) {
	$('#role').empty();
	$('#role').append($("<option></option>").attr("value", "0").text("--Select Role--"));	
	for (i = 0; i < roles.length ; i++) {
		if(roles[i].key == $('#roleId').val()) {
			$('#role').append($("<option></option>").attr("selected", "selected").attr("value", roles[i].key).text(roles[i].value));	
		} else {
			$('#role').append($("<option></option>").attr("value", roles[i].key).text(roles[i].value));
		}
	}
}

$('#application').change(function(){   
	var applicationId = $(this).val();
	loadRoles(applicationId);
})

$(window).bind("load", function() {
	var applicationId = $('#application').val();
	loadRoles(applicationId);
});

function loadRoles(applicationId) {
	if(typeof applicationId != "undefined" && applicationId != "-") {
		findRolesForApplication(applicationId);
	} else {
		addRoles(new Array());
	}
}