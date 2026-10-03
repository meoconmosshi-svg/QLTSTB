package com.company.qlts.dao;

import com.company.qlts.config.DBConnection;
import com.company.qlts.entity.TaiKhoan;
import java.sql.*;

public class TaiKhoanDAO {

    public TaiKhoan findByTenDangNhap(String tenDangNhap) {
        String sql = "SELECT * FROM tai_khoan WHERE ten_dang_nhap=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tenDangNhap);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    private TaiKhoan mapRow(ResultSet rs) throws SQLException {
        TaiKhoan tk = new TaiKhoan();
        tk.setMaTaiKhoan(rs.getInt("ma_tai_khoan"));
        tk.setTenDangNhap(rs.getString("ten_dang_nhap"));
        tk.setMatKhau(rs.getString("mat_khau"));
        tk.setHoTen(rs.getString("ho_ten"));
        tk.setMaQuyen(rs.getInt("ma_quyen"));
        tk.setTrangThai(rs.getBoolean("trang_thai"));
        return tk;
    }
}