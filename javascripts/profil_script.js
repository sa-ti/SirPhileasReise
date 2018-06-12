/**
 * setzt Werte des Users ins Profil und weist entsprechenden Titel zu
 */
function actualUser (){
    $.ajax({
        url:"loadProfile",
        method: "GET",
        dataType:"json",
        success: function (res) {
            document.getElementById("name").innerHTML = res.name;
            document.getElementById("mail").innerHTML = res.mail;

            if(res.titel < 3) {
                document.getElementById("titel").innerHTML = "Stubenhocker";

            }else if (res.titel <6){
                document.getElementById("titel").innerHTML = "kleiner Entdecker";
            }else if (res.titel < 9){
                document.getElementById("titel").innerHTML = "großer Entdecker";

            }else{
                document.getElementById("titel").innerHTML = "Weltenbummler";
            }

        },
        error: function (req, err) {
            console.log(req,err)

        }
    })

}