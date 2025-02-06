package org.appserver.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

public class DynamicJsonArrayToDatabase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chinese-gang?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public static void createTableAndInsertData(String json, String tableName) {
        try {
            // 解析 JSON 数组
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode jsonArray = (ArrayNode) mapper.readTree(json);
            // 创建表并插入数据
            createTableAndInsertData(jsonArray, tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTableAndInsertData(ArrayNode jsonArray, String tableName) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // 提取所有字段名
            Set<String> fieldNames = new LinkedHashSet<>();
            for (JsonNode jsonNode : jsonArray) {
                Iterator<String> fieldNamesIterator = jsonNode.fieldNames();
                while (fieldNamesIterator.hasNext()) {
                    fieldNames.add(fieldNamesIterator.next());
                }
            }

            // 生成创建表的 SQL 语句
            StringBuilder createTableSql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");
            boolean hasId = false;
            for (String fieldName : fieldNames) {
                if ("id".equals(fieldName)&&!tableName.equals("recharge_types")) {
                    createTableSql.append(fieldName).append(" INT PRIMARY KEY");
                    hasId = true;
                } else {
                    createTableSql.append(fieldName).append(" VARCHAR(255)");
                }
                if (fieldName != fieldNames.toArray()[fieldNames.size() - 1]) {
                    createTableSql.append(", ");
                }
            }
//            if (!hasId) {
//                throw new IllegalArgumentException("JSON 数据中未包含 'id' 字段。");
//            }
            createTableSql.append(")");

            // 创建表
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createTableSql.toString());
            }

            // 生成插入数据的 SQL 语句
            StringBuilder insertSql = new StringBuilder("INSERT INTO " + tableName + " (");
            for (Iterator<String> it = fieldNames.iterator(); it.hasNext(); ) {
                insertSql.append(it.next());
                if (it.hasNext()) {
                    insertSql.append(", ");
                }
            }
            insertSql.append(") VALUES (");
            for (int i = 0; i < fieldNames.size(); i++) {
                insertSql.append("?");
                if (i < fieldNames.size() - 1) {
                    insertSql.append(", ");
                }
            }
            insertSql.append(")");

            // 插入数据
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql.toString())) {
                for (JsonNode jsonNode : jsonArray) {
                    int index = 1;
                    for (String fieldName : fieldNames) {
                        JsonNode valueNode = jsonNode.get(fieldName);
                        if (valueNode != null) {
                            if ("id".equals(fieldName)&&!tableName.equals("recharge_types")) {
                                pstmt.setInt(index, valueNode.asInt());
                            } else {
                                pstmt.setString(index, valueNode.asText());
                            }
                        } else {
                            if ("id".equals(fieldName)&&!tableName.equals("recharge_types")) {
                                throw new IllegalArgumentException("JSON 对象中缺少 'id' 字段。");
                            }
                            pstmt.setString(index, null);
                        }
                        index++;
                    }
                    pstmt.executeUpdate();
                }
            }

            System.out.println("数据插入成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}