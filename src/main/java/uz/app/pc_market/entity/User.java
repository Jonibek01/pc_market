package uz.app.pc_market.entity;

import jakarta.persistence.*;
import lombok.Data;
import uz.app.pc_market.entity.enums.Role;

@Data
@Entity
@MappedSuperclass
@Table(name = "users")
public class User extends ABCEntity{
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean enabled;
    private Double balance;
    private String confCode;
}
