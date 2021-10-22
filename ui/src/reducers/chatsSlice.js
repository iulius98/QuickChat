import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const chatsSlice = createSlice({
  name: "chats",
  initialState,
  reducers: {
    chatAdded(state, action) {
      state.push(action.payload);
    },
    chatDeleted(state, action) {
      return state.filter((obj) => {
        return obj.id !== action.payload.id;
      });
    },
    chatUpdated(state, action) {
      return state.map((obj) => (obj.id === action.payload.id ? { ...obj, usersSize: action.payload.newSize } : obj));
    },
  },
});

export const { chatAdded, chatDeleted, chatUpdated } = chatsSlice.actions;

export default chatsSlice.reducer;
