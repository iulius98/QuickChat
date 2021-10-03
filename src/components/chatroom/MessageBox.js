import React from "react";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import { makeStyles } from "@mui/styles";

const messageStyles = makeStyles((theme) => {
  return {
    paper: {
      backgroundColor: "transparent",
      width: "50%",
      margin: "2%",
      float: (props) => {
        if (props.author === "Me") return "left";
        return "right";
      },
    },
    author: {
      textAlign: "left",
      paddingLeft: "2%",
      margin: "3%",
    },
    timestamp: {
      textAlign: "right",
      paddingRight: "2%",
      margin: "3%",
    },
    messageBody: {
      textAlign: "left",
      paddingLeft: "2%",
      margin: "5%",
    },
  };
});

export default function MessageBox(props) {
  const classes = messageStyles(props);

  return (
    <Paper className={classes.paper}>
      <Typography variant="h6" className={classes.author}>
        {props.author}
      </Typography>
      <Typography variant="body2" className={classes.messageBody}>
        {props.messageBody}
      </Typography>
      <Typography variant="subtitle2" className={classes.timestamp}>
        {new Date(props.timestamp).toLocaleTimeString().substr(0, 4)}
      </Typography>
    </Paper>
  );
}
