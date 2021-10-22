import React from "react";
import MessagesList from "./MessagesList";
import NewMessageBox from "./NewMessageBox";
import "../../styles/ChatRoom.css";
import ChatAppBar from "./ChatAppBar";

export default function ChatRoom() {
  return (
    <div className="ChatRoomContainer">
      <div className="ChatAppBar">
        <ChatAppBar />
      </div>

      <div className="MessagesList">
        <MessagesList />
      </div>

      <div className="NewMessageBox">
        <NewMessageBox />
      </div>
    </div>
  );
}
