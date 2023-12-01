package com.example.patient.repository;

import com.example.patient.model.Encounter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncounterRepository extends CrudRepository<Encounter, Long> {
}
