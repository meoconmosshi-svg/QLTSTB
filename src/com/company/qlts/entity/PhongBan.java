package com.company.qlts.entity;

import java.sql.Date;

public class PhongBan {
    private int maPhongBan;
    private String tenPhongBan;
    private String maPhongBanCode;
    private String diaDiem;
    private int maQuanLy;
    private Date ngayThanhLap;

    public PhongBan() {}

    public int getMaPhongBan() { return maPhongBan; }
    public void setMaPhongBan(int maPhongBan) { this.maPhongBan = maPhongBan; }

    public String getTenPhongBan() { return tenPhongBan; }
    public void setTenPhongBan(String tenPhongBan) { this.tenPhongBan = tenPhongBan; }

    public String getMaPhongBanCode() { return maPhongBanCode; }
    public void setMaPhongBanCode(String maPhongBanCode) { this.maPhongBanCode = maPhongBanCode; }

    public String getDiaDiem() { return diaDiem; }
    public void setDiaDiem(String diaDiem) { this.diaDiem = diaDiem; }

    public int getMaQuanLy() { return maQuanLy; }
    public void setMaQuanLy(int maQuanLy) { this.maQuanLy = maQuanLy; }

    public Date getNgayThanhLap() { return ngayThanhLap; }
    public void setNgayThanhLap(Date ngayThanhLap) { this.ngayThanhLap = ngayThanhLap; }

    @Override
    public String toString() {
        return tenPhongBan;
    }
}