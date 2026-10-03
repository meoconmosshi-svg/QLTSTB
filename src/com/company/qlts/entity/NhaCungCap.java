package com.company.qlts.entity;

public class NhaCungCap {
    private int maNcc;
    private String maNccCode;
    private String tenNcc;
    private String diaChi;
    private String soDienThoai;
    private String email;
    private String maSoThue;
    private String nguoiLienHe;
    private String ghiChu;

    public NhaCungCap() {}

    public int getMaNcc() { return maNcc; }
    public void setMaNcc(int maNcc) { this.maNcc = maNcc; }

    public String getMaNccCode() { return maNccCode; }
    public void setMaNccCode(String maNccCode) { this.maNccCode = maNccCode; }

    public String getTenNcc() { return tenNcc; }
    public void setTenNcc(String tenNcc) { this.tenNcc = tenNcc; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMaSoThue() { return maSoThue; }
    public void setMaSoThue(String maSoThue) { this.maSoThue = maSoThue; }

    public String getNguoiLienHe() { return nguoiLienHe; }
    public void setNguoiLienHe(String nguoiLienHe) { this.nguoiLienHe = nguoiLienHe; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}