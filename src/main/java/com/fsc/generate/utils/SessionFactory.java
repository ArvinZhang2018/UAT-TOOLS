package com.fsc.generate.utils;

import com.google.common.collect.Maps;
import common.config.tools.config.ConfigTools3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SessionFactory {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(String db) {
        String url = null;
        String username = null;
        String password = null;
        switch (db) {
            case "cc":
                url = ConfigTools3.getString("cc.db.url");
                username = ConfigTools3.getString("cc.db.username");
                password = ConfigTools3.getString("cc.db.password");
                break;
            case "device":
                url = ConfigTools3.getString("vms.user.db.url");
                username = ConfigTools3.getString("vms.user.db.username");
                password = ConfigTools3.getString("vms.user.db.password");
                break;
            case "account":
                url = ConfigTools3.getString("mfc.account.db.url");
                username = ConfigTools3.getString("mfc.account.db.username");
                password = ConfigTools3.getString("mfc.account.db.password");
                break;
            case "bill":
                url = ConfigTools3.getString("bill.db.url");
                username = ConfigTools3.getString("bill.db.username");
                password = ConfigTools3.getString("bill.db.password");
                break;
        }

        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean executeSql(String db, String sql, Object... params) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement =
                    getConnection(db).prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static List<Map<String,Object>> executeQuerySql(String db, String sql, Object... params) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement =
                    getConnection(db).prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Map<String,Object>> list = new ArrayList<>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while(resultSet.next()){
                Map<String,Object> map = Maps.newHashMap();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(metaData.getColumnName(i),resultSet.getString(metaData.getColumnName(i)));
                }
                list.add(map);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
