package com.company.qlts.bus;

import com.company.qlts.dao.ThietBiDAO;
import com.company.qlts.entity.ThietBi;
import java.util.List;

public class ThietBiBUS {

    private final ThietBiDAO dao = new ThietBiDAO();

    public List<ThietBi> getAll() { return dao.getAll(); }
    public ThietBi findById(int id) { return dao.findById(id); }

    public boolean them(ThietBi tb) {
        if (tb.getTenThietBi() == null || tb.getTenThietBi().isEmpty()) {
            System.out.println("❌ Tên thiết bị không được để trống!");
            return false;
        }
        if (tb.getGiaMua() < 0) {
            System.out.println("❌ Giá mua không hợp lệ!");
            return false;
        }
        tb.setMaTbCode(dao.sinhMaTb());
        if (tb.getTrangThai() == null) tb.setTrangThai("Trong kho");
        if (tb.getTinhTrang() == null) tb.setTinhTrang("Mới");
        tb.setGiaTriHienTai(tb.getGiaMua());
        return dao.insert(tb);
    }

    public boolean sua(ThietBi tb) {
        ThietBi cu = dao.findById(tb.getMaThietBi());
        if (cu == null) return false;
        return dao.update(tb);
    }

    public boolean xoa(int id) {
        ThietBi tb = dao.findById(id);
        if (tb == null) return false;
        if ("Đã cấp phát".equals(tb.getTrangThai())) return false;
        return dao.delete(id);
    }
}