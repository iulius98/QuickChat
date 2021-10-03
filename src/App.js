//import logo from "./logo.svg";
import "./styles/App.css";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import React from "react";
import Typography from "@mui/material/Typography";
import ChatRoom from "./components/chatroom/ChatRoom";
import MyAppBar from "./components/AppBar";

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
        <MyAppBar />
        <div className="AppContainer">
          <div className="Groups">
            <Typography variant="h5">This is the groups list</Typography>
          </div>

          <div className="ChatRoom">
            <ChatRoom />
          </div>
        </div>
      </ThemeProvider>
    );
  }
}
