package com.project.restaurant.components.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.restaurant.components.cities.entities.City;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(indexes = @Index(columnList = "email"))
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "deleted=false")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JsonIgnore
    @JoinColumn(name = "id_u", referencedColumnName = "id", nullable = false)
    protected User user;

    @Column
    protected String address;

    @Column
    protected String email;

    @Column
    @JsonIgnore
    protected String password;

    @OneToOne
    @JoinColumn(name = "id_city", referencedColumnName = "id")
    protected City city;

    @Column
    protected boolean deleted;

    @Version
    protected long version;

    public UserDetails(String address, String email, String password, City city, User user) {
        this.address = address;
        this.email = email;
        this.password = password;
        this.city = city;
        this.user = user;
    }

}
