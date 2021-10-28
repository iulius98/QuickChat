import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const usersSlice = createSlice({
  name: "users",
  initialState,
  reducers: {
    usersListUpdated(state, action) {
      return action.payload;
    },
    userAdded(state, action) {
      state.push(action.payload);
    },
    userDeleted(state, action) {
      console.log("AICI SUNT", action.payload);
      return state.filter((obj) => {
        return obj.id !== action.payload.id;
      });
    },
    usernameUpdated(state, action) {
      return state.map((obj) => (obj.id === action.payload.id ? { ...obj, name: action.payload.name } : obj));
    },
    currentyWritingUpdated(state, action) {
      return state.map((obj) => (obj.id === action.payload.id ? { ...obj, isWriting: action.payload.isWriting } : obj));
    },
  },
});

export const { usersListUpdated, userAdded, userDeleted, usernameUpdated, currentyWritingUpdated } = usersSlice.actions;

export default usersSlice.reducer;
