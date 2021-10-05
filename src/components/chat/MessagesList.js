import React from "react";
import { makeStyles } from "@mui/styles";
import MessageBox from "./MessageBox";
import { useSelector } from "react-redux";

const messagesListStyles = makeStyles((theme) => {
  return {
    messagesList: {
      maxHeight: "100%",
      backgroundColor: "#66ff66",
      overflowY: "scroll",
    },
  };
});

export default function MessagesList() {
  const messages = useSelector((state) => state.messages);
  const classes = messagesListStyles();

  return (
    <div className={classes.messagesList}>
      <ul>
        {messages.map((msg) => (
          <MessageBox key={msg.id} author={msg.author} content={msg.content} timestamp={msg.timestamp} />
        ))}
      </ul>
    </div>
  );
}
