package com.phutl.meowpet.modules.database;

import com.phutl.meowpet.shared.common.Rank;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "memberships")
@AllArgsConstructor
@NoArgsConstructor
public class Membership extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rank", nullable = false)
    private Rank rank;

    @Column(name = "points", nullable = false)
    private int points;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


}
