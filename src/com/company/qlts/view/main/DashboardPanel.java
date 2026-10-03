package com.company.qlts.view.main;

import com.company.qlts.common.AppColor;
import com.company.qlts.component.RoundedPanel;
import com.company.qlts.component.StatCard;
import com.company.qlts.dao.ThongKeDAO;
import com.company.qlts.util.UiUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setBackground(AppColor.BG_MAIN);
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        // Tiêu đề
        JLabel lblTitle = new JLabel("📊 Tổng quan hệ thống");
        lblTitle.setFont(UiUtil.bold(24));
        lblTitle.setForeground(AppColor.TEXT_PRIMARY);
        add(lblTitle, BorderLayout.NORTH);

        // Body
        JPanel body = new JPanel(new BorderLayout(20, 20));
        body.setOpaque(false);

        // 4 thẻ stat
        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        cardsPanel.setOpaque(false);
        cardsPanel.setPreferredSize(new Dimension(0, 130));

        ThongKeDAO dao = new ThongKeDAO();
        int tongTS = dao.demTaiSan();
        int daCap = dao.demTaiSanTheoTrangThai("Đã cấp phát");
        int dangBT = dao.demTaiSanTheoTrangThai("Đang bảo trì");
        double tongGiaTri = dao.tongGiaTriTaiSan();

        cardsPanel.add(new StatCard("Tổng tài sản", String.valueOf(tongTS),
                "📦", AppColor.PRIMARY));
        cardsPanel.add(new StatCard("Đã cấp phát", String.valueOf(daCap),
                "📤", AppColor.WARNING));
        cardsPanel.add(new StatCard("Đang bảo trì", String.valueOf(dangBT),
                "🔧", AppColor.DANGER));
        cardsPanel.add(new StatCard("Tổng giá trị",
                String.format("%,.0f", tongGiaTri / 1_000_000) + " tr",
                "💰", AppColor.SUCCESS));

        body.add(cardsPanel, BorderLayout.NORTH);

        // Biểu đồ đơn giản
        RoundedPanel chartPanel = new RoundedPanel(14, Color.WHITE);
        chartPanel.setLayout(new BorderLayout());
        chartPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel lblChartTitle = new JLabel("📈 Tài sản theo phòng ban");
        lblChartTitle.setFont(UiUtil.bold(16));
        lblChartTitle.setForeground(AppColor.TEXT_PRIMARY);
        chartPanel.add(lblChartTitle, BorderLayout.NORTH);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 15));
        area.setBackground(Color.WHITE);
        area.setForeground(AppColor.TEXT_PRIMARY);
        area.setBorder(new EmptyBorder(15, 0, 0, 0));
        area.setText(
                "  Kỹ thuật      ████████████████████  50\n\n" +
                "  Hành chính    ██████████████        35\n\n" +
                "  Kế toán       ██████████            25\n\n" +
                "  Nhân sự       ██████                15\n"
        );
        chartPanel.add(area, BorderLayout.CENTER);

        body.add(chartPanel, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);
    }
}