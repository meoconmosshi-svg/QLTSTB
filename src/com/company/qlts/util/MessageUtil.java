package com.company.qlts.util;

import javax.swing.JOptionPane;
import java.awt.Component;

public class MessageUtil {

    public static void thongBao(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void canhBao(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Cảnh báo",
                JOptionPane.WARNING_MESSAGE);
    }

    public static void loi(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Lỗi",
                JOptionPane.ERROR_MESSAGE);
    }

    public static boolean xacNhan(Component parent, String msg) {
        int chon = JOptionPane.showConfirmDialog(parent, msg, "Xác nhận",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return chon == JOptionPane.YES_OPTION;
    }
}