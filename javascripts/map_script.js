/**
 * speichert url der aufgedeckten COuntry
 */

function saveCountries(flagurl){

    $.ajax({
        type: "GET",
        url: "saveCountry",
        data: {
            flagurl: flagurl,
        },
        error: function(req, error){
            console.log(req, error);
        },
        success: function(res){
            console.log(res,"saved country");
        }
    })
}


/**
 * erhält Liste aller gesammelten Länder & läd entsprechende source in Map
 */
function setMap(){
    $.ajax({
        type: "GET",
        url: "loadCountries",
        dataType: "json",
        success: function(res){
            console.log(res);
            for(var i = 0; i < res.length; i++) {
                var country = res[i];
                switch(country) {
                    case"Ägypten":
                        document.getElementById("aegypt").style.visibility = "visible";
                        console.log("erfolgreich");
                        break;
                    case"Kanada":
                        document.getElementById("kanada").style.visibility = "visible";
                        console.log("erfolgreich");
                        break;
                    case"Dänemark":
                        document.getElementById("daenemark").style.visibility = "visible";
                        console.log("erfolgreich");
                        break;
                    case"Island":
                        document.getElementById("island").style.visibility = "visible";
                        console.log("erfolgreich");
                        break;
                    case"Japan":
                        document.getElementById("japan").style.visibility = "visible";
                        console.log("erfolgreich");
                        break;
                    case"Demokratische Republik Kongo":
                        document.getElementById("kongo").style.visibility = "visible";
                        console.log("erfolgreich");
                        break;
                    case"Nepal":
                        document.getElementById("nepal").style.visibility = "visible";
                        console.log("erfolgreich");
                        break;
                    case"Neuseeland":
                        document.getElementById("neuseeland").style.visibility = "visible";
                        console.log("erfolgreich");
                        break;
                    case"Peru":
                        document.getElementById("peru").style.visibility = "visible";
                        console.log("erfolgreich");
                        break;
                    case"Russland":
                        document.getElementById("russland").style.visibility = "visible";
                        console.log("erfolgreich");
                        break;
                    case"China":
                        document.getElementById("china").style.visibility = "visible";
                        break;
                }

            }



        },

        error: function(req,err){
            console.log(req,err)
        }
    })

}