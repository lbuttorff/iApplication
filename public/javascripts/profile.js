$('#editButton').click(function(){
    //Display editing fields
    $('#view-profile').hide();
    $('#edit-profile').show();
});

$('#cancelButton').click(function(){
    //Clear all fields
    $(this).closest('form').find("input[type=text], textarea, input[type=checkbox], select, input[type=email], input[type=number]").val("");
    //Display existing values
    $('#edit-profile').hide();
    $('#view-profile').show();
});

$('#saveButton').click(function(){
    //Display existing values
    $('#edit-profile').hide();
    $('#view-profile').show();
});