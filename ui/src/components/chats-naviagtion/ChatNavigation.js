import React from "react";
import OptionsBar from "./OptionsBar";
import ChatsList from "./chats-list/ChatsList";

export default function ChatNavigation() {
  const [filterText, setFilterText] = React.useState("");

  const handleSeacrh = (text) => {
    setFilterText(text);
  };

  return (
    <div>
      <div id="search">
        <OptionsBar handleSeacrh={handleSeacrh} filterText={filterText} />
      </div>

      <div id="chats-divider">
        <hr style={{ width: "95%" }} />
      </div>

      <div id="chats-list" style={{ justifyContent: "center", alignItems: "center" }}>
        <ChatsList filterText={filterText} />
      </div>
    </div>
  );
}
