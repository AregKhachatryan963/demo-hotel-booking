package com.aca.demohotelbookingv1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Data
@Table(name = "date_statuses")
public class DateStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private LocalDate date;
    @Column
    private boolean isBooked;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String passportId;
    @Column
    private Long userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

//    @OneToOne
//    @JoinColumn(name = "user_id",referencedColumnName = "id")
//    private User user;
}
