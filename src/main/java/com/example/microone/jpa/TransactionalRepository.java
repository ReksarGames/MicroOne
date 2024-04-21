package com.example.microone.jpa;

import com.example.microone.model.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionalRepository extends JpaRepository<Transactional, UUID> {
}
