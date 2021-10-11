import React,  { useRef, useEffect, useCallback } from 'react';
import { makeStyles } from "@mui/styles";
import MessageBox from "./MessageBox";
import { useSelector } from "react-redux";
import Paper from "@mui/material/Paper";

const messagesListStyles = makeStyles((theme) => {
  return {
    messagesList: {
      maxHeight: "100%",
      backgroundColor: "#66ff66",
      overflowY: "auto",
    },
    paper: {
      width: "50%",
      margin: "2%",
      visibility: "hidden",
      float: (props) => {
        if (props.author === "Me") return "left";
        return "right";
      },
    }
  };
});

export default function MessagesList() {
  const messages = useSelector((state) => state.messages);
  const classes = messagesListStyles();

  const getScroll = () => {
    if (window.pageYOffset !== undefined) {
        console.log(" Y-axis : " + window.pageYOffset); 
        return window.pageYOffset;
    } else {
        var y_axis, doc = document,
            ele = doc.documentElement,
            b = doc.body;
        y_axis = ele.scrollTop || b.scrollTop || 0;
        console.log(" Y-axis : " + y_axis);
        return y_axis;
    }
}

  useEffect(() => {
    getScroll() 
    document.getElementById("last").scrollIntoView(true);
    getScroll(); 
  }, [messages]);

  // window.onload = (e) => document.getElementById("last").scrollIntoView(true);

  return (
    <div id="list" className={classes.messagesList}>
      <ul>
        {
          messages.map((msg, index) => 
            <MessageBox key={msg.id} author={msg.author} content={msg.content} timestamp={msg.timestamp} />) 
        }
        <Paper className={classes.paper} color="primary">
                        <div id="last">STAT</div>
        </Paper>;
        </ul>
    </div>
  );
}

