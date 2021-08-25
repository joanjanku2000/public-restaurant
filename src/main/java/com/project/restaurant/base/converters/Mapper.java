package com.project.restaurant.base.converters;

import java.util.List;

public interface Mapper<T, S> {
     T toDto(S b);
     List<T> toDtoList(List<S> b);
}
