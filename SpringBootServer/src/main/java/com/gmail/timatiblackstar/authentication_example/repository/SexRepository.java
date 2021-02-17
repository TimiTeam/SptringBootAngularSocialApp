package com.gmail.timatiblackstar.authentication_example.repository;

import com.gmail.timatiblackstar.authentication_example.models.ESex;
import com.gmail.timatiblackstar.authentication_example.models.Sex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SexRepository extends JpaRepository<Sex, Integer> {
    Optional<Sex> findByName(ESex name);
}
