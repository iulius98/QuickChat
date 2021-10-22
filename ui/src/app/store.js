import { configureStore } from "@reduxjs/toolkit";
import usersReducer from "../reducers/usersSlice";
import messagesReducer from "../reducers/messagesSlice";
import chatsReducer from "../reducers/chatsSlice";
import profileReducer from "../reducers/profileSlice";

export default configureStore({
  reducer: {
    chats: chatsReducer,
    users: usersReducer,
    messages: messagesReducer,
    profile: profileReducer,
  },
});
