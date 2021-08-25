package com.project.restaurant.roles.unit;


import com.project.restaurant.base.exceptions.DuplicateException;
import com.project.restaurant.base.exceptions.NotFoundException;
import com.project.restaurant.components.roles.dtos.RoleCreateRequest;
import com.project.restaurant.components.roles.entities.Roles;
import com.project.restaurant.components.roles.repository.RolesRepository;
import com.project.restaurant.components.roles.service.RolesService;
import com.project.restaurant.components.roles.service.RolesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class RolesTest {
    @Mock
    private RolesRepository repository;
    @InjectMocks
    private RolesService rolesService = new RolesServiceImpl();

    @Test
    @DisplayName("Saving role success")
    public void saveSuccess() {
        RoleCreateRequest roleCreateRequest = new RoleCreateRequest();
        roleCreateRequest.setRole("user");
        /**
         * Return an emtpy array list => this role doesn't exist
         */
        Mockito.when(repository.findAllByName(roleCreateRequest.getRole())).thenReturn(new ArrayList<>());

        rolesService.save(roleCreateRequest);

        Mockito.verify(repository, Mockito.times(1)).save(any(Roles.class));

        log.debug("Save success : Saving {} was successfull ", roleCreateRequest);
    }

    @Test
    @DisplayName("Saving role failure")
    public void saveFailure() {
        RoleCreateRequest roleCreateRequest = new RoleCreateRequest();
        roleCreateRequest.setRole("user");
        /**
         * Return an emtpy array list => this role doesn't exist
         */
        List<Roles> rolesList = new ArrayList<>();
        rolesList.add(new Roles());
        Mockito.when(repository.findAllByName(roleCreateRequest.getRole())).thenReturn(rolesList);

        try {
            rolesService.save(roleCreateRequest);
        } catch (DuplicateException e) {
            Mockito.verify(repository, Mockito.times(0)).save(any(Roles.class));
        }


        log.debug("Save failure : Saving {} produced an exception and repo's save method was never called ", roleCreateRequest);
    }


    @Test
    @DisplayName("Find by Id success")
    public void findById() {
        long id = 1L;
        String name = "user";
        Roles roles = new Roles();
        roles.setName(name);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(roles));

        Assertions.assertEquals(rolesService.findById(id).getName(), name);

        log.debug("Find by id : Successfully got role with name {} " + name);
    }

    @Test
    @DisplayName("Delete failure")
    public void deleteFailure() {
        long id = 1L;
        String name = "user";
        Roles roles = new Roles();
        roles.setName(name);

        Mockito.when(repository.findById(id)).thenReturn(Optional.ofNullable(null));

        try {
            rolesService.delete(id);
        } catch (NotFoundException e) {
            Mockito.verify(repository, Mockito.times(0)).save(any(Roles.class));
            log.debug("Delete failure : Thrown exception : {} , Method save never invoked ", e.getMessage());
        }


    }
}
