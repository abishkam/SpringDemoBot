ALTER TABLE user_tg
    DROP COLUMN user_state;

ALTER TABLE time
    ADD date_to_return TIMESTAMP WITHOUT TIME ZONE;