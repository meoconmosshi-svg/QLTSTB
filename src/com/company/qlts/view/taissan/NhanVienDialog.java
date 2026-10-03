package com.company.qlts.view.taissan;

import com.company.qlts.bus.NhanVienBUS;
import com.company.qlts.bus.PhongBanBUS;
import com.company.qlts.common.AppColor;
import com.company.qlts.component.ModernButton;
import com.company.qlts.component.ModernTextField;
import com.company.qlts.entity.NhanVien;
import com.company.qlts.entity.PhongBan;
import com.company.qlts.util.MessageUtil;
import com.company.qlts.util.UiUtil;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NhanVienDialog extends JDialog {

    private final NhanVienBUS bus = new NhanVienBUS();
    private final PhongBanBUS pbBus = new PhongBanBUS();
    private NhanVien nhanVien;
    private boolean saved = false;

    private ModernTextField txtHoTen, txtCmnd, txtChucVu, txtEmail, txtSdt, txtDiaChi;
    private ModernTextField txtNgaySinh, txtNgayVaoLam;
    private JComboBox<String> cboGioiTinh;
    private JComboBox<PhongBan> cboPhongBan;
    private JComboBox<String> cboTrangThai;

    public NhanVienDialog(Frame parent, NhanVien nv) {
        super(parent, true);
        this.nhanVien = nv;
        initComponents();
        setTitle(nv == null ? "Thêm nhân viên" : "Sửa nhân viên: " + nv.getMaNvCode());
        setSize(600, 750);
        setLocationRelativeTo(parent);
        if (nv != null) fillData();
    }

    public boolean isSaved() { return saved; }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppColor.PRIMARY);
        header.setBorder(new EmptyBorder(18, 25, 18, 25));
        JLabel lblTitle = new JLabel(nhanVien == null ? "➕ Thêm nhân viên" : "✏️ Sửa nhân viên");
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

        int row = 0;
        txtHoTen = addField(form, gbc, row++, "Họ tên *", "");
        cboGioiTinh = addCombo(form, gbc, row++, "Giới tính", new String[]{"Nam", "Nữ", "Khác"});
        txtNgaySinh = addField(form, gbc, row++, "Ngày sinh (yyyy-MM-dd)", "");
        txtCmnd = addField(form, gbc, row++, "CMND/CCCD", "");
        txtChucVu = addField(form, gbc, row++, "Chức vụ", "");
        txtEmail = addField(form, gbc, row++, "Email", "");
        txtSdt = addField(form, gbc, row++, "Số điện thoại", "");
        txtDiaChi = addField(form, gbc, row++, "Địa chỉ", "");

        // Combo phòng ban
        JLabel lblPb = new JLabel("Phòng ban");
        lblPb.setFont(UiUtil.bold(12));
        lblPb.setForeground(AppColor.TEXT_PRIMARY);
        gbc.gridy = row * 2;
        form.add(lblPb, gbc);

        cboPhongBan = new JComboBox<>();
        cboPhongBan.setPreferredSize(new Dimension(0, 40));
        cboPhongBan.setFont(UiUtil.regular(13));
        List<PhongBan> listPb = pbBus.getAll();
        for (PhongBan pb : listPb) cboPhongBan.addItem(pb);
        gbc.gridy = row * 2 + 1;
        form.add(cboPhongBan, gbc);
        row++;

        txtNgayVaoLam = addField(form, gbc, row++, "Ngày vào làm (yyyy-MM-dd)", "");
        cboTrangThai = addCombo(form, gbc, row++, "Trạng thái",
                new String[]{"Đang làm", "Nghỉ việc"});

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

    private JComboBox<String> addCombo(JPanel form, GridBagConstraints gbc, int row,
            String label, String[] items) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(UiUtil.bold(12));
        lbl.setForeground(AppColor.TEXT_PRIMARY);
        gbc.gridy = row * 2;
        form.add(lbl, gbc);

        JComboBox<String> cbo = new JComboBox<>(items);
        cbo.setPreferredSize(new Dimension(0, 38));
        cbo.setFont(UiUtil.regular(13));
        gbc.gridy = row * 2 + 1;
        form.add(cbo, gbc);
        return cbo;
    }

    private void fillData() {
        txtHoTen.setText(nvl(nhanVien.getHoTen()));
        cboGioiTinh.setSelectedItem(nhanVien.getGioiTinh() == null ? "Nam" : nhanVien.getGioiTinh());
        txtNgaySinh.setText(nhanVien.getNgaySinh() == null ? "" : nhanVien.getNgaySinh().toString());
        txtCmnd.setText(nvl(nhanVien.getCmndCccd()));
        txtChucVu.setText(nvl(nhanVien.getChucVu()));
        txtEmail.setText(nvl(nhanVien.getEmail()));
        txtSdt.setText(nvl(nhanVien.getSoDienThoai()));
        txtDiaChi.setText(nvl(nhanVien.getDiaChi()));
        txtNgayVaoLam.setText(nhanVien.getNgayVaoLam() == null ? "" : nhanVien.getNgayVaoLam().toString());
        cboTrangThai.setSelectedItem(nhanVien.getTrangThai() == 1 ? "Đang làm" : "Nghỉ việc");

        // Chọn phòng ban theo mã
        for (int i = 0; i < cboPhongBan.getItemCount(); i++) {
            PhongBan pb = cboPhongBan.getItemAt(i);
            if (pb.getMaPhongBan() == nhanVien.getMaPhongBan()) {
                cboPhongBan.setSelectedIndex(i);
                break;
            }
        }
    }

    private String nvl(String s) { return s == null ? "" : s; }

    private void luu() {
        try {
            NhanVien nv = (nhanVien == null) ? new NhanVien() : nhanVien;
            if (txtHoTen.getText().trim().isEmpty()) {
                MessageUtil.canhBao(this, "Họ tên không được để trống!");
                return;
            }
            nv.setHoTen(txtHoTen.getText().trim());
            nv.setGioiTinh((String) cboGioiTinh.getSelectedItem());
            nv.setNgaySinh(parseDateSafe(txtNgaySinh.getText()));
            nv.setCmndCccd(txtCmnd.getText().trim());
            nv.setChucVu(txtChucVu.getText().trim());
            nv.setEmail(txtEmail.getText().trim());
            nv.setSoDienThoai(txtSdt.getText().trim());
            nv.setDiaChi(txtDiaChi.getText().trim());

            PhongBan pbSelected = (PhongBan) cboPhongBan.getSelectedItem();
            if (pbSelected != null) nv.setMaPhongBan(pbSelected.getMaPhongBan());

            nv.setNgayVaoLam(parseDateSafe(txtNgayVaoLam.getText()));
            nv.setTrangThai("Đang làm".equals(cboTrangThai.getSelectedItem()) ? 1 : 0);

            boolean kq = (nhanVien == null) ? bus.them(nv) : bus.sua(nv);
            if (kq) {
                MessageUtil.thongBao(this, nhanVien == null ? "Thêm thành công!" : "Cập nhật thành công!");
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