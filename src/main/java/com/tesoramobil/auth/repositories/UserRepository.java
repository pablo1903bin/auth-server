package com.tesoramobil.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tesoramobil.auth.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	

	
	Optional<UserEntity> findByUsername(String username);
	
	
}
