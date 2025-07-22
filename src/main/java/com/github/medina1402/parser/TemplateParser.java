package com.github.medina1402.parser;


import com.github.medina1402.model.ColumnModel;
import com.github.medina1402.model.ForeignKeyModel;
import com.github.medina1402.model.TableModel;
import com.github.medina1402.utils.Normalizations;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TemplateParser {
    public static String CreateCode(String templateName, Map<String, Object> data) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_34);
        cfg.setClassForTemplateLoading(TemplateParser.class, "/templates");
        Template template = cfg.getTemplate(templateName + ".ftl");

        StringWriter out = new StringWriter();
        template.process(data, out);
        return out.toString();
    }

    public static List<Map<String, Object>> CreateColumnMap(TableModel table) {
        List<Map<String, Object>> data = new ArrayList<>();

        for (ColumnModel column : table.getColumns()) {
            String normaliceName = NormaliceName(column.getName());
            String normaliceType = Normalizations.NormaliceType(column.getType());
            data.add(Map.of(
               "nameReferenceColumn", column.getName(),
               "name", normaliceName,
               "type", normaliceType,
               "primaryKey", column.isPrimaryKey(),
               "foreignKey", IsForeignKey(table, column.getName()),
               "foreignKeyReferenceTable", GetForeignKeyReferenceTable(table, column.getName()),
               "foreignKeyReferenceColumn", GetForeignKeyReferenceColumn(table, column.getName()),
               "foreignKeyColumn", GetForeignKeyColumn(table, column.getName()),
               "nameSetter", GetNameSetter(normaliceName, normaliceType),
               "nameGetter", GetNameGetter(normaliceName, normaliceType)

            ));
        }
        return data;
    }

    private static String GetNameSetter(String name, String type) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private static String GetNameGetter(String name, String type) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private static String GetForeignKeyColumn(TableModel table, String data) {
        for (ForeignKeyModel foreignKey : table.getForeignKeys()) {
            if (foreignKey.getColumn().equalsIgnoreCase(data)) {
                return foreignKey.getColumn();
            }
        }
        return "";
    }

    private static String GetForeignKeyReferenceColumn(TableModel table, String data) {
        for (ForeignKeyModel foreignKey : table.getForeignKeys()) {
            if (foreignKey.getColumn().equalsIgnoreCase(data)) {
                return foreignKey.getReferencedColumn();
            }
        }
        return "";
    }

    private static String GetForeignKeyReferenceTable(TableModel table, String data) {
        for (ForeignKeyModel foreignKey : table.getForeignKeys()) {
            if (foreignKey.getColumn().equalsIgnoreCase(data)) {
                return Normalizations.TableName(foreignKey.getReferencedTable());
            }
        }
        return "";
    }

    private static boolean IsForeignKey(TableModel table, String columnName) {
        for (ForeignKeyModel foreignKey : table.getForeignKeys()) {
            if (foreignKey.getColumn().equalsIgnoreCase(columnName)) {
                return true;
            }
        }
        return false;
    }

    private static String NormaliceName(String columnName) {
        String name = columnName.endsWith("_id") ? columnName.substring(0, columnName.length() - 3) : columnName;
        return Normalizations.ColumnName(name);
    }
}
