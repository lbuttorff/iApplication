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
        //honors:document.getElementById("honors").checked,
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
    //console.log(json);
    //ajax GET request to SearchController.filter()
    $.ajax({
        url: "/filter",
        data: json,
        type: "POST",
        success: function(data){
            document.getElementById("resultsDiv").innerHTML = "";
            updateResults(data);
        },
        error: function (response) {
            console.log(response.text);
        }
    });
});

function updateResults(data) {
    var json = data;
    var resultsDiv = document.getElementById("resultsDiv");
    var standings = ["Undergraduate Student","Masters Student","Ph.D. Student","Faculty Member"];
    var departments = ["Agricultural Sciences","Arts and Architecture","Smeal College of Business",
        "College of Communications","Earth and Mineral Sciences","Education","Engineering",
        "Health and Human Development","Information Sciences and Technology","Dickinson Law","Penn State Law",
        "The Liberal Arts","College of Medicine","College of Nursing","Eberly College of Science"
    ];
    for(var i=0;i<json.length;i++){
        var user = json[i];
        var resultDiv = document.createElement("div");
        resultDiv.classList.add("search-result");
        resultDiv.classList.add("row");
        var leftPanel = document.createElement("div");
        leftPanel.classList.add("col-md-3");
        leftPanel.classList.add("search-result-left-panel");
        leftPanel.align = "center";
        var rightPanel = document.createElement("div");
        rightPanel.classList.add("col-md-8");
        rightPanel.classList.add("search-result-right-panel");
        /*var img = document.createElement("img");
        img.classList.add("rounded-circle");
        img.src = "default-src";
        img.width = "100";
        img.height = "100";*/
        var name = document.createElement("h6");
        name.innerText = user.firstName + " " + user.lastName;
        var rating = document.createElement("p");
        rating.innerText = "Rating";
        var standing = document.createElement("h6");
        standing.innerText = "Academic Status: "+standings[user.standing];
        var department = document.createElement("h6");
        department.innerText = "Department: "+departments[user.department];
        var services = document.createElement("h6");
        services.innerText = "Services: Coming soon...";
        var viewProf = document.createElement("button");
        viewProf.classList.add("btn");
        viewProf.classList.add("btn-secondary");
        viewProf.classList.add("d-inline");
        viewProf.classList.add("m-2");
        viewProf.innerText = "View Profile";
        var sendReq = document.createElement("button");
        sendReq.classList.add("btn");
        sendReq.classList.add("btn-primary");
        sendReq.classList.add("d-inline");
        sendReq.classList.add("m-2");
        sendReq.innerText = "Send Connection Request";
        //leftPanel.appendChild(img);
        leftPanel.appendChild(name);
        leftPanel.appendChild(rating);
        rightPanel.appendChild(standing);
        rightPanel.appendChild(department);
        rightPanel.appendChild(services);
        rightPanel.appendChild(viewProf);
        rightPanel.appendChild(sendReq);
        resultDiv.appendChild(leftPanel);
        resultDiv.appendChild(rightPanel);
        resultsDiv.appendChild(resultDiv);
    }
}

window.onload = function(){
    var term = document.getElementById("term").innerText;
    term = term.substring(term.indexOf("'")+1,term.lastIndexOf("'"));
    $.ajax({
        url: "/search/results",
        type: "GET",
        data: term,
        success: function(data){
            //console.log(data);
            updateResults(data);
        },
        error: function(response){
            console.log(response.text);
        }
    });
};