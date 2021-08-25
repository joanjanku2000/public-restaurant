package com.project.restaurant.components.items.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.restaurant.components.ingredients.entities.Ingredient;
import com.project.restaurant.components.orders.entities.Orders;
import com.project.restaurant.components.user.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Where(clause = "deleted=false")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private String description;

	@Column
	private BigDecimal price;

	@Column
	private String picture;

	@Column
	private boolean deleted;

	@Version
	private long version;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "item_ingredient",
			joinColumns = @JoinColumn(name = "id_item", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_ingredient", referencedColumnName = "id"))
	private List<Ingredient> ingredients;

	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "seller_id", referencedColumnName = "id")
	private User seller;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "item")
	@JsonIgnore
	private List<Orders> orders;

	public Item(String name, String description, BigDecimal price, List<Ingredient> ingredients, User user, String picture, long version) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ingredients = ingredients;
        this.seller = user;
        this.picture = picture;
        this.version = version;
    }

}
