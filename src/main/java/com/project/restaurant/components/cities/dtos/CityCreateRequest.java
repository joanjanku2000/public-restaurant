package com.project.restaurant.components.cities.dtos;

import com.project.restaurant.base.dtos.ErrorResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
public class CityCreateRequest extends ErrorResponse implements Serializable {

    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
