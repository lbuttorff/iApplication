window.onload = function() {
    //Display existing values
    $('#edit-profile').hide();
    $('#view-profile').show();
};

$('#editButton').click(function(){
    //Display editing fields
    $('#view-profile').hide();
    $('#edit-profile').show();
});

$('#cancelButton').click(function(){
    //Display existing values
    $('#edit-profile').hide();
    $('#view-profile').show();
});

$('#saveButton').click(function(){
    //Display existing values
    $('#edit-profile').hide();
    $('#view-profile').show();
});