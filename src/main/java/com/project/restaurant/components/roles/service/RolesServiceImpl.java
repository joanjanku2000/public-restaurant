package com.project.restaurant.components.roles.service;

import com.project.restaurant.base.exceptions.DuplicateException;
import com.project.restaurant.base.exceptions.NotFoundException;
import com.project.restaurant.base.utils.ErrorMessage;
import com.project.restaurant.components.roles.converter.RolesConverter;
import com.project.restaurant.components.roles.dtos.RoleCreateRequest;
import com.project.restaurant.components.roles.entities.Roles;
import com.project.restaurant.components.roles.repository.RolesRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@NoArgsConstructor
public class RolesServiceImpl implements RolesService {

    private RolesRepository rolesRepo;

    @Autowired
    public RolesServiceImpl(RolesRepository rolesRepo) {
        this.rolesRepo = rolesRepo;
    }

    public void save(RoleCreateRequest req) {
        if (findByName(req.getRole()) != null) {
            throw new DuplicateException("This role already exists");
        }
        rolesRepo.save(RolesConverter.requestToEntity(req));
    }

    public Roles findByName(String name) {
        List<Roles> roles = rolesRepo.findAllByName(name);
        return !roles.isEmpty() ? roles.get(0) : null;
    }

    public Roles findById(Long id) {
        return rolesRepo.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(ErrorMessage.NOT_FOUND + " role with id " + id));
    }

    public void delete(Long id) {
        Roles role = findById(id);
        role.setDeleted(true);
        rolesRepo.save(role);
    }
}
