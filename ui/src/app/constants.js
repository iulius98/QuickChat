// SERVER MESSAGE TYPES CONSTANTS
export const MESSAGE = "MESSAGE";
export const UPDATE_CHAT_USER = "UPDATE_CHAT_USER";
export const ADD_USER_CHAT = "ADD_USER_CHAT";
export const DELETE_USER_CHAT = "DELETE_USER_CHAT";
export const NEW_CHAT = "NEW_CHAT";
export const REQUESTED_CHAT = "REQUESTED_CHAT";

// CHAT TYPES CONSTANTS
export const GROUP = "GROUP";
export const CONVERSATION = "CONVERSATION";

// APP CONSTANTS
export const principalChatId = "2021";
export const serverHost = process.env.NODE_ENV !== "development" ? "" : "http://localhost:8080";
