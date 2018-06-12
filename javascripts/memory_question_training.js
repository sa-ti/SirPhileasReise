
//****************************************************MEMORY***********************************************************

//Memory-Array
var images = [];

//Variablen zum Vergleich der aufgedeckten Pärchen
var guess1 = "";
var guess2 = "";

//Variablen um Listenelement zwischenzuspeichern
var temp1;
var temp2;

//Variable zum feststellen ob 0,1 oder 2 Karten aufgedeckt sind
var count = 0;

//Variable zum zählen bis zum Ende des Spiels
var zugCount = 0


/**
 * Lädt die Flaggen ins Array und füllt die Liste mit dem Coverbild
 */
getFlags();

var cover = '../assets/images/memory_cover.jpg';

var output = "<ol>";
for (var i = 0; i < 12; i++) {

    output += "<li>";

    output += "<img src = '" + cover + "' height='100%' width='100%' id= '" + i + "'/>";

    output += "</li>";
}
output += "</ol>";

document.getElementById("container").innerHTML = output;



/**
 * Dreht Karten um und überprüft ob sie passen, ändert progressbar
 */
$("li").click(function() {

    if ((count < 2) &&  ($(this).children("img").hasClass("aufgedeckt")) === false) {

        //Erhöht den "Rundenzähler", ändert die source des angelickten Bildes, sodass
        //dies eine Flagge zeigt
        count++;

        var sourceChange = images[$(this).children("img").attr("id")];

        $(this).children("img").attr("src", sourceChange);

        $(this).children("img").addClass("aufgedeckt");


        //Wenn erste Karte die aufgedeckt wird, wird die Flagge zwischengespeichert
        //
        if (count === 1 ) {

            guess1 = $(this).children("img").attr("src");
            temp1 = $(this).children("img");

        }


        else {

            //Gleiches Verfahren für die zweite Karte
            guess2 = $(this).children("img").attr("src");
            temp2 = $(this).children("img");


            //Wenn die umgedrehten Karten passen, bleiben sie aufgedeckt liegen
            //und die Fragen werden initialisiert
            if (guess1 === guess2) {

                console.log("match");
                console.log(guess1);

                $("li").children("img[src='" + guess2 + "']").addClass("match");


                questionOne();
                zugCount++;


            }


            else {

                //Wenn die umgedrehten Karten nicht passen, werden sie wieder verdeckt
                console.log("miss");

                setTimeout(function() {

                    //Tempvariablen um die richtigen Karten wieder umdrehen zu können falls miss
                    temp1.attr("src", cover);
                    temp2.attr("src", cover);
                    $("img").not(".match").removeClass("aufgedeckt");

                }, 400);

            }



            //Reset

            count = 0;

            setTimeout(function() { console.clear(); }, 60000);

        }

    }

});

//--------------------------------MemoryLoad&Shuffle---------------------------------------------
/**
 * lädt Flaggen aus DB, lädt sie mit url in containerarray und mischt sie
 */
function getFlags(){
    $.ajax({
        url:"loadFlags",
        method: "GET",
        dataType: "json",
        success: function(res){
            images.push("../assets/images/" + res.Flagge1);
            images.push("../assets/images/" + res.Flagge2);
            images.push("../assets/images/" + res.Flagge3);
            images.push("../assets/images/" + res.Flagge4);
            images.push("../assets/images/" + res.Flagge5);
            images.push("../assets/images/" + res.Flagge6);
            images.push("../assets/images/" + res.Flagge1);
            images.push("../assets/images/" + res.Flagge2);
            images.push("../assets/images/" + res.Flagge3);
            images.push("../assets/images/" + res.Flagge4);
            images.push("../assets/images/" + res.Flagge5);
            images.push("../assets/images/" + res.Flagge6);
            Array.prototype.randomize = function () {

                var i = this.length, j, temp;

                while (--i) {

                    j = Math.floor(Math.random() * (i - 1));

                    temp = this[i];

                    this[i] = this[j];

                    this[j] = temp;

                }

            };

            images.randomize();

        },
        error: function(req,err){
            console.log(req,err);
        }


    })
}


