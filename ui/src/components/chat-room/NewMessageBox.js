import React, { useState } from "react";

import IconButton from "@mui/material/IconButton";
import { SendSharp } from "@mui/icons-material";
import { InputAdornment, OutlinedInput } from "@mui/material";

import { useDispatch } from "react-redux";
import { nanoid } from "@reduxjs/toolkit";
import { messageAdded } from "../../reducers/messagesSlice";
import { useSelector } from "react-redux";


export default function NewMessageBox(props) {
  const userName = useSelector((state) => state.userName);

  const [content, setContent] = useState("");

  const dispatch = useDispatch();

  const onContentChanged = (event) => setContent(event.target.value);

  const onSubmit = () => {
    if (content) {
      const msg = { id: nanoid(), author: "Me", content: content, timestamp: Date.now() }
      dispatch(messageAdded(msg));
      
      if (props.client) {
        const msgExt = { id: nanoid(), author: userName, content: content, timestamp: Date.now() }
        props.client.send('/chat', {}, JSON.stringify(msgExt));
      }
      else
        console.log("Client not defined!");
    }
    setContent("");
  };

  return (
    <OutlinedInput id="message-input-with-icon-adornment" 
      variant='outlined' 
      color="primary" 
      value={content}
      sx={{width: "90%", borderRadius: "50px"}}
      onChange={onContentChanged}
      onKeyPress={(event) => {
        if (event.key === 'Enter')
            onSubmit();
      }}
      endAdornment = {
      <InputAdornment position="end">
        <IconButton color="primary" aria-label="upload picture" component="span" onClick={onSubmit}>
          <SendSharp color="secondary"/>
        </IconButton>
      </InputAdornment>
    } />
  );
}
