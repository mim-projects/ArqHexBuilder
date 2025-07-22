package com.github.medina1402.generator;

import com.github.medina1402.model.TableModel;
import com.github.medina1402.utils.FileUtils;
import com.github.medina1402.utils.Normalizations;
import com.github.medina1402.utils.PathUtils;
import freemarker.template.TemplateException;

import java.io.IOException;

public class ServiceGenerator {
    public static void Create(TableModel table, String path, String packageStr) throws IOException, TemplateException {
        CreateInterface(table, path, packageStr);
        CreateImplementation(table, path, packageStr);
    }

    private static void CreateInterface(TableModel table, String path, String packageStr) throws IOException, TemplateException {
        String tableNameNormalize = Normalizations.TableName(table.getTableName());
        String pathFile = PathUtils.FormatDirectoryPath(path) + "/application/service/" + tableNameNormalize + "Service.java";
        String packageFile = packageStr + ".application.service";

        String code = Generator.CreateCode(table, tableNameNormalize, packageStr, packageFile, "ServiceInterface");
        FileUtils.WriteToFile(pathFile, code);
    }

    private static void CreateImplementation(TableModel table, String path, String packageStr) throws IOException, TemplateException {
        String tableNameNormalize = Normalizations.TableName(table.getTableName());
        String pathFile = PathUtils.FormatDirectoryPath(path) + "/infrastructure/adapter/service/" + tableNameNormalize + "ServiceImpl.java";
        String packageFile = packageStr + ".infrastructure.adapter.service";

        String code = Generator.CreateCode(table, tableNameNormalize, packageStr, packageFile, "ServiceImplementation");
        FileUtils.WriteToFile(pathFile, code);
    }
}
