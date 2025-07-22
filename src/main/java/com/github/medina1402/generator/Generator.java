package com.github.medina1402.generator;

import com.github.medina1402.model.ColumnModel;
import com.github.medina1402.model.TableModel;
import com.github.medina1402.parser.TemplateParser;
import com.github.medina1402.utils.Normalizations;
import com.github.medina1402.utils.PathUtils;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class Generator {
    public static String CreateCode(TableModel table, String tableNameNormalize, String packageBase, String packageFile, String template) throws TemplateException, IOException {
        boolean importDate = false;
        for (ColumnModel column : table.getColumns()) {
            if (Normalizations.NormaliceType(column.getType()).equals("Date")) {
                importDate = true;
                break;
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("package", packageFile);
        data.put("packageBase", packageBase);
        data.put("tableName", table.getTableName());
        data.put("className", tableNameNormalize);
        data.put("columns", TemplateParser.CreateColumnMap(table));
        data.put("importDate", importDate);

        return TemplateParser.CreateCode(template, data);
    }
}
