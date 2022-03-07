const http = require("http");
const WebSocketServer = require("websocket").server;

const httpServer = http.createServer((rew, res) => {
   console.log("We have received a request");
})

const webSocket = new WebSocketServer({
   "httpServer": httpServer,
})

const connections = []
webSocket.on("request", (request) => {
  const  connection = request.accept();
  console.log("New connection accepted");
   connections.push(connection);
   connection.on("open", () => {
       console.log("Connection Open");
   })

   connection.on("close", () => {
       console.log("Connection Closed");
       connections.splice(connections.indexOf(connection), 1)
   })

   connection.on("message", message => {

       connections.forEach(element => {
           if(element !== connection) {
               console.log(`Received Message: ${message.utf8Data}`);
               element.sendUTF(message.utf8Data);
           }

       });
   })
})

httpServer.listen(8080, () => console.log("My Server is running on port 8080"));

//  const SocketServer = require('websocket').server
//  const http = require('http')

//  const server = http.createServer((req, res) => {})

//  server.listen(8080, ()=>{
//      console.log("Listening on port 8080...")
//  })

//  wsServer = new SocketServer({httpServer:server})

//  const connections = []

//  wsServer.on('request', (req) => {
//      const connection = req.accept()
//      console.log('new connection')
//      connections.push(connection)

//      connection.on('message', (mes) => {
//          connections.forEach(element => {
//              if (element != connection)
//                  element.sendUTF(mes.utf8Data)
//          })
//      })

//      connection.on('close', (resCode, des) => {
//          console.log('connection closed')
//          connections.splice(connections.indexOf(connection), 1)
//      })

//  })