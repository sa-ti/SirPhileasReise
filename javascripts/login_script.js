/**
 * LOGIN
 */
function checkLogin() {
    var un = document.getElementById("username_id").value;
    var pw = document.getElementById("password_id").value;
    if(un != ""){
        if(pw != ""){
             $.ajax({
                type: "GET",
                url: "loginUser",
                data: {
                    id: un,
                    pwd: pw
                },
                error: function(req,error){
                    alert(req.responseText);
                },
                success: function(res){
                    window.location = "startseite";
                }
            });

        }else {
            alert("Passwort eintragen ");
        }
        }else {
            alert ("Username zu kurz");
        }



}
/**
 * REGISTER
 bei richtigen Eingabe-Parametern wird User erstellt, in Liste angelegt
 */
function checkLoginAfterRegister(){
    var un = document.getElementById("username_id").value;
    var pw = document.getElementById("password_id").value;
    var email = document.getElementById("email_id").value;

        if(un.length > 2){
        if(pw.length > 5 ) {
            if (email.length > 4 ){
                $.ajax({
                    type: "GET",
                    url: "createUser",
                    data: {
                        id: un,
                        pwd: pw,
                        email: email
                    },
                    error: function (req, error) {
                        alert(req.responseText);
                    },
                    success: function (res) {
                        window.location = "startseite";
                    }
                });
            } else{
                alert("Ungültige email!"); }
            }else {
            alert("Ungültige Passworteingabe, für Informationen Maus über das Eingabefeld bewegen!");}
            }else{
            alert("Ungültiger Benutzername, für Informationen Maus über das Eingabefeld bewegen!")

        }

}
/**
 * on enter wird checkLogin() ausgeführt und eingeloggt/Session erstellt
 */

function checkSubmit(e){
    if(e && e.keyCode == 13){
        checkLogin();
    }
}
/**
 * on enter wird checkRegister ausgeführt, user erstellt, eingeloggt/Session erstellt

 */
function checkRegister(e){
    if(e && e.keyCode == 13){
        checkLoginAfterRegister();
    }
}

