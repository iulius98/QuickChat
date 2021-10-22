import React from "react";

import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

export default function UserBox(props) {
  return (
    <Paper
      sx={{
        width: "100%",
        height: "min-content",
        maxWidth: "95%",
        wordWrap: "break-word",
        overflow: "auto",
        textAlign: "center",
        cursor: "context-menu",
      }}
    >
      <Typography variant="h5"> {props.user.name} </Typography>
    </Paper>
  );
}
