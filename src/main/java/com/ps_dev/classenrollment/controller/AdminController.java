package com.ps_dev.classenrollment.controller;

import com.ps_dev.classenrollment.model.Kelas;
import com.ps_dev.classenrollment.model.MataKuliah;
import com.ps_dev.classenrollment.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MataKuliahRepository mataKuliahRepository;
    @Autowired
    private KelasRepository kelasRepository;
    @Autowired
    private DosenRepository dosenRepository;

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

    @PostMapping("/manage-kelas/save")
    public String saveKelas(@ModelAttribute("kelas") Kelas kelas) {
        kelasRepository.save(kelas);
        return "redirect:/admin/manage-kelas";
    }
}