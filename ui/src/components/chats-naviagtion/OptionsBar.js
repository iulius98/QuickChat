import React from "react";

import { IconButton, InputAdornment, OutlinedInput, Typography } from "@mui/material";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import AddCircleOutlineOutlinedIcon from "@mui/icons-material/AddCircleOutlineOutlined";
import SearchIcon from "@mui/icons-material/Search";

import UsersDialog from "./users-navigation/UsersDialog";

import { useSelector } from "react-redux";

export default function OptionsBar(props) {
  const [contextMenu, setContextMenu] = React.useState(null);
  const [openUserDialog, setOpenUsersDialog] = React.useState(false);

  const handleContextMenu = (event) => {
    event.preventDefault();
    setContextMenu(
      contextMenu === null
        ? {
            mouseX: event.clientX - 2,
            mouseY: event.clientY - 4,
          }
        : // repeated contextmenu when it is already open closes it with Chrome 84 on Ubuntu
          // Other native context menus might behave different.
          // With this behavior we prevent contextmenu from the backdrop to re-locale existing context menus.
          null
    );
  };

  const handleClose = () => {
    setContextMenu(null);
  };

  const handleSeacrhChange = (event) => {
    props.handleSeacrh(event.target.value);
  };

  const startChat = (event) => {
    console.log(event.target.id);
    setOpenUsersDialog(true);
  };

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <Typography sx={{ flexBasis: "10" }}> Search: </Typography>
      <OutlinedInput
        margin="dense"
        id="filter-input-with-icon-adornment"
        type="text"
        sx={{
          flexBasis: "80%",
          margin: "2%",
          borderRadius: "50px",
        }}
        value={props.filterText}
        variant="outlined"
        onChange={handleSeacrhChange}
        endAdornment={
          <InputAdornment position="end">
            <SearchIcon fontSize="medium" />
          </InputAdornment>
        }
      />
      <IconButton sx={{ flexBasis: "10%", cursor: "context-menu" }} onClick={handleContextMenu}>
        <AddCircleOutlineOutlinedIcon fontSize="large" color="success" />
        <Menu
          open={contextMenu !== null}
          onClose={handleClose}
          anchorReference="anchorPosition"
          anchorPosition={contextMenu !== null ? { top: contextMenu.mouseY, left: contextMenu.mouseX } : undefined}
        >
          <MenuItem id="conversation" onClick={startChat}>
            Start a conversation with someone
          </MenuItem>

          <MenuItem id="group" onClick={startChat}>
            Start a group with some people
          </MenuItem>
        </Menu>
      </IconButton>
      <UsersDialog openDialog={openUserDialog} setOpen={setOpenUsersDialog} />
    </div>
  );
}
