package com.example.castservice.repository;

import com.example.castservice.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends MongoRepository<Role, String>{
    Role findRoleById(String id);
    Role findRoleByName(String name);
}
