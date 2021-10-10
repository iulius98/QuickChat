import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const websocketApi = createApi({
  reducerPath: 'wsApi',
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8080"}),
  endpoints: (build) => ({
    getMessages: build.query({
      query: () => `/messages`,
      async onCacheEntryAdded(arg, { updateCachedData, cacheDataLoaded, cacheEntryRemoved }) {
        // create a websocket connection when the cache subscription starts
        const ws = new WebSocket("ws://localhost:8080/ws-quick");
       // console.log("Made connedtion!");
        try {
          // wait for the initial query to resolve before proceeding
          // await cacheDataLoaded;
          //console.log("Ready to listen!");
          // when data is received from the socket connection to the server,
          // if it is a message and for the appropriate channel,
          // update our query result with the received message
          const listener = (event) => {
            const data = JSON.parse(event.data);
            console.log(data);

            updateCachedData((draft) => {
              draft.push(data);
            });
          };

          ws.addEventListener("message", listener);
        } catch {
          // no-op in case `cacheEntryRemoved` resolves before `cacheDataLoaded`,
          // in which case `cacheDataLoaded` will throw
        }
        // cacheEntryRemoved will resolve when the cache subscription is no longer active
        await cacheEntryRemoved;
        // perform cleanup steps once the `cacheEntryRemoved` promise resolves
        ws.close();
      },
    }),
  }),
});

export const { useGetMessagesQuery } = websocketApi;
