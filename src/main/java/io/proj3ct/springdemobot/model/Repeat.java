package io.proj3ct.springdemobot.model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Repeat {

    @Id
    private Long messageId;
    private String message;
}
