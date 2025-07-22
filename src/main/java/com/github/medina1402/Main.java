package com.github.medina1402;

import com.github.medina1402.generator.*;
import com.github.medina1402.model.TableModel;
import com.github.medina1402.parser.SqlTableParser;

public class Main {
    private final static String SQLExample = """
            create table chemical_configuration (
                id int not null auto_increment primary key,
                name varchar(255) not null,
                type int not null,
                value text not null
            );
            
            create table chemical_user_types (
                id int not null auto_increment primary key,
                name varchar(50) not null unique,
                description varchar(255)
            );
            
            create table chemical_users (
                id int not null auto_increment primary key,
                usuario_id int not null,
                user_type_id int not null,
                foreign key (usuario_id) references usuario(idusuario) on delete cascade,
                foreign key (user_type_id) references chemical_user_types(id) on delete cascade
            );
            
            create table chemical_module_types (
                id int not null auto_increment primary key,
                name varchar(255) not null,
                user_id int not null,
                foreign key (user_id) references chemical_users(id) on delete cascade
            );
            
            create table chemical_units (
                id int not null auto_increment primary key,
                name varchar(50) not null unique,
                description varchar(255)
            );
            
            create table chemical_risk_types (
                id int not null auto_increment primary key,
                name varchar(255) not null unique,
                description varchar(255),
                color_code varchar(10)
            );
            
            create table chemical_risks (
                id int not null auto_increment primary key,
                name varchar(50) not null unique,
                description varchar(700),
                image_url varchar(500),
                risk_type_id int not null,
                foreign key (risk_type_id) references chemical_risk_types(id) on delete cascade\s
            );
            
            create table chemical_supplier (
                id int not null auto_increment primary key,
                company_id int not null,
                foreign key (company_id) references empresa(idempresa) on delete cascade
            );
            
            create table chemical_physical_status (
                id int not null auto_increment primary key,
                name varchar(50) not null unique,
                description varchar(255)
            );
            
            create table chemical_product_status (
                id int not null auto_increment primary key,
                name varchar(50) not null unique
            );
            
            create table chemical_products (
                id int not null auto_increment primary key,
                name varchar(255) not null,
                cas varchar(255) not null,
                description varchar(255),
                image_url varchar(500),
                unit_id int not null,
                product_status_id int not null,
                physical_status_id int not null,
                created_at timestamp not null default current_timestamp,
                updated_at timestamp not null default current_timestamp,
                foreign key (product_status_id) references chemical_product_status(id) on delete cascade,
                foreign key (physical_status_id) references chemical_physical_status(id) on delete cascade,
                foreign key (unit_id) references chemical_units(id) on delete cascade
            );
            
            create table chemical_product_supplier (
                id int not null auto_increment primary key,
                supplier_id int not null,
                product_id int not null,
                foreign key (supplier_id) references chemical_supplier(id) on delete cascade,
                foreign key (product_id) references chemical_products(id) on delete cascade
            );
            
            create table chemical_product_responsible (
                id int not null auto_increment primary key,
                responsible_id int not null,
                product_id int not null,
                foreign key (product_id) references chemical_products(id) on delete cascade,
                foreign key (responsible_id) references chemical_users(id) on delete cascade
            );
            
            create table chemical_product_documents (
                id int not null auto_increment primary key,
                name varchar(255) not null,
                url varchar(500) not null,
                product_id int not null,
                foreign key (product_id) references chemical_products(id) on delete cascade
            );
            
            create table chemical_product_module (
                id int not null auto_increment primary key,
                name varchar(255) not null,
                message text,
                user_id int not null,
                product_id int not null,
                product_status_id int not null,
                created_at timestamp not null default current_timestamp,
                updated_at timestamp not null default current_timestamp,
                foreign key (user_id) references chemical_users(id) on delete cascade,
                foreign key (product_id) references chemical_products(id) on delete cascade,
                foreign key (product_status_id) references chemical_product_status(id) on delete cascade
            );
            
            create table chemical_product_risks (
                id int not null auto_increment primary key,
                product_id int not null,
                risk_id int not null,
                foreign key (product_id) references chemical_products(id) on delete cascade,
                foreign key (risk_id) references chemical_risks(id) on delete cascade
            );
            
            create table chemical_product_locations (
                id int not null auto_increment primary key,
                location_x float not null,
                location_y float not null,
                product_id int not null,
                area_id int not null,
                subarea_id int not null,
                foreign key (product_id) references chemical_products(id) on delete cascade,
                foreign key (area_id) references area(idarea) on delete cascade,
                foreign key (subarea_id) references subarea(idsubarea) on delete cascade
            );
        """;

    public static void main(String[] args) {
        String path = "C:\\Users\\medina\\Desktop\\mimsoft\\lisa\\mexico_chemical_products\\mexico_chemical_products\\apollo-6.0.0\\tag\\src\\main\\java\\com\\mim\\modules\\chemical_products";
        String dataSQL = SQLExample;
        String packageStr = "com.mim.modules.chemical_products";
        try {
            for (TableModel table : SqlTableParser.ExtractDataFromMultipleTableSQL(dataSQL)) {
                System.out.println("===============================");
                System.out.println(table.getTableName());
                System.out.println(table.getColumns());
                System.out.println(table.getPrimaryKeys());
                System.out.println(table.getForeignKeys());

                EntityGenerator.Create(table, path, packageStr);
                DtoGenerator.Create(table, path, packageStr);
                ServiceGenerator.Create(table, path, packageStr);
                RepositoryGenerator.Create(table, path, packageStr);
                MapperGenerator.Create(table, path, packageStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}