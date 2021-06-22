package com.example.demo.security.repository;

import com.example.demo.security.domain.Role;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(String name);
}
