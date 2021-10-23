import React from "react";

import IconButton from "@mui/material/IconButton";
import { SendSharp } from "@mui/icons-material";
import { InputAdornment, OutlinedInput } from "@mui/material";

import { useDispatch } from "react-redux";
import { nanoid } from "@reduxjs/toolkit";
import { messageAdded } from "../../reducers/messagesSlice";
import { useSelector } from "react-redux";

import { WsClientContext } from "../../app/WsClientContext";

export default function NewMessageBox() {
  const wsClient = React.useContext(WsClientContext);
  const [content, setContent] = React.useState("");

  const profile = useSelector((state) => state.profile);
  const dispatch = useDispatch();

  const onContentChanged = (event) => setContent(event.target.value);

  const onSubmit = () => {
    if (content) {
      const timestamp = Date.now();
      const msg = {
        id: nanoid(),
        authorId:  profile.userId,
        authorName: "Me",
        content: content,
        createdAt: timestamp,
      };
      dispatch(messageAdded(msg));

      if (wsClient) {
        const msgExt = {
          chat: { id: profile.currentChat },
          content: content,
          createdAt: timestamp,
        };
        wsClient.send("/chat", {}, JSON.stringify(msgExt));
      } else console.log("Client not defined!");
    }
    setContent("");
  };

  return (
    <OutlinedInput
      id="message-input-with-icon-adornment"
      variant="outlined"
      color="primary"
      value={content}
      sx={{ width: "75%", borderRadius: "50px" }}
      onChange={onContentChanged}
      maxRows={12}
      multiline={true}
      onKeyPress={(event) => {
        if (event.key === "Enter") {
          event.preventDefault();
          onSubmit();
        }
      }}
      endAdornment={
        <InputAdornment position="end">
          <IconButton color="primary" aria-label="upload picture" component="span" onClick={onSubmit}>
            <SendSharp color="secondary" />
          </IconButton>
        </InputAdornment>
      }
    />
  );
}
