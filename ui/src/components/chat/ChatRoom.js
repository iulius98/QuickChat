import React from "react";
import MessagesList from "./MessagesList";
import NewMessageBox from "./NewMessageBox";
import "../../styles/ChatRoom.css";


export default function ChatRoom(props) {
  // const dispatch = useDispatch();

  // props.socket.onmessage = (event) => {
  //   if (event.data) {
  //     console.log(event);
  //     const msg = JSON.parse(event.data);
  //     dispatch(messageAdded(msg));
  //   }
  // };


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
