package io.proj3ct.springdemobot.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "userTg")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id
    private Long chatId;
    private String userName;
    private Timestamp registeredAt;
    @OneToMany(targetEntity = Repeat.class, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private List<Repeat> repeat;

}
