package com.company.qlts.entity;

import java.sql.Date;

public class ThietBi {
    private int maThietBi;
    private String maTbCode;
    private String tenThietBi;
    private int maLoai;
    private int maNcc;
    private String hangSanXuat;
    private String model;
    private String soSerial;
    private String cauHinh;
    private Date ngayMua;
    private Date ngayHetBh;
    private double giaMua;
    private double giaTriHienTai;
    private String tinhTrang;
    private String trangThai;
    private String viTri;
    private String ghiChu;

    public ThietBi() {}

    // Getters & Setters — Alt+Insert để tự sinh
    public int getMaThietBi() { return maThietBi; }
    public void setMaThietBi(int maThietBi) { this.maThietBi = maThietBi; }

    public String getMaTbCode() { return maTbCode; }
    public void setMaTbCode(String maTbCode) { this.maTbCode = maTbCode; }

    public String getTenThietBi() { return tenThietBi; }
    public void setTenThietBi(String tenThietBi) { this.tenThietBi = tenThietBi; }

    public int getMaLoai() { return maLoai; }
    public void setMaLoai(int maLoai) { this.maLoai = maLoai; }

    public int getMaNcc() { return maNcc; }
    public void setMaNcc(int maNcc) { this.maNcc = maNcc; }

    public String getHangSanXuat() { return hangSanXuat; }
    public void setHangSanXuat(String hangSanXuat) { this.hangSanXuat = hangSanXuat; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getSoSerial() { return soSerial; }
    public void setSoSerial(String soSerial) { this.soSerial = soSerial; }

    public String getCauHinh() { return cauHinh; }
    public void setCauHinh(String cauHinh) { this.cauHinh = cauHinh; }

    public Date getNgayMua() { return ngayMua; }
    public void setNgayMua(Date ngayMua) { this.ngayMua = ngayMua; }

    public Date getNgayHetBh() { return ngayHetBh; }
    public void setNgayHetBh(Date ngayHetBh) { this.ngayHetBh = ngayHetBh; }

    public double getGiaMua() { return giaMua; }
    public void setGiaMua(double giaMua) { this.giaMua = giaMua; }

    public double getGiaTriHienTai() { return giaTriHienTai; }
    public void setGiaTriHienTai(double giaTriHienTai) { this.giaTriHienTai = giaTriHienTai; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getViTri() { return viTri; }
    public void setViTri(String viTri) { this.viTri = viTri; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}