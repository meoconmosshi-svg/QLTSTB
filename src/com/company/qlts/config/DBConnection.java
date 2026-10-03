
package com.company.qlts.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Cấu hình cho SQL Server
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL    = "jdbc:sqlserver://localhost:1433;"
                                       + "databaseName=qlts_db;"
                                       + "encrypt=true;trustServerCertificate=true;";
    private static final String USER   = "sa";           // Tài khoản SQL Server của anh
    private static final String PASS   = "123456789";    // Mật khẩu SQL Server của anh

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Không tìm thấy driver SQL Server!", e);
        }
    }

    // Test nhanh
    public static void main(String[] args) {
        try (Connection c = getConnection()) {
            System.out.println("Kết nối thành công: " + c.getCatalog());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}