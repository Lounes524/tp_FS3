-- Table categories
create table categories (
    id int8 not null,
    name varchar(255) not null,
    primary key (id)
);

-- Table localized_product
create table localized_product (
    id int8 not null,
    description varchar(255),
    locale varchar(255) not null,
    name varchar(255) not null,
    primary key (id)
);

-- Table opening_hours
create table opening_hours (
    id int8 not null,
    close_at time not null,
    day int4 not null check (day>=1 AND day<=7),
    open_at time not null,
    primary key (id)
);

-- Table products
create table products (
    id int8 not null,
    price int8 not null,
    shop_id int8,
    primary key (id)
);

-- Table products_categories
create table products_categories (
    product_id int8 not null,
    category_id int8 not null
);

-- Table products_localized_product
create table products_localized_product (
    product_id int8 not null,
    localized_product_id int8 not null
);

-- Table shops
create table shops (
    id int8 not null,
    created_at date not null,
    in_vacations boolean not null,
    name varchar(255) not null,
    primary key (id)
);

-- Table shops_opening_hours
create table shops_opening_hours (
    shop_id int8 not null,
    opening_hours_id int8 not null
);

-- Table translation
create table translation (
    id int8 not null,
    field_type varchar(255) not null,
    language varchar(255) not null,
    value varchar(255) not null,
    primary key (id)
);

-- Ajout des contraintes de clés étrangères
alter table products_localized_product
add constraint UK_n8q0vltkv2dgjclj2aqn26l03 unique(localized_product_id);

alter table shops_opening_hours
add constraint UK_cnkerx0e3gn4yuhpjkr1d7heu unique (opening_hours_id);

alter table products
add constraint FK7kp8sbhxboponhx3lxqtmkcoj foreign key (shop_id) references shops;

alter table products_categories
add constraint FKqt6m2o5dly3luqcm00f5t4h2p foreign key (category_id) references categories;

alter table products_categories
add constraint FKtj1vdea8qwerbjqie4xldl1el foreign key (product_id) references products;

alter table products_localized_product
add constraint FKjs8yfvw4we59oaei8c9txb4wy foreign key (localized_product_id) references localized_product;

alter table products_localized_product add constraint
FK6i2yelx9i3lagm1u7n6v0xnfh foreign key (product_id) references products;

alter table shops_opening_hours
add constraint FKti43xlm3mfbeodhgi4qn1yhgw foreign key (opening_hours_id) references opening_hours;

alter table shops_opening_hours
add constraint FK8dcjdnasobclsvyy8wjfki7gj foreign key (shop_id) references shops;

-- Création des index pour optimiser les jointures sur les clés étrangères

CREATE INDEX idx_products_shop_id ON products(shop_id);

CREATE INDEX idx_products_categories_product_id ON products_categories(product_id);
CREATE INDEX idx_products_categories_category_id ON products_categories(category_id);

CREATE INDEX idx_products_localized_product_product_id ON products_localized_product(product_id);
CREATE INDEX idx_products_localized_product_localized_product_id ON products_localized_product(localized_product_id);

CREATE INDEX idx_shops_opening_hours_shop_id ON shops_opening_hours(shop_id);
CREATE INDEX idx_shops_opening_hours_opening_hours_id ON shops_opening_hours(opening_hours_id);
