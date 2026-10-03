package com.company.qlts.view.taissan;

import com.company.qlts.bus.LoaiTaiSanBUS;
import com.company.qlts.common.AppColor;
import com.company.qlts.component.ModernButton;
import com.company.qlts.component.ModernTextField;
import com.company.qlts.entity.LoaiTaiSan;
import com.company.qlts.util.MessageUtil;
import com.company.qlts.util.UiUtil;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoaiTaiSanDialog extends JDialog {

    private final LoaiTaiSanBUS bus = new LoaiTaiSanBUS();
    private LoaiTaiSan loaiTaiSan;
    private boolean saved = false;

    private ModernTextField txtTen, txtMoTa, txtTyLe;

    public LoaiTaiSanDialog(Frame parent, LoaiTaiSan lts) {
        super(parent, true);
        this.loaiTaiSan = lts;
        initComponents();
        setTitle(lts == null ? "Thêm loại tài sản" : "Sửa loại: " + lts.getTenLoai());
        setSize(500, 420);
        setLocationRelativeTo(parent);
        if (lts != null) fillData();
    }

    public boolean isSaved() { return saved; }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppColor.PRIMARY);
        header.setBorder(new EmptyBorder(18, 25, 18, 25));
        JLabel lblTitle = new JLabel(loaiTaiSan == null ? "➕ Thêm loại tài sản" : "✏️ Sửa loại tài sản");
        lblTitle.setFont(UiUtil.bold(20));
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(25, 30, 25, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.gridx = 0; gbc.weightx = 1;

        txtTen = addField(form, gbc, 0, "Tên loại tài sản *", "");
        txtMoTa = addField(form, gbc, 1, "Mô tả", "");
        txtTyLe = addField(form, gbc, 2, "Tỷ lệ khấu hao (%/năm)", "0");

        add(form, BorderLayout.CENTER);

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
        txt.setPreferredSize(new Dimension(0, 40));
        gbc.gridy = row * 2 + 1;
        form.add(txt, gbc);
        return txt;
    }

    private void fillData() {
        txtTen.setText(loaiTaiSan.getTenLoai());
        txtMoTa.setText(loaiTaiSan.getMoTa() == null ? "" : loaiTaiSan.getMoTa());
        txtTyLe.setText(String.valueOf(loaiTaiSan.getTyLeKhauHao()));
    }

    private void luu() {
        try {
            LoaiTaiSan lts = (loaiTaiSan == null) ? new LoaiTaiSan() : loaiTaiSan;
            if (txtTen.getText().trim().isEmpty()) {
                MessageUtil.canhBao(this, "Tên loại không được để trống!");
                return;
            }
            lts.setTenLoai(txtTen.getText().trim());
            lts.setMoTa(txtMoTa.getText().trim());
            lts.setTyLeKhauHao(parseDoubleSafe(txtTyLe.getText(), 0));

            boolean kq = (loaiTaiSan == null) ? bus.them(lts) : bus.sua(lts);
            if (kq) {
                MessageUtil.thongBao(this, loaiTaiSan == null ? "Thêm thành công!" : "Cập nhật thành công!");
                saved = true;
                dispose();
            } else {
                MessageUtil.loi(this, "Lưu thất bại!");
            }
        } catch (Exception ex) {
            MessageUtil.loi(this, "Lỗi: " + ex.getMessage());
        }
    }

    private double parseDoubleSafe(String s, double def) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return def; }
    }
}