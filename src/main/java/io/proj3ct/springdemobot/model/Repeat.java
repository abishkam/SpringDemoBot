package io.proj3ct.springdemobot.model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Repeat {

    @Id
    private Integer messageId;
    private String message;
}