// ---------------------------------------------QUIZFRAGEN-------------------------------------------------------------
/**
 * zählt Wie oft Quiz erscheint
 * @type {number}
 */
var number_Called =1;
/**
 * zählt Anzahl richtig beantworteter Fragen
 * @type {number}
 */
var count_correctAnswer = 0;
/**
 * speichert String der korrekten Antwort
 * @type {string}
 */
var korrekte_Antwort = "";
/**
 * überprüft, ob zuerst Button prüfen aufgerufen wurde
 * @type {boolean}
 */
var answer_checked = false;

/**
 * überprüft, wieoft Button prüfen aufgerufen wurde
 * @type {number}
 */
var count_answer_checked = 0;

/**
 * Memory wird verdeckt & Quiz wird aufgedeckt
 */
function questionOne(){
    document.getElementById("container").style.visibility="hidden";
    document.getElementById("questions").style.visibility="visible";
    document.getElementById("flagge").src = guess1;
    getQuestions(guess1);
    count_answer_checked = 0;
}



/**
 * überprüft, Wie oft Frage erscheint und ruft je nach dem andere Funktion auf
 */
console.log(number_Called + "global");
function nextQuestion(){
    if(!answer_checked){
        alert("Bitte wähle eine Antwort aus");
    }else{
        if(number_Called == 1){
            console.log(number_Called);
            questionTwo();
            number_Called++;
            console.log(number_Called + "1nachher");

        }else if(number_Called == 2){

            questionThree();
            number_Called++;
            console.log(number_Called + "2nachher");
        } else {
            number_Called = 1;
            console.log(number_Called +"3nachher");
            goToMemory();


        }
    }


}

/**
 * ändert Text & generiert neue Fragen
 */
function questionTwo(){

    document.getElementById("text").innerHTML="";
    getQuestions(guess1);
    count_answer_checked = 0;
}


/**
 * ändert Text, Button & generiert neue Fragen
 */
function questionThree(){
    document.getElementById("text").innerHTML="";
    getQuestions(guess1);
    document.getElementById("buttonNext").src = '../assets/images/b_weiter.png';
    count_answer_checked = 0;
}

/**
 * überprüft Antwort & färbt richtige grün & falsch rot
 */
function checkAnswer(){
    answer_checked = true;

    console.log("richtigeAntwort: " + korrekte_Antwort);
    var check_id = checkButton()
    var checked_button = document.getElementById(check_id).innerHTML;
    console.log("gewählte Antwort: " + checked_button);
    if(korrekte_Antwort == checked_button){
        document.getElementById(check_id).style.color = "green";
        if(count_answer_checked == 0){
            count_correctAnswer++;
        }


    } else{
        document.getElementById(check_id).style.color = "red";
        var correct_answer = correctAnswer(korrekte_Antwort);
        document.getElementById(correct_answer).style.color = "green";
    }

    count_answer_checked = 1;


}

/**
 * überprüft, welcher Radiobutton vom User gewählt wurde
 */
function checkButton(){
    if(document.getElementById("button1").checked){
        return ("a1");
    } else if(document.getElementById("button2").checked){
        return ("a2");
    } else if(document.getElementById("button3").checked){
        return ("a3");
    } else if(document.getElementById("button4").checked){
        return ("a4");
    }else{
        alert("Bitte wähle eine Antwort aus")
    }
}


/**
 * Gibt Rückmeldung über erreichte Punkte, ruft SpeichertLand auf, bereitet div auf neue Frage vor & ändert div zu Memory
 */
