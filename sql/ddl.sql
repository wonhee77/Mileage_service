create table place
(
    id         char(36) not null,
    place_name varchar(45) charset utf8,
    primary key (id)
);

create table review
(
    id             char(36) not null,
    review_content varchar(255) charset utf8,
    place_id       char(36) not null,
    user_id        char(36) not null,
    primary key (id)
);
create
    index i_review on review (place_id);

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
    id         char(36) not null,
    user_point integer  not null,
    primary key (id)
);

alter table review
    add constraint review_place_fk
        foreign key (place_id)
            references place (id);

alter table review
    add constraint review_user_fk
        foreign key (user_id)
            references user (id);