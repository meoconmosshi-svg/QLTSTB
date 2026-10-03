package com.company.qlts.dao;

import com.company.qlts.config.DBConnection;
import com.company.qlts.entity.NhanVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM nhan_vien ORDER BY ma_nhan_vien";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public NhanVien findById(int id) {
        String sql = "SELECT * FROM nhan_vien WHERE ma_nhan_vien=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(NhanVien nv) {
        String sql = "INSERT INTO nhan_vien(ma_nv_code, ho_ten, gioi_tinh, ngay_sinh, cmnd_cccd, "
                   + "chuc_vu, email, so_dien_thoai, dia_chi, ma_phong_ban, ngay_vao_lam, trang_thai) "
                   + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nv.getMaNvCode());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getGioiTinh());
            ps.setDate(4, nv.getNgaySinh());
            ps.setString(5, nv.getCmndCccd());
            ps.setString(6, nv.getChucVu());
            ps.setString(7, nv.getEmail());
            ps.setString(8, nv.getSoDienThoai());
            ps.setString(9, nv.getDiaChi());
            ps.setInt(10, nv.getMaPhongBan());
            ps.setDate(11, nv.getNgayVaoLam());
            ps.setInt(12, nv.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(NhanVien nv) {
        String sql = "UPDATE nhan_vien SET ho_ten=?, gioi_tinh=?, ngay_sinh=?, cmnd_cccd=?, "
                   + "chuc_vu=?, email=?, so_dien_thoai=?, dia_chi=?, ma_phong_ban=?, "
                   + "ngay_vao_lam=?, trang_thai=? WHERE ma_nhan_vien=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getGioiTinh());
            ps.setDate(3, nv.getNgaySinh());
            ps.setString(4, nv.getCmndCccd());
            ps.setString(5, nv.getChucVu());
            ps.setString(6, nv.getEmail());
            ps.setString(7, nv.getSoDienThoai());
            ps.setString(8, nv.getDiaChi());
            ps.setInt(9, nv.getMaPhongBan());
            ps.setDate(10, nv.getNgayVaoLam());
            ps.setInt(11, nv.getTrangThai());
            ps.setInt(12, nv.getMaNhanVien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM nhan_vien WHERE ma_nhan_vien=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public String sinhMaNv() {
        String sql = "SELECT MAX(CAST(SUBSTRING(ma_nv_code, 3, 10) AS INT)) FROM nhan_vien";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int max = rs.getInt(1);
                return String.format("NV%03d", max + 1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "NV001";
    }

    private NhanVien mapRow(ResultSet rs) throws SQLException {
        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(rs.getInt("ma_nhan_vien"));
        nv.setMaNvCode(rs.getString("ma_nv_code"));
        nv.setHoTen(rs.getString("ho_ten"));
        nv.setGioiTinh(rs.getString("gioi_tinh"));
        nv.setNgaySinh(rs.getDate("ngay_sinh"));
        nv.setCmndCccd(rs.getString("cmnd_cccd"));
        nv.setChucVu(rs.getString("chuc_vu"));
        nv.setEmail(rs.getString("email"));
        nv.setSoDienThoai(rs.getString("so_dien_thoai"));
        nv.setDiaChi(rs.getString("dia_chi"));
        nv.setMaPhongBan(rs.getInt("ma_phong_ban"));
        nv.setNgayVaoLam(rs.getDate("ngay_vao_lam"));
        nv.setTrangThai(rs.getInt("trang_thai"));
        return nv;
    }
}