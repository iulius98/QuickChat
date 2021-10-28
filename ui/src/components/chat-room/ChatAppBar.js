import React from "react";

import { useSelector } from "react-redux";

import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";

export default function ChatAppBar() {
  const users = useSelector((state) => state.users);
  const currentChat = useSelector((state) => state.profile.currentChat);
  let chat = useSelector((state) => state.chats.find((chat) => chat.id === currentChat));
  if (chat === undefined) chat = { name: "You" };

  return (
    <Box sx={{ backgroundColor: "info.main", margin: 0, height: "100%" }}>
      <Typography variant="h5">{chat.name}</Typography>
      <Typography variant="body1">
        {users.map((user) => (user.isWriting ? `${user.name} is typing` : user.name)).join(", ")}
      </Typography>
    </Box>
  );
}
