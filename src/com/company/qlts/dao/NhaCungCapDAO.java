package com.company.qlts.dao;

import com.company.qlts.config.DBConnection;
import com.company.qlts.entity.NhaCungCap;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAO {

    public List<NhaCungCap> getAll() {
        List<NhaCungCap> list = new ArrayList<>();
        String sql = "SELECT * FROM nha_cung_cap ORDER BY ma_ncc";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public NhaCungCap findById(int id) {
        String sql = "SELECT * FROM nha_cung_cap WHERE ma_ncc=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(NhaCungCap ncc) {
        String sql = "INSERT INTO nha_cung_cap(ma_ncc_code, ten_ncc, dia_chi, so_dien_thoai, "
                   + "email, ma_so_thue, nguoi_lien_he, ghi_chu) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, ncc.getMaNccCode());
            ps.setString(2, ncc.getTenNcc());
            ps.setString(3, ncc.getDiaChi());
            ps.setString(4, ncc.getSoDienThoai());
            ps.setString(5, ncc.getEmail());
            ps.setString(6, ncc.getMaSoThue());
            ps.setString(7, ncc.getNguoiLienHe());
            ps.setString(8, ncc.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(NhaCungCap ncc) {
        String sql = "UPDATE nha_cung_cap SET ten_ncc=?, dia_chi=?, so_dien_thoai=?, email=?, "
                   + "ma_so_thue=?, nguoi_lien_he=?, ghi_chu=? WHERE ma_ncc=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, ncc.getTenNcc());
            ps.setString(2, ncc.getDiaChi());
            ps.setString(3, ncc.getSoDienThoai());
            ps.setString(4, ncc.getEmail());
            ps.setString(5, ncc.getMaSoThue());
            ps.setString(6, ncc.getNguoiLienHe());
            ps.setString(7, ncc.getGhiChu());
            ps.setInt(8, ncc.getMaNcc());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM nha_cung_cap WHERE ma_ncc=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public String sinhMaNcc() {
        String sql = "SELECT MAX(CAST(SUBSTRING(ma_ncc_code, 4, 10) AS INT)) FROM nha_cung_cap";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int max = rs.getInt(1);
                return String.format("NCC%03d", max + 1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "NCC001";
    }

    private NhaCungCap mapRow(ResultSet rs) throws SQLException {
        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNcc(rs.getInt("ma_ncc"));
        ncc.setMaNccCode(rs.getString("ma_ncc_code"));
        ncc.setTenNcc(rs.getString("ten_ncc"));
        ncc.setDiaChi(rs.getString("dia_chi"));
        ncc.setSoDienThoai(rs.getString("so_dien_thoai"));
        ncc.setEmail(rs.getString("email"));
        ncc.setMaSoThue(rs.getString("ma_so_thue"));
        ncc.setNguoiLienHe(rs.getString("nguoi_lien_he"));
        ncc.setGhiChu(rs.getString("ghi_chu"));
        return ncc;
    }
}