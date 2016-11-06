package com.ge.inspection.ia.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ge.inspection.ia.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
