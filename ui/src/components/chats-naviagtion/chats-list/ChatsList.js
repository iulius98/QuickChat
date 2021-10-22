import React from "react";

import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import Typography from "@mui/material/Typography";

import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";

import { serverHost } from "../../../app/constants";
import { WsClientContext } from "../../../app/WsClientContext";
import axios from "axios";

export default function ChatsList(props) {
  const chats = useSelector((state) => state.chats);
  const sessionId = useSelector((state) => state.profile.sessionId);
  const wsClient = React.useContext(WsClientContext);

  const handleClickedItem = async (chatId) => {
    console.log(chatId);
    wsClient.send(`/user/${sessionId}/chat/${chatId}`, {}, {});
    // axios
    //   .get(serverHost + `/user/${sessionId}/chat/${chatId}`)
    //   .then((response) => {
    //     console.log(response);

    //   })
    //   .catch((error) => console.error(error));
  };

  const renderChatsList = () => {
    let renderedChats = chats;
    if (props.filterText !== "") {
      let reg = new RegExp("\\w*" + props.filterText + "\\w*");
      renderedChats = renderedChats.filter((chat) => reg.test(chat.name));
    }
    return (
      <List sx={{ width: "100%", maxWidth: "100%", bgcolor: "background.paper" }}>
        {renderedChats.map((chat, index) => {
          const labelId = `chat-list-label-${index}`;
          return (
            <ListItem key={chat.id} disablePadding>
              <ListItemButton role={undefined} onClick={() => handleClickedItem(chat.id)} dense>
                <ListItemText id={labelId} primary={<Typography variant="h5"> {chat.name} </Typography>} />
              </ListItemButton>
            </ListItem>
          );
        })}
      </List>
    );
  };

  return renderChatsList();
}
