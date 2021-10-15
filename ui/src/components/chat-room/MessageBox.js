import React from "react";

import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import { makeStyles } from "@mui/styles";

const messageStyles = makeStyles((theme) => {
  return {
    paper: {
      width: "fit-content",
      height: "auto",
      maxWidth: "90%",
      minWidth: "20%",
      margin: "1%",
      float: (props) => {
        if (props.author === "Me") return "left";
        return "right";
      },
    },
    author: {
      textAlign: "left",
      paddingLeft: "2%",
      margin: "2%",
    },
    timestamp: {
      textAlign: "right",
      paddingRight: "2%",
      margin: "2%",
    },
    messageBody: {
      textAlign: "left",
      wordWrap: "break-word",
      paddingLeft: "2%",
      paddingRight: "2%",
      margin: "5%",
    },
  };
});

export default function MessageBox(props) {
  const classes = messageStyles(props);

  return (
    <Box sx={{width: "100%", display: "inline-block", margin: 0, padding: 0}}>
      <Paper sx={{backgroundColor: "#bdbdbd", borderRadius: "15px" }} className={classes.paper}>
        <Typography variant="h6" className={classes.author}>
          {props.author}
        </Typography>
        <Typography variant="body2" className={classes.messageBody}>
          {props.content}
        </Typography>
        <Typography variant="subtitle2" className={classes.timestamp}>
          {new Date(props.timestamp).toTimeString().substr(0,5)}
        </Typography>
      </Paper>
    </Box>
  );
}
