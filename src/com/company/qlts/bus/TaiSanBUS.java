package com.company.qlts.bus;

import com.company.qlts.dao.TaiSanDAO;
import com.company.qlts.entity.TaiSan;
import java.util.List;

public class TaiSanBUS {

    private final TaiSanDAO dao = new TaiSanDAO();

    public List<TaiSan> getAll() { return dao.getAll(); }
    public TaiSan findById(int id) { return dao.findById(id); }

    public boolean them(TaiSan ts) {
        if (ts.getTenTaiSan() == null || ts.getTenTaiSan().trim().isEmpty()) {
            System.out.println("❌ Tên tài sản không được để trống!");
            return false;
        }
        if (ts.getGiaMua() < 0) {
            System.out.println("❌ Giá mua không hợp lệ!");
            return false;
        }
        ts.setMaTsCode(dao.sinhMaTaiSan());
        if (ts.getTrangThai() == null) ts.setTrangThai("Trong kho");
        if (ts.getTinhTrang() == null) ts.setTinhTrang("Mới");
        return dao.insert(ts);
    }

    public boolean sua(TaiSan ts) {
        TaiSan cu = dao.findById(ts.getMaTaiSan());
        if (cu == null) return false;
        return dao.update(ts);
    }

    public boolean xoa(int id) {
        TaiSan ts = dao.findById(id);
        if (ts == null) return false;
        if ("Đã cấp phát".equals(ts.getTrangThai())) return false;
        return dao.delete(id);
    }
}