import React from "react";
import { useSelector } from "react-redux";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

export default function UsersList() {
  const users = useSelector((state) => state.users);

  console.log("users");
  console.log(users);

  const renderUsers = users.map((user) => (
    <Paper key={user.id} sx={{ width: "100%", maxWidth: "95%", wordWrap: "break-word", overflow: "auto" }}>
      <Typography variant="h5"> {user.name} </Typography>
    </Paper>
  ));

  return (
    <section>
      <Typography variant="h2"> Online Users </Typography>
      {renderUsers}
    </section>
  );
}
