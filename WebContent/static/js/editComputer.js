// Get the introduced date and set the min attribute of the discontinued date
(function ( $ ) {

    $("#introduced").change(function () {
    	var introduced = $("#introduced").val();
    	$("#discontinued").prop('min',introduced);
    });

}( jQuery ));

