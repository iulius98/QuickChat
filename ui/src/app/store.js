import { configureStore } from "@reduxjs/toolkit";
import usersReducer from "../reducers/usersSlice";
import messagesReducer from "../reducers/messagesSlice";
import userNameReducer from "../reducers/userNameSlice";

export default configureStore({
  reducer: {
    users: usersReducer,
    messages: messagesReducer,
    userName: userNameReducer,
  },
});
