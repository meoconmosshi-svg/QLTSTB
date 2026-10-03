package com.company.qlts.bus;

import com.company.qlts.dao.LoaiTaiSanDAO;
import com.company.qlts.entity.LoaiTaiSan;
import java.util.List;

public class LoaiTaiSanBUS {

    private final LoaiTaiSanDAO dao = new LoaiTaiSanDAO();

    public List<LoaiTaiSan> getAll() { return dao.getAll(); }
    public LoaiTaiSan findById(int id) { return dao.findById(id); }

    public boolean them(LoaiTaiSan lts) {
        if (lts.getTenLoai() == null || lts.getTenLoai().trim().isEmpty()) {
            System.out.println("❌ Tên loại không được để trống!");
            return false;
        }
        lts.setMaLoaiCode(dao.sinhMaLoai());
        return dao.insert(lts);
    }

    public boolean sua(LoaiTaiSan lts) {
        if (dao.findById(lts.getMaLoai()) == null) return false;
        if (lts.getTenLoai() == null || lts.getTenLoai().trim().isEmpty()) return false;
        return dao.update(lts);
    }

    public boolean xoa(int id) {
        if (dao.findById(id) == null) return false;
        if (dao.coTaiSan(id)) {
            System.out.println("❌ Không thể xóa! Còn tài sản thuộc loại này.");
            return false;
        }
        return dao.delete(id);
    }
}