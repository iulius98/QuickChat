import { createSlice } from "@reduxjs/toolkit";

const initialState = "Anonymous_" + (Math.floor(100000 + Math.random() * 900000)).toString();

const userNameSlice = createSlice({
    name: "userName",
    initialState,
    reducers: {
      userNameChanged(state, action) {
        return (action.payload);
      },
    },
  });
  
  export const { userNameChanged } = userNameSlice.actions;
  
  export default userNameSlice.reducer;