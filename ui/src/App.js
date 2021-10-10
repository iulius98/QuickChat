import React, { useEffect, useState } from "react";
import "./styles/App.css";

import store from "./app/store";
import messageAdded from "./reducers/messagesSlice";
import { Provider } from "react-redux";

import { createTheme, ThemeProvider } from "@mui/material/styles";
import ChatRoom from "./components/chat/ChatRoom";
import MyAppBar from "./components/AppBar";
import UsersList from "./components/chat/UsersList";

// import {client, baseUrl, userName} from "./app/wsApi";

import SockJS from "sockjs-client/dist/sockjs";
import Stomp from "stompjs";
import axios from "axios";

import { nanoid } from "@reduxjs/toolkit";

// App material themes
const darkTheme = createTheme({
  palette: {
    type: "dark",
  },
});

const lightTheme = createTheme({});

const baseUrl = "http://localhost:8080";

const userName = "TestUser";
var sessionId;
var client;
var subscription;

// if (client) console.log("Client is here");
// else console.log("Client is not here");

export default function App() {
  const [isDark] = useState(false);
  const [isConnected, setIsConnected] = useState(false);

  const messageFilter = (message) => {
    // called when the client receives a STOMP message from the server
    if (message) {
      if (message.body) {
        console.log("Am primit: ");
        console.log(message);
        const msg = { id: nanoid(), author: "Me", content: "This is a test", timestamp: Date.now() };
        var msgPrimit = JSON.parse(message.body);
        console.log(msgPrimit);
        msgPrimit.id = nanoid();
        console.log(msg);
        store.dispatch(messageAdded(msgPrimit));
      } else {
        console.log('Got empty message');
      }
    }
  };
  
  useEffect(() => {
    axios.post(baseUrl + '/user/create', {
              name: userName,
              timestamp: Date.now(),
    }).then(function (response) {
      sessionId = response.data.id;

      var ws = new SockJS(`http://localhost:8080/ws-quick?sessionId=${sessionId}`);
      client = Stomp.over(ws);

      const connectCallback = function() {
        console.log("Connected");
        setIsConnected(true);
        subscription = client.subscribe(`/user/${sessionId}/usertell`, messageFilter);
      };

      const errorCallback = function(error) {
          console.log(error);
      };

      client.connect({}, connectCallback, errorCallback);
    }).catch(function (error) {
      console.log(error);
    });

  }, []);
  
  return (    
    <ThemeProvider theme={isDark ? { ...darkTheme } : { ...lightTheme }}>
      { isConnected ? (
          <Provider store={store}>
            <MyAppBar userName={userName}/>
            <div className="AppContainer">
              <div className="Groups">
                <UsersList />
              </div>

              <div className="ChatRoom">
                <ChatRoom client={client} userName={userName} sessionId={sessionId}/>
              </div>
            </div>
          </Provider>) : (
            <h1>Se incarca clientul</h1>
          )
      }
    </ThemeProvider>
  );
}
