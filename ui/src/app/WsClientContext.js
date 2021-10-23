import React, { createContext, useState, useEffect } from "react";

import store from "./store";
import { updateMessagesList, messageAdded } from "../reducers/messagesSlice";
import { usersListUpdated, userAdded, userDeleted } from "../reducers/usersSlice";
import { chatAdded } from "../reducers/chatsSlice";
import { currentChatChanged, sessionIdChanged, userIdChanged } from "../reducers/profileSlice";

import * as constants from "./constants";

import SockJS from "sockjs-client/dist/sockjs";
import Stomp from "stompjs";
import axios from "axios";

// create context
const WsClientContext = createContext();

const messageFilter = (message) => {
  // called when the client receives a STOMP message from the server
  if (message) {
    if (message.body) {
      console.log("Am primit: ");
      const generalMessage = JSON.parse(message.body);
      console.log(generalMessage);
      switch (generalMessage.messageType) {
        case constants.MESSAGE:
          console.log(generalMessage);
          store.dispatch(messageAdded(generalMessage.content));
          break;

        case constants.NEW_CHAT:
          store.dispatch(
            chatAdded({
              id: generalMessage.content.id,
              name: generalMessage.content.name,
            })
          );
          break;

        case constants.REQUESTED_CHAT:
          store.dispatch(usersListUpdated(generalMessage.content.users));
          store.dispatch(updateMessagesList(generalMessage.content.messages));
          store.dispatch(currentChatChanged(generalMessage.content.id));
          break;

        case constants.ADD_USER_CHAT:
          store.dispatch(userAdded(generalMessage.content.user));
          break;

        case constants.DELETE_USER_CHAT:
          store.dispatch(userDeleted(generalMessage.content.user));
          break;

        default:
          console.log(`MESSAGE TYPE NOT RECOGNIZED: ${generalMessage.messageType}`);
          break;
      }
    } else {
      console.log("GOT EMPTY MESSAGE!");
    }
  }
};

const WsClientContextProvider = ({ children }) => {
  const [isConnected, setIsConnected] = useState(false);
  const [wsClient, setWsClient] = useState(null);

  useEffect(() => {
    axios
      .post(constants.serverHost + "/user/create", {
        name: store.getState().profile.username,
        timestamp: Date.now(),
      })
      .then(function (response) {
        const sessionId = response.data.sessionId;
        const userId = response.data.id;

        const ws = new SockJS(`${constants.serverHost}/ws-quick?sessionId=${sessionId}`);
        const client = Stomp.over(ws);

        const connectCallback = function () {
          store.dispatch(sessionIdChanged(sessionId));
          store.dispatch(userIdChanged(userId));
          setIsConnected(true);
          setWsClient(client);
          client.subscribe(`/user/${sessionId}/usertell`, messageFilter);
        };

        const errorCallback = function (error) {
          console.log(error);
        };

        client.connect({}, connectCallback, errorCallback);
      })
      .catch((error) => console.error(error));
  }, []);

  const whatToRender = () => {
    if (isConnected) {
      // the Provider gives access to the context to its children
      return <WsClientContext.Provider value={wsClient}>{children};</WsClientContext.Provider>;
    }
    return (
      <div
        style={{
          display: "flex",
          top: "50%",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <h1>Client is loading</h1>
      </div>
    );
  };

  return whatToRender();
};

export { WsClientContext, WsClientContextProvider };
