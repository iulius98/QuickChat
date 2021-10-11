import React, { useEffect, useState } from "react";
import "./styles/App.css";

import store from "./app/store";
import { messageAdded } from "./reducers/messagesSlice";
import { Provider } from "react-redux";

import { createTheme, ThemeProvider } from "@mui/material/styles";
import ChatRoom from "./components/chat/ChatRoom";
import MyAppBar from "./components/AppBar";
import UsersList from "./components/chat/UsersList";


import SockJS from "sockjs-client/dist/sockjs";
import Stomp from "stompjs";
import axios from "axios";


// App material themes
const darkTheme = createTheme({
  palette: {
    type: "dark",
  },
});

const lightTheme = createTheme({});

const baseUrl = "http://localhost:8080";

var sessionId;
var client;


export default function App() {
  const [isDark] = useState(false);
  const [isConnected, setIsConnected] = useState(false);

  const messageFilter = (message) => {
    // called when the client receives a STOMP message from the server
    if (message) {
      if (message.body) {
        console.log("Am primit: ");
        console.log(message);
        var msgPrimit = JSON.parse(message.body);
        store.dispatch(messageAdded(msgPrimit));
      } else {
        console.log('Got empty message');
      }
    }
  };
  
  useEffect(() => {
    console.log(store.getState().userName);
    axios.post(baseUrl + '/user/create', {
              name: store.getState().userName,
              timestamp: Date.now(),
    }).then(function (response) {
      sessionId = response.data.id;

      var ws = new SockJS(`http://localhost:8080/ws-quick?sessionId=${sessionId}`);
      client = Stomp.over(ws);

      const connectCallback = function() {
        console.log("Connected");
        setIsConnected(true);
        const subscription = client.subscribe(`/user/${sessionId}/usertell`, messageFilter);
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
            <MyAppBar />
            <div className="AppContainer">
              <div className="Groups">
                <UsersList />
              </div>

              <div className="ChatRoom">
                <ChatRoom client={client} sessionId={sessionId}/>
              </div>
            </div>
          </Provider>) : (
            <h1>Se incarca clientul</h1>
          )
      }
    </ThemeProvider>
  );
}
