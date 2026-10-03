package com.company.qlts.dao;

import com.company.qlts.config.DBConnection;
import com.company.qlts.entity.ThietBi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThietBiDAO {

    public List<ThietBi> getAll() {
        List<ThietBi> list = new ArrayList<>();
        String sql = "SELECT * FROM thiet_bi ORDER BY ma_thiet_bi";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public ThietBi findById(int id) {
        String sql = "SELECT * FROM thiet_bi WHERE ma_thiet_bi=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(ThietBi tb) {
        String sql = "INSERT INTO thiet_bi(ma_tb_code, ten_thiet_bi, ma_loai, ma_ncc, "
                   + "hang_san_xuat, model, so_serial, cau_hinh, ngay_mua, ngay_het_bh, "
                   + "gia_mua, gia_tri_hien_tai, tinh_trang, trang_thai, vi_tri, ghi_chu) "
                   + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tb.getMaTbCode());
            ps.setString(2, tb.getTenThietBi());
            ps.setInt(3, tb.getMaLoai());
            ps.setInt(4, tb.getMaNcc());
            ps.setString(5, tb.getHangSanXuat());
            ps.setString(6, tb.getModel());
            ps.setString(7, tb.getSoSerial());
            ps.setString(8, tb.getCauHinh());
            ps.setDate(9, tb.getNgayMua());
            ps.setDate(10, tb.getNgayHetBh());
            ps.setDouble(11, tb.getGiaMua());
            ps.setDouble(12, tb.getGiaTriHienTai());
            ps.setString(13, tb.getTinhTrang());
            ps.setString(14, tb.getTrangThai());
            ps.setString(15, tb.getViTri());
            ps.setString(16, tb.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(ThietBi tb) {
        String sql = "UPDATE thiet_bi SET ten_thiet_bi=?, ma_loai=?, ma_ncc=?, "
                   + "hang_san_xuat=?, model=?, so_serial=?, cau_hinh=?, "
                   + "ngay_mua=?, ngay_het_bh=?, gia_mua=?, gia_tri_hien_tai=?, "
                   + "tinh_trang=?, trang_thai=?, vi_tri=?, ghi_chu=? WHERE ma_thiet_bi=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tb.getTenThietBi());
            ps.setInt(2, tb.getMaLoai());
            ps.setInt(3, tb.getMaNcc());
            ps.setString(4, tb.getHangSanXuat());
            ps.setString(5, tb.getModel());
            ps.setString(6, tb.getSoSerial());
            ps.setString(7, tb.getCauHinh());
            ps.setDate(8, tb.getNgayMua());
            ps.setDate(9, tb.getNgayHetBh());
            ps.setDouble(10, tb.getGiaMua());
            ps.setDouble(11, tb.getGiaTriHienTai());
            ps.setString(12, tb.getTinhTrang());
            ps.setString(13, tb.getTrangThai());
            ps.setString(14, tb.getViTri());
            ps.setString(15, tb.getGhiChu());
            ps.setInt(16, tb.getMaThietBi());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM thiet_bi WHERE ma_thiet_bi=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public String sinhMaTb() {
        String sql = "SELECT MAX(CAST(SUBSTRING(ma_tb_code, 3, 10) AS INT)) FROM thiet_bi";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int max = rs.getInt(1);
                return String.format("TB%04d", max + 1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "TB0001";
    }

    private ThietBi mapRow(ResultSet rs) throws SQLException {
        ThietBi tb = new ThietBi();
        tb.setMaThietBi(rs.getInt("ma_thiet_bi"));
        tb.setMaTbCode(rs.getString("ma_tb_code"));
        tb.setTenThietBi(rs.getString("ten_thiet_bi"));
        tb.setMaLoai(rs.getInt("ma_loai"));
        tb.setMaNcc(rs.getInt("ma_ncc"));
        tb.setHangSanXuat(rs.getString("hang_san_xuat"));
        tb.setModel(rs.getString("model"));
        tb.setSoSerial(rs.getString("so_serial"));
        tb.setCauHinh(rs.getString("cau_hinh"));
        tb.setNgayMua(rs.getDate("ngay_mua"));
        tb.setNgayHetBh(rs.getDate("ngay_het_bh"));
        tb.setGiaMua(rs.getDouble("gia_mua"));
        tb.setGiaTriHienTai(rs.getDouble("gia_tri_hien_tai"));
        tb.setTinhTrang(rs.getString("tinh_trang"));
        tb.setTrangThai(rs.getString("trang_thai"));
        tb.setViTri(rs.getString("vi_tri"));
        tb.setGhiChu(rs.getString("ghi_chu"));
        return tb;
    }
}