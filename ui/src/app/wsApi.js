// import SockJS from "sockjs-client/dist/sockjs";

// import store from "./store";
// import { messageAdded } from "../reducers/messagesSlice";

// const axios = require('axios');
// var Stomp = require('stompjs'); //require('@stomp/stompjs');
// const baseUrl = "http://localhost:8080";

// const userName = "TestUser";
// var sessionId;
// var client;
// var subscription;

// function messageFilter(message) {
//   if (message.body) {
//     console.log(message.body);
//     store.dispatch(messageAdded(message));
//   } else {
//     console.log('Got empty message');
//   }
// };


// function connectToServer(){
//  axios.post(baseUrl + '/user/create', {
//     name: userName,
//     timestamp: Date.now(),
//   })
//   .then(function (response) {
//     sessionId = response.data.id;

//     var ws = new SockJS(`http://localhost:8080/ws-quick?sessionId=${sessionId}`);
//     client = Stomp.over(ws);

//     const connectCallback = function() {
//       console.log("Connected");
//       subscription = client.subscribe(`/user/${sessionId}/usertell`, messageFilter);
//     };

//     const errorCallback = function(error) {
//         console.log(error);
//     };

//     client.connect({}, connectCallback, errorCallback);
//   })
//   .catch(function (error) {
//     console.log(error);
//   });
// };

// connectToServer();


// export  {userName, baseUrl, client};


