import { configureStore } from "@reduxjs/toolkit";
import usersReducer from "../reducers/usersSlice";
import messagesReducer from "../reducers/messagesSlice";
import { websocketApi } from "./websocketAPI";

export default configureStore({
  reducer: {
    users: usersReducer,
    messages: messagesReducer,
    [websocketApi.reducerPath]: websocketApi.reducer,
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(websocketApi.middleware),
});
