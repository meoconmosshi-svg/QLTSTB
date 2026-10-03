package com.company.qlts.view.taissan;

import com.company.qlts.bus.TaiSanBUS;
import com.company.qlts.common.AppColor;
import com.company.qlts.component.ModernButton;
import com.company.qlts.component.ModernTextField;
import com.company.qlts.entity.TaiSan;
import com.company.qlts.util.MessageUtil;
import com.company.qlts.util.UiUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;  
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class TaiSanPanel extends JPanel {

    private final TaiSanBUS bus = new TaiSanBUS();
    private JTable table;
    private DefaultTableModel model;
    private ModernTextField txtTimKiem;
    private JLabel lblTong;

    public TaiSanPanel() {
        setBackground(AppColor.BG_MAIN);
        setLayout(new BorderLayout(0, 15));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        // ===== TIÊU ĐỀ =====
        JLabel lblTitle = new JLabel("📦 Quản lý Tài sản");
        lblTitle.setFont(UiUtil.bold(24));
        lblTitle.setForeground(AppColor.TEXT_PRIMARY);
        add(lblTitle, BorderLayout.NORTH);

        // ===== TOOLBAR =====
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);

        // --- Bên trái: tìm kiếm ---
        JPanel leftTool = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftTool.setOpaque(false);

        txtTimKiem = new ModernTextField("🔍 Tìm theo mã hoặc tên...");
        txtTimKiem.setPreferredSize(new Dimension(300, 40));
        txtTimKiem.addActionListener(e -> timKiem());
        leftTool.add(txtTimKiem);

        ModernButton btnTim = new ModernButton("Tìm", AppColor.INFO);
        btnTim.setPreferredSize(new Dimension(90, 40));
        btnTim.addActionListener(e -> timKiem());
        leftTool.add(btnTim);

        ModernButton btnRefresh = new ModernButton("🔄", AppColor.TEXT_SECONDARY);
        btnRefresh.setPreferredSize(new Dimension(50, 40));
        btnRefresh.addActionListener(e -> {
            txtTimKiem.setText("");
            loadData();
        });
        leftTool.add(btnRefresh);

        toolbar.add(leftTool, BorderLayout.WEST);

        // --- Bên phải: nút thêm ---
        JPanel rightTool = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightTool.setOpaque(false);

        ModernButton btnThem = new ModernButton("+ Thêm tài sản", AppColor.SUCCESS);
        btnThem.setPreferredSize(new Dimension(160, 40));
        btnThem.addActionListener(e -> them());
        rightTool.add(btnThem);

        toolbar.add(rightTool, BorderLayout.EAST);

        // ===== TABLE =====
        String[] cols = {"ID", "Mã TS", "Tên tài sản", "Mã loại", "Mã NCC",
                         "Giá mua", "Tình trạng", "Trạng thái", "Vị trí"};

        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
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

        // Sortable
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Header style
        table.getTableHeader().setFont(UiUtil.bold(13));
        table.getTableHeader().setBackground(AppColor.BG_MAIN);
        table.getTableHeader().setForeground(AppColor.TEXT_PRIMARY);
        table.getTableHeader().setPreferredSize(new Dimension(0, 42));
        table.getTableHeader().setBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, AppColor.BORDER));

        // Độ rộng cột
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(230);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);

        // Căn giữa cột ID và Mã
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(1).setCellRenderer(center);
        table.getColumnModel().getColumn(3).setCellRenderer(center);
        table.getColumnModel().getColumn(4).setCellRenderer(center);

        // Căn phải cột Giá mua
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(5).setCellRenderer(right);

        // Double-click để sửa
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() >= 0) {
                    sua();
                }
            }
        });

        // Phím Delete để xóa
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    xoa();
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(AppColor.BORDER, 1));
        scroll.getViewport().setBackground(Color.WHITE);

        // ===== BOTTOM: actions =====
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        // Đếm số dòng bên trái
        lblTong = new JLabel("Tổng: 0 tài sản");
        lblTong.setFont(UiUtil.regular(13));
        lblTong.setForeground(AppColor.TEXT_SECONDARY);
        JPanel leftBottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        leftBottom.setOpaque(false);
        leftBottom.add(lblTong);
        bottom.add(leftBottom, BorderLayout.WEST);

        // Nút sửa/xóa bên phải
        JPanel rightBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightBottom.setOpaque(false);

        ModernButton btnSua = new ModernButton("✏️ Sửa", AppColor.WARNING);
        btnSua.setPreferredSize(new Dimension(100, 38));
        btnSua.addActionListener(e -> sua());
        rightBottom.add(btnSua);

        ModernButton btnXoa = new ModernButton("🗑️ Xóa", AppColor.DANGER);
        btnXoa.setPreferredSize(new Dimension(100, 38));
        btnXoa.addActionListener(e -> xoa());
        rightBottom.add(btnXoa);

        bottom.add(rightBottom, BorderLayout.EAST);

        // ===== CENTER =====
        add(toolbar, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        // Load dữ liệu lần đầu
        loadData();
    }

    // ============================================================
    // LOAD DATA
    // ============================================================
    private void loadData() {
        model.setRowCount(0);
        List<TaiSan> list = bus.getAll();
        for (TaiSan ts : list) {
            model.addRow(toRow(ts));
        }
        capNhatTong();
    }

    private void capNhatTong() {
        int soDong = model.getRowCount();
        lblTong.setText("Tổng: " + soDong + " tài sản");
    }

    private Object[] toRow(TaiSan ts) {
        return new Object[]{
            ts.getMaTaiSan(),
            ts.getMaTsCode() == null ? "" : ts.getMaTsCode(),
            ts.getTenTaiSan() == null ? "" : ts.getTenTaiSan(),
            ts.getMaLoai(),
            ts.getMaNcc(),
            String.format("%,.0f", ts.getGiaMua()),
            ts.getTinhTrang() == null ? "" : ts.getTinhTrang(),
            ts.getTrangThai() == null ? "" : ts.getTrangThai(),
            ts.getViTri() == null ? "" : ts.getViTri()
        };
    }

    // ============================================================
    // TÌM KIẾM
    // ============================================================
    private void timKiem() {
        String kw = txtTimKiem.getText().trim().toLowerCase();
        if (kw.isEmpty()) {
            loadData();
            return;
        }
        model.setRowCount(0);
        List<TaiSan> list = bus.getAll();
        for (TaiSan ts : list) {
            String ma = ts.getMaTsCode() == null ? "" : ts.getMaTsCode().toLowerCase();
            String ten = ts.getTenTaiSan() == null ? "" : ts.getTenTaiSan().toLowerCase();
            if (ma.contains(kw) || ten.contains(kw)) {
                model.addRow(toRow(ts));
            }
        }
        capNhatTong();
    }

    // ============================================================
    // THÊM
    // ============================================================
    private void them() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        TaiSanDialog dlg = new TaiSanDialog(
                parent instanceof java.awt.Frame ? (java.awt.Frame) parent : null,
                null);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            loadData();
        }
    }

    // ============================================================
    // SỬA
    // ============================================================
    private void sua() {
        int row = table.getSelectedRow();
        if (row < 0) {
            MessageUtil.canhBao(this, "Vui lòng chọn 1 dòng để sửa!");
            return;
        }

        // Chuyển từ view row sang model row (do có sorter)
        int modelRow = table.convertRowIndexToModel(row);
        int id = (int) model.getValueAt(modelRow, 0);

        TaiSan ts = bus.findById(id);
        if (ts == null) {
            MessageUtil.loi(this, "Không tìm thấy tài sản có ID = " + id);
            return;
        }

        Window parent = SwingUtilities.getWindowAncestor(this);
        TaiSanDialog dlg = new TaiSanDialog(
                parent instanceof java.awt.Frame ? (java.awt.Frame) parent : null,
                ts);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            loadData();
        }
    }

    // ============================================================
    // XÓA
    // ============================================================
    private void xoa() {
        int row = table.getSelectedRow();
        if (row < 0) {
            MessageUtil.canhBao(this, "Vui lòng chọn 1 dòng để xóa!");
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        int id = (int) model.getValueAt(modelRow, 0);
        String ma = String.valueOf(model.getValueAt(modelRow, 1));
        String ten = String.valueOf(model.getValueAt(modelRow, 2));

        if (!MessageUtil.xacNhan(this, "Xóa tài sản:\n" + ma + " - " + ten + " ?")) {
            return;
        }

        if (bus.xoa(id)) {
            MessageUtil.thongBao(this, "Xóa thành công!");
            loadData();
        } else {
            MessageUtil.loi(this, "Không thể xóa! Tài sản có thể đang được cấp phát.");
        }
    }
}