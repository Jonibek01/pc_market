package uz.app.pc_market.entity;

import jakarta.persistence.*;

@Entity
public class ABCEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
}
