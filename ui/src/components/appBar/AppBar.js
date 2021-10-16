import * as React from "react";

import { makeStyles } from "@mui/styles";
import { useTheme } from "@emotion/react";
import { TextField, Typography, Toolbar, AppBar, IconButton} from "@mui/material";
import Grid from '@mui/material/Grid';
import Brightness2Icon from '@mui/icons-material/Brightness2';
import Brightness5Icon from '@mui/icons-material/Brightness5';
import SettingsIcon from '@mui/icons-material/Settings';
import UserProfileDialog from "./UserProfileDialog";

import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { userNameChanged } from "../../reducers/userNameSlice";

const appBarStyles = makeStyles((theme) => ({
    appBar: {
      backgroundColor: theme.palette.primary.dark,
    },
    userNameLabel: {
      margin: "15px", 
      display: "inline-block",
    },
    userNameField: {
      position: "relative",
      color: "white",
      top: "2px",
      left: "7px",
      display: "inline-block",
    }
  })
);

export default function MyAppBar(props) {
  const classes = appBarStyles();
  const [openSettings, setOpenSettings] = React.useState(false);

  const theme = useTheme();

  const userName = useSelector((state) => state.userName);

  const lightingMode = () => {
    props.lightingMode();
  }

  const clickedSettings = () => {
    setOpenSettings(true);
  }

  const dispatch = useDispatch();

  const changeUserName = (name) => {
    dispatch(userNameChanged(name));
    props.client.send('/user/change/name', {}, name);
  };

  return (
    <div>
      <AppBar position="fixed">
        <Toolbar className={classes.appBar}>
          <Grid container> 
            <Grid item xs={3}>
              <Typography variant="h6"  className={classes.userNameLabel}> Your name: {userName} </Typography>
              {/* <TextField id="userName" className={classes.userNameField}  
              variant="standard" value={userName} onChange={changedUserName}/>  */}
            </Grid>

            <Grid item xs={8}>
            </Grid>

            <Grid item xs={1}>
              <IconButton aria-label="dark-light-mode" onClick={lightingMode} sx={{color: "yellow"}}>
                {theme.palette.mode === 'dark' ? <Brightness5Icon />  : <Brightness2Icon />}
              </IconButton>
              <IconButton aria-label="dark-light-mode" sx={{color: "white"}} onClick={clickedSettings}>
                <SettingsIcon />
              </IconButton>
            </Grid>
          </Grid>
        </Toolbar>
      </AppBar>
      <UserProfileDialog openDialog={openSettings} setOpen={setOpenSettings} changeUserName={changeUserName}/>
    </div>
  );
}
