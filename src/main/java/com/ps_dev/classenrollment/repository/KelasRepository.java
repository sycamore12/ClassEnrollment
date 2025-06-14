package com.ps_dev.classenrollment.repository;

import com.ps_dev.classenrollment.model.Kelas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KelasRepository extends JpaRepository<Kelas, Long> {
}