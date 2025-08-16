package com.github.medina1402;

import com.github.medina1402.generator.*;
import com.github.medina1402.model.PropertiesModel;
import com.github.medina1402.model.TableModel;
import com.github.medina1402.parser.SqlTableParser;

import java.util.List;

public class Main {
    private final static String SQLExample = """
        
            create table mia_ui_user_rol (
                    id int not null auto_increment primary key,
                    name varchar(50) not null,
                    description varchar(500)
                );
            
                create table mia_ui_user (
                    id int not null auto_increment primary key,
                    name varchar(255) not null,
                    username varchar(255) not null,
                    password varchar(255) not null,
                    rol_id int not null,
                    foreign key (rol_id) references mia_ui_user_rol (id) on delete cascade
                );
            
                create table mia_ui_room_types (
                    id int not null auto_increment primary key,
                    name varchar(255) not null,
                    description varchar(500),
                    primaries_table int default 0,
                    secondaries_table int default 0,
                    primaries_series int default 0,
                    secondaries_series int default 0
                );
            
                create table mia_ui_room (
                    id int not null auto_increment primary key,
                    name varchar(255) not null,
                    code varchar(100) not null,
                    description varchar(500) not null,
                    type_id int not null,
                    created_at timestamp not null default current_timestamp,
                    foreign key (type_id) references mia_ui_room_types (id) on delete cascade
                );
            
                create table mia_ui_room_query (
                    id int not null auto_increment primary key,
                    data_title varchar(255) not null,
                    data_type varchar(50) not null,
                    data_new_ref varchar(50) not null,
                    data_ref varchar(20) not null,
                    data_query text not null,
                    data_raw_query boolean not null,
                    data_format_result varchar(20) not null,
                    data_datasource_type text not null,
                    data_datasource_uid text not null,
                    primary_type boolean default false,
                    room_id int not null,
                    created_at timestamp not null default current_timestamp,
                    foreign key (room_id) references mia_ui_room (id) on delete cascade
                );
            
                create table mia_ui_access_db (
                    id int not null auto_increment primary key,
                    username varchar(255) not null,
                    password varchar(255) not null
                );
            
        """;

    public static void main(String[] args) {
        // Estos parametros son ideales para agregar por CLI o UI
        PropertiesModel.PATH = "C:\\Users\\medina\\Desktop\\mimsoft\\monitoreo_inteligencia_artificial\\mia_ui\\src\\main\\java\\com\\mimsoft\\mia_ui\\";
        PropertiesModel.MODULE = "mia_ui";
        PropertiesModel.PACKAGE = "com.mimsoft.mia_ui";
        PropertiesModel.SQL = SQLExample;
        PropertiesModel.USE_JAKARTA = true;
        PropertiesModel.INCLUDE_BASE_WEB = true;
        PropertiesModel.INCLUDE_BASE_API = false;

        try {
            List<TableModel> allTablesModel = SqlTableParser.ExtractDataFromMultipleTableSQL(PropertiesModel.SQL);
            BaseGenerator.Create(allTablesModel);
            for (TableModel table : allTablesModel) {
                String tableName = table.getTableName().startsWith(PropertiesModel.MODULE)
                        ? table.getTableName().substring(PropertiesModel.MODULE.length())
                        : table.getTableName();
                table.setTableName(tableName);

                EntityGenerator.Create(table);
                DtoGenerator.Create(table);
                ServiceGenerator.Create(table);
                RepositoryGenerator.Create(table);
                MapperGenerator.Create(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}