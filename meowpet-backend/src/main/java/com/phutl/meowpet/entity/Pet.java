package com.phutl.meowpet.entity;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pets")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "date_of_birth", nullable = false, columnDefinition = "DATE")
    private LocalDate dateOfBirth;

    @Column(name = "adoption_date", nullable = false, columnDefinition = "DATE")
    private LocalDate adoptionDate;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "weight", nullable = false)
    private double weight;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "height", nullable = false)
    private double height;

    @Column(name = "image", nullable = false, length = 255)
    private String image;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Breed breed;

    @ManyToMany(mappedBy = "pets")
    private List<Customer> customers;

    @ManyToMany(mappedBy = "pets")
    private List<Character> characters;

}
