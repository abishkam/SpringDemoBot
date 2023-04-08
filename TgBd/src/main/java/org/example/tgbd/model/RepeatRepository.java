package org.example.tgbd.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepeatRepository extends JpaRepository<Repeat, Long> {

}
