package com.ps_dev.classenrollment.controller;

import com.ps_dev.classenrollment.model.Kelas;
import com.ps_dev.classenrollment.model.Mahasiswa;
import com.ps_dev.classenrollment.repository.KelasRepository;
import com.ps_dev.classenrollment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mahasiswa")
public class MahasiswaController {

    @Autowired private KelasRepository kelasRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        Mahasiswa mhs = (Mahasiswa) userRepository.findByUsername(authentication.getName()).orElse(null);
        model.addAttribute("mahasiswa", mhs);
        return "mahasiswa/dashboard";
    }

    @GetMapping("/my-classes")
    public String myClasses(Model model, Authentication authentication) {
        Mahasiswa mhs = (Mahasiswa) userRepository.findByUsername(authentication.getName()).orElse(null);
        if (mhs != null) {
            model.addAttribute("listKelas", mhs.getKelasDiikuti());
        }
        return "mahasiswa/my-classes";
    }

    @GetMapping("/available-classes")
    public String availableClasses(Model model, Authentication authentication) {
        Mahasiswa mhs = (Mahasiswa) userRepository.findByUsername(authentication.getName()).orElse(null);
        if (mhs != null) {
            var enrolledClassIds = mhs.getKelasDiikuti().stream().map(Kelas::getId).collect(Collectors.toSet());
            var availableClasses = kelasRepository.findAll().stream()
                    .filter(k -> !enrolledClassIds.contains(k.getId()))
                    .collect(Collectors.toList());
            model.addAttribute("listKelas", availableClasses);
        }
        return "mahasiswa/available-classes";
    }

    @PostMapping("/enroll/{kelasId}")
    public String enroll(@PathVariable Long kelasId, Authentication authentication) {
        Mahasiswa mhs = (Mahasiswa) userRepository.findByUsername(authentication.getName()).orElse(null);
        Kelas kelas = kelasRepository.findById(kelasId).orElse(null);

        if (mhs != null && kelas != null) {
            kelas.getPeserta().add(mhs);
            kelasRepository.save(kelas);
        }
        return "redirect:/mahasiswa/available-classes";
    }
}