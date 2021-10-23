CREATE TABLE users (
  "id" serial PRIMARY KEY,
  "name" varchar(20),
  "created_at" bigint,
  "photo_profile_id" bigint,
  "session_id" varchar(120),
  "current_chat_id" bigint
);

CREATE TABLE photo (
  "id" serial PRIMARY KEY,
  "big_photo_uri" varchar(120),
  "jpeg_photo_uri" varchar(120)
);

CREATE TABLE chats (
  "id" serial PRIMARY KEY,
  "photo_id" bigint,
  "name" varchar(20),
  "chat_type" varchar(20),
  "created_at" bigint
);

CREATE TABLE messages (
  "id" serial PRIMARY KEY,
  "author_id" bigint,
  "author_name" varchar(20),
  "chat_id" int,
  "status" varchar(20),
  "content" varchar(10000),
  "created_at" bigint
);

CREATE TABLE users_to_chat (
  "user_id" bigint,
  "chat_id" bigint,
  PRIMARY KEY(user_id, chat_id)
);

ALTER TABLE users ADD FOREIGN KEY ("photo_profile_id") REFERENCES photo ("id");

-- ALTER TABLE users ADD FOREIGN KEY ("current_chat_id") REFERENCES chats ("id");

ALTER TABLE chats ADD FOREIGN KEY ("photo_id") REFERENCES photo ("id");

ALTER TABLE messages ADD FOREIGN KEY ("chat_id") REFERENCES chats ("id");

--ALTER TABLE messages ADD FOREIGN KEY ("author_id") REFERENCES users ("id");

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public 
TO quickchat;

GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO quickchat;
