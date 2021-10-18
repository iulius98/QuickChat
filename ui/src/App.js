import React, { useEffect, useState } from "react";
import "./styles/App.css";

import store from "./app/store";
import { messageAdded } from "./reducers/messagesSlice";
import { usersListUpdated, userAdded, userDeleted, userUpdated } from "./reducers/usersSlice";
import { Provider } from "react-redux";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import useMediaQuery from '@mui/material/useMediaQuery';
import CssBaseline from '@mui/material/CssBaseline';
import darkScrollbar from '@mui/material/darkScrollbar';

import ChatRoom from "./components/chat-room/ChatRoom";
import MyAppBar from "./components/appBar/AppBar";
import UsersPage from "./components/users/UsersPage";

import * as constants from "./app/constants";

import SockJS from "sockjs-client/dist/sockjs";
import Stomp from "stompjs";
import axios from "axios";

var sessionId;
var client;

const messageFilter = (message) => {
  // called when the client receives a STOMP message from the server
  if (message) {
    if (message.body) {
      // console.log("Am primit: ");
      const generalMessage = JSON.parse(message.body);
      // console.log(generalMessage);
      switch (generalMessage.messageType) {
        case constants.CHAT_MESSAGE:
          console.log(generalMessage);
        break;
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
          console.log(`MESSAGE TYPE NOT RECOGNIZED: ${generalMessage.messageType}`);
        break;
      }
    } else {
      console.log('GOT EMPTY MESSAGE!');
    }
  }
};

export default function App() {
  const [isConnected, setIsConnected] = useState(false);

  const [mode, setMode] = useState(useMediaQuery('(prefers-color-scheme: dark)') ? 'light' : 'dark');

  const theme = React.useMemo(
    () =>
      createTheme({
        palette: {
          mode: mode,
          secondary: {
            main: '#e65100',
          },
        },
        components: {
          MuiCssBaseline: {
            styleOverrides: {
              body: mode === 'dark' ? darkScrollbar() : null,
            },
          },
        },
      }),
    [mode],
  );
  
  useEffect(() => {
    console.log(store.getState().userName);
    axios.post(constants.serverHost + '/user/create', {
              name: store.getState().userName,
              timestamp: Date.now(),
    }).then(function (response) {
      // console.log(response);
      sessionId = response.data.sessionId;
      setIsConnected(true);

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

  const lightingMode = () => { setMode((prevMode) => (prevMode === 'light' ? 'dark' : 'light')); console.log(mode); }
  
  return (    
    <ThemeProvider theme={theme}>
      <CssBaseline />
      { isConnected ? (
          <Provider store={store}>
            <MyAppBar client={client} lightingMode={lightingMode}/>
            <div className="AppContainer">
              <div className="Groups">
                <UsersPage />
              </div>

              <div className="ChatRoom">
                <ChatRoom client={client} sessionId={sessionId}/>
              </div>
            </div>
          </Provider>
          ) : (
            <div style={{display: "flex", top: "50%", justifyContent: "center", alignItems: "center"}}>
                <h1>Client is loading</h1>  
            </div>
          )
      }
    </ThemeProvider>
  );
}
