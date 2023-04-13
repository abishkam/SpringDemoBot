package org.example.tgbd.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "user_tg")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private Long chatId;
    private String userName;
    @OneToMany(targetEntity = Repeat.class,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Repeat> repeat;

    @Override
    public String toString() {
        return "User{"
                + "chatId=" + chatId
                + ", userName='" + userName + '\''
                + ", repeat=" + repeat
                + '}';
    }
}
