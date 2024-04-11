package com.aca.demohotelbookingv1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "room")
public class Room {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roomType;

    @Column
    private Integer roomNumber;

    @Column
    private double pricePerDay;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    private List<DateStatus> dateStatus;
}
