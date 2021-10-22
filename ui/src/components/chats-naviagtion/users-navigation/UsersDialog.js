import React from "react";

import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import { Button, InputAdornment, OutlinedInput, Typography } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";

import UsersListCheck from "./UsersListCheck";
import { WsClientContext } from "../../../app/WsClientContext";

import { chatAdded } from "../../../reducers/chatsSlice";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";

import axios from "axios";

import { serverHost } from "../../../app/constants";
import { nanoid } from "@reduxjs/toolkit";

export default function UsersDialog(props) {
  const [lookupText, setLookupText] = React.useState("");
  const [checked, setChecked] = React.useState([]);
  const [users, setUsers] = React.useState([]);
  const wsClient = React.useContext(WsClientContext);
  const sessionId = useSelector((state) => state.profile.sessionId);

  const dispatch = useDispatch();

  const getUsersList = () => {
    if (props.openDialog) {
      console.log("Get Users");
      axios
        .get(serverHost + `/users/${sessionId}`)
        .then(function (response) {
          console.log(response.data);
          setUsers(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  };

  React.useEffect(getUsersList, [props.openDialog, sessionId]);

  const handleToggle = (value) => () => {
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }
    setChecked(newChecked);
  };

  async function StartChat() {
    let chatId;
    const chatName = nanoid(20);
    await axios
      .post(serverHost + `/chat/create/${sessionId}?name=${chatName}`)
      .then(function (response) {
        chatId = response.data.id;
        console.log(chatId);
        dispatch(chatAdded({ id: chatId, name: chatName }));
      })
      .catch(function (error) {
        console.log("ERROR!");
        console.log(error);
      });
    users.forEach((user) => {
      // console.log(user);
      if (checked.indexOf(user.id) !== -1) {
        // console.log(user.id);
        wsClient.send(`/chat/addUser/${chatId}/${user.id}`, {}, {});
      }
    });
  }

  const handleLookup = (text) => {
    setLookupText(text);
  };

  const handleClose = () => {
    props.setOpen(false);
    setChecked([]);
    setUsers([]);
  };

  const handleStart = () => {
    StartChat();
    // handleClose();
  };

  return (
    <Dialog open={props.openDialog} onClose={handleClose}>
      <DialogTitle> Start a chat </DialogTitle>

      <DialogContent>
        <DialogContentText>Choose an user to start a conversation.</DialogContentText>
        <div id="users-dialog" style={{ height: "60vh", width: "30vw" }}>
          <div id="top-divider">
            <hr style={{ width: "98%" }} />
          </div>

          <div
            id="llok-up"
            style={{
              height: "10%",
              width: "100%",
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Typography sx={{ flexBasis: "10", marginRight: "1%", marginLeft: "0" }}> Lookup users: </Typography>
            <OutlinedInput
              margin="dense"
              id="lookup-input-with-icon-adornment"
              type="text"
              sx={{
                flexBasis: "90",
                margin: "1%",
                borderRadius: "50px",
              }}
              value={lookupText}
              variant="outlined"
              onChange={handleLookup}
              endAdornment={
                <InputAdornment position="end">
                  <SearchIcon fontSize="medium" />
                </InputAdornment>
              }
            />
          </div>

          <div id="bottom-divider">
            <hr style={{ width: "98%" }} />
          </div>

          <div id="users-list" style={{ justifyContent: "center", alignItems: "center" }}>
            <UsersListCheck users={users} checked={checked} handleToggle={handleToggle} />
          </div>
        </div>
      </DialogContent>

      <DialogActions>
        <Button onClick={handleClose}>Ok</Button>
        <Button onClick={handleStart}>Start</Button>
      </DialogActions>
    </Dialog>
  );
}
