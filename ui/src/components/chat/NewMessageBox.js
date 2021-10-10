import React, { useState } from "react";

import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import IconButton from "@mui/material/IconButton";
import { SendSharp } from "@mui/icons-material";

import { useDispatch } from "react-redux";
import { nanoid } from "@reduxjs/toolkit";
import { messageAdded } from "../../reducers/messagesSlice";
import { makeStyles } from "@mui/styles";
import { userName } from "../../app/wsApi";


const newMessageBoxStyles = makeStyles((theme) => {
  return {
    inputArea: {
      width: "100%",
      padding: "20px",
      position: "relative",
      top: "15%",
    },
    sendButton: {
      position: "relative",
      left: "20%",
      top: "5%",
    },
  };
});

export default function NewMessageBox(props) {
  const classes = newMessageBoxStyles();

  const [content, setContent] = useState("");

  const dispatch = useDispatch();

  const onContentChanged = (event) => setContent(event.target.value);
  const onSumbit = () => {
    if (content) {
      const msg = { id: nanoid(), author: "Me", content: content, timestamp: Date.now() }
      dispatch(messageAdded(msg));
      
      if (props.client) {
        const msgExt = { id: nanoid(), author: props.sessionId, content: content, timestamp: Date.now() }
        props.client.send('/chat', {}, JSON.stringify(msgExt));
      }
      else
        console.log("Client not defined!");
    }
    setContent("");
  };

  return (
    <Grid container className={classes.inputArea}>
      <Grid item xs={11} sm={11} md={11} lg={11} xl={11}>
        <TextField id="outlined-multiline-flexible" label="Your message" fullWidth maxRows={2} multiline value={content} onChange={onContentChanged} />
      </Grid>
      <Grid item xs={1} sm={1} md={1} lg={1} xl={1}>
        <IconButton aria-label="send" color="primary" size="small" className={classes.sendButton} onClick={onSumbit}>
          <SendSharp fontSize="large" />
        </IconButton>
      </Grid>
    </Grid>
  );
}
