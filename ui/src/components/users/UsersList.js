import React from "react";
import { useSelector } from "react-redux";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

export default function UsersList(props) {
  const users = useSelector((state) => state.users);

  const renderUsers = () => {
    let renderedUsers = users;
    console.log(renderedUsers);
    if (props.filterText != "") {
      let reg = new RegExp("\w*" + props.filterText + "\w*");
      renderedUsers = renderedUsers.filter((user) => reg.test(user.name));
    } 
    console.log(renderedUsers);
    return renderedUsers.map((user) => (
      <Paper key={user.id} sx={{ width: "100%", maxWidth: "95%", wordWrap: "break-word", overflow: "auto" }}>
        <Typography variant="h5"> {user.name} </Typography>
      </Paper>
    )); 
  };

  return (
    <section>
      {renderUsers()}
    </section>
  );
}
