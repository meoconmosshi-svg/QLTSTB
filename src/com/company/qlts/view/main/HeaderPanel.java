package com.company.qlts.view.main;

import com.company.qlts.common.AppColor;
import com.company.qlts.common.Session;
import com.company.qlts.util.UiUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HeaderPanel extends JPanel {

    public HeaderPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(0, 60));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColor.BORDER));

        // Bên trái: breadcrumb
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 18));
        leftPanel.setOpaque(false);

        JLabel lblBread = new JLabel("🏠  Trang chủ");
        lblBread.setFont(UiUtil.regular(14));
        lblBread.setForeground(AppColor.TEXT_SECONDARY);
        leftPanel.add(lblBread);

        add(leftPanel, BorderLayout.WEST);

        // Bên phải: user info
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 22, 15));
        rightPanel.setOpaque(false);

        JLabel lblBell = new JLabel("🔔");
        lblBell.setFont(UiUtil.regular(20));
        lblBell.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rightPanel.add(lblBell);

        JLabel lblUser = new JLabel("👤  " + Session.currentUser.getHoTen());
        lblUser.setFont(UiUtil.bold(14));
        lblUser.setForeground(AppColor.TEXT_PRIMARY);
        rightPanel.add(lblUser);

        JLabel lblArrow = new JLabel("▾");
        lblArrow.setFont(UiUtil.regular(12));
        lblArrow.setForeground(AppColor.TEXT_SECONDARY);
        rightPanel.add(lblArrow);

        add(rightPanel, BorderLayout.EAST);
    }
}