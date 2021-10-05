import { configureStore } from "@reduxjs/toolkit";
import usersReducer from "../reducers/usersSlice";
import messagesReducer from "../reducers/messagesSlice";

export default configureStore({
  reducer: {
    users: usersReducer,
    messages: messagesReducer,
  },
});
