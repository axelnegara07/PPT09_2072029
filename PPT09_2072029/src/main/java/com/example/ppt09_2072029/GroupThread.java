package com.example.ppt09_2072029;


import com.example.ppt09_2072029.util.MySQLConnection;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class GroupThread extends Thread {

    @Override
    public void run() {
        JasperPrint jp;
        Connection conn = MySQLConnection.getConnection();

        Map param = new HashMap();
        try {
            jp = JasperFillManager.fillReport("reports/Items_Report_Group.jasper", param, conn);
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("Group Report");
            viewer.setVisible(true);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
