create table if not exists members.repeat (
       message_id bigint not null,
       message text unique not null,
       user_id bigint,
       primary key (message_id)
);

create table if not exists members.user_tg(
        chat_id bigint not null,
        user_name varchar(255),
        primary key (chat_id)
);

alter table if exists members.repeat
    add constraint user_repeat_fk
    foreign key (user_id) references members.user_tg