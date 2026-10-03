package com.company.qlts.dao;

import com.company.qlts.config.DBConnection;
import com.company.qlts.entity.TaiSan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiSanDAO {

    public List<TaiSan> getAll() {
        List<TaiSan> list = new ArrayList<>();
        String sql = "SELECT * FROM tai_san ORDER BY ma_tai_san";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public TaiSan findById(int id) {
        String sql = "SELECT * FROM tai_san WHERE ma_tai_san=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(TaiSan ts) {
        String sql = "INSERT INTO tai_san(ma_ts_code, ten_tai_san, ma_loai, ma_ncc, "
                   + "so_serial, ngay_mua, ngay_het_bh, gia_mua, tinh_trang, "
                   + "trang_thai, vi_tri, ghi_chu) "
                   + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, ts.getMaTsCode());
            ps.setString(2, ts.getTenTaiSan());
            ps.setInt(3, ts.getMaLoai());
            ps.setInt(4, ts.getMaNcc());
            ps.setString(5, ts.getSoSerial());
            ps.setDate(6, ts.getNgayMua());
            ps.setDate(7, ts.getNgayHetBh());
            ps.setDouble(8, ts.getGiaMua());
            ps.setString(9, ts.getTinhTrang());
            ps.setString(10, ts.getTrangThai());
            ps.setString(11, ts.getViTri());
            ps.setString(12, ts.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(TaiSan ts) {
        String sql = "UPDATE tai_san SET ten_tai_san=?, ma_loai=?, ma_ncc=?, "
                   + "so_serial=?, ngay_mua=?, ngay_het_bh=?, gia_mua=?, "
                   + "tinh_trang=?, trang_thai=?, vi_tri=?, ghi_chu=? WHERE ma_tai_san=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, ts.getTenTaiSan());
            ps.setInt(2, ts.getMaLoai());
            ps.setInt(3, ts.getMaNcc());
            ps.setString(4, ts.getSoSerial());
            ps.setDate(5, ts.getNgayMua());
            ps.setDate(6, ts.getNgayHetBh());
            ps.setDouble(7, ts.getGiaMua());
            ps.setString(8, ts.getTinhTrang());
            ps.setString(9, ts.getTrangThai());
            ps.setString(10, ts.getViTri());
            ps.setString(11, ts.getGhiChu());
            ps.setInt(12, ts.getMaTaiSan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM tai_san WHERE ma_tai_san=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public String sinhMaTaiSan() {
        String sql = "SELECT MAX(CAST(SUBSTRING(ma_ts_code, 3, 10) AS INT)) FROM tai_san";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int max = rs.getInt(1);
                return String.format("TS%04d", max + 1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "TS0001";
    }

    private TaiSan mapRow(ResultSet rs) throws SQLException {
        TaiSan ts = new TaiSan();
        ts.setMaTaiSan(rs.getInt("ma_tai_san"));
        ts.setMaTsCode(rs.getString("ma_ts_code"));
        ts.setTenTaiSan(rs.getString("ten_tai_san"));
        ts.setMaLoai(rs.getInt("ma_loai"));
        ts.setMaNcc(rs.getInt("ma_ncc"));
        ts.setSoSerial(rs.getString("so_serial"));
        ts.setNgayMua(rs.getDate("ngay_mua"));
        ts.setNgayHetBh(rs.getDate("ngay_het_bh"));
        ts.setGiaMua(rs.getDouble("gia_mua"));
        ts.setTinhTrang(rs.getString("tinh_trang"));
        ts.setTrangThai(rs.getString("trang_thai"));
        ts.setViTri(rs.getString("vi_tri"));
        ts.setGhiChu(rs.getString("ghi_chu"));
        return ts;
    }
}