<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="scripts/main.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Lato:wght@400;900&family=Lora:wght@700&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Veneet</title>
</head>
<body>
	<div class="flex-container">
	    <div class="flex-div">
	    	<h1>Veneet</h1>
	    	<div id="nav">
		    	<span class="veneet-head-span" id="lisaaVene">Lisää uusi vene</span>
		    	<input class="navi" type="text" id="hakusana">
		    	<input class="navi" type="submit" value="Etsi" id="hakunappi">
	    	</div>
			<table id="veneet">
				<thead>
					<tr class="veneet-head">
						<th>Nimi</th>
						<th>Merkki ja malli</th>
						<th>Pituus</th>
						<th>Leveys</th>
						<th>Hinta</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody id="tbody">
				</tbody>
			</table>
		</div>
	</div>
<script>
$(document).ready(function(){
	
	$("#lisaaVene").click(function(){
		document.location="lisaavene.jsp";
	});
	
	haeVeneet();
	$("#hakunappi").click(function(){		
		haeVeneet();
	});
	$(document.body).on("keydown", function(event){
		  if(event.which==13){
			  haeVeneet();
		  }
	});
	$("#hakusana").focus();
});	

function haeVeneet(){
	$("#veneet tbody").empty();
	$.ajax({url:"veneet/"+$("#hakusana").val(), type:"GET", dataType:"json", success:function(result){
		$.each(result.veneet, function(i, field){  
        	var htmlStr;
        	htmlStr+="<tr id='rivi_"+field.tunnus+"'>";
        	htmlStr+="<td class='column15'>"+field.nimi+"</td>";
        	htmlStr+="<td class='column20'>"+field.merkkimalli+"</td>";
        	htmlStr+="<td class='column10'>"+field.pituus+"</td>";
        	htmlStr+="<td class='column10'>"+field.leveys+"</td>";
        	htmlStr+="<td class='column15'>"+field.hinta+"</td>";
        	htmlStr+="<td class='column15'><a class='muuta' href='muutavene.jsp?tunnus="+field.tunnus+"'>Muuta</a></td>";
        	htmlStr+="<td class='column15'><span class='poista' onclick=poista('"+field.tunnus+"'\,'"+field.nimi.replace(" ", "&nbsp;")+"'\,'"+field.merkkimalli.replace(" ", "&nbsp;")+"')>Poista</span></td>"; 
        	htmlStr+="</tr>";
        	$("#veneet tbody").append(htmlStr);
        });	
    }});
}

function poista(tunnus, nimi, merkkimalli){
	if(confirm("Poista vene " + nimi + "\ " + merkkimalli + "?")){
		$.ajax({url:"veneet/"+tunnus, type:"DELETE", dataType:"json", success:function(result) {
	        if(result.response==0){
	        	$("#ilmo").html("Veneet poisto epäonnistui.");
	        }else if(result.response==1){
	        	alert("Veneet " + nimi + "\ " + merkkimalli + " poisto onnistui.");
				haeVeneet();
			}
	    }});
	}	

}


</script>
</body>
</html>