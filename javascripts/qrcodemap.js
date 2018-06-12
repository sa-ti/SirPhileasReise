/**
 * Erhält den QRCode-Wert von Würzburg aus der DB und setzet dementsprechend die Map
 */
function loadQRCode(){
    $.ajax({
        url:"loadQRCode",
        method: "GET",
        dataType: "json",
        success: function(res){
            if(res.wuerzburg == 1){
                document.getElementById("image").src= '../assets/images/qrmapgrün1.png';
                console.log("changed map");
            }

        },
        error: function(req,err){
            console.log(req,err)
        }


    })
}

/**
 * mouseover über Würzburg: Ort des QRCodes wird sichtbar
 */
function displayWuerzburg(){
    document.getElementById("ort_wuerzburg").style.visibility="visible";
}
/**
 * mouseover über Madrid: Ort des QRCodes wird sichtbar
 */
function displayMadrid(){
    document.getElementById("ort_madrid").style.visibility="visible";
}
/**
 * mouseover über London: Ort des QRCodes wird sichtbar
 */
function displayLondon(){
    document.getElementById("ort_london").style.visibility="visible";


}
/**
 * mouseout: default map ohne Ortbeschreibungen wird sichtbar
 */
function displayDefault(){
    document.getElementById("ort_london").style.visibility="hidden";
    document.getElementById("ort_madrid").style.visibility="hidden";
    document.getElementById("ort_wuerzburg").style.visibility="hidden";
}

