function post(url, obj, handler) {

  	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
  		if (xmlhttp.readyState==4 && xmlhttp.status==200) {
			if (handler != null) {
				handler();
			}
    	}
  	}

	xmlhttp.open("POST", url, true);
	xmlhttp.setRequestHeader("Content-type","application/json");
	xmlhttp.send(JSON.stringify(obj));
}

function get(url, handler) {
  	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
  		if (xmlhttp.readyState==4 && xmlhttp.status==200) {
			if (handler != null) {
				handler(xmlhttp.responseText);
			}
    	}
  	}

	xmlhttp.open("GET", url, true);
	xmlhttp.send();
}
