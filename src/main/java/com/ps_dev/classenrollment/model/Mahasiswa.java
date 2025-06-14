package com.ps_dev.classenrollment.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Mahasiswa extends User {

    @Column(nullable = false, unique = true)
    private String nim;

    @Column(nullable = false)
    private String namaLengkap;

    @ManyToMany(mappedBy = "peserta", fetch = FetchType.EAGER)
    private Set<Kelas> kelasDiikuti = new HashSet<>();

    // --- GETTERS AND SETTERS ---
    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public Set<Kelas> getKelasDiikuti() {
        return kelasDiikuti;
    }

    public void setKelasDiikuti(Set<Kelas> kelasDiikuti) {
        this.kelasDiikuti = kelasDiikuti;
    }
}