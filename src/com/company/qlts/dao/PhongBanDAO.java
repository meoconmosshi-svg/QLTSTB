package com.company.qlts.dao;

import com.company.qlts.config.DBConnection;
import com.company.qlts.entity.PhongBan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongBanDAO {

    public List<PhongBan> getAll() {
        List<PhongBan> list = new ArrayList<>();
        String sql = "SELECT * FROM phong_ban ORDER BY ma_phong_ban";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public PhongBan findById(int id) {
        String sql = "SELECT * FROM phong_ban WHERE ma_phong_ban=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(PhongBan pb) {
        String sql = "INSERT INTO phong_ban(ten_phong_ban, ma_phong_ban_code, dia_diem, ngay_thanh_lap) "
                   + "VALUES(?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, pb.getTenPhongBan());
            ps.setString(2, pb.getMaPhongBanCode());
            ps.setString(3, pb.getDiaDiem());
            ps.setDate(4, pb.getNgayThanhLap());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(PhongBan pb) {
        String sql = "UPDATE phong_ban SET ten_phong_ban=?, ma_phong_ban_code=?, dia_diem=?, ngay_thanh_lap=? "
                   + "WHERE ma_phong_ban=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, pb.getTenPhongBan());
            ps.setString(2, pb.getMaPhongBanCode());
            ps.setString(3, pb.getDiaDiem());
            ps.setDate(4, pb.getNgayThanhLap());
            ps.setInt(5, pb.getMaPhongBan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM phong_ban WHERE ma_phong_ban=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // Kiểm tra có nhân viên nào thuộc phòng ban không
    public boolean coNhanVien(int maPhongBan) {
        String sql = "SELECT COUNT(*) FROM nhan_vien WHERE ma_phong_ban=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maPhongBan);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private PhongBan mapRow(ResultSet rs) throws SQLException {
        PhongBan pb = new PhongBan();
        pb.setMaPhongBan(rs.getInt("ma_phong_ban"));
        pb.setTenPhongBan(rs.getString("ten_phong_ban"));
        pb.setMaPhongBanCode(rs.getString("ma_phong_ban_code"));
        pb.setDiaDiem(rs.getString("dia_diem"));
        pb.setMaQuanLy(rs.getInt("ma_quan_ly"));
        pb.setNgayThanhLap(rs.getDate("ngay_thanh_lap"));
        return pb;
    }
}