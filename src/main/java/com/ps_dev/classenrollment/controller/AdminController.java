package com.ps_dev.classenrollment.controller;

import com.ps_dev.classenrollment.model.Kelas;
import com.ps_dev.classenrollment.model.MataKuliah;
import com.ps_dev.classenrollment.model.Dosen;
import com.ps_dev.classenrollment.model.Mahasiswa;
import com.ps_dev.classenrollment.model.User;
import com.ps_dev.classenrollment.repository.*;
import com.ps_dev.classenrollment.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final MataKuliahRepository mataKuliahRepository;
    private final KelasRepository kelasRepository;
    private final DosenRepository dosenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired // This @Autowired on the constructor is optional in recent Spring versions but good for clarity
    public AdminController(MataKuliahRepository mataKuliahRepository,
                           KelasRepository kelasRepository,
                           DosenRepository dosenRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        // 3. Assign the dependencies inside the constructor
        this.mataKuliahRepository = mataKuliahRepository;
        this.kelasRepository = kelasRepository;
        this.dosenRepository = dosenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/manage-matakuliah")
    public String manageMataKuliah(Model model) {
        model.addAttribute("listMataKuliah", mataKuliahRepository.findAll());
        model.addAttribute("mataKuliah", new MataKuliah());
        return "admin/manage-matakuliah";
    }

    @PostMapping("/manage-matakuliah/save")
    public String saveMataKuliah(@ModelAttribute("mataKuliah") MataKuliah mataKuliah) {
        mataKuliahRepository.save(mataKuliah);
        return "redirect:/admin/manage-matakuliah";
    }

    @GetMapping("/manage-kelas")
    public String manageKelas(Model model) {
        model.addAttribute("listKelas", kelasRepository.findAll());
        model.addAttribute("listMataKuliah", mataKuliahRepository.findAll());
        model.addAttribute("listDosen", dosenRepository.findAll());
        model.addAttribute("kelas", new Kelas());
        return "admin/manage-kelas";
    }

    @GetMapping("/kelas/{kelasId}/students")
    public String viewEnrolledStudents(@PathVariable Long kelasId, Model model) {
        Kelas kelas = kelasRepository.findById(kelasId).orElse(null);
        if (kelas != null) {
            model.addAttribute("kelas", kelas);
            model.addAttribute("students", kelas.getPeserta());
        }
        return "admin/view-enrollments";
    }

    @GetMapping("/manage-kelas/edit/{id}")
    public String showEditKelasForm(@PathVariable Long id, Model model) {
        Kelas kelas = kelasRepository.findById(id).orElse(null);
        if (kelas == null) {
            return "redirect:/admin/manage-kelas";
        }
        model.addAttribute("kelas", kelas);
        model.addAttribute("listMataKuliah", mataKuliahRepository.findAll());
        model.addAttribute("listDosen", dosenRepository.findAll());
        return "admin/edit-kelas";
    }

    @PostMapping("/manage-kelas/save")
    public String saveKelas(@ModelAttribute("kelas") Kelas kelas) {
        kelasRepository.save(kelas);
        return "redirect:/admin/manage-kelas";
    }

    @PostMapping("/manage-kelas/delete/{id}")
    public String deleteKelas(@PathVariable Long id) {
        kelasRepository.deleteById(id);
        return "redirect:/admin/manage-kelas";
    }

    @GetMapping("/manage-users")
    public String manageUsers(Model model) {
        model.addAttribute("listUsers", userRepository.findAll());
        model.addAttribute("userDto", new UserDTO()); // Pass the DTO to the form
        return "admin/manage-users";
    }

    // REPLACE your old saveUser method with this one

    @PostMapping("/manage-users/save")
    public String saveUser(@ModelAttribute("userDto") UserDTO userDto) {
        // Check if it's an UPDATE (ID is present) or CREATE (ID is null)
        if (userDto.getId() != null) {
            // --- UPDATE LOGIC ---
            User existingUser = userRepository.findById(userDto.getId()).orElse(null);
            if (existingUser != null) {
                // Update common fields
                existingUser.setUsername(userDto.getUsername()); // Or keep it read-only

                // Only update password if a new one was entered
                if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
                }

                // Update specific fields based on role
                if (existingUser instanceof Mahasiswa mhs) {
                    mhs.setNamaLengkap(userDto.getFullName());
                    mhs.setNim(userDto.getUniqueId());
                } else if (existingUser instanceof Dosen dsn) {
                    dsn.setNamaLengkap(userDto.getFullName());
                    dsn.setNidn(userDto.getUniqueId());
                }
                userRepository.save(existingUser);
            }
        } else {
            // --- CREATE LOGIC (your original code) ---
            if ("MAHASISWA".equals(userDto.getRole())) {
                Mahasiswa mhs = new Mahasiswa();
                mhs.setUsername(userDto.getUsername());
                mhs.setPassword(passwordEncoder.encode(userDto.getPassword()));
                mhs.setRole("ROLE_MAHASISWA");
                mhs.setNim(userDto.getUniqueId());
                mhs.setNamaLengkap(userDto.getFullName());
                userRepository.save(mhs);
            } else if ("DOSEN".equals(userDto.getRole())) {
                Dosen dsn = new Dosen();
                dsn.setUsername(userDto.getUsername());
                dsn.setPassword(passwordEncoder.encode(userDto.getPassword()));
                dsn.setRole("ROLE_DOSEN");
                dsn.setNidn(userDto.getUniqueId());
                dsn.setNamaLengkap(userDto.getFullName());
                userRepository.save(dsn);
            }
        }
        return "redirect:/admin/manage-users";
    }

    // Add this method inside your AdminController class

    @PostMapping("/manage-users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/manage-users";
    }

    // Add this method inside your AdminController class

    @GetMapping("/manage-users/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return "redirect:/admin/manage-users";
        }

        // Convert the User entity to a UserDTO to pass to the form
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        // Note: We don't pass the password for security

        if (user instanceof Mahasiswa mhs) {
            userDto.setRole("MAHASISWA");
            userDto.setFullName(mhs.getNamaLengkap());
            userDto.setUniqueId(mhs.getNim());
        } else if (user instanceof Dosen dsn) {
            userDto.setRole("DOSEN");
            userDto.setFullName(dsn.getNamaLengkap());
            userDto.setUniqueId(dsn.getNidn());
        }

        model.addAttribute("userDto", userDto);
        return "admin/edit-user";
    }
}