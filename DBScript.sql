create table customer
(
    id       int auto_increment,
    email    varchar(225) not null,
    password varchar(32)  not null,
    username varchar(225) not null,
    constraint Customer_email_uindex
        unique (email),
    constraint Customer_id_uindex
        unique (id)
);

alter table customer
    add primary key (id);

create table lot
(
    id          int auto_increment,
    name        varchar(45)                         not null,
    description varchar(450)                        null,
    start_price decimal(19, 4)                      not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    is_active   tinyint                             not null,
    customer_id int                                 not null,
    constraint Lot_id_uindex
        unique (id),
    constraint lot_customer_id
        foreign key (customer_id) references customer (id)
            on delete cascade
);

alter table lot
    add primary key (id);

create table lot_offer
(
    id              int auto_increment,
    description     varchar(450)                        null,
    suggested_price decimal(19, 4)                      not null,
    create_time     timestamp default CURRENT_TIMESTAMP not null,
    lot_id          int                                 not null,
    customer_id     int                                 not null,
    constraint LotOffer_id_uindex
        unique (id),
    constraint offer_customer_id
        foreign key (customer_id) references customer (id),
    constraint offer_lot_id
        foreign key (lot_id) references lot (id)
);

alter table lot_offer
    add primary key (id);


