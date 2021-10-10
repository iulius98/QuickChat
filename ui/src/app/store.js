import { configureStore } from "@reduxjs/toolkit";
import usersReducer from "../reducers/usersSlice";
import messagesReducer from "../reducers/messagesSlice";
import userNameReducer from "../reducers/userNameSlice";
//import clientReducer from "../reducers/clientSlice";
//import { websocketApi } from "./websocketAPI";

export default configureStore({
  reducer: {
    users: usersReducer,
    messages: messagesReducer,
    // userName: userNameReducer,
    //client: clientReducer, 
    //[websocketApi.reducerPath]: websocketApi.reducer,
  },
  //middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(websocketApi.middleware),
});
