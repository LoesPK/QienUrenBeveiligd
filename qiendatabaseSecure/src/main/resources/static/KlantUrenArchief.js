/**
 * 
 */

//Loes: de api's
var apiTrainee = "http://localhost:8082/api/trainee/13";//+sessionStorage.getItem("storedUserID");
//trainee variabele 
var trainee;


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
            trainee = JSON.parse(this.responseText);	
//            trainee.uren.sort(function(a,b){return b.factuurDatum<a.factuurDatum?-1:1});
            for(var i = 0; i<trainee.uren.length; i++){
                uurInDBHemZeMonth = trainee.uren[i].factuurDatum.substring(5,7);
                    
                         if(trainee.uren[i].accordStatus == "TEACCODEREN" || trainee.uren[i].accordStatus == "GOEDGEKEURD" || trainee.uren[i].accordStatus == "AFGEKEURD" ){
                       	  akkoordstatus = trainee.uren[i].accordStatus;
                             aantalUren += trainee.uren[i].aantal ;
                             console.log(akkoordstatus);
                         }
                      
                    	
                        
                    	
            }
      		if(uurInDBHemZeMonth == theMonth) {
            GETRowUrenTabel(trainee);
      		}
            }

    };
      xhttp.open("GET", apiTrainee, true);
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

////EMIEL - De maand selecteren
//function selectMonth(){
//	var tableBody = document.getElementById("selectedMonth");
//		theMonth = tableBody[tableBody.selectedIndex].value;
//		console.log("theMonth: " + theMonth);
//		GETTrainees(theMonth);
//		}
//
//
////GET trainees voor vullen tabel
//function GETTrainees(theMonth){
//	var table = document.createElement("table");
//	console.log("check in gettrainees");
//	for(var i = table.rows.length - 1; i > 0; i--)
//		{
//		table. deleteRow(i);
//		}
//  var xhttp = new XMLHttpRequest();
//    xhttp.onreadystatechange = function() {
//      if (this.readyState == 4 && this.status == 200) {
//      var trainee = JSON.parse(this.responseText); 
//      //onderstaande roept verschillende functies aan om de tabel te maken
//      var table = document.createElement("table");
////      addHtmlElement(table, traineeTableHeader());
//      var body = document.createElement("tbody")
//      var tbody = addHtmlElement(table, body);
//      addHtmlElement(tbody, traineeTableRow(trainee));
//      document.getElementById("traineelijst").appendChild(table);
//      
//      for(var i = 0; i<trainee.uren.length; i++){
//          uurInDBHemZeMonth = trainee.uren[i].factuurDatum.substring(5,7);
//              	
//      }
//      if(uurInDBHemZeMonth == theMonth) {
//    	  traineeTableRow(trainee)
//      }
//      
//          
//      }
//    };
//      xhttp.open("GET", apiTrainee, true);
//      xhttp.setRequestHeader("Content-type", "application/json");
//      xhttp.send(); 
//}
//
//
////tabelmaken
//function traineeTableHeader() {
//   var tableHeader = document.createElement("thead");
//   var tr = addHtmlElement(tableHeader, document.createElement("tr"));
//   addHtmlElementContent(tr, document.createElement("th"), "Voornaam");
//   addHtmlElementContent(tr, document.createElement("th"), "Achternaam");
//   addHtmlElementContent(tr, document.createElement("th"), "Aantal uren");
//   addHtmlElementContent(tr, document.createElement("th"), "Akkoordstatus");
//   return tableHeader;
//}
//
//function addHtmlElement(parent, child) {
//  parent.appendChild(child);
//  return child;
//}
//
//function addHtmlElementContent(parent, child, tekst) {
//   parent.appendChild(child);
//   child.innerHTML = tekst;
//   return child;
//}

