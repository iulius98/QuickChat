import React, { useEffect, useState } from "react";
import "./styles/App.css";

import store from "./app/store";
import { messageAdded } from "./reducers/messagesSlice";
import { usersListUpdated, userAdded, userDeleted, userUpdated } from "./reducers/usersSlice";
import { Provider } from "react-redux";

import { createTheme, ThemeProvider } from "@mui/material/styles";
import ChatRoom from "./components/chat/ChatRoom";
import MyAppBar from "./components/AppBar";
import UsersList from "./components/chat/UsersList";

import * as constants from "./app/constants";

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
        const generalMessage = JSON.parse(message.body);
        console.log(generalMessage);
        switch (generalMessage.messageType) {
          case constants.MESSAGE:
            store.dispatch(messageAdded(generalMessage));
          break;

          case constants.UPGRADE_LIST_USERS:
            store.dispatch(usersListUpdated(generalMessage.content));
          break;

          case constants.ADD_USER:
            store.dispatch(userAdded(generalMessage.content));
          break;

          case constants.DELETE_USER:
            store.dispatch(userDeleted(generalMessage.content));
          break;
          
          case constants.UPDATE_USER:
            store.dispatch(userUpdated(generalMessage.content));
          break;

          default:
            console.log("MESSAGE TYPE NOT AN OPTION!");
          break;
        }
      } else {
        console.log('Got empty message');
      }
    }
  };
  
  useEffect(() => {
    console.log(store.getState().userName);
    axios.post(constants.serverHost + '/user/create', {
              name: store.getState().userName,
              timestamp: Date.now(),
    }).then(function (response) {
      sessionId = response.data.id;

      var ws = new SockJS(`${constants.serverHost}/ws-quick?sessionId=${sessionId}`);
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
            <MyAppBar client={client}/>
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
