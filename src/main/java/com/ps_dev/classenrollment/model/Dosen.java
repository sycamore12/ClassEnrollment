package com.ps_dev.classenrollment.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Dosen extends User {

    @Column(nullable = false, unique = true)
    private String nidn;

    @Column(nullable = false)
    private String namaLengkap;

    @OneToMany(mappedBy = "dosenPengajar", fetch = FetchType.EAGER)
    private Set<Kelas> kelasDiajar = new HashSet<>();

    // --- GETTERS AND SETTERS ---
    public String getNidn() {
        return nidn;
    }

    public void setNidn(String nidn) {
        this.nidn = nidn;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public Set<Kelas> getKelasDiajar() {
        return kelasDiajar;
    }

    public void setKelasDiajar(Set<Kelas> kelasDiajar) {
        this.kelasDiajar = kelasDiajar;
    }
}