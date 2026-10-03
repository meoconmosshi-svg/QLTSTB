package com.company.qlts.entity;

import java.sql.Date;

public class TaiSan {
    private int maTaiSan;
    private String maTsCode;
    private String tenTaiSan;
    private int maLoai;
    private int maNcc;
    private String soSerial;
    private Date ngayMua;
    private Date ngayHetBh;
    private double giaMua;
    private String tinhTrang;
    private String trangThai;
    private String viTri;
    private String ghiChu;

    public TaiSan() {}

    public int getMaTaiSan() { return maTaiSan; }
    public void setMaTaiSan(int maTaiSan) { this.maTaiSan = maTaiSan; }

    public String getMaTsCode() { return maTsCode; }
    public void setMaTsCode(String maTsCode) { this.maTsCode = maTsCode; }

    public String getTenTaiSan() { return tenTaiSan; }
    public void setTenTaiSan(String tenTaiSan) { this.tenTaiSan = tenTaiSan; }

    public int getMaLoai() { return maLoai; }
    public void setMaLoai(int maLoai) { this.maLoai = maLoai; }

    public int getMaNcc() { return maNcc; }
    public void setMaNcc(int maNcc) { this.maNcc = maNcc; }

    public String getSoSerial() { return soSerial; }
    public void setSoSerial(String soSerial) { this.soSerial = soSerial; }

    public Date getNgayMua() { return ngayMua; }
    public void setNgayMua(Date ngayMua) { this.ngayMua = ngayMua; }

    public Date getNgayHetBh() { return ngayHetBh; }
    public void setNgayHetBh(Date ngayHetBh) { this.ngayHetBh = ngayHetBh; }

    public double getGiaMua() { return giaMua; }
    public void setGiaMua(double giaMua) { this.giaMua = giaMua; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getViTri() { return viTri; }
    public void setViTri(String viTri) { this.viTri = viTri; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}