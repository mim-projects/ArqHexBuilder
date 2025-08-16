package com.github.medina1402.utils;

import com.github.medina1402.model.PropertiesModel;

public class Normalizations {
    public static String TableName(String rawName) {
        if (rawName == null || rawName.isBlank()) return "";
        String[] parts = rawName.toLowerCase().split("_");
        StringBuilder normalized = new StringBuilder();
        for (String part : parts) {
            if (!part.isBlank()) {
                normalized.append(Character.toUpperCase(part.charAt(0)));
                if (part.length() > 1) {
                    normalized.append(part.substring(1));
                }
            }
        }
        return normalized.toString();
    }

    public static String ColumnName(String rawName) {
        String normalizeUsingTableName = TableName(rawName);
        return normalizeUsingTableName.substring(0, 1).toLowerCase() + normalizeUsingTableName.substring(1);
    }

    public static String NormaliceType(String type) {
        String cleanType = type.toLowerCase().replaceAll(" ", "");
        if (cleanType.startsWith("int")) return "Integer";
        if (cleanType.startsWith("bigint")) return "Long";
        if (cleanType.startsWith("varchar")) return "String";
        if (cleanType.startsWith("text")) return "String";
        if (cleanType.startsWith("float")) return "Float";
        if (cleanType.startsWith("double")) return "Double";
        if (cleanType.startsWith("boolean")) return "Boolean";
        if (cleanType.startsWith("timestamp")) return "Date";
        if (cleanType.startsWith("datetime")) return "Date";
        if (cleanType.startsWith("date")) return "Date";
        return "Object";
    }

    public static String CleanTableName(String tableName) {
        String name = tableName.startsWith(PropertiesModel.MODULE)
                ? tableName.substring(PropertiesModel.MODULE.length())
                : tableName;
        return name.startsWith("_") ? name.substring(1) : name;
    }
}
