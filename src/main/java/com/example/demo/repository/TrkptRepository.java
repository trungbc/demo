package com.example.demo.repository;

import com.example.demo.entity.TrkptEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrkptRepository extends CrudRepository<TrkptEntity, Long> {

    List<TrkptEntity> findAll(Pageable pageable);
}
