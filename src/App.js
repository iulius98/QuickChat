import React, { useState } from "react";
import "./styles/App.css";

import store from "./app/store";
import { Provider } from "react-redux";

import { createTheme, ThemeProvider } from "@mui/material/styles";
import ChatRoom from "./components/chat/ChatRoom";
import MyAppBar from "./components/AppBar";
import UsersList from "./components/chat/UsersList";

import {client, baseUrl, userName} from "./app/wsApi";

// App material themes
const darkTheme = createTheme({
  palette: {
    type: "dark",
  },
});

const lightTheme = createTheme({});

export default function App() {
  const [isDark] = useState(false);
  
    return (
      <ThemeProvider theme={isDark ? { ...darkTheme } : { ...lightTheme }}>
        <Provider store={store}>
          <MyAppBar />
          <div className="AppContainer">
            <div className="Groups">
              <UsersList />
            </div>

            <div className="ChatRoom">
              <ChatRoom client={client} userName={userName}/>
            </div>
          </div>
        </Provider>
      </ThemeProvider>
    );
}
