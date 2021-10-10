import { createSlice } from "@reduxjs/toolkit";

const initialState = [
  { id: "1", username: "USER1" },
  { id: "2", username: "USER2" },
  { id: "3", username: "USER3" },
];

const userSlice = createSlice({
  name: "users",
  initialState,
  reducers: {},
});

export default userSlice.reducer;
