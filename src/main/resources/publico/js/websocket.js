let ws;
const worker = new Worker("/js/webworker.js");


worker.onmessage = (data) => {
  console.log(data.data);
  ws.send(JSON.stringify(data.data));
}

function connectSocket() {
  ws = new WebSocket("ws://localhost:7000/inapp/push-forms");

  ws.onopen = (e) => {
    console.log("Conncted: " + this.readyState);
  }
  ws.onclose = (e) => {
    console.log("Disconnected: " + this.readyState);
  }
  ws.onmessage = (data) => {
    console.log("Received Data: " + data.data);
  }
}

function checkConnection() {
  if (!ws || ws.readyState === 3) connectSocket();
}

setInterval(checkConnection, 3000);

document.addEventListener("DOMContentLoaded", connectSocket);

const btnSendToServer = document.getElementById("btnSync");
btnSendToServer.addEventListener("click", () => {
  worker.postMessage('GET');
});