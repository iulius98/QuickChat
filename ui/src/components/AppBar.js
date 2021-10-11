import * as React from "react";

import { makeStyles } from "@mui/styles";
import { TextField, Typography, Toolbar, AppBar} from "@mui/material";

import { userNameChanged } from "../reducers/userNameSlice";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";


const appBarStyles = makeStyles((theme) => {
  return {
    userNameLabel: {
      margin: "15px", 
    },
    userNameField: {
      position: "relative",
      top: "2px",
      left: "7px",
    }
  };
});

export default function MyAppBar(props) {
  const classes = appBarStyles();

  const userName = useSelector((state) => state.userName);

  const dispatch = useDispatch();

  const changedUserName = (event) => {
    dispatch(userNameChanged(event.target.value));
    props.client.send('/user/change/name', {}, userName);
  }

  return (
    <AppBar position="fixed" className={props.className}>
      <Toolbar variant="dense">
        <Typography variant="h6"  className={classes.userNameLabel}>
          Your name: 
        </Typography>
        <TextField id="userName" color="secondary" className={classes.userNameField} 
        variant="standard" value={userName} onChange={changedUserName}/> 
      </Toolbar>
    </AppBar>
  );
}
