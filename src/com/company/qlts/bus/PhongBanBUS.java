package com.company.qlts.bus;

import com.company.qlts.dao.PhongBanDAO;
import com.company.qlts.entity.PhongBan;
import java.util.List;

public class PhongBanBUS {

    private final PhongBanDAO dao = new PhongBanDAO();

    public List<PhongBan> getAll() { return dao.getAll(); }
    public PhongBan findById(int id) { return dao.findById(id); }

    public boolean them(PhongBan pb) {
        if (pb.getTenPhongBan() == null || pb.getTenPhongBan().trim().isEmpty()) {
            System.out.println("❌ Tên phòng ban không được để trống!");
            return false;
        }
        return dao.insert(pb);
    }

    public boolean sua(PhongBan pb) {
        if (dao.findById(pb.getMaPhongBan()) == null) return false;
        if (pb.getTenPhongBan() == null || pb.getTenPhongBan().trim().isEmpty()) return false;
        return dao.update(pb);
    }

    public boolean xoa(int id) {
        if (dao.findById(id) == null) return false;
        // Không cho xóa nếu có nhân viên
        if (dao.coNhanVien(id)) {
            System.out.println("❌ Không thể xóa! Phòng ban còn nhân viên.");
            return false;
        }
        return dao.delete(id);
    }
}