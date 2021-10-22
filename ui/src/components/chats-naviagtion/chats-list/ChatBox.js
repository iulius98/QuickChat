import React from "react";

import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

import { useDispatch } from "react-redux";

import { currentChatChanged } from "../../../reducers/profileSlice";

import axios from "axios";

export default function ChatBox(props) {
  // const [contextMenu, setContextMenu] = React.useState(null);
  const dispatch = useDispatch();

  return (
    <Paper
      sx={{
        width: "100%",
        height: "min-content",
        maxWidth: "95%",
        wordWrap: "break-word",
        overflow: "auto",
        textAlign: "center",
        cursor: "context-menu",
      }}
    >
      <Typography variant="h5"> {props.chat.name} </Typography>
    </Paper>
  );
}
