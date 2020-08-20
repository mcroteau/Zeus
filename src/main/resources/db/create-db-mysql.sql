
CREATE TABLE account (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	name varchar(55),
	age varchar(155),
	location varchar(155),
	image_uri varchar(75),
	username varchar(55) NOT NULL,
	password varchar(155) NOT NULL,
	disabled boolean,
	date_disabled bigint,
	uuid varchar(55)
);

CREATE TABLE role (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	name varchar(55) NOT NULL UNIQUE
);

CREATE TABLE account_permissions (
	account_id bigint REFERENCES account(id),
	permission varchar(55)
);

CREATE TABLE account_roles (
	role_id bigint NOT NULL REFERENCES role(id),
	account_id bigint NOT NULL REFERENCES account(id)
);


CREATE TABLE posts (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	account_id bigint NOT NULL REFERENCES account(id),
	content text,
	image_file_uri varchar(75),
	music_file_uri varchar(75),
	video_file_uri varchar(75),
	hidden boolean,
	flagged boolean,
	date_posted bigint NOT NULL
);


CREATE TABLE friends (
	account_id bigint NOT NULL REFERENCES account(id),
	friend_id bigint NOT NULL REFERENCES account(id),
	date_created bigint NOT NULL,
	constraint unique_friend unique(account_id, friend_id)
);

CREATE TABLE musics (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	account_id bigint NOT NULL REFERENCES account(id),
	uri varchar(155) NOT NULL,
	title varchar(155) NOT NULL,
	artist varchar(155) NOT NULL,
	release_date varchar(55),
	duration varchar(155),
	date_uploaded bigint NOT NULL
);


CREATE TABLE account_musics (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	account_id bigint NOT NULL REFERENCES account(id),
	music_file_id bigint NOT NULL REFERENCES musics(id),
	date_added bigint NOT NULL
);


create table post_likes(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	account_id bigint NOT NULL REFERENCES account(id),
	post_id bigint NOT NULL REFERENCES posts(id),
    date_liked bigint NOT NULL
);

create table post_shares(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	account_id bigint NOT NULL REFERENCES account(id),
	post_id bigint NOT NULL REFERENCES posts(id),
	comment text,
    date_shared bigint NOT NULL
);

create table friend_invites(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	invitee_id bigint NOT NULL REFERENCES account(id),
	invited_id bigint NOT NULL REFERENCES account(id),
    date_created bigint NOT NULL,
    accepted int,
    ignored int,
    new_invite int
);

create table mail_messages(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	sender_id bigint NOT NULL REFERENCES account(id),
	recipient_id bigint NOT NULL REFERENCES account(id),
    date_sent bigint NOT NULL,
    subject varchar(155),
	content text,
    opened boolean
);


create table post_comments(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	post_id bigint NOT NULL REFERENCES posts(id),
	account_id bigint NOT NULL REFERENCES account(id),
	account_name varchar(155),
	account_image_uri varchar(75),
    date_created bigint NOT NULL,
	comment text
);


create table post_share_comments(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	post_share_id bigint NOT NULL REFERENCES post_shares(id),
	account_id bigint NOT NULL REFERENCES account(id),
	account_name varchar(155),
	account_image_uri varchar(75),
    date_created bigint NOT NULL,
	comment text
);

create table post_images(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	post_id bigint NOT NULL REFERENCES posts(id),
	uri varchar(155),
    date_uploaded bigint NOT NULL
);

create table post_music(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	account_id bigint NOT NULL REFERENCES account(id),
	post_id bigint NOT NULL REFERENCES posts(id),
	music_file_id bigint NOT NULL REFERENCES musics(id),
    date_uploaded bigint NOT NULL
);

create table notifications (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	post_id bigint NOT NULL REFERENCES posts(id),
	authenticated_account_id bigint NOT NULL REFERENCES account(id),
	notification_account_id bigint NOT NULL REFERENCES account(id),
	date_created bigint NOT NULL,
    liked boolean,
    shared boolean,
    commented boolean
);

create table resources(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	account_id bigint NOT NULL REFERENCES account(id),
	uri text,
    date_added bigint NOT NULL
);

create table resource_likes(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	resource_id bigint NOT NULL REFERENCES resources(id),
	account_id bigint NOT NULL REFERENCES account(id),
    date_liked bigint NOT NULL
);

create table resource_shares(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	resource_id bigint NOT NULL REFERENCES resources(id),
	post_id bigint NOT NULL REFERENCES posts(id),
	account_id bigint NOT NULL REFERENCES account(id),
	comment text,
    date_shared bigint NOT NULL
);

create table messages(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	sender_id bigint NOT NULL REFERENCES account(id),
	recipient_id bigint NOT NULL REFERENCES account(id),
    date_sent bigint NOT NULL,
	content text,
    viewed boolean
);

create table profile_views (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	profile_id bigint NOT NULL REFERENCES account(id),
	viewer_id bigint NOT NULL REFERENCES account(id),
	date_viewed bigint NOT NULL
);

create table profile_likes(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	profile_id bigint NOT NULL REFERENCES account(id),
	liker_id bigint NOT NULL REFERENCES account(id),
    date_liked bigint NOT NULL
);

create table post_flags (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	post_id bigint NOT NULL REFERENCES posts(id),
	account_id bigint NOT NULL REFERENCES account(id),
    date_flagged bigint NOT NULL,
    shared boolean
);

create table hidden_posts (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	post_id bigint NOT NULL REFERENCES posts(id),
	account_id bigint NOT NULL REFERENCES account(id),
    date_hidden bigint NOT NULL
);

create table account_blocks (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	person_id bigint NOT NULL REFERENCES account(id),
	blocker_id bigint NOT NULL REFERENCES account(id),
    date_blocked bigint NOT NULL
);
