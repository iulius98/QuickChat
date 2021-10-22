import * as React from "react";

import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import Checkbox from "@mui/material/Checkbox";

export default function UsersListCheck(props) {
  return (
    <List sx={{ width: "100%", maxWidth: "100%", bgcolor: "background.paper" }}>
      {props.users.map((user, index) => {
        const labelId = `checkbox-list-label-${index}`;

        return (
          <ListItem
            key={user.id}
            // secondaryAction={
            //   <IconButton edge="end" aria-label="comments">
            //     <CommentIcon />
            //   </IconButton>
            // }
            disablePadding
          >
            <ListItemButton role={undefined} onClick={props.handleToggle(user.id)} dense>
              <ListItemIcon>
                <Checkbox
                  edge="start"
                  checked={props.checked.indexOf(user.id) !== -1}
                  tabIndex={-1}
                  disableRipple
                  inputProps={{ "aria-labelledby": labelId }}
                />
              </ListItemIcon>
              <ListItemText id={labelId} primary={user.name} />
            </ListItemButton>
          </ListItem>
        );
      })}
    </List>
  );
}
