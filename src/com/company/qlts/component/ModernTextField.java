package com.company.qlts.component;

import com.company.qlts.common.AppColor;
import com.company.qlts.util.UiUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;

public class ModernTextField extends JTextField {

    private String placeholder = "";
    private int radius = 8;

    public ModernTextField() {
        this("");
    }

    public ModernTextField(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(false);
        setFont(UiUtil.regular(14));
        setForeground(AppColor.TEXT_PRIMARY);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 14, 10, 14));
        setCaretColor(AppColor.PRIMARY);
    }

    public void setPlaceholder(String p) { this.placeholder = p; repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Nền
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        // Viền
        g2.setColor(hasFocus() ? AppColor.PRIMARY : AppColor.BORDER);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        g2.dispose();

        super.paintComponent(g);

        // Placeholder
        if (getText().isEmpty() && !hasFocus() && !placeholder.isEmpty()) {
            Graphics2D g3 = (Graphics2D) g.create();
            g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g3.setColor(AppColor.TEXT_MUTED);
            g3.setFont(getFont());
            g3.drawString(placeholder, 15, getHeight() / 2 + 5);
            g3.dispose();
        }
    }
}