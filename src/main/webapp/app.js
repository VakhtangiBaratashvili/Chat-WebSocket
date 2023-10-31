let ws;
function connect() {
    const username = document.getElementById("username").value;
    const host = document.location.host;
    const pathName = document.location.pathname;
    ws = new WebSocket("ws://" + host + pathName + "chat/" +username);
    ws.onmessage = function (event) {
        const message = JSON.parse(event.data);
        const log = document.getElementById("logs");
        log.innerHTML += message.from + ": " + message.content + "\n";
    }
}

function send() {
    const message = document.getElementById("message").value;
    const messageJSON = JSON.stringify({
        "content": message
    });
    ws.send(messageJSON);
}