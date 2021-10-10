import * as React from "react";
import MenuIcon from "@mui/icons-material/Menu";
import { TextField, IconButton, Typography, Toolbar, AppBar} from "@mui/material";

export default function MyAppBar(props) {
    return (
      <AppBar position="fixed" className={props.className}>
        <Toolbar variant="dense">
          <IconButton edge="start" color="inherit" aria-label="menu" sx={{ mr: 2 }}>
            <MenuIcon />
          </IconButton>
          {/* <TextField id="outlined-basic" label="Username" variant="outlined" value={props.userName}/> */}
          <Typography variant="h6" color="inherit" component="div">
            {props.userName}
          </Typography>
        </Toolbar>
      </AppBar>
    );
}
