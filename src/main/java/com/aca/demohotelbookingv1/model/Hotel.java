package com.aca.demohotelbookingv1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String hotelName;

    @Column
    private String address;

    @Column
    private Double rating;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> room;


}
