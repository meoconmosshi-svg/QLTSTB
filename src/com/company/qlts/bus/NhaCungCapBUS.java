package com.company.qlts.bus;

import com.company.qlts.dao.NhaCungCapDAO;
import com.company.qlts.entity.NhaCungCap;
import java.util.List;

public class NhaCungCapBUS {

    private final NhaCungCapDAO dao = new NhaCungCapDAO();

    public List<NhaCungCap> getAll() { return dao.getAll(); }
    public NhaCungCap findById(int id) { return dao.findById(id); }

    public boolean them(NhaCungCap ncc) {
        if (ncc.getTenNcc() == null || ncc.getTenNcc().trim().isEmpty()) {
            System.out.println("❌ Tên NCC không được để trống!");
            return false;
        }
        ncc.setMaNccCode(dao.sinhMaNcc());
        return dao.insert(ncc);
    }

    public boolean sua(NhaCungCap ncc) {
        if (dao.findById(ncc.getMaNcc()) == null) return false;
        if (ncc.getTenNcc() == null || ncc.getTenNcc().trim().isEmpty()) return false;
        return dao.update(ncc);
    }

    public boolean xoa(int id) {
        return dao.findById(id) != null && dao.delete(id);
    }
}