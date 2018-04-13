//Display mentor fields if mentor option is selected
$('#mentorOption').click(function() {
    if($('#mentorOption').is(':checked')) {
        $('#mentorFields').show();
    }
    else {
        $('#mentorFields').hide();
    }
});

//Hide mentor fields when applicant is selected
$('#applicantOption').click(function() {
    if($('#mentorOption').is(':checked')) {
        $('#mentorFields').show();
    }
    else {
        $('#mentorFields').hide();
    }
});