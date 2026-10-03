package com.company.qlts.component;

import com.company.qlts.common.AppColor;
import com.company.qlts.util.UiUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class StatCard extends JPanel {

    private Color accentColor;

    public StatCard(String title, String value, String icon, Color accent) {
        this.accentColor = accent;
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(18, 20, 18, 20));

        // Top: icon + title
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(UiUtil.regular(28));
        lblIcon.setForeground(accent);
        top.add(lblIcon, BorderLayout.WEST);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(UiUtil.regular(12));
        lblTitle.setForeground(AppColor.TEXT_SECONDARY);
        lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
        top.add(lblTitle, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        // Value
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(UiUtil.bold(30));
        lblValue.setForeground(AppColor.TEXT_PRIMARY);
        add(lblValue, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Nền trắng bo tròn
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);

        // Viền trái màu accent
        g2.setColor(accentColor);
        g2.fillRoundRect(0, 0, 5, getHeight(), 4, 4);
        g2.fillRect(0, 4, 8, getHeight() - 8);

        // Viền bao quanh
        g2.setColor(AppColor.BORDER);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);

        g2.dispose();
    }
}