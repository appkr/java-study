package com.example.petservice.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "pets")
@EntityListeners(AuditingEntityListener.class)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 60, unique = true)
    private String name;

    @Column(name = "pet_type", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private PetType petType;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Instant updatedAt = Instant.now();

    protected Pet() {
    }

    private Pet(String name, PetType petType) {
        this.name = name;
        this.petType = petType;
    }

    public static Pet of(String name, PetType petType) {
        return new Pet(name, petType);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PetType getPetType() {
        return petType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet that = (Pet) o;

        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}