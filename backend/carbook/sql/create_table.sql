CREATE TABLE `USER` (
	`id`	int	NOT NULL auto_increment,
	`email`	varchar(64)	NOT NULL unique,
	`password`	varchar(16)	NOT NULL,
	`nickname`	varchar(16)	NOT NULL unique,
    primary key (id)
);

CREATE TABLE `POST` (
	`id`	int	NOT NULL auto_increment,
	`user_id`	int	NOT NULL,
	`create_date`	timestamp	NOT NULL default current_timestamp,
	`update_date`	timestamp	NOT NULL default current_timestamp,
	`content`	varchar(500)	NULL,
	`model_id` int NOT NULL,
    primary key (id),
    INDEX    `POST_create_date_index` (`create_date`),
    constraint post_user_id_fk
        foreign key (user_id) references USER (id)
            on update cascade on delete cascade,
    constraint post_model_id_fk
        foreign key (model_id) references MODEL (id)
            on update cascade on delete cascade,
);

CREATE TABLE `POST_LIKE` (
	`id`	int	NOT NULL auto_increment,
	`user_id`	int	NOT NULL,
	`post_id`	int	NOT NULL,
    primary key (id),
    constraint post_like_user_id_fk
        foreign key (user_id) references USER (id)
            on update cascade on delete cascade,
    constraint post_like_post_id_fk
        foreign key (post_id) references POST (id)
            on update cascade on delete cascade
);

CREATE TABLE `FOLLOW` (
	`id`	int	NOT NULL auto_increment,
	`follower_id`	int	NOT NULL,
	`following_id`	int	NOT NULL,
    primary key (id),
    constraint follow_follower_id_fk
        foreign key (follower_id) references USER (id)
            on update cascade on delete cascade,
	constraint follow_following_id_fk
        foreign key (following_id) references USER (id)
            on update cascade on delete cascade
);

CREATE TABLE `HASHTAG` (
	`id`	int	NOT NULL auto_increment,
	`tag`	varchar(16)	NOT NULL unique,
    primary key (id)
);

CREATE TABLE `POST_HASHTAG` (
	`id`	int	NOT NULL auto_increment,
	`post_id`	int	NOT NULL,
	`tag_id`	int	NOT NULL,
    primary key (id),
    constraint post_hashtag_post_id_fk
        foreign key (post_id) references POST (id)
            on update cascade on delete cascade,
	constraint post_hashtag_tag_id_fk
        foreign key (tag_id) references HASHTAG (id)
            on update cascade on delete cascade
);

CREATE TABLE `IMAGE` (
	`id`	int	NOT NULL auto_increment,
	`post_id`	int	NOT NULL,
	`file_name`	varchar(255)	NOT NULL,
	`file_path`	varchar(255)	NOT NULL,
    primary key (id),
    constraint image_post_id_fk
        foreign key (post_id) references POST (id)
            on update cascade on delete cascade
);

CREATE TABLE `TYPE` (
                        `id`    int NOT NULL auto_increment,
                        `type`  varchar(16) NOT NULL unique,
                        primary key (id)
);

CREATE TABLE `MODEL` (
                        `id`    int NOT NULL auto_increment,
                        `type_id`   int NOT NULL,
                        `model` varchar(16) NOT NULL unique,
                        primary key (id),
                        constraint model_type_id_fk
                            foreign key (type_id) references TYPE (id)
                                on update cascade on delete cascade
);
