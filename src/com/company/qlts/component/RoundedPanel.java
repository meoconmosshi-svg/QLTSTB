package com.company.qlts.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {
    private int radius = 12;
    private Color bgColor = Color.WHITE;

    public RoundedPanel() {
        setOpaque(false);
    }

    public RoundedPanel(int radius, Color bg) {
        this.radius = radius;
        this.bgColor = bg;
        setOpaque(false);
    }

    public void setBgColor(Color c) {
        this.bgColor = c;
        repaint();
    }

    public void setRadius(int r) {
        this.radius = r;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        super.paintComponent(g);
    }
}