function goToMemory(){
    if(count_correctAnswer==0 || count_correctAnswer == 1){
        alert("Du hast " +count_correctAnswer+"/3 Fragen richtig! Das Land wird leider nicht in deine Karte hinzugefügt. Viel Erfolg beim nächsten Mal");
        document.getElementById("questions").style.visibility = "hidden";
        document.getElementById("text").innerHTML="Glückwunsch!Damit das Land in deine Karte eingefügt wird, musst du 2 der 3 Fragen richtig beantworten";
        document.getElementById("buttonNext").src= '../assets/images/b_naechsteFrage.png';
        document.getElementById("container").style.visibility = "visible"
        document.getElementById("progressbar").style.visibility = "visible";
        count_correctAnswer= 0;
    } else{
        alert("Du hast " +count_correctAnswer+"/3 Fragen richtig! Super! Das Land wird in deine Karte hinzugefügt.");
        document.getElementById("questions").style.visibility = "hidden";
        document.getElementById("text").innerHTML="Glückwunsch!Damit das Land in deine Karte eingefügt wird, musst du 2 der 3 Fragen richtig beantworten";
        document.getElementById("buttonNext").src= '../assets/images/b_naechsteFrage.png';
        document.getElementById("container").style.visibility = "visible";
        count_correctAnswer= 0;
        console.log("memory_question_level1: " +guess1);
        answer_checked = false;
    }

    if(zugCount==6){
        finishGame();
    }
}

/**
 * Abschlussmessage nach Spielende
 */
function finishGame(){
    var r = confirm("Herzlichen Glückwunsch!\nDurch drücken auf OK kannst du erneut" +
        " spielen, durch abbrechen gelangst du zu deiner Karte.")
    if(r){
        location.reload()
    }
    else{
        location.href = "map";
    }

}


/**
 * lädt Fragen & Antworten aus Datenbank & zeigt sie auf der Seite an
 */
function getQuestions(guess1){

    // Fragen über routes aus QuestionsApplication beziehen
    $.ajax({
        url:"loadQuestionsL1",
        method: "GET",
        dataType: "json",
        data:{
            flagurl: guess1,
        },
        success: function(res){
            console.log(res);
            document.getElementById("frage").innerHTML=  res.frage;
            document.getElementById("a1").innerHTML = res.antwort1;
            document.getElementById("button1").checked=false;
            document.getElementById("a1").style.color = "black";
            document.getElementById("a2").innerHTML = res.antwort2;
            document.getElementById("button2").checked=false;
            document.getElementById("a2").style.color = "black";
            document.getElementById("a3").innerHTML =res.antwort3;
            document.getElementById("button3").checked=false;
            document.getElementById("a3").style.color = "black";
            document.getElementById("a4").innerHTML =res.antwort4;
            document.getElementById("button4").checked=false;
            document.getElementById("a4").style.color = "black";
            korrekte_Antwort = res.korrekte_Antwort;
        },
        error: function(req,err){
            console.log(req,err)
        }


    })
}


/**
 * überprüft, wo sich korrekte Antwort befindet & gibt Id der korrekten Antwort zurück
 */
function correctAnswer(korrekte_Antwort){
    if(document.getElementById("a1").innerHTML == korrekte_Antwort){
        return "a1";
    } else if (document.getElementById("a2").innerHTML == korrekte_Antwort){
        return "a2";
    } else if (document.getElementById("a3").innerHTML == korrekte_Antwort){
        return "a3";
    }else {
        return "a4";
    }

}

/**
 * Überprüfung, ob Spiel wirklich abgebrochen werden soll
 */
function checkAbbrechen(){
    window.onbeforeunload = function() {
        return "Möchten Sie die Seite wirklich verlassen?";
    }

}

/**
 * Onload werden die eventuell bereits geladenen Fragen (z.B. durch Spielabbruch) zurueckgesetzt
 */
$( document ).ready(function() {
    console.log( "ready!" );
    $.ajax({
        url:"resetQuiz",
        method: "GET",
        success: function(res){
            console.log(res);
        },
        error: function(req,err){
            console.log(req,err)
        }


    })
});