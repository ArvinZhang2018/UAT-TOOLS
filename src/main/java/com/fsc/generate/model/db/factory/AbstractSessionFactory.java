package com.fsc.generate.model.db.factory;

import com.fsc.generate.annotation.CrmColumn;
import com.fsc.generate.annotation.CrmExecuteSql;
import com.fsc.generate.exception.CrmCode;
import com.fsc.generate.exception.CrmException;
import com.fsc.generate.model.db.ddo.ToolsUser;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractSessionFactory {

    private Logger logger = LoggerFactory.getLogger(AbstractSessionFactory.class);

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public abstract Connection getConnection();

    public <T> boolean executeSql(Class<T> type, String executedMethod, Object... params) throws CrmException {
        PreparedStatement preparedStatement = null;
        try {
            Method method = type.getDeclaredMethod(executedMethod);
            String sql = method.getAnnotation(CrmExecuteSql.class).value();
            preparedStatement =
                    getConnection().prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement.execute();
        } catch (Exception e) {
            logger.error("[executeSql] [ERROR] [{}] [{}] [{}]", type.getName(), executedMethod,
                    Arrays.toString(params), e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("[executeSql] [ERROR] [{}] [{}] [{}]", type.getName(), executedMethod,
                        Arrays.toString(params), e);
            }
        }
        throw CrmException.newCrmException(CrmCode.RET_DB, CrmCode.ERR_DB_QUERY_USER_FAILURE);
    }

    public <T> List<T> executeQuerySql(Class<T> type, String executedMethod, Object... params) throws CrmException {
        PreparedStatement preparedStatement = null;
        try {
            Method method = type.getDeclaredMethod(executedMethod);
            String sql = method.getAnnotation(CrmExecuteSql.class).value();
            preparedStatement =
                    getConnection().prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> list = new ArrayList<>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> map = Maps.newHashMap();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(metaData.getColumnName(i));
                    map.put(metaData.getColumnName(i), value);
                }
                list.add(convertMapToEntity(map, type));
            }
            return list;
        } catch (Exception e) {
            logger.error("[executeQuerySql] [ERROR] [{}] [{}] [{}]", type.getName(), executedMethod,
                    Arrays.toString(params), e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.error("[executeQuerySql] [ERROR] [{}] [{}] [{}]", type.getName(),
                        executedMethod, Arrays.toString(params), e);
            }
        }
        throw CrmException.newCrmException(CrmCode.RET_DB, CrmCode.ERR_DB_QUERY_USER_FAILURE);
    }

    private <T> T convertMapToEntity(Map<String, Object> map, Class<T> classType) throws Exception {
        Field[] fields = classType.getDeclaredFields();
        T obj = classType.newInstance();
        for (int i = 0; i < fields.length; i++) {
            CrmColumn annotation = fields[i].getAnnotation(CrmColumn.class);
            if (Objects.nonNull(annotation)) {
                String column = annotation.value();
                if (Objects.nonNull(map.get(column))) {
                    fields[i].setAccessible(true);
                    fields[i].set(obj, map.get(column));
                }
            }
        }
        return obj;
    }

}
