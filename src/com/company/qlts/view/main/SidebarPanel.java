package com.company.qlts.view.main;

import com.company.qlts.common.AppColor;
import com.company.qlts.common.Session;
import com.company.qlts.util.UiUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class SidebarPanel extends JPanel {

    private MainFrame parent;
    private JPanel activeItem = null;
    private final List<JPanel> allItems = new ArrayList<>();

    public SidebarPanel(MainFrame parent) {
        this.parent = parent;
        setPreferredSize(new Dimension(240, 0));
        setBackground(AppColor.BG_SIDEBAR);
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        // Logo
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(AppColor.BG_SIDEBAR);
        logoPanel.setBorder(new EmptyBorder(22, 22, 22, 22));
        logoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JLabel lblLogo = new JLabel("🏢 QLTS");
        lblLogo.setFont(UiUtil.bold(22));
        lblLogo.setForeground(Color.WHITE);
        logoPanel.add(lblLogo, BorderLayout.WEST);
        add(logoPanel);

        // Separator
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0x334155));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        add(sep);

        // Các menu
        addItem("📊", "Tổng quan", () -> parent.setContentPanel(new DashboardPanel()));

addItem("📦", "Tài sản", () -> parent.setContentPanel(
        new com.company.qlts.view.taissan.TaiSanPanel()));

addItem("🖥️", "Thiết bị", () -> parent.setContentPanel(
        new com.company.qlts.view.taissan.ThietBiPanel()));

addItem("👥", "Nhân viên", () -> parent.setContentPanel(
        new com.company.qlts.view.taissan.NhanVienPanel()));

addItem("🏢", "Phòng ban", () -> parent.setContentPanel(
        new com.company.qlts.view.taissan.PhongBanPanel()));

addItem("🚚", "Nhà cung cấp", () -> parent.setContentPanel(
        new com.company.qlts.view.taissan.NhaCungCapPanel()));

addItem("🏷️", "Loại tài sản", () -> parent.setContentPanel(
        new com.company.qlts.view.taissan.LoaiTaiSanPanel()));

        addSpacer();

        addItem("📋", "Cấp phát", () -> showChuaLam());
        addItem("🔄", "Điều chuyển", () -> showChuaLam());
        addItem("🔧", "Bảo trì", () -> showChuaLam());
        addItem("💰", "Khấu hao", () -> showChuaLam());
        addItem("📝", "Kiểm kê", () -> showChuaLam());
        addItem("🗑️", "Thanh lý", () -> showChuaLam());

        addSpacer();

        addItem("📈", "Báo cáo", () -> showChuaLam());
        addItem("📜", "Nhật ký", () -> showChuaLam());

        // Đăng xuất
        addItem("🚪", "Đăng xuất", this::dangXuat);
    }

    private void addSpacer() {
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 15));
        add(spacer);
    }

    private void addItem(String icon, String text, Runnable action) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(AppColor.BG_SIDEBAR);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        item.setMinimumSize(new Dimension(0, 42));
        item.setPreferredSize(new Dimension(0, 42));
        item.setBorder(new EmptyBorder(0, 22, 0, 22));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lbl = new JLabel(icon + "   " + text);
        lbl.setFont(UiUtil.regular(14));
        lbl.setForeground(new Color(0xCBD5E1));
        item.add(lbl, BorderLayout.WEST);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (item != activeItem) {
                    item.setBackground(AppColor.BG_SIDEBAR_HOVER);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (item != activeItem) {
                    item.setBackground(AppColor.BG_SIDEBAR);
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                setActive(item, lbl);
                action.run();
            }
        });

        add(item);
        allItems.add(item);
    }

    private void setActive(JPanel item, JLabel lbl) {
        if (activeItem != null) {
            activeItem.setBackground(AppColor.BG_SIDEBAR);
            ((JLabel) activeItem.getComponent(0)).setForeground(new Color(0xCBD5E1));
        }
        item.setBackground(AppColor.BG_SIDEBAR_ACTIVE);
        lbl.setForeground(Color.WHITE);
        activeItem = item;
    }

    private void showChuaLam() {
        JOptionPane.showMessageDialog(this, "Chức năng này chưa làm!",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void dangXuat() {
        int chon = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn đăng xuất?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (chon == JOptionPane.YES_OPTION) {
            Session.currentUser = null;
            SwingUtilities.getWindowAncestor(this).dispose();
            new com.company.qlts.view.login.LoginFrame().setVisible(true);
        }
    }
}
