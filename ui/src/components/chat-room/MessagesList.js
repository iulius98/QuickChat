import React,  { useEffect } from 'react';
import MessageBox from "./MessageBox";

import { useSelector } from "react-redux";

import Box from "@mui/material/Box";
import { makeStyles } from "@mui/styles";

const messagesListStyles = makeStyles((theme) => {
  return {
    messagesList: {
      maxHeight: "100%",
      overflowY: "auto",
    },
    box: {
      width: "100%", 
      display: "inline-block", 
      visibility: "hidden",
       height: 0, 
       float: "left"
    }
  };
});

export default function MessagesList() {
  const messages = useSelector((state) => state.messages);
  const classes = messagesListStyles();

  const getScroll = () => {
    if (window.pageYOffset !== undefined) {
        // console.log(" Y-axis : " + window.pageYOffset); 
        return window.pageYOffset;
    } else {
        var y_axis, doc = document,
            ele = doc.documentElement,
            b = doc.body;
        y_axis = ele.scrollTop || b.scrollTop || 0;
        // console.log(" Y-axis : " + y_axis);
        return y_axis;
    }
}

  useEffect(() => {
    // getScroll() 
    document.getElementById("last").scrollIntoView(true);
    // getScroll(); 
  }, [messages]);

  return (
    <div className={classes.messagesList}>
        {
          messages.map((msg, index) => 
            <MessageBox key={msg.id} author={msg.author} content={msg.content} timestamp={msg.timestamp} />) 
        }
        <Box className={classes.box}>
            <div id="last" />
        </Box>
    </div>
  );
}

