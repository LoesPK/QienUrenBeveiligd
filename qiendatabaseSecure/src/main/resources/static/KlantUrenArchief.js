/**
 * 
 */

//Loes: de api's
var apiKlant = "http://localhost:8082/api/klant/";
//trainee variabele 
var klant;


//Bepalen huidige datum zodat er nooit een leeg datumveld wordt opgestuurd
var today = new Date();
var dd = today.getDate();
var mm = today.getMonth()+1; //As January is 0.
var yyyy = today.getFullYear();
if(dd<10) dd='0'+dd;
if(mm<10) mm='0'+mm;
today = yyyy+'-'+mm+'-'+dd ;




// EMIEL - De maand selecteren
function selectMonth(){
	var tableBody = document.getElementById("selectedMonth");
		theMonth = tableBody[tableBody.selectedIndex].value;
		console.log("theMonth: " + theMonth);
		GETUrenPerMaand(theMonth);
		}

var akkoordstatus = "";
var aantalUren = 0;

// EMIEL - GET Uren per maand
function GETUrenPerMaand(theMonth){
	var table = document. getElementById("traineelijst");
	if(table.rows.length > 0){
	for(var i = table.rows.length - 1; i > 0; i--)
		{
		table. deleteRow(i);
		}
	}
  var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
            klant = JSON.parse(this.responseText);	
//            trainee.uren.sort(function(a,b){return b.factuurDatum<a.factuurDatum?-1:1});
			for(var j = 0; j<klant.trainee.length; j++){
            for(var i = 0; i<klant.trainee[j].uren.length; i++){
                uurInDBHemZeMonth = klant.trainee[j].uren[i].factuurDatum.substring(5,7);
                    
                         if(klant.trainee[j].uren[i].accordStatus == "TEACCODEREN" || klant.trainee[j].uren[i].accordStatus == "GOEDGEKEURD" || klant.trainee[j].uren[i].accordStatus == "AFGEKEURD" ){
                       	  akkoordstatus = klant.trainee[j].uren[i].accordStatus;
                             aantalUren += klant.trainee[j].uren[i].aantal ;
                             console.log(akkoordstatus);
                         }
                      
                    	
                        
                    	
            }
      		if(uurInDBHemZeMonth == theMonth) {
            GETRowUrenTabel(klant.trainee[j]);
      		}
            }
        }

    };
      xhttp.open("GET", apiKlant, true);
	    xhttp.setRequestHeader("Content-type", "application/json");
	    xhttp.send();	
}

//GET functie met opbouwen rijen urentabel
function GETRowUrenTabel(trainee){
	var table = document.getElementById("traineelijst");
	var insertedRow = table.insertRow(1);
	insertedRow.id = trainee.id;
    
    //datum
	var insertedCell = insertedRow.insertCell(0);
	var Voornaam = document.createElement("td");
	Voornaam.innerHTML = trainee.voornaam;
		insertedCell.appendChild(Voornaam);
    
    //soort uren
	var insertedCell1 = insertedRow.insertCell(1);
    var Achternaam = document.createElement("td");
    Achternaam.innerHTML = trainee.achternaam;
        insertedCell1.appendChild(Achternaam);

    //aantal uren
	var insertedCell2 = insertedRow.insertCell(2);   
    var AantalUur = document.createElement("td");
		AantalUur.innerHTML = aantalUren;
				insertedCell2.appendChild(AantalUur);

		//akkoordstatus
	var insertedCell3 = insertedRow.insertCell(3);   
	var status = document.createElement("td");
	var statusAkkoord;
	if(akkoordstatus == "NIETINGEVULD"){
		statusAkkoord = "Onbekend";
	}if(akkoordstatus == "TEACCODEREN"){
		statusAkkoord = "Te Accoderen";
	}if(akkoordstatus == "GOEDGEKEURD"){
		statusAkkoord = "Goedgekeurd";
	}if(akkoordstatus == "AFGEKEURD"){
		statusAkkoord = "Afgekeurd";
	}
	insertedCell3.innerHTML = statusAkkoord;
	insertedCell3.appendChild(status);
}

function Userlogout(){
	sessionStorage.clear();
}

//EMIEL - de geselecteerde maand
var theMonth = "";
//EMIEL - de maand van het uur in database
var uurInDBHemZeMonth;

