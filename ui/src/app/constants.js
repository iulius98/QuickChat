export const MESSAGE = "MESSAGE";
export const UPGRADE_LIST_USERS = "UPGRADE_LIST_USERS";
export const ADD_USER = "ADD_USER";
export const DELETE_USER = "DELETE_USER";
export const UPDATE_USER = "UPDATE_USER";
export const CHAT_MESSAGE = "CHAT_MESSAGE";

export const serverHost = process.env.NODE_ENV !== "development" ? "" : "http://localhost:8080";