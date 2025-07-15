package uz.app.pc_market.entity;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class ABCEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
