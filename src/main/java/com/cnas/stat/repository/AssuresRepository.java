package com.cnas.stat.repository;

import com.cnas.stat.domain.Assures;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Assures entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssuresRepository extends JpaRepository<Assures, Long> {

}
