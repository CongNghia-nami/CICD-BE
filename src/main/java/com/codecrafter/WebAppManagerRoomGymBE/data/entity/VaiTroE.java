package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "gym_vai_tro")
public class VaiTroE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maVaiTro;

    private String tenVaiTro;
    private String moTa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vaiTro")
    private List<NguoiDungE> nguoiDungs;
}
