CREATE TABLE "users" (
  "id" serial PRIMARY KEY,
  "name" varchar(20),
  "created_at" timestamp,
  "photo_profile_id" bigint,
  "session_id" varchar(120),
  "current_chat_id" bigint
);

CREATE TABLE "photos" (
  "id" serial PRIMARY KEY,
  "big_photo_uri" varchar(120),
  "jpeg_photo_uri" varchar(120)
);

CREATE TABLE "messages" (
  "id" serial PRIMARY KEY,
  "author_name" varchar(20),
  "author_id" bigint,
  "chat_id" bigint,
  "status" varchar(20),
  "content" varchar(10000),
  "created_at" timestamp
);

CREATE TABLE "chats" (
  "id" serial PRIMARY KEY,
  "is_secure" boolean,
  "created_at" timestamp
);

CREATE TABLE "groups" (
  "id" bigint PRIMARY KEY,
  "group_photo_id" bigint,
  "group_name" varchar(25)
);

CREATE TABLE "conversations" (
  "id" bigint PRIMARY KEY
);

CREATE TABLE "conversation_info" (
  "id" serial PRIMARY KEY,
  "user_id" bigint,
  "conversation_id" bigint,
  "user_photo_id" bigint,
  "chat_name" varchar(20)
);

CREATE TABLE "users_to_chat" (
  "id" serial PRIMARY KEY,
  "user_id" bigint,
  "chat_id" bigint
);

ALTER TABLE "users" ADD FOREIGN KEY ("photo_profile_id") REFERENCES "photos" ("id");

ALTER TABLE "messages" ADD FOREIGN KEY ("chat_id") REFERENCES "chats" ("id");

ALTER TABLE "groups" ADD FOREIGN KEY ("id") REFERENCES "chats" ("id");

ALTER TABLE "groups" ADD FOREIGN KEY ("group_photo_id") REFERENCES "photos" ("id");

ALTER TABLE "conversations" ADD FOREIGN KEY ("id") REFERENCES "chats" ("id");

ALTER TABLE "conversation_info" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "conversation_info" ADD FOREIGN KEY ("conversation_id") REFERENCES "conversations" ("id");

ALTER TABLE "conversation_info" ADD FOREIGN KEY ("user_photo_id") REFERENCES "photos" ("id");

ALTER TABLE "users_to_chat" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public 
TO quickchat;

GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO quickchat;
