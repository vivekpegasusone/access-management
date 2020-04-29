function find(url, objType){ 
    $.ajax({
    	type: "get",
        url: url,
        cache: false,
        success: function(response){
            $("#errors").html(jQuery.trim(response));
            //if errors not present
            if(jQuery.trim(response)==''){
            	alert("No " + objType + " found for the application.");
            	add(response, objType);
            } else {
            	add(response, objType);
            }
        },
        error: function( ){
        	alert("No " + objType + " found for the application.")
        }
    });
}

function findActionsForApplication(applicationId){ 
    var url='/access-management/application/actions?applicationId='+applicationId; 
    find(url, "actions");
}

function findResourcesForApplication(applicationId){ 
    var url='/access-management/application/resources?applicationId='+applicationId; 
    find(url, "resources");
}

function add(list, objType) {
	if("actions" == objType) {
		addActions(list);
	} else {
		addResources(list);
	}
}

function addActions(actions) {
	$('#action').empty();
	$('#action').append($("<option></option>").attr("value", "0").text("--Select Action--"));	
	for (i = 0; i < actions.length ; i++) {
		if(actions[i].key == $('#actionId').val()) {
			$('#action').append($("<option></option>").attr("selected", "selected").attr("value", actions[i].key).text(actions[i].value));	
		} else {
			$('#action').append($("<option></option>").attr("value", actions[i].key).text(actions[i].value));
		}
	}
}

function addResources(resources) {
	$('#resource').empty();
	$('#resource').append($("<option></option>").attr("value", "0").text("--Select Resource--"));	
	for (i = 0; i < resources.length ; i++) {
		if(resources[i].key == $('#resourceId').val()) {
			$('#resource').append($("<option></option>").attr("selected", "selected").attr("value", resources[i].key).text(resources[i].value));	
		} else {
			$('#resource').append($("<option></option>").attr("value", resources[i].key).text(resources[i].value));
		}
	}
}

$('#appPermission').change(function(){   
	var applicationId = $(this).val();
	loadRoles(applicationId);
})

$(window).bind("load", function() {
	var applicationId = $('#appPermission').val();
	loadRoles(applicationId);
});

function loadRoles(applicationId) {
	if(typeof applicationId != "undefined" && applicationId != "-") {
		findActionsForApplication(applicationId);
		findResourcesForApplication(applicationId);
	} else {
		var arr = new Array();
		addActions(arr);
		addResources(arr);
	}
}