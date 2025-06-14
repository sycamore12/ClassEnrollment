package com.ps_dev.classenrollment.repository;

import com.ps_dev.classenrollment.model.Mahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Long> {
}