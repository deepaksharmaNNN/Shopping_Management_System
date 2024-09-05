package com.deepaksharma.shoppingmanagementsystem.model;


import com.deepaksharma.shoppingmanagementsystem.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String username;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Order> orders;

}
