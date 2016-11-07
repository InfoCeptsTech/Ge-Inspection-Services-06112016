package com.ge.inspection.ia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ge.inspection.ia.domain.Users;


@Repository("userRepository")
public interface UserRepository extends JpaRepository<Users, String> {

    @Query("SELECT u FROM Users u WHERE LOWER(u.username) = LOWER(:username)")
    Users findByUsernameCaseInsensitive(@Param("username") String username);

    @Query
    Users findByEmail(String email);

    @Query
    Users findByEmailAndActivationKey(String email, String activationKey);

    @Query
    Users findByEmailAndResetPasswordKey(String email, String resetPasswordKey);

}