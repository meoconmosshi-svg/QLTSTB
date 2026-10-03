package com.company.qlts.entity;

public class LoaiTaiSan {
    private int maLoai;
    private String maLoaiCode;
    private String tenLoai;
    private String moTa;
    private double tyLeKhauHao;

    public LoaiTaiSan() {}

    public int getMaLoai() { return maLoai; }
    public void setMaLoai(int maLoai) { this.maLoai = maLoai; }

    public String getMaLoaiCode() { return maLoaiCode; }
    public void setMaLoaiCode(String maLoaiCode) { this.maLoaiCode = maLoaiCode; }

    public String getTenLoai() { return tenLoai; }
    public void setTenLoai(String tenLoai) { this.tenLoai = tenLoai; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public double getTyLeKhauHao() { return tyLeKhauHao; }
    public void setTyLeKhauHao(double tyLeKhauHao) { this.tyLeKhauHao = tyLeKhauHao; }

    @Override
    public String toString() {
        return tenLoai;
    }
}