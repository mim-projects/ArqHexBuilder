package com.github.medina1402.generator;

import com.github.medina1402.model.TableModel;
import com.github.medina1402.utils.FileUtils;
import com.github.medina1402.utils.Normalizations;
import com.github.medina1402.utils.PathUtils;
import freemarker.template.TemplateException;

import java.io.IOException;

public class EntityGenerator {
    public static void Create(TableModel table, String path, String packageStr) throws IOException, TemplateException {
        String tableNameNormalize = Normalizations.TableName(table.getTableName());
        String pathFile = PathUtils.FormatDirectoryPath(path) + "/domain/entities/" + tableNameNormalize + ".java";
        String packageFile = packageStr + ".domain.entities";

        String code = Generator.CreateCode(table, tableNameNormalize, packageStr, packageFile, "Entity");
        FileUtils.WriteToFile(pathFile, code);
    }
}
