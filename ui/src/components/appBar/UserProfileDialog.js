import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

import { useSelector } from "react-redux";


export default function UserProfileDialog(props) {
    const userName = useSelector((state) => state.userName);
    const [auxUserName, setAuxUserName] = React.useState(userName);

    const handleChangeUserName = (event) => {
        let name = event.target.value;
        if (name.length <= 20) {
            setAuxUserName(event.target.value);
        }
    }

    const handleClose = () => {
        props.setOpen(false);
    };

    const handleAccept = () => {
        props.changeUserName(auxUserName);
    }

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
                    value={auxUserName}
                    variant="standard"
                    onChange={handleChangeUserName}
                />
            </DialogContent>
            
            <DialogActions>
                <Button onClick={handleClose}>Ok</Button>
                <Button onClick={handleAccept}>Accept</Button>
            </DialogActions>
        </Dialog>
    );
}