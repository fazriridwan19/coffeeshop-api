package com.webkop.coffeshopweb.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Cart")
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coffe_id", referencedColumnName = "id")
    private Coffe coffe;

    @Column(columnDefinition = "integer default 1")
    private Integer quantity;

    private Date createdAt;

    @Column(columnDefinition = "boolean default false")
    private Boolean sold;
}
