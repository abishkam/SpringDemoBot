package org.example.tgbd.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "user_tg")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private Long chatId;
    private String userName;
    @OneToMany(targetEntity = Memorization.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "chatId")
    private List<Memorization> memorizations;
    @Column(columnDefinition = "varchar(255) default 'free'")
    private String userState;

    @Override
    public String toString() {
        return "User{"
                + "chatId=" + chatId
                + ", userName='" + userName + '\''
                + ", memorizations=" + memorizations
                + '}';
    }
}
