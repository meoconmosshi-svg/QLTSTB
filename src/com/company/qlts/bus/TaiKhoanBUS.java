package com.company.qlts.bus;

import com.company.qlts.dao.TaiKhoanDAO;
import com.company.qlts.entity.TaiKhoan;

public class TaiKhoanBUS {

    private final TaiKhoanDAO dao = new TaiKhoanDAO();

    public TaiKhoan checkLogin(String tenDangNhap, String matKhau) {
        if (tenDangNhap == null || tenDangNhap.trim().isEmpty()) return null;
        if (matKhau == null || matKhau.isEmpty()) return null;

        TaiKhoan tk = dao.findByTenDangNhap(tenDangNhap.trim());
        if (tk == null) return null;
        if (tk.getMatKhau() == null || !matKhau.equals(tk.getMatKhau())) return null;
        return tk;
    }
}