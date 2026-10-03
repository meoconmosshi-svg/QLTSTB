package com.company.qlts.view.taissan;

import com.company.qlts.bus.PhongBanBUS;
import com.company.qlts.common.AppColor;
import com.company.qlts.component.ModernButton;
import com.company.qlts.component.ModernTextField;
import com.company.qlts.entity.PhongBan;
import com.company.qlts.util.MessageUtil;
import com.company.qlts.util.UiUtil;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class PhongBanPanel extends JPanel {

    private final PhongBanBUS bus = new PhongBanBUS();
    private JTable table;
    private DefaultTableModel model;
    private ModernTextField txtTimKiem;
    private JLabel lblTong;

    public PhongBanPanel() {
        setBackground(AppColor.BG_MAIN);
        setLayout(new BorderLayout(0, 15));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel lblTitle = new JLabel("🏢 Quản lý Phòng ban");
        lblTitle.setFont(UiUtil.bold(24));
        lblTitle.setForeground(AppColor.TEXT_PRIMARY);
        add(lblTitle, BorderLayout.NORTH);

        // Toolbar
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);

        JPanel leftTool = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftTool.setOpaque(false);
        txtTimKiem = new ModernTextField("🔍 Tìm theo tên phòng...");
        txtTimKiem.setPreferredSize(new Dimension(300, 40));
        txtTimKiem.addActionListener(e -> timKiem());
        leftTool.add(txtTimKiem);

        ModernButton btnTim = new ModernButton("Tìm", AppColor.INFO);
        btnTim.setPreferredSize(new Dimension(90, 40));
        btnTim.addActionListener(e -> timKiem());
        leftTool.add(btnTim);

        ModernButton btnRefresh = new ModernButton("🔄", AppColor.TEXT_SECONDARY);
        btnRefresh.setPreferredSize(new Dimension(50, 40));
        btnRefresh.addActionListener(e -> { txtTimKiem.setText(""); loadData(); });
        leftTool.add(btnRefresh);
        toolbar.add(leftTool, BorderLayout.WEST);

        JPanel rightTool = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightTool.setOpaque(false);
        ModernButton btnThem = new ModernButton("+ Thêm phòng ban", AppColor.SUCCESS);
        btnThem.setPreferredSize(new Dimension(180, 40));
        btnThem.addActionListener(e -> them());
        rightTool.add(btnThem);
        toolbar.add(rightTool, BorderLayout.EAST);

        // Table
        String[] cols = {"ID", "Mã PB", "Tên phòng ban", "Địa điểm", "Ngày thành lập"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setFont(UiUtil.regular(13));
        table.setRowHeight(38);
        table.setGridColor(AppColor.BORDER);
        table.setSelectionBackground(AppColor.PRIMARY_LIGHT);
        table.setSelectionForeground(AppColor.TEXT_PRIMARY);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSorter(new TableRowSorter<>(model));

        table.getTableHeader().setFont(UiUtil.bold(13));
        table.getTableHeader().setBackground(AppColor.BG_MAIN);
        table.getTableHeader().setPreferredSize(new Dimension(0, 42));
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColor.BORDER));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(1).setCellRenderer(center);

        table.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() >= 0) sua();
            }
        });
        table.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) xoa();
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(AppColor.BORDER, 1));
        scroll.getViewport().setBackground(Color.WHITE);

        // Bottom
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        lblTong = new JLabel("Tổng: 0 phòng ban");
        lblTong.setFont(UiUtil.regular(13));
        lblTong.setForeground(AppColor.TEXT_SECONDARY);
        JPanel leftB = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        leftB.setOpaque(false);
        leftB.add(lblTong);
        bottom.add(leftB, BorderLayout.WEST);

        JPanel rightB = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightB.setOpaque(false);
        ModernButton btnSua = new ModernButton("✏️ Sửa", AppColor.WARNING);
        btnSua.setPreferredSize(new Dimension(100, 38));
        btnSua.addActionListener(e -> sua());
        rightB.add(btnSua);

        ModernButton btnXoa = new ModernButton("🗑️ Xóa", AppColor.DANGER);
        btnXoa.setPreferredSize(new Dimension(100, 38));
        btnXoa.addActionListener(e -> xoa());
        rightB.add(btnXoa);
        bottom.add(rightB, BorderLayout.EAST);
        
        add(toolbar, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        for (PhongBan pb : bus.getAll()) {
            model.addRow(new Object[]{
                pb.getMaPhongBan(),
                pb.getMaPhongBanCode() == null ? "" : pb.getMaPhongBanCode(),
                pb.getTenPhongBan() == null ? "" : pb.getTenPhongBan(),
                pb.getDiaDiem() == null ? "" : pb.getDiaDiem(),
                pb.getNgayThanhLap() == null ? "" : pb.getNgayThanhLap().toString()
            });
        }
        lblTong.setText("Tổng: " + model.getRowCount() + " phòng ban");
    }

    private void timKiem() {
        String kw = txtTimKiem.getText().trim().toLowerCase();
        if (kw.isEmpty()) { loadData(); return; }
        model.setRowCount(0);
        for (PhongBan pb : bus.getAll()) {
            String ten = pb.getTenPhongBan() == null ? "" : pb.getTenPhongBan().toLowerCase();
            if (ten.contains(kw)) {
                model.addRow(new Object[]{
                    pb.getMaPhongBan(), pb.getMaPhongBanCode(),
                    pb.getTenPhongBan(), pb.getDiaDiem(),
                    pb.getNgayThanhLap() == null ? "" : pb.getNgayThanhLap().toString()
                });
            }
        }
        lblTong.setText("Tổng: " + model.getRowCount() + " phòng ban");
    }

    private void them() {
        Window p = SwingUtilities.getWindowAncestor(this);
        PhongBanDialog dlg = new PhongBanDialog(
            p instanceof Frame ? (Frame) p : null, null);
        dlg.setVisible(true);
        if (dlg.isSaved()) loadData();
    }

    private void sua() {
        int row = table.getSelectedRow();
        if (row < 0) { MessageUtil.canhBao(this, "Vui lòng chọn 1 dòng!"); return; }
        int modelRow = table.convertRowIndexToModel(row);
        int id = (int) model.getValueAt(modelRow, 0);
        PhongBan pb = bus.findById(id);
        if (pb == null) { MessageUtil.loi(this, "Không tìm thấy!"); return; }
        Window p = SwingUtilities.getWindowAncestor(this);
        PhongBanDialog dlg = new PhongBanDialog(
            p instanceof Frame ? (Frame) p : null, pb);
        dlg.setVisible(true);
        if (dlg.isSaved()) loadData();
    }

    private void xoa() {
        int row = table.getSelectedRow();
        if (row < 0) { MessageUtil.canhBao(this, "Vui lòng chọn 1 dòng!"); return; }
        int modelRow = table.convertRowIndexToModel(row);
        int id = (int) model.getValueAt(modelRow, 0);
        String ten = String.valueOf(model.getValueAt(modelRow, 2));
        if (MessageUtil.xacNhan(this, "Xóa phòng ban: " + ten + " ?")) {
            if (bus.xoa(id)) {
                MessageUtil.thongBao(this, "Xóa thành công!");
                loadData();
            } else {
                MessageUtil.loi(this, "Không thể xóa! Phòng ban có thể còn nhân viên.");
            }
        }
    }
}