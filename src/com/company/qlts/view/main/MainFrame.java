package com.company.qlts.view.main;

import com.company.qlts.common.AppColor;
import com.company.qlts.common.Session;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

    private JPanel contentPanel;

    public MainFrame() {
        initComponents();
        setTitle("Quản lý Tài sản & Thiết bị - " + Session.currentUser.getHoTen());
        setSize(1400, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Header
        add(new HeaderPanel(), BorderLayout.NORTH);

        // Sidebar
        add(new SidebarPanel(this), BorderLayout.WEST);

        // Content
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(AppColor.BG_MAIN);
        contentPanel.add(new DashboardPanel(), BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
    }

    public void setContentPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}