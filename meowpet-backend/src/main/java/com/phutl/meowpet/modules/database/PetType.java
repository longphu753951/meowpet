package com.phutl.meowpet.modules.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pet_types")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false, length = 50)
    private String name;

    @Column(name = "description",nullable = false, length = 255)
    private String description;

    @Column(name = "image",nullable = false, length = 255)
    private String image;
}
