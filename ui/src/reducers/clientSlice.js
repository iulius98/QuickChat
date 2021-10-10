// import { createSlice } from "@reduxjs/toolkit";
// import { messageAdded } from "./messagesSlice";

// import SockJS from "sockjs-client/dist/sockjs";
// import Stomp from "stompjs";
// import axios from "axios";

// const initialState = undefined;

// const baseUrl = "http://localhost:8080";

// function messageFilter(message) {
//     if (message.body) {
//         console.log(message.body);
//         store.dispatch(messageAdded(message));
//     } else {
//         console.log('Got empty message');
//     }
// };

// const clientSlice = createSlice({
//     name: "client",
//     initialState,
//     reducers: {
//       clientConnect(state) {
//         axios.post(baseUrl + '/user/create', {
//             name: state.userName,
//             timestamp: Date.now(),
//           })
//           .then(function (response) {
//             sessionId = response.data.id;
        
//             var ws = new SockJS(`http://localhost:8080/ws-quick?sessionId=${sessionId}`);
//             state.client = Stomp.over(ws);
        
//             const connectCallback = function() {
//               console.log("Connected");
//               subscription = client.subscribe(`/user/${sessionId}/usertell`, messageFilter);
//               isConnected = true;
//             };
        
//             const errorCallback = function(error) {
//                 console.log(error);
//             };
        
//             state.client.connect({}, connectCallback, errorCallback);
//           })
//           .catch(function (error) {
//             console.log(error);
//           });
//       },
//     },
//   });
  
//   export const { clientConnect } = clientSlice.actions;
  
//   export default clientSlice.reducer;