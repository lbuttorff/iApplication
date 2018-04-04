$('#filter-criteria').submit(function( event ) {
    event.preventDefault();
    //alert("Submit button called");
    //Get the filter criteria from the DOM and put it into variables
    var json = {
        csrfToken:document.getElementById("filter-criteria").csrfToken.value,
        campus:document.getElementById("campusSelect").value,
        //Start with departments
        agSciences:document.getElementById("agSciences").checked,
        artsAndArch:document.getElementById("artsAndArch").checked,
        business:document.getElementById("business").checked,
        communications:document.getElementById("communications").checked,
        earthAndMineralSciences:document.getElementById("earthAndMineralSciences").checked,
        education:document.getElementById("education").checked,
        engineering:document.getElementById("engineering").checked,
        hhd:document.getElementById("hhd").checked,
        ist:document.getElementById("ist").checked,
        dickinsonLaw:document.getElementById("dickinsonLaw").checked,
        psuLaw:document.getElementById("psuLaw").checked,
        liberalArts:document.getElementById("liberalArts").checked,
        medicine:document.getElementById("medicine").checked,
        nursing:document.getElementById("nursing").checked,
        science:document.getElementById("science").checked,
        honors:document.getElementById("honors").checked,
        //Next is services
        undergradApp:document.getElementById("undergradApp").checked,
        gradApp:document.getElementById("gradApp").checked,
        essayEdit:document.getElementById("essayEdit").checked,
        interviewPrep:document.getElementById("interviewPrep").checked,
        dormAptHelp:document.getElementById("dormAptHelp").checked,
        collegeVisit:document.getElementById("collegeVisit").checked,
        //Lastly experience level
        undergrad:document.getElementById("undergrad").checked,
        masters:document.getElementById("masters").checked,
        phd:document.getElementById("phd").checked,
        faculty:document.getElementById("faculty").checked
    };
    //Test statement; this should not be uncommented during production use
    console.log(json);
    //ajax GET request to SearchController.filter()
    $.ajax({
        url: "/filter",
        data: json,
        type: "POST",
        success: function(data){
            updateResults(data);
        },
        error: function (response) {
                console.log(response.text);
        }
    });
});

function updateResults(data) {

}