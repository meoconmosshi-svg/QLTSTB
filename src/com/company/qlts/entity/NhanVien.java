package com.company.qlts.entity;

import java.sql.Date;

public class NhanVien {
    private int maNhanVien;
    private String maNvCode;
    private String hoTen;
    private String gioiTinh;
    private Date ngaySinh;
    private String cmndCccd;
    private String chucVu;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private int maPhongBan;
    private Date ngayVaoLam;
    private int trangThai;

    public NhanVien() {}

    public int getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(int maNhanVien) { this.maNhanVien = maNhanVien; }

    public String getMaNvCode() { return maNvCode; }
    public void setMaNvCode(String maNvCode) { this.maNvCode = maNvCode; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getCmndCccd() { return cmndCccd; }
    public void setCmndCccd(String cmndCccd) { this.cmndCccd = cmndCccd; }

    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public int getMaPhongBan() { return maPhongBan; }
    public void setMaPhongBan(int maPhongBan) { this.maPhongBan = maPhongBan; }

    public Date getNgayVaoLam() { return ngayVaoLam; }
    public void setNgayVaoLam(Date ngayVaoLam) { this.ngayVaoLam = ngayVaoLam; }

    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
}