import React from "react";

import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import { makeStyles } from "@mui/styles";
import { useSelector } from "react-redux";

const messageStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
    display: "inline-block",
    margin: 0,
    padding: 0,

    "&.paper": {
      width: "fit-content",
      height: "auto",
      maxWidth: "90%",
      minWidth: "20%",
      margin: "0.3%",
      borderRadius: "15px",
      float: (props) => {
        return props ? "left" : "right";
      },
      backgroundColor: (props) => {
        return props ? "#00cf00" : "#6699ff";
      },
    },
  },
  author: {
    textAlign: "left",
    paddingLeft: "2%",
    margin: "2%",
  },
  createdAt: {
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
}));

export default function MessageBox(props) {
  const userId = useSelector((state) => state.profile.userId);
  const classes = messageStyles(props.author.id === userId);
  console.log(props);
  return (
    <div className={classes.root}>
      <Paper className={classes.root + " paper"}>
        <Typography variant="h6" className={classes.author}>
          {userId !== props.author.id ? props.author.name : "Me"}
        </Typography>
        <Typography variant="body2" className={classes.messageBody}>
          {props.content}
        </Typography>
        <Typography variant="subtitle2" className={classes.createdAt}>
          {new Date(props.createdAt).toTimeString().substr(0, 5)}
        </Typography>
      </Paper>
    </div>
  );
}
