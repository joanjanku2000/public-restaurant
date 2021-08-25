package com.project.restaurant.components.roles.converter;

import com.project.restaurant.base.converters.Mapper;
import com.project.restaurant.components.roles.dtos.RoleCreateRequest;
import com.project.restaurant.components.roles.dtos.RolesDto;
import com.project.restaurant.components.roles.entities.Roles;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RolesConverter implements Mapper<RolesDto, Roles> {
    @Autowired
    private ModelMapper modelMapper;

    public static Roles requestToEntity(RoleCreateRequest req) {
        return new Roles(req.getRole());
    }

    public RolesDto toDto(Roles role) {
        return modelMapper.map(role, RolesDto.class);
    }

    @Override
    public List<RolesDto> toDtoList(List<Roles> b) {
        List<RolesDto> rolesDtos = new ArrayList<>();
        b.forEach(this::toDto);
        return rolesDtos;
    }
}
