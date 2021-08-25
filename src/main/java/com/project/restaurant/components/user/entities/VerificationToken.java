package com.project.restaurant.components.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@ToString
@Getter
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String token;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @Column
    private Date expiryDate;

    @Version
    private long version;

    public VerificationToken() {

    }


    public VerificationToken(User user) {
        this.token = UUID.randomUUID().toString() + user.getId();
        this.user = user;
    }
}
