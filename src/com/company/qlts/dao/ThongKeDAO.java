package com.company.qlts.dao;

import com.company.qlts.config.DBConnection;
import java.sql.*;

public class ThongKeDAO {

    public int demTaiSan() {
        String sql = "SELECT COUNT(*) FROM tai_san";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public int demTaiSanTheoTrangThai(String tt) {
        String sql = "SELECT COUNT(*) FROM tai_san WHERE trang_thai = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tt);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public double tongGiaTriTaiSan() {
        String sql = "SELECT ISNULL(SUM(gia_mua), 0) FROM tai_san";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
}