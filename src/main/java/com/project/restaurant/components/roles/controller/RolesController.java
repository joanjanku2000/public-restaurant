package com.project.restaurant.components.roles.controller;

import com.project.restaurant.components.roles.dtos.RoleCreateRequest;
import com.project.restaurant.components.roles.entities.Roles;
import com.project.restaurant.components.roles.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RolesController {
	
	@Autowired
	private RolesService roleService;
	
	@PostMapping("/save")
	@ResponseStatus(HttpStatus.OK)
	public void save(@RequestBody RoleCreateRequest req) {
		roleService.save(req);
	}
	
	@GetMapping
	public ResponseEntity<Roles> findById(@RequestParam Long id){
		return ResponseEntity.ok(roleService.findById(id));
		
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	public void delete(Long id) {
		roleService.delete(id);
	}
}
