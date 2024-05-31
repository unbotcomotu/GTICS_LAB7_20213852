package com.example.gtics_lab7_20213852.Repository;

import com.example.gtics_lab7_20213852.Entity.Resource;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {

    @Query(nativeQuery = true,value = "select name from resources where resourceId=?1")
    String verificarRecursoPorId(Integer id);

    Resource findByName(String name);
}