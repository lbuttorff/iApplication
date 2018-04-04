$('#filter-criteria').submit(function( event ) {
   alert("Submit button called");
   //ajax GET request to SearchController.filter()
   event.preventDefault();
});