//function traineeTableRow(trainee) {
//	var tr = document.createElement("tr");
//   var akkoordstatus = "";
////   var uren = trainee.uren;
//   var aantalUren = 0;
//   console.log(trainee.uren.length);
//   for(var i = 0; i<trainee.uren.length; i++){
//      console.log(trainee.uren[i].accordStatus);
//      if(trainee.uren[i].accordStatus == "TEACCODEREN" || trainee.uren[i].accordStatus == "GOEDGEKEURD" || trainee.uren[i].accordStatus == "AFGEKEURD" ){
//    	  akkoordstatus = trainee.uren[i].accordStatus;
//          aantalUren += trainee.uren[i].aantal ;
//          console.log(akkoordstatus);
//      }
//   }
//   console.log(akkoordstatus);
//   if(akkoordstatus == "TEACCODEREN" || akkoordstatus == "GOEDGEKEURD" || akkoordstatus == "AFGEKEURD" ){
//	   
//	   addHtmlElementContent(tr, document.createElement("td"), trainee.voornaam, trainee.id);
//	   addHtmlElementContent(tr, document.createElement("td"), trainee.achternaam,trainee.id);
//	   addHtmlElementContent(tr, document.createElement("td"), aantalUren, trainee.id);
//	   addHtmlElementContent(tr, document.createElement("td"), akkoordstatus, trainee.id);
//	   
//	   
//   }
//   return tr;
//}
//
//function addHtmlElement(parent, child) {
//   parent.appendChild(child);
//   return child;
//}
//
//function addHtmlElementContent(parent, child, tekst, id) {
//  parent.id = id
//   parent.appendChild(child);
//   child.innerHTML = tekst;
//   return child;
//}
//
//function addButton(parent, child, select, option1, option2, id){
//  parent.id = id;
//  parent.appendChild(child);
//  select.appendChild(option1);
//  option1.innerHTML = "goedkeuren";
//  select.appendChild(option2);
//  option2.innerHTML = "afkeuren";
//  child.appendChild(select);
//   return child;
//}
//
//function klantSendAccord(trainee){
//  var uren = trainee.uren;
//  
//   for(var i = 0; i<uren.length; i++){
//  var table = document.getElementById("traineelijst");
//  var table = table.children[0];
//  var body = table.children[1];
//  var rows = body.children;
//  var aantal = rows.length;
//  // for(var i = 0; i<aantal; i++){
//    var uur = {}
//    uur.id = uren[i].id;
//    var row = rows[0];
//    var cellA = row.children[4];
//    var cellAInhoud = cellA.children[0];
//    if(cellAInhoud.value == "goedkeuren"){
//      uur.accordStatus = 2;
//    }
//    if(cellAInhoud.value == "afkeuren"){
//      uur.accordStatus = 3;
//    }
//    PUTHourAccordStatus(uur, uur.id);
//  }
//
//}
//
////PUT uren
//function PUTHourAccordStatus(uur, rij){
//  var xhttp = new XMLHttpRequest();
//
//   xhttp.onreadystatechange = function () {
//       if (this.readyState == 4) {
//                  console.log(uur);
//                    console.log(uur.accordStatus);
//           if (this.status == 200) {
//
//           } else {
//             
//           }
//       }
//   };
//
//   xhttp.open("PUT", apiUur+rij+"/akkoordstatus/", true);
//   xhttp.setRequestHeader("Content-type", "application/json");
//   xhttp.send(JSON.stringify(uur));  
//}
//
//
////GET uren voor tabel
//function TraineeHourChange(){
//  var xhttp = new XMLHttpRequest();
//    xhttp.onreadystatechange = function() {
//      if (this.readyState == 4 && this.status == 200) {
//      console.log(this.responseText);
//      var trainee = JSON.parse(this.responseText); 
//      console.log(trainee.uren.length);
//              klantSendAccord(trainee);
//      }
//    };
//      xhttp.open("GET", apiTrainee, true);
//      xhttp.setRequestHeader("Content-type", "application/json");
//      xhttp.send(); 
//}

