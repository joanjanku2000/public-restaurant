package com.project.restaurant.components.orders.entities;

import com.project.restaurant.components.items.entities.Item;
import com.project.restaurant.components.user.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Where(clause = "deleted=false")
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private LocalDateTime dateTime;

	@Column
	private String feedback;

	@Column
	private boolean delivered;

	@OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column
    private boolean active;

    @Column
    private boolean deleted;

    @Version
    private long version;

    public Orders(LocalDateTime dateTime, String feedback, boolean delivered, User user, Item item, boolean active) {
        this.active = active;
        this.dateTime = dateTime;
        this.feedback = feedback;
        this.delivered = delivered;
        this.user = user;
        this.item = item;
    }


}
