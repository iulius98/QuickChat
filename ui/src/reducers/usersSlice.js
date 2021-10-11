import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const usersSlice = createSlice({
  name: "users",
  initialState,
  reducers: {
    usersUpdated(state, action) {
      return action.payload;
    },
  },
});

export const { usersUpdated } = usersSlice.actions;

export default usersSlice.reducer;
