package com.company.qlts.view.login;

import com.company.qlts.bus.TaiKhoanBUS;
import com.company.qlts.common.AppColor;
import com.company.qlts.common.Session;
import com.company.qlts.component.ModernButton;
import com.company.qlts.component.ModernPasswordField;
import com.company.qlts.component.ModernTextField;
import com.company.qlts.entity.TaiKhoan;
import com.company.qlts.util.MessageUtil;
import com.company.qlts.util.UiUtil;
import com.company.qlts.view.main.MainFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame {

    private ModernTextField txtTenDangNhap;
    private ModernPasswordField txtMatKhau;
    private ModernButton btnDangNhap;
    private ModernButton btnThoat;

    public LoginFrame() {
        initComponents();
        setTitle("Đăng nhập - Quản lý tài sản");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // ============ PANEL TRÁI: BANNER GRADIENT ============
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient từ xanh đậm sang xanh tím
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(0x1E3A8A),
                        getWidth(), getHeight(), new Color(0x7C3AED));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Vẽ các vòng tròn trang trí
                g2.setColor(new Color(255, 255, 255, 20));
                g2.fillOval(-50, -50, 250, 250);
                g2.fillOval(getWidth() - 150, getHeight() - 150, 300, 300);
                g2.setColor(new Color(255, 255, 255, 15));
                g2.fillOval(50, getHeight() - 100, 180, 180);

                g2.dispose();

                // Text
                Graphics2D g3 = (Graphics2D) g.create();
                g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g3.setColor(Color.WHITE);
                g3.setFont(UiUtil.bold(36));
                g3.drawString("QUẢN LÝ", 50, 200);
                g3.drawString("TÀI SẢN &", 50, 250);
                g3.drawString("THIẾT BỊ", 50, 300);

                g3.setFont(UiUtil.regular(14));
                g3.setColor(new Color(255, 255, 255, 200));
                g3.drawString("Hệ thống quản lý tài sản doanh nghiệp", 50, 350);

                g3.setColor(new Color(255, 255, 255, 150));
                g3.setFont(UiUtil.regular(12));
                g3.drawString("Phiên bản 1.0 - Desktop Edition", 50, 500);

                g3.dispose();
            }
        };
        leftPanel.setPreferredSize(new Dimension(420, 0));
        leftPanel.setLayout(null);
        add(leftPanel, BorderLayout.WEST);

        // ============ PANEL PHẢI: FORM ============
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(40, 60, 40, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        // Tiêu đề
        JLabel lblTitle = new JLabel("Đăng nhập");
        lblTitle.setFont(UiUtil.bold(30));
        lblTitle.setForeground(AppColor.TEXT_PRIMARY);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 0);
        rightPanel.add(lblTitle, gbc);

        // Subtitle
        JLabel lblSub = new JLabel("Vui lòng đăng nhập để tiếp tục");
        lblSub.setFont(UiUtil.regular(13));
        lblSub.setForeground(AppColor.TEXT_SECONDARY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 35, 0);
        rightPanel.add(lblSub, gbc);

        // Label user
        JLabel lblUser = new JLabel("Tên đăng nhập");
        lblUser.setFont(UiUtil.bold(12));
        lblUser.setForeground(AppColor.TEXT_PRIMARY);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 6, 0);
        rightPanel.add(lblUser, gbc);

        // Ô nhập user
        txtTenDangNhap = new ModernTextField("Nhập tên đăng nhập");
        txtTenDangNhap.setPreferredSize(new Dimension(0, 45));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 18, 0);
        rightPanel.add(txtTenDangNhap, gbc);

        // Label pass
        JLabel lblPass = new JLabel("Mật khẩu");
        lblPass.setFont(UiUtil.bold(12));
        lblPass.setForeground(AppColor.TEXT_PRIMARY);
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 6, 0);
        rightPanel.add(lblPass, gbc);

        // Ô nhập pass
        txtMatKhau = new ModernPasswordField("Nhập mật khẩu");
        txtMatKhau.setPreferredSize(new Dimension(0, 45));
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 30, 0);
        rightPanel.add(txtMatKhau, gbc);

        // Nút đăng nhập
        btnDangNhap = new ModernButton("ĐĂNG NHẬP", AppColor.PRIMARY);
        btnDangNhap.setPreferredSize(new Dimension(0, 48));
        btnDangNhap.setFont(UiUtil.bold(14));
        btnDangNhap.addActionListener(e -> xuLyDangNhap());
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 10, 0);
        rightPanel.add(btnDangNhap, gbc);

        // Nút thoát
        btnThoat = new ModernButton("Thoát", new Color(0xE2E8F0));
        btnThoat.setForeground(AppColor.TEXT_SECONDARY);
        btnThoat.setPreferredSize(new Dimension(0, 38));
        btnThoat.setFont(UiUtil.regular(12));
        btnThoat.addActionListener(e -> System.exit(0));
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 0, 0);
        rightPanel.add(btnThoat, gbc);

        add(rightPanel, BorderLayout.CENTER);

        // Enter để đăng nhập
        getRootPane().setDefaultButton(btnDangNhap);
    }

    private void xuLyDangNhap() {
        String user = txtTenDangNhap.getText().trim();
        String pass = new String(txtMatKhau.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            MessageUtil.canhBao(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        TaiKhoanBUS bus = new TaiKhoanBUS();
        TaiKhoan tk = bus.checkLogin(user, pass);

        if (tk != null) {
            Session.currentUser = tk;
            MessageUtil.thongBao(this, "Xin chào " + tk.getHoTen() + "!");
            this.dispose();
            SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
        } else {
            MessageUtil.loi(this, "Sai tên đăng nhập hoặc mật khẩu!");
            txtMatKhau.setText("");
            txtMatKhau.requestFocus();
        }
    }
}