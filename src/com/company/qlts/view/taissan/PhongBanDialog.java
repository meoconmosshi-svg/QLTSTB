package com.company.qlts.view.taissan;

import com.company.qlts.bus.PhongBanBUS;
import com.company.qlts.common.AppColor;
import com.company.qlts.component.ModernButton;
import com.company.qlts.component.ModernTextField;
import com.company.qlts.entity.PhongBan;
import com.company.qlts.util.MessageUtil;
import com.company.qlts.util.UiUtil;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PhongBanDialog extends JDialog {

    private final PhongBanBUS bus = new PhongBanBUS();
    private PhongBan phongBan;
    private boolean saved = false;

    private ModernTextField txtTen, txtCode, txtDiaDiem, txtNgayThanhLap;

    public PhongBanDialog(Frame parent, PhongBan pb) {
        super(parent, true);
        this.phongBan = pb;
        initComponents();
        setTitle(pb == null ? "Thêm phòng ban" : "Sửa phòng ban: " + pb.getTenPhongBan());
        setSize(500, 420);
        setLocationRelativeTo(parent);
        if (pb != null) fillData();
    }

    public boolean isSaved() { return saved; }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppColor.PRIMARY);
        header.setBorder(new EmptyBorder(18, 25, 18, 25));
        JLabel lblTitle = new JLabel(phongBan == null ? "➕ Thêm phòng ban" : "✏️ Sửa phòng ban");
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

        txtTen = addField(form, gbc, 0, "Tên phòng ban *", "");
        txtCode = addField(form, gbc, 1, "Mã code (HC, KT, NS...)", "");
        txtDiaDiem = addField(form, gbc, 2, "Địa điểm", "");
        txtNgayThanhLap = addField(form, gbc, 3, "Ngày thành lập (yyyy-MM-dd)", "");

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
        txtTen.setText(phongBan.getTenPhongBan());
        txtCode.setText(phongBan.getMaPhongBanCode() == null ? "" : phongBan.getMaPhongBanCode());
        txtDiaDiem.setText(phongBan.getDiaDiem() == null ? "" : phongBan.getDiaDiem());
        txtNgayThanhLap.setText(phongBan.getNgayThanhLap() == null ? "" : phongBan.getNgayThanhLap().toString());
    }

    private void luu() {
        try {
            PhongBan pb = (phongBan == null) ? new PhongBan() : phongBan;
            if (txtTen.getText().trim().isEmpty()) {
                MessageUtil.canhBao(this, "Tên phòng ban không được để trống!");
                return;
            }
            pb.setTenPhongBan(txtTen.getText().trim());
            pb.setMaPhongBanCode(txtCode.getText().trim());
            pb.setDiaDiem(txtDiaDiem.getText().trim());
            pb.setNgayThanhLap(parseDateSafe(txtNgayThanhLap.getText()));

            boolean kq = (phongBan == null) ? bus.them(pb) : bus.sua(pb);
            if (kq) {
                MessageUtil.thongBao(this, phongBan == null ? "Thêm thành công!" : "Cập nhật thành công!");
                saved = true;
                dispose();
            } else {
                MessageUtil.loi(this, "Lưu thất bại!");
            }
        } catch (Exception ex) {
            MessageUtil.loi(this, "Lỗi: " + ex.getMessage());
        }
    }

    private java.sql.Date parseDateSafe(String s) {
        try {
            if (s == null || s.trim().isEmpty()) return null;
            return java.sql.Date.valueOf(s.trim());
        } catch (Exception e) { return null; }
    }
}