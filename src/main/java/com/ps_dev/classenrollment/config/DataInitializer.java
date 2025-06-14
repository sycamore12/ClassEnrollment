package com.ps_dev.classenrollment.config;

import com.ps_dev.classenrollment.model.*;
import com.ps_dev.classenrollment.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MataKuliahRepository mataKuliahRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if there are no users yet
        if (userRepository.count() == 0) {
            // Create Admin
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);

            // Create Mahasiswa
            Mahasiswa mhs1 = new Mahasiswa();
            mhs1.setUsername("budi");
            mhs1.setPassword(passwordEncoder.encode("password"));
            mhs1.setRole("ROLE_MAHASISWA");
            mhs1.setNim("211401001");
            mhs1.setNamaLengkap("Budi Sanjaya");
            userRepository.save(mhs1);

            // Create Dosen
            Dosen dsn1 = new Dosen();
            dsn1.setUsername("gusfring");
            dsn1.setPassword(passwordEncoder.encode("password"));
            dsn1.setRole("ROLE_DOSEN");
            dsn1.setNidn("198501012010121001");
            dsn1.setNamaLengkap("Dr. Gustavo Fring, S.Kom., M.Sc.");
            userRepository.save(dsn1);

            // Create MataKuliah
            MataKuliah mk1 = new MataKuliah();
            mk1.setKodeMk("IK241");
            mk1.setNamaMk("Pemrograman Berorientasi Objek");
            mk1.setSks(3);
            mataKuliahRepository.save(mk1);

            MataKuliah mk2 = new MataKuliah();
            mk2.setKodeMk("IK311");
            mk2.setNamaMk("Jaringan Komputer");
            mk2.setSks(3);
            mataKuliahRepository.save(mk2);
        }
    }
}