package com.company.qlts.bus;

import com.company.qlts.dao.NhanVienDAO;
import com.company.qlts.entity.NhanVien;
import java.util.List;

public class NhanVienBUS {

    private final NhanVienDAO dao = new NhanVienDAO();

    public List<NhanVien> getAll() { return dao.getAll(); }
    public NhanVien findById(int id) { return dao.findById(id); }

    public boolean them(NhanVien nv) {
        if (nv.getHoTen() == null || nv.getHoTen().trim().isEmpty()) {
            System.out.println("❌ Họ tên không được để trống!");
            return false;
        }
        nv.setMaNvCode(dao.sinhMaNv());
        if (nv.getTrangThai() == 0) nv.setTrangThai(1);
        return dao.insert(nv);
    }

    public boolean sua(NhanVien nv) {
        if (dao.findById(nv.getMaNhanVien()) == null) return false;
        if (nv.getHoTen() == null || nv.getHoTen().trim().isEmpty()) return false;
        return dao.update(nv);
    }

    public boolean xoa(int id) {
        return dao.findById(id) != null && dao.delete(id);
    }
}