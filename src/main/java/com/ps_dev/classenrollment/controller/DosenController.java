package com.ps_dev.classenrollment.controller;

import com.ps_dev.classenrollment.model.Dosen;
import com.ps_dev.classenrollment.model.Kelas;
import com.ps_dev.classenrollment.repository.KelasRepository;
import com.ps_dev.classenrollment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dosen")
public class DosenController {

    @Autowired private KelasRepository kelasRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        Dosen dosen = (Dosen) userRepository.findByUsername(authentication.getName()).orElse(null);
        model.addAttribute("dosen", dosen);
        return "dosen/dashboard";
    }

    @GetMapping("/my-classes")
    public String myClasses(Model model, Authentication authentication) {
        Dosen dosen = (Dosen) userRepository.findByUsername(authentication.getName()).orElse(null);
        if (dosen != null) {
            model.addAttribute("listKelas", dosen.getKelasDiajar());
        }
        return "dosen/my-classes";
    }

    @GetMapping("/available-classes")
    public String availableClasses(Model model) {
        var availableClasses = kelasRepository.findAll().stream()
                .filter(k -> k.getDosenPengajar() == null)
                .collect(Collectors.toList());
        model.addAttribute("listKelas", availableClasses);
        return "dosen/available-classes";
    }

    @PostMapping("/teach/{kelasId}")
    public String teach(@PathVariable Long kelasId, Authentication authentication) {
        Dosen dosen = (Dosen) userRepository.findByUsername(authentication.getName()).orElse(null);
        Kelas kelas = kelasRepository.findById(kelasId).orElse(null);

        if (dosen != null && kelas != null && kelas.getDosenPengajar() == null) {
            kelas.setDosenPengajar(dosen);
            kelasRepository.save(kelas);
        }
        return "redirect:/dosen/available-classes";
    }
}