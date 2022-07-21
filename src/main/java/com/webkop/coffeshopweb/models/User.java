package com.webkop.coffeshopweb.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotBlank(message = "name not blank")
    private String name;

    @Column(
            unique = true,
            length = 50
    )
    @NotBlank(message = "username not blank")
    private String username;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "password not blank")
    private String password;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "address not blank")
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

}
