package com.example.employee.repository;

import com.example.employee.model.Encounter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EncounterRepository extends CrudRepository<Encounter, Long> {
    List<Encounter> findByEmployeeIdAndDateTimeAfterOrderByDateTimeAsc(Long employee, LocalDateTime dateTime);

}
