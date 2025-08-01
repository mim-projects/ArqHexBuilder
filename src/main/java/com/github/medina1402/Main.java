package com.github.medina1402;

import com.github.medina1402.generator.*;
import com.github.medina1402.model.PropertiesModel;
import com.github.medina1402.model.TableModel;
import com.github.medina1402.parser.SqlTableParser;

import java.util.List;

public class Main {
    private final static String SQLExample = """
            create table if not exists risk_work_permit_sections (
                    id int not null auto_increment primary key,
                    value varchar(255) not null
                );
            
                create table if not exists risk_work_permit_property_types (
                    id int not null auto_increment primary key,
                    value varchar(255) not null,
                    description varchar(255),
                    section_id int default null,
                    foreign key (section_id) references risk_work_permit_sections (id) on delete cascade
                );
            
                create table if not exists risk_work_permit_signature_types (
                    id int not null auto_increment primary key,
                    value varchar(255) not null,
                    description varchar(500),
                    section_id int default null,
                    foreign key (section_id) references risk_work_permit_sections (id) on delete cascade
                );
            
                create table if not exists risk_work_permit_options (
                    id int not null auto_increment primary key,
                    section_id int not null,
                    value varchar(500) not null,
                    foreign key (section_id) references risk_work_permit_sections (id) on delete cascade
                );
            
                create table if not exists risk_work_permit_option_items (
                    id int not null auto_increment primary key,
                    options_id int not null,
                    value varchar(500) not null,
                    foreign key (options_id) references risk_work_permit_options (id) on delete cascade
                );
            
                create table if not exists risk_work_permit_level_risks (
                    id int not null auto_increment primary key,
                    value varchar(255) not null
                );
            
                create table if not exists risk_work_permit (
                    id int not null auto_increment primary key,
                    work_permit_id int not null,
                    level_risks_id int not null,
                    contractor_id int not null,
                    area_id int not null,
                    author_id int not null,
            
                    urgent boolean default false,
                    first_approval boolean default false,
            
                    department varchar(500),
                    folio varchar(255),
                    description varchar(500),
                    created_at timestamp not null default current_timestamp,
                    updated_at timestamp not null default current_timestamp,
                    started_at timestamp not null,
                    ended_at timestamp not null,
            
                    foreign key (work_permit_id) references permiso_trabajo (idpermiso_trabajo) on delete cascade,
                    foreign key (level_risks_id) references risk_work_permit_level_risks (id) on delete cascade,
                    foreign key (contractor_id) references empresa (idempresa) on delete cascade,
                    foreign key (area_id) references area (idarea) on delete cascade,
                    foreign key (author_id) references usuario (idusuario) on delete cascade
                );
            
                create table if not exists risk_work_permit_emergency (
                    id int not null auto_increment primary key,
                    risk_work_permit_id int not null,
                    radio_channel varchar(20),
                    phone_direct varchar(20),
                    phone_direct_ext varchar(20),
                    phone_wsp varchar(20),
                    foreign key (risk_work_permit_id) references risk_work_permit (id) on delete cascade
                );
            
                create table if not exists risk_work_permit_section_observations (
                    id int not null auto_increment primary key,
                    risk_work_permit_id int not null,
                    section_id int,
                    user_reference varchar(20),
                    username varchar(500),
                    value varchar(500) not null,
                    created_at timestamp not null default current_timestamp,
                    foreign key (risk_work_permit_id) references risk_work_permit (id) on delete cascade,
                    foreign key (section_id) references risk_work_permit_sections (id) on delete cascade
                );
            
                create table if not exists risk_work_permit_section_responses (
                    id int not null auto_increment primary key,
                    risk_work_permit_id int not null,
                    section_id int not null,
                    options_id int not null,
                    options_item_id int,
                    other varchar(500),
                    foreign key (risk_work_permit_id) references risk_work_permit (id) on delete cascade,
                    foreign key (section_id) references risk_work_permit_sections (id) on delete cascade,
                    foreign key (options_id) references risk_work_permit_options (id) on delete cascade,
                    foreign key (options_item_id) references risk_work_permit_option_items (id) on delete cascade
                );
            
                create table if not exists risk_work_permit_properties (
                    id int not null auto_increment primary key,
                    risk_work_permit_id int not null,
                    property_type_id int not null,
                    keyword varchar(20) not null,
                    value varchar(500),
                    foreign key (risk_work_permit_id) references risk_work_permit (id) on delete cascade,
                    foreign key (property_type_id) references risk_work_permit_property_types (id) on delete cascade
                );
            
                create table if not exists risk_work_permit_signatures (
                    id int not null auto_increment primary key,
                    risk_work_permit_id int not null,
                    signature_type_id int not null,
                    user_reference varchar(20),
                    username varchar(500),
                    image_url varchar(500),
                    foreign key (risk_work_permit_id) references risk_work_permit (id) on delete cascade,
                    foreign key (signature_type_id) references risk_work_permit_signature_types (id) on delete cascade
                );
        """;

    public static void main(String[] args) {
        // Estos parametros son ideales para agregar por CLI o UI
        PropertiesModel.PATH = "C:\\Users\\medina\\Desktop\\mimsoft\\permiso_digital\\dc3BackEndServices\\dc3BackEnd2_1\\src\\main\\java\\com\\mim\\service\\risk_work_permit";
        PropertiesModel.MODULE = "risk_work_permit";
        PropertiesModel.PACKAGE = "com.mim.service.risk_work_permit";
        PropertiesModel.SQL = SQLExample;
        PropertiesModel.USE_JAKARTA = true;
        PropertiesModel.INCLUDE_BASE_WEB = false;

        try {
            List<TableModel> allTablesModel = SqlTableParser.ExtractDataFromMultipleTableSQL(PropertiesModel.SQL);
            BaseGenerator.Create(allTablesModel);
            for (TableModel table : allTablesModel) {
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