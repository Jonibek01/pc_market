package uz.app.pc_market.entity;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class ABCEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
