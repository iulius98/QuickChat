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
      const newState = state.filter((obj) => {return obj.id !== action.payload.id})
      console.log(newState);
      return newState; //state.filter((obj) => {return obj.id !== action.payload.id});
    }, 
    userUpdated(state, action) {
      return state.map((obj) => obj.id === action.payload.id ? {...obj, name: action.payload.name} : obj);
    }
  },
});

export const { usersListUpdated, userAdded, userDeleted, userUpdated } = usersSlice.actions;

export default usersSlice.reducer;
