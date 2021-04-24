let ws;
const worker = new Worker("/js/webworker.js");


worker.onmessage = (data) => {
  console.log(data.data);
  ws.send(JSON.stringify(data.data));
  /*
    ws.onmessage = (data) => { //Telling the worker to delete the data in IndexDB and then create forms with the new information.
      console.log("Received Data: " + data.data);
      //worker.postMessage(['UPDATE',data.data]); //sending forms to worker for process
    }*/
}

function connectSocket() {
  ws = new WebSocket("ws://localhost:7000/inapp/push-forms");
  console.log(ws.bufferedAmount);
  ws.onopen = (e) => {
    console.log("Conncted: " + this.readyState);
  }
  ws.onclose = (e) => {
    console.log("Disconnected: " + this.readyState);
  }
  ws.onmessage = (data) => { //Telling the worker to delete the data in IndexDB and then create forms with the new information.
    console.log("Received Data: " + data.data);
    worker.postMessage(['UPDATE', data.data]); //sending forms to worker for process
  }
}



function checkConnection() {
  if (!ws || ws.readyState === 3) connectSocket();
}

connectSocket()
setInterval(checkConnection, 3000);

//document.addEventListener("DOMContentLoaded", connectSocket);

const btnSendToServer = document.getElementById("btnSync");
btnSendToServer.addEventListener("click", () => {
  worker.postMessage(['GET']);
});