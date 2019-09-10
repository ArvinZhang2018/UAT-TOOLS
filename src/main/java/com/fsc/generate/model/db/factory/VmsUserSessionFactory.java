package com.fsc.generate.model.db.factory;

import common.config.tools.config.ConfigTools3;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Repository
public class VmsUserSessionFactory extends AbstractSessionFactory {

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    ConfigTools3.getString("crm.vms.user.db.url"),
                    ConfigTools3.getString("crm.vms.user.db.username"),
                    ConfigTools3.getString("crm.vms.user.db.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
