
var Request = function(context){

    let _context = context

    var http = function(uri, method){

        if(uri.includes(_context + "/post/like") ||
                uri.includes(_context + "/post/remove") ||
                    uri.includes(_context + "/friend/invite/") ||
                        uri.includes(_context + "/friend/accept/")){
            overlay.style.display = "block"
        }

        var request = new XMLHttpRequest();
        request.onprogress = updateRequestProgress;

        return new Promise(function(resolve, reject){
            request.onreadystatechange = function () {

                // Only run if the request is complete
                if (request.readyState !== 4) return;
                if (request.status >= 200 && request.status < 300) {
                    resolve(request);
                } else {
                    reject({
                        status: request.status,
                        statusText: request.statusText
                    });
                    overlay.style.display = "none"
                }
            };
            request.open(method ? method : 'get', uri, true);
            request.send();
            performingRequest = true;
        });
    }

    var updateRequestProgress = function(arg){
        //console.log("update progress", arg)
    }

    return {
        http: http
    };

}



