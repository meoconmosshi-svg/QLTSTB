package com.company.qlts.view.taissan;

import com.company.qlts.bus.NhaCungCapBUS;
import com.company.qlts.common.AppColor;
import com.company.qlts.component.ModernButton;
import com.company.qlts.component.ModernTextField;
import com.company.qlts.entity.NhaCungCap;
import com.company.qlts.util.MessageUtil;
import com.company.qlts.util.UiUtil;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NhaCungCapDialog extends JDialog {

    private final NhaCungCapBUS bus = new NhaCungCapBUS();
    private NhaCungCap nhaCungCap;
    private boolean saved = false;

    private ModernTextField txtTen, txtDiaChi, txtSdt, txtEmail;
    private ModernTextField txtMaSoThue, txtNguoiLienHe, txtGhiChu;

    public NhaCungCapDialog(Frame parent, NhaCungCap ncc) {
        super(parent, true);
        this.nhaCungCap = ncc;
        initComponents();
        setTitle(ncc == null ? "Thêm nhà cung cấp" : "Sửa NCC: " + ncc.getTenNcc());
        setSize(560, 620);
        setLocationRelativeTo(parent);
        if (ncc != null) fillData();
    }

    public boolean isSaved() { return saved; }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppColor.PRIMARY);
        header.setBorder(new EmptyBorder(18, 25, 18, 25));
        JLabel lblTitle = new JLabel(nhaCungCap == null ? "➕ Thêm NCC" : "✏️ Sửa NCC");
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

        txtTen = addField(form, gbc, 0, "Tên NCC *", "");
        txtDiaChi = addField(form, gbc, 1, "Địa chỉ", "");
        txtSdt = addField(form, gbc, 2, "Số điện thoại", "");
        txtEmail = addField(form, gbc, 3, "Email", "");
        txtMaSoThue = addField(form, gbc, 4, "Mã số thuế", "");
        txtNguoiLienHe = addField(form, gbc, 5, "Người liên hệ", "");
        txtGhiChu = addField(form, gbc, 6, "Ghi chú", "");

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
        txtTen.setText(nvl(nhaCungCap.getTenNcc()));
        txtDiaChi.setText(nvl(nhaCungCap.getDiaChi()));
        txtSdt.setText(nvl(nhaCungCap.getSoDienThoai()));
        txtEmail.setText(nvl(nhaCungCap.getEmail()));
        txtMaSoThue.setText(nvl(nhaCungCap.getMaSoThue()));
        txtNguoiLienHe.setText(nvl(nhaCungCap.getNguoiLienHe()));
        txtGhiChu.setText(nvl(nhaCungCap.getGhiChu()));
    }

    private String nvl(String s) { return s == null ? "" : s; }

    private void luu() {
        try {
            NhaCungCap ncc = (nhaCungCap == null) ? new NhaCungCap() : nhaCungCap;
            if (txtTen.getText().trim().isEmpty()) {
                MessageUtil.canhBao(this, "Tên NCC không được để trống!");
                return;
            }
            ncc.setTenNcc(txtTen.getText().trim());
            ncc.setDiaChi(txtDiaChi.getText().trim());
            ncc.setSoDienThoai(txtSdt.getText().trim());
            ncc.setEmail(txtEmail.getText().trim());
            ncc.setMaSoThue(txtMaSoThue.getText().trim());
            ncc.setNguoiLienHe(txtNguoiLienHe.getText().trim());
            ncc.setGhiChu(txtGhiChu.getText().trim());

            boolean kq = (nhaCungCap == null) ? bus.them(ncc) : bus.sua(ncc);
            if (kq) {
                MessageUtil.thongBao(this, nhaCungCap == null ? "Thêm thành công!" : "Cập nhật thành công!");
                saved = true;
                dispose();
            } else {
                MessageUtil.loi(this, "Lưu thất bại!");
            }
        } catch (Exception ex) {
            MessageUtil.loi(this, "Lỗi: " + ex.getMessage());
        }
    }
}