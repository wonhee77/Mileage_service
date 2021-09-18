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
    review_id       varchar(45),
    review_content  varchar(255),
    user_id         varchar(45),
    place_id        varchar(45),
    is_first_review boolean,
    primary key (review_id),
    constraint review_ibfk_1
        foreign key (user_id) references user (user_id),
    constraint review_ibfk_2
        foreign key (place_id) references place (place_id)
);

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


