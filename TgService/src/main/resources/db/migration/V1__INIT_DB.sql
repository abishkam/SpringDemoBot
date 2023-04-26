CREATE SEQUENCE IF NOT EXISTS time_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE memorization
(
    message_id BIGINT NOT NULL,
    message    VARCHAR(255),
    chat_id    BIGINT,
    CONSTRAINT pk_memorization PRIMARY KEY (message_id)
);

CREATE TABLE time
(
    id           BIGINT   NOT NULL,
    unit_of_time VARCHAR(255),
    amount       SMALLINT NOT NULL,
    message_id   BIGINT,
    CONSTRAINT pk_time PRIMARY KEY (id)
);

CREATE TABLE user_tg
(
    chat_id    BIGINT NOT NULL,
    user_name  VARCHAR(255),
    user_state VARCHAR(255) DEFAULT 'free',
    CONSTRAINT pk_user_tg PRIMARY KEY (chat_id)
);

ALTER TABLE memorization
    ADD CONSTRAINT FK_MEMORIZATION_ON_CHATID FOREIGN KEY (chat_id) REFERENCES user_tg (chat_id);

ALTER TABLE time
    ADD CONSTRAINT FK_TIME_ON_MESSAGEID FOREIGN KEY (message_id) REFERENCES memorization (message_id);