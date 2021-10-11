import { createSlice } from "@reduxjs/toolkit";

const initialState = [
  {
    id: 1,
    author: "User",
    content:
      "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quos blanditiis" +
      +"tenetur unde suscipit, quam beatae rerum inventore consectetur, neque doloribus," +
      "cupiditate numquam dignissimos laborum fugiat deleniti? Eum quasi quidem quibusdam",
    timestamp: Date.now() - 355,
  },
  {
    id: 2,
    author: "Me",
    content:
      "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quos blanditiis ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
      +"tenetur unddasdae suscipit, quam beatae rerum inventore consectetur, neque doloribus," +
      "cupiditate numquam dignissimos laborum fugiat deleniti? Eum quasi quidem quibusdam",
    timestamp: Date.now() - 255,
  },
  {
    id: 3,
    author: "User",
    content:
      "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quos blanditiis" +
      +"tenetur unde suscipit, quam beatae rerum inventore consectetur, neque doloribus," +
      "cupiditate numquam dignissimos laborum fugiat deleniti? Eum quasi quidem quibusdam",
    timestamp: Date.now() - 155,
  },
  {
    id: 4,
    author: "User",
    content:
      "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quos blanditiis" +
      +"tenetur unde suscipit, quam beatae rerum inventore consectetur, neque doloribus," +
      "cupiditate numquam dignissimos laborum fugiat deleniti? Eum quasi quidem quibusdam",
    timestamp: Date.now() - 55,
  },
  {
    id: 5,
    author: "Me",
    content:
      "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quos blanditiis" +
      +"tenetur unde suscipit, quam beatae rerum inventore consectetur, neque doloribus," +
      "cupiditate numquam dignissimos laborum fugiat deleniti? Eum quasi quidem quibusdam",
    timestamp: Date.now(),
  }
];

const messagesSlice = createSlice({
  name: "messages",
  initialState,
  reducers: {
    messageAdded(state, action) {
      state.push(action.payload);
    },
  },
});

export const { messageAdded } = messagesSlice.actions;

export default messagesSlice.reducer;
