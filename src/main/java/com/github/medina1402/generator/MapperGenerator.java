package com.github.medina1402.generator;

import com.github.medina1402.model.PropertiesModel;
import com.github.medina1402.model.TableModel;
import com.github.medina1402.utils.FileUtils;
import com.github.medina1402.utils.Normalizations;
import com.github.medina1402.utils.PathUtils;
import freemarker.template.TemplateException;

import java.io.IOException;

public class MapperGenerator {
    public static void Create(TableModel table) throws IOException, TemplateException {
        String tableNameNormalize = Normalizations.TableName(table.getTableName());
        String pathFile = PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/application/mapper/" + tableNameNormalize + "Mapper.java";

        String code = Generator.CreateCode(table, tableNameNormalize, ".application.mapper", "Mapper");
        FileUtils.WriteToFile(pathFile, code);
    }
}
