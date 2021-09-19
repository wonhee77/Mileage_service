create table place
(
    place_id   char(36) not null,
    place_name varchar(45) charset utf8,
    primary key (place_id)
);

create table review
(
    review_id      char(36) not null,
    review_content varchar(255) charset utf8,
    place_place_id char(36),
    user_user_id   char(36) not null,
    primary key (review_id)
);
create
    index i_review on review (place_place_id);

create table review_point_history
(
    id                   bigint      not null auto_increment,
    action               varchar(45) not null,
    attached_photo_count integer     not null,
    changed_point        integer     not null,
    is_first_review      tinyint     not null,
    place_id             char(36)    not null,
    review_content       varchar(255) charset utf8,
    review_id            char(36)    not null,
    review_point         integer     not null,
    user_id              char(36)    not null,
    primary key (id)
);
create
    index i_review_point_history on review_point_history (review_id);

create table user
(
    user_id    char(36) not null,
    user_point integer  not null,
    primary key (user_id)
);

alter table review
    add constraint FKg0a43205m2mbhfhnn9i5atdps
        foreign key (place_place_id)
            references place (place_id);

alter table review
    add constraint FK5bhefci502sd63299f0mw09t7
        foreign key (user_user_id)
            references user (user_id);