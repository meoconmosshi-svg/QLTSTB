package com.company.qlts.view.taissan;

import com.company.qlts.bus.ThietBiBUS;
import com.company.qlts.common.AppColor;
import com.company.qlts.component.ModernButton;
import com.company.qlts.component.ModernTextField;
import com.company.qlts.entity.ThietBi;
import com.company.qlts.util.MessageUtil;
import com.company.qlts.util.UiUtil;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ThietBiDialog extends JDialog {

    private final ThietBiBUS bus = new ThietBiBUS();
    private ThietBi thietBi;
    private boolean saved = false;

    private ModernTextField txtTen, txtMaLoai, txtMaNcc, txtHang, txtModel;
    private ModernTextField txtSerial, txtCauHinh, txtNgayMua, txtNgayHetBh;
    private ModernTextField txtGiaMua, txtViTri, txtGhiChu;

    public ThietBiDialog(Frame parent, ThietBi tb) {
        super(parent, true);
        this.thietBi = tb;
        initComponents();
        setTitle(tb == null ? "Thêm thiết bị mới" : "Sửa thiết bị: " + tb.getMaTbCode());
        setSize(600, 700);
        setLocationRelativeTo(parent);
        if (tb != null) fillData();
    }

    public boolean isSaved() { return saved; }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppColor.PRIMARY);
        header.setBorder(new EmptyBorder(18, 25, 18, 25));
        JLabel lblTitle = new JLabel(thietBi == null ? "➕ Thêm thiết bị" : "✏️ Sửa thiết bị");
        lblTitle.setFont(UiUtil.bold(20));
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0; gbc.weightx = 1;

        txtTen = addField(form, gbc, 0, "Tên thiết bị *", "");
        txtMaLoai = addField(form, gbc, 1, "Mã loại", "");
        txtMaNcc = addField(form, gbc, 2, "Mã NCC", "");
        txtHang = addField(form, gbc, 3, "Hãng sản xuất", "");
        txtModel = addField(form, gbc, 4, "Model", "");
        txtSerial = addField(form, gbc, 5, "Serial", "");
        txtCauHinh = addField(form, gbc, 6, "Cấu hình", "");
        txtNgayMua = addField(form, gbc, 7, "Ngày mua (yyyy-MM-dd)", "");
        txtNgayHetBh = addField(form, gbc, 8, "Ngày hết BH (yyyy-MM-dd)", "");
        txtGiaMua = addField(form, gbc, 9, "Giá mua", "");
        txtViTri = addField(form, gbc, 10, "Vị trí", "");
        txtGhiChu = addField(form, gbc, 11, "Ghi chú", "");

        JScrollPane scroll = new JScrollPane(form);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 15));
        footer.setBackground(AppColor.BG_MAIN);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, AppColor.BORDER));

        ModernButton btnHuy = new ModernButton("Hủy", new Color(0xE2E8F0));
        btnHuy.setForeground(AppColor.TEXT_SECONDARY);
        btnHuy.setPreferredSize(new Dimension(100, 40));
        btnHuy.addActionListener(e -> dispose());
        footer.add(btnHuy);

        ModernButton btnLuu = new ModernButton("💾 Lưu", AppColor.SUCCESS);
        btnLuu.setPreferredSize(new Dimension(120, 40));
        btnLuu.addActionListener(e -> luu());
        footer.add(btnLuu);

        add(footer, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(btnLuu);
    }

    private ModernTextField addField(JPanel form, GridBagConstraints gbc, int row,
            String label, String placeholder) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(UiUtil.bold(12));
        lbl.setForeground(AppColor.TEXT_PRIMARY);
        gbc.gridy = row * 2;
        form.add(lbl, gbc);

        ModernTextField txt = new ModernTextField(placeholder);
        txt.setPreferredSize(new Dimension(0, 38));
        gbc.gridy = row * 2 + 1;
        form.add(txt, gbc);
        return txt;
    }

    private void fillData() {
        txtTen.setText(thietBi.getTenThietBi());
        txtMaLoai.setText(String.valueOf(thietBi.getMaLoai()));
        txtMaNcc.setText(String.valueOf(thietBi.getMaNcc()));
        txtHang.setText(nvl(thietBi.getHangSanXuat()));
        txtModel.setText(nvl(thietBi.getModel()));
        txtSerial.setText(nvl(thietBi.getSoSerial()));
        txtCauHinh.setText(nvl(thietBi.getCauHinh()));
        txtNgayMua.setText(thietBi.getNgayMua() == null ? "" : thietBi.getNgayMua().toString());
        txtNgayHetBh.setText(thietBi.getNgayHetBh() == null ? "" : thietBi.getNgayHetBh().toString());
        txtGiaMua.setText(String.valueOf((long) thietBi.getGiaMua()));
        txtViTri.setText(nvl(thietBi.getViTri()));
        txtGhiChu.setText(nvl(thietBi.getGhiChu()));
    }

    private String nvl(String s) { return s == null ? "" : s; }

    private void luu() {
        try {
            ThietBi tb = (thietBi == null) ? new ThietBi() : thietBi;
            if (txtTen.getText().trim().isEmpty()) {
                MessageUtil.canhBao(this, "Tên thiết bị không được để trống!");
                return;
            }
            tb.setTenThietBi(txtTen.getText().trim());
            tb.setMaLoai(parseIntSafe(txtMaLoai.getText(), 1));
            tb.setMaNcc(parseIntSafe(txtMaNcc.getText(), 1));
            tb.setHangSanXuat(txtHang.getText().trim());
            tb.setModel(txtModel.getText().trim());
            tb.setSoSerial(txtSerial.getText().trim());
            tb.setCauHinh(txtCauHinh.getText().trim());
            tb.setNgayMua(parseDateSafe(txtNgayMua.getText()));
            tb.setNgayHetBh(parseDateSafe(txtNgayHetBh.getText()));
            tb.setGiaMua(parseDoubleSafe(txtGiaMua.getText(), 0));
            tb.setViTri(txtViTri.getText().trim());
            tb.setGhiChu(txtGhiChu.getText().trim());

            boolean kq = (thietBi == null) ? bus.them(tb) : bus.sua(tb);
            if (kq) {
                MessageUtil.thongBao(this, thietBi == null ? "Thêm thành công!" : "Cập nhật thành công!");
                saved = true;
                dispose();
            } else {
                MessageUtil.loi(this, "Lưu thất bại!");
            }
        } catch (Exception ex) {
            MessageUtil.loi(this, "Lỗi: " + ex.getMessage());
        }
    }

    private int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }
    private double parseDoubleSafe(String s, double def) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return def; }
    }
    private java.sql.Date parseDateSafe(String s) {
        try {
            if (s == null || s.trim().isEmpty()) return null;
            return java.sql.Date.valueOf(s.trim());
        } catch (Exception e) { return null; }
    }
}