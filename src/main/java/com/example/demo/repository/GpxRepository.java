package com.example.demo.repository;

import com.example.demo.entity.Gpx;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpxRepository extends CrudRepository<Gpx, Long> {
}
