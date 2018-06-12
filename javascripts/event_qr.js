/**
 * Created by Susanne on 28.02.2017.
 */
$( drag );
$( drop );
$  (wrongdrop);

function drag() {

    $('#draggableright').draggable({
        cursor: "move",
        revert: "invalid",
        opacity: 0.7,


    });
    $('#draggablefalse').draggable({
        cursor: "move",
        revert: "invalid",
        opacity: 0.7,

    });
    $('#draggablefalse2').draggable({
        cursor: "move",
        revert: "invalid",
        opacity: 0.7,

    });
}

/**
 * dropzone ertsellen
 */
function drop() {
    $('#droppable').droppable({
        accept: '#draggableright, #draggablefalse, #draggablefalse2',
        hoverClass: 'hovered',
        drop: function rightevent(event, ui){
            var id = $(ui.draggable).attr('id');
            if(id == ('draggableright')){
                var flagurl = "../assets/images/Flagge11.png";
                alert('Super, du hast das richtige Land gewählt! China wird nun zur deiner Weltkarte hinzugefügt');
                window.location = 'startseite';
                saveQRCode();
                saveCountries(flagurl);

            }else{
                alert('Das war leider die falsche Wahl. Du erhälst das Land leider nicht. Mit Klick auf "ok" kommst du zu deinem Profil zurück');
                window.location = 'startseite';
            }
        }
    });

}

/**
 * speichert, dass für diesen Ort QRCode bereits eingescannt wurde
 */
function saveQRCode(){
    console.log("Ich geh in Ajax rein");
    $.ajax({
        type: "GET",
        url: "saveQRCode",
        error: function(req, error){
            console.log(req, error);
        },
        success: function(res){
            console.log(res,"saved qr");
        }
    })
}