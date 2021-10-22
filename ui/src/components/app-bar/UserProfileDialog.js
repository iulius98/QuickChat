import * as React from "react";

import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";

import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { usernameChanged } from "../../reducers/profileSlice";

import { WsClientContext } from "../../app/WsClientContext";

export default function UserProfileDialog(props) {
  const username = useSelector((state) => state.profile.username);
  const wsClient = React.useContext(WsClientContext);
  const [auxUsername, setAuxUsername] = React.useState(username);

  const dispatch = useDispatch();

  const handleChangeusername = (event) => {
    let name = event.target.value;
    if (name.length <= 20) {
      setAuxUsername(event.target.value);
    }
  };

  const handleClose = () => {
    props.setOpen(false);
  };

  const handleAccept = () => {
    dispatch(usernameChanged(auxUsername));
    wsClient.send("/user/change/name", {}, auxUsername);
  };

  return (
    <Dialog open={props.openDialog} onClose={handleClose}>
      <DialogTitle> Profile </DialogTitle>

      <DialogContent>
        <DialogContentText> Set your user name (only 20 characters are allowed). </DialogContentText>
        <TextField
          autoFocus
          margin="dense"
          id="name"
          label="Your user name: "
          type="text"
          fullWidth
          value={auxUsername}
          variant="standard"
          onChange={handleChangeusername}
        />
      </DialogContent>

      <DialogActions>
        <Button onClick={handleClose}>Ok</Button>
        <Button onClick={handleAccept}>Accept</Button>
      </DialogActions>
    </Dialog>
  );
}
