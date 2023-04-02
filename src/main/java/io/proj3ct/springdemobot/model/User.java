package io.proj3ct.springdemobot.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "userTg")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private Long chatId;
    private String userName;
    @OneToMany(targetEntity = Repeat.class, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Repeat> repeat;


    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", userName='" + userName + '\'' +
                ", repeat=" + repeat +
                '}';
    }
}
