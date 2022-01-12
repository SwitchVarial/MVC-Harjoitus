<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="scripts/main.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/jquery.validate.min.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Lato:wght@400;900&family=Lora:wght@700&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Lisaa vene</title>
</head>
<body>
	<div class="flex-container">
	    <div class="flex-div">
	    	<h1>Lisää uusi vene</h1>
	    	<div id="nav">
	    		<span class="takaisin" id="takaisin">Takaisin listaukseen</span>
	    	</div>
			<form id="veneen_tiedot">
				<table>
					<thead>		
						<tr>
							<th><label for="nimi">Nimi</label></th>
							<th><label for="merkkimalli">Merkki ja malli</label></th>
							<th><label for="pituus">Pituus</label></th>
							<th><label for="leveys">Leveys</label></th>
							<th><label for="hinta">Hinta</label></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="column20"><input class="paivita" type="text" name="nimi" id="nimi"></td>
							<td class="column20"><input class="paivita" type="text" name="merkkimalli" id="merkkimalli"></td>
							<td class="column10"><input class="paivita" type="text" name="pituus" id="pituus"></td>
							<td class="column10"><input class="paivita" type="text" name="leveys" id="leveys"></td>
							<td class="column20"><input class="paivita" type="text" name="hinta" id="hinta"></td> 
							<td class="column20"><input class="muuta" type="submit" id="tallenna" value="Hyväksy"></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
			
<script>
$(document).ready(function(){
	$("#takaisin").click(function(){
		document.location="listaaveneet.jsp";
	});
	
	
	$("#veneen_tiedot").validate({	
		rules: {
			nimi: {
				required: true,
				minlength: 3	
			},
			merkkimalli: {
				required: true,
				minlength: 3				
			},
			pituus: {
				required: true,
				number: true
			},	
			leveys: {
			    required: true,
				number: true
			},
			hinta: {
			    required: true,
			    number: true
			}
		},
		messages: {
			nimi: {     
				required: "Puuttuu",
				minlength: "Minimi pituus 3 merkkiä."			
			},
			merkkimalli: {
				required: "Puuttuu",
				minlength: "Minimi pituus 3 merkkiä."
			},
			pituus: {
				required: "Puuttuu",
				number: "Pituuden pitää olla numeerinen. Käytä pistettä desimaalierottimena."
			},
			leveys: {
				required: "Puuttuu",
				number: "Leveyden pitää olla numeerinen. Käytä pistettä desimaalierottimena."
			},
			hinta: {
				required: "Puuttuu",
				number: "Hinnan pitää olla numeerinen. Käytä pistettä desimaalierottimena."
			}
		},			
		submitHandler: function(form) {	
			lisaaVene();
		}		
	}); 	
});

function lisaaVene(){	
	var formJsonStr = formDataJsonStr($("#veneen_tiedot").serializeArray());
	$.ajax({url:"veneet", data:formJsonStr, type:"POST", dataType:"json", success:function(result) {
		if (result.response==0){
	    	alert("Veneen lisääminen epäonnistui.");
      	} else if (result.response==1){
    	alert("Veneen lisääminen onnistui.");
      	$("#nimi").val("");
      	$("#merkkimalli").val("");
      	$("#pituus").val("");
      	$("#leveys").val("");
      	$("#hinta").val("");
      	}
  }});	
}
</script>
</body>
</html>