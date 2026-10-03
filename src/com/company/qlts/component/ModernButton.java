package com.company.qlts.component;

import com.company.qlts.common.AppColor;
import com.company.qlts.util.UiUtil;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class ModernButton extends JButton {

    private Color bgColor;
    private Color hoverColor;
    private Color pressColor;
    private int radius = 8;
    private boolean isHover = false;
    private boolean isPress = false;

    public ModernButton(String text) {
        this(text, AppColor.PRIMARY);
    }

    public ModernButton(String text, Color bg) {
        super(text);
        this.bgColor = bg;
        this.hoverColor = darken(bg, 0.1f);
        this.pressColor = darken(bg, 0.2f);

        setForeground(Color.WHITE);
        setFont(UiUtil.bold(13));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { isHover = true;  repaint(); }
            @Override public void mouseExited(MouseEvent e)  { isHover = false; repaint(); }
            @Override public void mousePressed(MouseEvent e) { isPress = true;  repaint(); }
            @Override public void mouseReleased(MouseEvent e){ isPress = false; repaint(); }
        });
    }

    public void setRadius(int r) { this.radius = r; repaint(); }

    public void setBgColor(Color c) {
        this.bgColor = c;
        this.hoverColor = darken(c, 0.1f);
        this.pressColor = darken(c, 0.2f);
        repaint();
    }

    private Color darken(Color c, float amount) {
        int r = Math.max(0, (int)(c.getRed()   * (1 - amount)));
        int g = Math.max(0, (int)(c.getGreen() * (1 - amount)));
        int b = Math.max(0, (int)(c.getBlue()  * (1 - amount)));
        return new Color(r, g, b);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color fill = isPress ? pressColor : (isHover ? hoverColor : bgColor);
        g2.setColor(fill);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();

        super.paintComponent(g);
    }
}