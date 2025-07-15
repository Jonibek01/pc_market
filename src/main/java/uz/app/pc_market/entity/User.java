package uz.app.pc_market.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.app.pc_market.entity.enums.Role;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User extends ABCEntity {
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean enabled;
    private Double balance;
    private String confCode;

    @OneToMany(mappedBy = "user")
    private List<Basket> baskets;

    @OneToMany(mappedBy = "cardHolder")
    private List<Card> cards;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<History> histories;
}
