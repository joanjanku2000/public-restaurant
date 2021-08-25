package com.project.restaurant.components.cities.exceptions;
import com.project.restaurant.base.exceptions.BadRequestException;
import com.project.restaurant.components.cities.dtos.CityCreateRequest;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BadRequestForCityException extends BadRequestException {

    private final transient CityCreateRequest createRequest;

    public BadRequestForCityException(String message, CityCreateRequest createRequest) {
        super(message);
        this.createRequest = createRequest;
    }
}
