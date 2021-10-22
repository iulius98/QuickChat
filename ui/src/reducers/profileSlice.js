import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  username: "Anonymous_" + Math.floor(100000 + Math.random() * 900000).toString(),
  userId: null,
  currentChat: null,
  sessionId: null,
};

const profileSlice = createSlice({
  name: "profile",
  initialState,
  reducers: {
    usernameChanged(state, action) {
      return { ...state, username: action.payload };
    },
    userIdChanged(state, action) {
      return { ...state, userId: action.payload };
    },
    currentChatChanged(state, action) {
      return { ...state, currentChat: action.payload };
    },
    sessionIdChanged(state, action) {
      return { ...state, sessionId: action.payload };
    },
  },
});

export const { usernameChanged, userIdChanged, currentChatChanged, sessionIdChanged } = profileSlice.actions;

export default profileSlice.reducer;
