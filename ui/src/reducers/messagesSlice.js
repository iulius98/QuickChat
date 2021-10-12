import { createSlice } from "@reduxjs/toolkit";

const initialState = [
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
