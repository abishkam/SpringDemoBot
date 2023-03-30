package io.proj3ct.springdemobot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Repeat {

    @Id
    @GeneratedValue
    private Long repeatId;
    private String message;
    private Timestamp time;

    public Repeat(String message, Timestamp time) {
        this.message = message;
        this.time = time;
    }
}
