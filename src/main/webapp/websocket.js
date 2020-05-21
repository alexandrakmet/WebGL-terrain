var wsUri = "ws://" + document.location.host + document.location.pathname + "terrain";
var websocket = new WebSocket(wsUri);


websocket.onopen = function(evt) { onOpen(evt) };
websocket.onmessage = function(evt) { onMessage(evt) };

function onOpen() {
}

function sendInput() {
    var str = document.getElementById("soundSpeed").value;
    console.log("sending text: " + "image");
   // img.src="image.png";
   // init();
    soundSpeed = str;
    //websocket.send(str);
    websocket.send("image");
}

function onMessage(evt) {
    //console.log("received : " + evt.data);
/*
    var bytes = new Uint8Array(evt.data);
    var blob = new Blob( [ bytes ], { type: "image/png" } );
    var urlCreator = window.URL || window.webkitURL;
    // var img = document.querySelector( "#photo" );
       // img.src = urlCreator.createObjectURL(blob);
    var image = document.getElementById('image');
    image.src = 'data:image/png;base64,'+bytes;*/
   /* var arrayBuffer = evt.data;
    var bytes = new Uint8Array(arrayBuffer);
    var blob        = new Blob([bytes.buffer]);
    var image = document.getElementById('image');

    var reader = new FileReader();
    reader.onload = function(e) {
        image.src = e.target.result;
    };
    reader.readAsDataURL(blob);*/
   console.log("received");
    img.src = "data:image/png;base64," + evt.data;
    init();

}