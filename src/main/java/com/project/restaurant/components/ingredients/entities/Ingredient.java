package com.project.restaurant.components.ingredients.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.restaurant.components.items.entities.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"items"})
@Where(clause = "deleted=false")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

    @Column
    private Double calories;

    @Column
    private boolean deleted;

    @Version
    private long version;

    @JsonIgnore
    @ManyToMany(mappedBy = "ingredients")
    private List<Item> items;

    public Ingredient(String name, Double calories) {
        this.name = name;
        this.calories = calories;
    }
}
