package com.project.restaurant.components.user.entities;

import com.project.restaurant.components.items.entities.Item;
import com.project.restaurant.components.roles.entities.Roles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USER_TABLE")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted=false")
@Cacheable
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fullName;

    @Column
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "id_role", referencedColumnName = "id")
    private Roles role;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private UserDetails userDetails;

    @Column
    private boolean enabled;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private List<Item> items;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<VerificationToken> verificationTokenList;

    @Column
    private boolean deleted;

    @Version
    private long version;

    public User(String fullName, String phoneNumber, Roles role, boolean enabled) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.enabled = enabled;
    }

}
