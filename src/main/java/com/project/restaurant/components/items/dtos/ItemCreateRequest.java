package com.project.restaurant.components.items.dtos;


import com.project.restaurant.base.dtos.ErrorResponse;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemCreateRequest extends ErrorResponse {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private BigDecimal price;

    private Long version;

    private List<Long> ingredientIds;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof ItemCreateRequest)) return false;

        return ((ItemCreateRequest) o).getName().equals(this.getName()) && ((ItemCreateRequest) o).getDescription().equals(this.getDescription())
                && ((ItemCreateRequest) o).getPrice().equals(this.getPrice()) && ((ItemCreateRequest) o).getIngredientIds() == this.getIngredientIds();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

