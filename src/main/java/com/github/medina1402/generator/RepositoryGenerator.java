package com.github.medina1402.generator;

import com.github.medina1402.model.PropertiesModel;
import com.github.medina1402.model.TableModel;
import com.github.medina1402.utils.FileUtils;
import com.github.medina1402.utils.Normalizations;
import com.github.medina1402.utils.PathUtils;
import freemarker.template.TemplateException;

import java.io.IOException;

public class RepositoryGenerator {
    public static void Create(TableModel table) throws IOException, TemplateException {
        CreateInterface(table);
        CreateImplementation(table);
    }

    private static void CreateInterface(TableModel table) throws IOException, TemplateException {
        String tableNameNormalize = Normalizations.TableName(table.getTableName());
        String pathFile = PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/domain/repository/" + tableNameNormalize + "Repository.java";

        String code = Generator.CreateCode(table, tableNameNormalize, ".domain.repository", "RepositoryInterface");
        FileUtils.WriteToFile(pathFile, code);
    }

    private static void CreateImplementation(TableModel table) throws IOException, TemplateException {
        String tableNameNormalize = Normalizations.TableName(table.getTableName());
        String pathFile = PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/adapter/repository/" + tableNameNormalize + "RepositoryImpl.java";

        String code = Generator.CreateCode(table, tableNameNormalize, ".infrastructure.adapter.repository", "RepositoryImplementation");
        FileUtils.WriteToFile(pathFile, code);
    }
}
