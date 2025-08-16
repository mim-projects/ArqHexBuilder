package com.github.medina1402.generator;

import com.github.medina1402.model.PropertiesModel;
import com.github.medina1402.model.TableModel;
import com.github.medina1402.utils.FileUtils;
import com.github.medina1402.utils.Normalizations;
import com.github.medina1402.utils.PathUtils;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BaseGenerator {
    public static void Create(List<TableModel> allTablesModel) throws IOException, TemplateException {
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/application/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/application/dto/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/application/mapper/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/application/service/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/application/usecases/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/configuration/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/domain/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/domain/entities/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/domain/repository/");
        if (PropertiesModel.INCLUDE_BASE_WEB) CreateWEB();
        if (PropertiesModel.INCLUDE_BASE_API) {
            List<String> tableNamesNormalizations = new ArrayList<>();
            for (TableModel tableModel : allTablesModel) {
                String normalizeTable = Normalizations.TableName(tableModel.getTableName());
                tableNamesNormalizations.add(normalizeTable);
            }
            CreateAPI(allTablesModel);
            FileUtils.WriteToFile(
                    PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/configuration/Configuration.java",
                    Generator.CreateCode(null, null, ".configuration", "Configuration", tableNamesNormalizations)
            );
        } else {
            FileUtils.WriteToFile(
                    PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/configuration/Configuration.java",
                    Generator.CreateCode(null, null, ".configuration", "Configuration")
            );
        }
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/adapter/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/adapter/repository/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/adapter/service/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/adapter/usecases/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/event/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/event/notification/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/event/schedule/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/reports/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/reports/model/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/reports/builder/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/reports/utils/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/shared/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/shared/constants/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/shared/utils/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/shared/utils/lang/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/shared/utils/system/");

        FileUtils.WriteToFile(
                PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/application/usecases/UseCaseCrud.java",
                Generator.CreateCode(null, null, ".application.usecases", "UseCaseCrud")
        );
        FileUtils.WriteToFile(
                PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/domain/repository/EntityRepository.java",
                Generator.CreateCode(null, null, ".domain.repository", "EntityRepository")
        );
        FileUtils.WriteToFile(
                PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/adapter/repository/EntityRepositoryImpl.java",
                Generator.CreateCode(null, null, ".infrastructure.adapter.repository", "EntityRepositoryImpl")
        );
        FileUtils.WriteToFile(
                PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/infrastructure/adapter/usecases/UseCaseCrudImpl.java",
                Generator.CreateCode(null, null, ".infrastructure.adapter.usecases", "UseCaseCrudImpl")
        );
    }

    private static void CreateWEB() throws IOException, TemplateException {
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/web/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/web/models/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/web/models/base/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/web/models/lazy/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/web/views/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/web/utils/");
        FileUtils.WriteToFile(
                PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/web/models/lazy/GenericLazyDataModel.java",
                Generator.CreateCode(null, null, ".entrypoint.web.models.lazy", "GenericLazyDataModel")
        );
    }

    private static void CreateAPI(List<TableModel> allTablesModel) throws IOException, TemplateException {
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/api/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/api/utils/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/api/factory/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/api/filters/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/api/annotations/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/api/controllers/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/api/controllers/base/");
        FileUtils.CreateFolder(PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/api/controllers/custom/");

        FileUtils.WriteToFile(
                PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/shared/utils/system/FileUtils.java",
                Generator.CreateCode(null, null, ".shared.utils.system", "FileUtils")
        );
        FileUtils.WriteToFile(
                PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/api/factory/CrudControllerFactory.java",
                Generator.CreateCode(null, null, ".entrypoint.api.factory", "CrudControllerFactory")
        );
        FileUtils.WriteToFile(
                PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/api/annotations/UseTokenMiddleware.java",
                Generator.CreateCode(null, null, ".entrypoint.api.annotations", "UseTokenMiddleware")
        );
        FileUtils.WriteToFile(
                PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/api/filters/TokenFilter.java",
                Generator.CreateCode(null, null, ".entrypoint.api.filters", "TokenFilter")
        );
        FileUtils.WriteToFile(
                PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/api/filters/CorsFilter.java",
                Generator.CreateCode(null, null, ".entrypoint.api.filters", "CorsFilter")
        );
        FileUtils.WriteToFile(
                PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/api/utils/MultipartFormUtils.java",
                Generator.CreateCode(null, null, ".entrypoint.api.utils", "MultipartFormUtils")
        );

        for (TableModel tableModel : allTablesModel) {
            String tableNameNormalize = Normalizations.TableName(tableModel.getTableName());
            FileUtils.WriteToFile(
                    PathUtils.FormatDirectoryPath(PropertiesModel.PATH) + "/entrypoint/api/controllers/base/" + tableNameNormalize + "Controller.java",
                    Generator.CreateCode(tableModel, tableNameNormalize, ".entrypoint.api.controllers.base", "ApiController")
            );
        }
    }
}
