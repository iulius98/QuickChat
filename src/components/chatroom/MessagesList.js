import React from "react";
import { withStyles } from "@mui/styles";
import MessageBox from "./MessageBox";

const messagesListStyles = (theme) => ({
  messagesList: {
    maxHeight: "100%",
    backgroundColor: "#66ff66",
    overflowY: "scroll",
  },
});

class MessagesList extends React.Component {
  constructor(props) {
    super(props);
    console.log(this.props.className);
  }

  render() {
    const { classes } = this.props;
    return (
      <div className={classes.messagesList}>
        <ul>
          {messages.map((msg) => (
            <MessageBox key={msg.id} author={msg.author} messageBody={msg.messageBody} timestamp={msg.timestamp} />
          ))}
        </ul>
      </div>
    );
  }
}

export default withStyles(messagesListStyles)(MessagesList);

const messages = [
  {
    id: 1,
    author: "User",
    messageBody:
      "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quos blanditiis" +
      +"tenetur unde suscipit, quam beatae rerum inventore consectetur, neque doloribus," +
      "cupiditate numquam dignissimos laborum fugiat deleniti? Eum quasi quidem quibusdam",
    timestamp: Date.now() - 355,
  },
  {
    id: 2,
    author: "Me",
    messageBody:
      "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quos blanditiis" +
      +"tenetur unddasdae suscipit, quam beatae rerum inventore consectetur, neque doloribus," +
      "cupiditate numquam dignissimos laborum fugiat deleniti? Eum quasi quidem quibusdam",
    timestamp: Date.now() - 255,
  },
  {
    id: 3,
    author: "User",
    messageBody:
      "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quos blanditiis" +
      +"tenetur unde suscipit, quam beatae rerum inventore consectetur, neque doloribus," +
      "cupiditate numquam dignissimos laborum fugiat deleniti? Eum quasi quidem quibusdam",
    timestamp: Date.now() - 155,
  },
  {
    id: 4,
    author: "User",
    messageBody:
      "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quos blanditiis" +
      +"tenetur unde suscipit, quam beatae rerum inventore consectetur, neque doloribus," +
      "cupiditate numquam dignissimos laborum fugiat deleniti? Eum quasi quidem quibusdam",
    timestamp: Date.now() - 55,
  },
  {
    id: 5,
    author: "Me",
    messageBody:
      "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quos blanditiis" +
      +"tenetur unde suscipit, quam beatae rerum inventore consectetur, neque doloribus," +
      "cupiditate numquam dignissimos laborum fugiat deleniti? Eum quasi quidem quibusdam",
    timestamp: Date.now(),
  },
];
