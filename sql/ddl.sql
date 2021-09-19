create table user
(
    user_id    varchar(255),
    user_point int,
    primary key (user_id)
);

create table place
(
    place_id   varchar(45),
    place_name varchar(45),
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
    review_id          varchar(45),
    action             enum('ADD','MOD','DELETE'),
    reviewId           varchar(45),
    attachedPhotoCount int,
    userId             varchar(45),
    placeId            varchar(45),
    reviewContent      varchar(255),
    isFirstReview      tinyint,
    point              int,
    primary key (review_id)
);


