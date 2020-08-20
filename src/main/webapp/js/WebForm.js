
var WebForm = function(){

    var publish = function(uri, form){

        overlay.style.display = "block"

        var request = new XMLHttpRequest();
        var formData = new FormData(form);
        return new Promise(function(resolve, reject){

          request.onreadystatechange = function () {

              // Only run if the request is complete
              if (request.readyState !== 4) return;

              if(request.status >= 200 && request.status < 300) {
                  resolve(request);
                  overlay.style.display = "none"
              }
              else {
                  reject({
                      status: request.status,
                      statusText: request.statusText
                  });
                  overlay.style.display = "none"
              }
          };

          request.open('post', uri, true);
          request.send(formData);
          performingRequest = true

        });
    }

    return {
        publish: publish,
    };

}



