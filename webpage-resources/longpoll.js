function longpoll() {
  	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function()
  	{
  		if (xmlhttp.readyState==4 && xmlhttp.status==200)
    	{
    		document.getElementById("p1").innerHTML=xmlhttp.responseText;
			var obj = JSON.parse(xmlhttp.responseText);
    		document.getElementById("p2").innerHTML= "The cycle counter: " + obj.someLong + ", other bits: " + obj.someClass.someInt + ", " + obj.someArray[1];
			longpoll();
    	}
  	}
	xmlhttp.open("GET","longpoll", true);
	xmlhttp.send();
}
