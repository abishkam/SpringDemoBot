

create table pavlik.repeat (
       message_id bigint not null,
       message varchar(2048) not null,
       user_id bigint,
       primary key (message_id)
);

create table pavlik.user_tg (
        chat_id bigint not null,
        user_name varchar(255),
        primary key (chat_id)
);

alter table if exists pavlik.repeat
    add constraint user_repeat_fk
    foreign key (user_id) references pavlik.user_tg