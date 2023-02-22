SET MODE MYSQL;

drop table if exists FOLLOW;
drop table if exists IMAGE;
drop table if exists POST_HASHTAG;
drop table if exists POST_LIKE;
drop table if exists HASHTAG;
drop table if exists POST;
drop table if exists MODEL;
drop table if exists `USER`;
drop table if exists TYPE;

create table HASHTAG
(
    id  int auto_increment
        primary key,
    tag varchar(16) not null,
    constraint tag
        unique (tag)
);

create table TYPE
(
    id  int auto_increment
        primary key,
    tag varchar(16) null,
    constraint type
        unique (tag)
);

create table MODEL
(
    id      int auto_increment
        primary key,
    type_id int         not null,
    tag     varchar(16) null,
    constraint model
        unique (tag),
    constraint model_type_id_fk
        foreign key (type_id) references TYPE (id)
            on update cascade on delete cascade
);

create table `USER`
(
    id       int auto_increment
        primary key,
    email    varchar(64)  not null,
    password varchar(100) not null,
    nickname varchar(16)  not null,
    constraint email
        unique (email),
    constraint nickname
        unique (nickname)
);

create table FOLLOW
(
    id           int auto_increment
        primary key,
    follower_id  int                  not null,
    following_id int                  not null,
    is_deleted   boolean default 0 not null,
    constraint FOLLOW_pk
        unique (following_id, follower_id),
    constraint follow_follower_id_fk
        foreign key (follower_id) references `USER` (id)
            on update cascade on delete cascade,
    constraint follow_following_id_fk
        foreign key (following_id) references `USER` (id)
            on update cascade on delete cascade
);

create table POST
(
    id          int auto_increment
        primary key,
    user_id     int                                  not null,
    create_date timestamp  default CURRENT_TIMESTAMP not null,
    update_date timestamp  default CURRENT_TIMESTAMP not null,
    content     varchar(500)                         null,
    model_id    int                                  not null,
    is_deleted  boolean default 0                 not null,
    like_count  int        default 0                 not null,
    constraint post_model_id_fk
        foreign key (model_id) references MODEL (id)
            on update cascade on delete cascade,
    constraint post_user_id_fk
        foreign key (user_id) references `USER` (id)
            on update cascade on delete cascade
);

create table IMAGE
(
    id        int auto_increment
        primary key,
    post_id   int          not null,
    image_url varchar(255) not null,
    constraint image_post_id_fk
        foreign key (post_id) references POST (id)
            on update cascade on delete cascade
);

create index POST_create_date_index
    on POST (create_date);

create table POST_HASHTAG
(
    id      int auto_increment
        primary key,
    post_id int not null,
    tag_id  int not null,
    constraint post_hashtag_post_id_fk
        foreign key (post_id) references POST (id)
            on update cascade on delete cascade,
    constraint post_hashtag_tag_id_fk
        foreign key (tag_id) references HASHTAG (id)
            on update cascade on delete cascade
);

create table POST_LIKE
(
    id         int auto_increment
        primary key,
    user_id    int                  not null,
    post_id    int                  not null,
    is_deleted boolean default 0 not null,
    constraint POST_LIKE_pk
        unique (user_id, post_id),
    constraint post_like_post_id_fk
        foreign key (post_id) references POST (id)
            on update cascade on delete cascade,
    constraint post_like_user_id_fk
        foreign key (user_id) references `USER` (id)
            on update cascade on delete cascade
);

