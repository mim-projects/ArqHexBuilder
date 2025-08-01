package com.github.medina1402.generator;

import com.github.medina1402.model.ColumnModel;
import com.github.medina1402.model.PropertiesModel;
import com.github.medina1402.model.TableModel;
import com.github.medina1402.parser.TemplateParser;
import com.github.medina1402.utils.Normalizations;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Generator {
    public static String CreateCode(TableModel table, String tableNameNormalize, String packageFile, String template) throws TemplateException, IOException {
        return CreateCode(table, tableNameNormalize, packageFile, template, new ArrayList<>());
    }

    public static String CreateCode(TableModel table, String tableNameNormalize, String packageFile, String template, List<String> allTables) throws TemplateException, IOException {
        if (table == null) {
            Map<String, Object> data = new HashMap<>();
            data.put("package", PropertiesModel.PACKAGE + packageFile);
            data.put("packageBase", PropertiesModel.PACKAGE);
            data.put("useJakarta", PropertiesModel.USE_JAKARTA);
            data.put("module", PropertiesModel.MODULE);
            data.put("allTables", allTables);
            data.put("allTablesRender", !allTables.isEmpty());
            return TemplateParser.CreateCode(template, data);
        }

        boolean importDate = false;
        for (ColumnModel column : table.getColumns()) {
            if (Normalizations.NormaliceType(column.getType()).equals("Date")) {
                importDate = true;
                break;
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("package", PropertiesModel.PACKAGE + packageFile);
        data.put("packageBase", PropertiesModel.PACKAGE);
        data.put("tableName", table.getTableName());
        data.put("className", tableNameNormalize);
        data.put("tableNameStartLowerCase", tableNameNormalize.substring(0, 1).toLowerCase() + tableNameNormalize.substring(1));
        data.put("columns", TemplateParser.CreateColumnMap(table));
        data.put("importDate", importDate);
        data.put("useJakarta", PropertiesModel.USE_JAKARTA);
        data.put("module", PropertiesModel.MODULE);
        data.put("allTables", allTables);
        data.put("allTablesRender", !allTables.isEmpty());
        data.put("apiControllerName", table.getTableName().equalsIgnoreCase(PropertiesModel.MODULE)
                ? table.getTableName()
                : PropertiesModel.MODULE + "/" + table.getTableName().replaceFirst(PropertiesModel.MODULE + "_", ""));
        return TemplateParser.CreateCode(template, data);
    }
}
