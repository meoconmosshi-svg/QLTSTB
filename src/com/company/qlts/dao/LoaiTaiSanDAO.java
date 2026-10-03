package com.company.qlts.dao;

import com.company.qlts.config.DBConnection;
import com.company.qlts.entity.LoaiTaiSan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoaiTaiSanDAO {

    public List<LoaiTaiSan> getAll() {
        List<LoaiTaiSan> list = new ArrayList<>();
        String sql = "SELECT * FROM loai_tai_san ORDER BY ma_loai";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public LoaiTaiSan findById(int id) {
        String sql = "SELECT * FROM loai_tai_san WHERE ma_loai=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(LoaiTaiSan lts) {
        String sql = "INSERT INTO loai_tai_san(ma_loai_code, ten_loai, mo_ta, ty_le_khau_hao) VALUES(?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, lts.getMaLoaiCode());
            ps.setString(2, lts.getTenLoai());
            ps.setString(3, lts.getMoTa());
            ps.setDouble(4, lts.getTyLeKhauHao());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(LoaiTaiSan lts) {
        String sql = "UPDATE loai_tai_san SET ten_loai=?, mo_ta=?, ty_le_khau_hao=? WHERE ma_loai=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, lts.getTenLoai());
            ps.setString(2, lts.getMoTa());
            ps.setDouble(3, lts.getTyLeKhauHao());
            ps.setInt(4, lts.getMaLoai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM loai_tai_san WHERE ma_loai=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean coTaiSan(int maLoai) {
        String sql = "SELECT COUNT(*) FROM tai_san WHERE ma_loai=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maLoai);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public String sinhMaLoai() {
        String sql = "SELECT MAX(CAST(SUBSTRING(ma_loai_code, 4, 10) AS INT)) FROM loai_tai_san";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int max = rs.getInt(1);
                return String.format("LTS%03d", max + 1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "LTS001";
    }

    private LoaiTaiSan mapRow(ResultSet rs) throws SQLException {
        LoaiTaiSan lts = new LoaiTaiSan();
        lts.setMaLoai(rs.getInt("ma_loai"));
        lts.setMaLoaiCode(rs.getString("ma_loai_code"));
        lts.setTenLoai(rs.getString("ten_loai"));
        lts.setMoTa(rs.getString("mo_ta"));
        lts.setTyLeKhauHao(rs.getDouble("ty_le_khau_hao"));
        return lts;
    }
}