import React from "react";
import MessagesList from "./MessagesList";
import NewMessageBox from "./NewMessageBox";
import "../../styles/ChatRoom.css";


export default function ChatRoom(props) {

  return (
    <div className="ChatRoomContainer">
      <div className="MessagesList">
        <MessagesList />
      </div>
      <div className="NewMessageBox">
        <NewMessageBox client={props.client} sessionId={props.sessionId}/>
      </div>
    </div>
  );
}
