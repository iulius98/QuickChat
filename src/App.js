import React from "react";
import "./styles/App.css";

import store from "./app/store";
import { Provider } from "react-redux";

import { createTheme, ThemeProvider } from "@mui/material/styles";
import ChatRoom from "./components/chat/ChatRoom";
import MyAppBar from "./components/AppBar";
import UsersList from "./components/chat/UsersList";

// App material themes
const darkTheme = createTheme({
  palette: {
    type: "dark",
  },
});

const lightTheme = createTheme({});

export default class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isDark: false,
    };
  }

  render() {
    // const { classes } = this.props;
    return (
      <ThemeProvider theme={this.state.isDark ? { ...darkTheme } : { ...lightTheme }}>
        <Provider store={store}>
          <MyAppBar />
          <div className="AppContainer">
            <div className="Groups">
              <UsersList />
            </div>

            <div className="ChatRoom">
              <ChatRoom />
            </div>
          </div>
        </Provider>
      </ThemeProvider>
    );
  }
}
