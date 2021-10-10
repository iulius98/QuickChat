import React from "react";
import { makeStyles } from "@mui/styles";
import MessageBox from "./MessageBox";
import { useSelector } from "react-redux";
import { useGetMessagesQuery } from "../../app/websocketAPI";
import { Typography } from "@mui/material";

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
  // const { data, error, isError, isLoading } = useGetMessagesQuery("test");
  const messages = useSelector((state) => state.messages);
  const classes = messagesListStyles();

  return (
    <div className={classes.messagesList}>
      {/* {isError ? (
        <>There is an error: {error.messages}</>
      ) : isLoading ? (
        <>Loading...</>
      ) : data ? ( */}
        <ul>
          {messages.map((msg) => (
           <MessageBox key={msg.id} author={msg.author} content={msg.content} timestamp={msg.timestamp} />
          ))}
        </ul>
     {/* ) : null} */}
    </div>
  );
}

{/* <MessageBox key={msg.id} author={msg.author} content={msg.content} timestamp={msg.timestamp} /> */}
