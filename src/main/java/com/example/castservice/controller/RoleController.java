package com.example.castservice.controller;

import com.example.castservice.model.Role;
import com.example.castservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

     @GetMapping("/role/{id}")
     public Role getRolesById(@PathVariable String id){
         return roleRepository.findRoleById(id);
     }
     @PostMapping("/role")
     public Role addRole(@RequestBody Role role){
         roleRepository.save(role);
         return role;
     }
     @PutMapping("/role")
     public Role updateRole(@RequestBody Role updatedRole){
         Role retrievedReview = roleRepository.findRoleById(updatedRole.getId());
         retrievedReview.setName(updatedRole.getName());
         roleRepository.save(retrievedReview);
         return retrievedReview;
     }
     @DeleteMapping("/role/{id}")
     public ResponseEntity deleteRole(@PathVariable String id){
         Role role = roleRepository.findRoleById(id);
         if(role != null){
             roleRepository.delete(role);
             return ResponseEntity.ok().build();
         }
         return ResponseEntity.notFound().build();
     }
}
