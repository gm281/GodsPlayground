function main() {
	//alert("JS test");
	//document.getElementById("p1").innerHTML = "New text!";
  	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function()
  	{
  		if (xmlhttp.readyState==4 && xmlhttp.status==200)
    	{
    		document.getElementById("p1").innerHTML=xmlhttp.responseText;
    	}
  	}
	xmlhttp.open("GET","hello", true);
	xmlhttp.send();

  	var xmlhttp2=new XMLHttpRequest();
	xmlhttp2.onreadystatechange=function()
  	{
  		if (xmlhttp2.readyState==4 && xmlhttp2.status==200)
    	{
    		document.getElementById("p1").innerHTML=xmlhttp2.responseText;
    	}
  	}
	xmlhttp2.open("GET","hello2", true);
	xmlhttp2.send();

	longpoll();
}