package com.company.qlts.view.taissan;

import com.company.qlts.bus.TaiSanBUS;
import com.company.qlts.common.AppColor;
import com.company.qlts.component.ModernButton;
import com.company.qlts.component.ModernTextField;
import com.company.qlts.entity.TaiSan;
import com.company.qlts.util.MessageUtil;
import com.company.qlts.util.UiUtil;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TaiSanDialog extends JDialog {

    private final TaiSanBUS bus = new TaiSanBUS();
    private TaiSan taiSan;
    private boolean saved = false;

    private ModernTextField txtTen, txtMaLoai, txtMaNcc, txtSerial;
    private ModernTextField txtNgayMua, txtNgayHetBh, txtGiaMua, txtViTri, txtGhiChu;

    public TaiSanDialog(Frame parent, TaiSan ts) {
        super(parent, true);
        this.taiSan = ts;
        initComponents();
        setTitle(ts == null ? "Thêm tài sản mới" : "Sửa tài sản: " + ts.getMaTsCode());
        setSize(600, 700);
        setMinimumSize(new Dimension(500, 400));
        setLocationRelativeTo(parent);
        setResizable(true);     // ← Cho phép kéo dãn dialog
        if (ts != null) fillData();
    }

    public boolean isSaved() { return saved; }

    private void initComponents() {
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppColor.PRIMARY);
        header.setBorder(new EmptyBorder(18, 25, 18, 25));

        JLabel lblTitle = new JLabel(taiSan == null ? "➕ Thêm tài sản mới" : "✏️ Sửa tài sản");
        lblTitle.setFont(UiUtil.bold(20));
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle, BorderLayout.WEST);

        add(header, BorderLayout.NORTH);

        // ===== FORM (bên trong JScrollPane) =====
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;
        gbc.weightx = 1;

        int row = 0;
        txtTen       = addField(form, gbc, row++, "Tên tài sản *", "");
        txtMaLoai    = addField(form, gbc, row++, "Mã loại", "");
        txtMaNcc     = addField(form, gbc, row++, "Mã nhà cung cấp", "");
        txtSerial    = addField(form, gbc, row++, "Số serial", "");
        txtNgayMua   = addField(form, gbc, row++, "Ngày mua (yyyy-MM-dd)", "");
        txtNgayHetBh = addField(form, gbc, row++, "Ngày hết BH (yyyy-MM-dd)", "");
        txtGiaMua    = addField(form, gbc, row++, "Giá mua", "");
        txtViTri     = addField(form, gbc, row++, "Vị trí", "");
        txtGhiChu    = addField(form, gbc, row++, "Ghi chú", "");

        // ⭐ BỌC FORM TRONG JSCROLLPANE ⭐
        JScrollPane scrollForm = new JScrollPane(form);
        scrollForm.setBorder(null);
        scrollForm.getVerticalScrollBar().setUnitIncrement(16);   // cuộn mượt
        scrollForm.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollForm.getViewport().setBackground(Color.WHITE);

        add(scrollForm, BorderLayout.CENTER);

        // ===== FOOTER: NÚT =====
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
        txtTen.setText(taiSan.getTenTaiSan());
        txtMaLoai.setText(String.valueOf(taiSan.getMaLoai()));
        txtMaNcc.setText(String.valueOf(taiSan.getMaNcc()));
        txtSerial.setText(nvl(taiSan.getSoSerial()));
        txtNgayMua.setText(taiSan.getNgayMua() == null ? "" : taiSan.getNgayMua().toString());
        txtNgayHetBh.setText(taiSan.getNgayHetBh() == null ? "" : taiSan.getNgayHetBh().toString());
        txtGiaMua.setText(String.valueOf((long) taiSan.getGiaMua()));
        txtViTri.setText(nvl(taiSan.getViTri()));
        txtGhiChu.setText(nvl(taiSan.getGhiChu()));
    }

    private String nvl(String s) { return s == null ? "" : s; }

    private void luu() {
        try {
            TaiSan ts = (taiSan == null) ? new TaiSan() : taiSan;

            if (txtTen.getText().trim().isEmpty()) {
                MessageUtil.canhBao(this, "Tên tài sản không được để trống!");
                return;
            }

            ts.setTenTaiSan(txtTen.getText().trim());
            ts.setMaLoai(parseIntSafe(txtMaLoai.getText(), 1));
            ts.setMaNcc(parseIntSafe(txtMaNcc.getText(), 1));
            ts.setSoSerial(txtSerial.getText().trim());
            ts.setNgayMua(parseDateSafe(txtNgayMua.getText()));
            ts.setNgayHetBh(parseDateSafe(txtNgayHetBh.getText()));
            ts.setGiaMua(parseDoubleSafe(txtGiaMua.getText(), 0));
            ts.setViTri(txtViTri.getText().trim());
            ts.setGhiChu(txtGhiChu.getText().trim());

            boolean kq;
            if (taiSan == null) {
                kq = bus.them(ts);
                if (kq) MessageUtil.thongBao(this, "Thêm thành công! Mã: " + ts.getMaTsCode());
            } else {
                kq = bus.sua(ts);
                if (kq) MessageUtil.thongBao(this, "Cập nhật thành công!");
            }

            if (kq) {
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