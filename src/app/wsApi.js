import SockJS from "sockjs-client/dist/sockjs";
// import {Stomp} from "stomp-websocket/lib/stomp";

// import { Client, Message } from '@stomp/stompjs';
import store from "./store";
import { messageAdded } from "../reducers/messagesSlice";

const axios = require('axios');
var Stomp = require('stompjs');//require('@stomp/stompjs');
// var SockJS = require('sockjs');
const baseUrl = "http://localhost:8080";

const userName = "TestUser";
var sessionId;
var client;
var subscription;

function messageFilter(message) {
    // called when the client receives a STOMP message from the server
    if (message.body) {
      alert('got message with body ' + message.body);
      console.log(message.body);
      store.dispatch(messageAdded(message));
    } else {
      alert('got empty message');
    }
  };


function connectToServer(){
 axios.post(baseUrl + '/user/create', {
    name: userName,
    timestamp: Date.now(),
  })
  .then(function (response) {
    sessionId = response.data.id;
    console.log(sessionId);
    var ws = new SockJS(`http://localhost:8080/ws-quick?sessionId=${sessionId}`);
    var client = Stomp.over(ws);
    var connectCallback = function() {
        console.log("Connected");
    };
    var error_callback = function(error) {
        console.log(error);
    };
    client.connect({}, connectCallback, error_callback);
    // client = new StompJs.Client({
    //     brokerURL: `ws://localhost:9000/ws-quick?sessionId=${sessionId}`,
    //     debug: function (str) {
    //       console.log(str);
    //     },
    //     reconnectDelay: 0,
    //     // heartbeatIncoming: 4000,
    //     // heartbeatOutgoing: 4000,
    //     onConnect: (frame) => {
    //         // Do something, all subscribes must be done is this callback
    //         // This is needed because this will be executed after a (re)connect
    //         console.log("This was in onConnect!");
    //         subscription = client.subscribe(`/${sessionId}/usertell`, messageFilter);
    //       },
    //     onStompError: (frame) => {
    //         // Will be invoked in case of error encountered at Broker
    //         // Bad login/passcode typically will cause an error
    //         // Complaint brokers will set `message` header with a brief message. Body may contain details.
    //         // Compliant brokers will terminate the connection after any error
    //         console.log('Broker reported error: ' + frame.headers['message']);
    //         console.log('Additional details: ' + frame.body);
    //       }
    //   });

    //   client.activate();
  })
  .catch(function (error) {
    console.log(error);
  });
};

connectToServer();

export  {userName, baseUrl, client};


