package com.github.medina1402.parser;

import com.github.medina1402.model.ColumnModel;
import com.github.medina1402.model.ForeignKeyModel;
import com.github.medina1402.model.TableModel;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.create.table.*;
import net.sf.jsqlparser.statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class SqlTableParser {
    public static List<TableModel> ExtractDataFromMultipleTableSQL(String sql) throws Exception {
        List<TableModel> tables = new ArrayList<>();
        CCJSqlParserUtil.parseStatements(sql).getStatements().forEach(statement -> {
            try {
                tables.add(ExtractDataFromTableSQL(null, statement));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return tables;
    }

    public static TableModel ExtractDataFromTableSQL(String sql, Statement statement) throws Exception {
        statement = statement == null ? CCJSqlParserUtil.parse(sql) : statement;

        if (!(statement instanceof CreateTable)) {
            throw new IllegalArgumentException("No es una sentencia CREATE TABLE.");
        }

        CreateTable createTable = (CreateTable) statement;
        TableModel table = new TableModel();
        table.setTableName(createTable.getTable().getName());

        // Extraer columnas y detectar PK embebido
        for (ColumnDefinition columnDef : createTable.getColumnDefinitions()) {
            ColumnModel column = new ColumnModel();
            column.setName(columnDef.getColumnName());
            column.setType(columnDef.getColDataType().getDataType());

            // Verifica si la columna declara PRIMARY KEY directamente
            if (columnDef.getColumnSpecs() != null) {
                for (String spec : columnDef.getColumnSpecs()) {
                    if (spec.equalsIgnoreCase("primary") || spec.equalsIgnoreCase("key")) {
                        column.setPrimaryKey(true);
                        if (!table.getPrimaryKeys().contains(column.getName())) {
                            table.getPrimaryKeys().add(column.getName());
                        }
                    }
                }
            }

            table.getColumns().add(column);
        }

        // Extraer claves primarias explícitas (por índice separado)
        if (createTable.getIndexes() != null) {
            for (Index idx : createTable.getIndexes()) {
                if ("PRIMARY KEY".equalsIgnoreCase(idx.getType())) {
                    for (String pk : idx.getColumnsNames()) {
                        table.getPrimaryKeys().add(pk);
                        table.getColumns().forEach(col -> {
                            if (col.getName().equals(pk)) col.setPrimaryKey(true);
                        });
                    }
                }

                if (idx instanceof ForeignKeyIndex fkIndex) {
                    List<String> columnNames = fkIndex.getColumnsNames();
                    List<String> referencedColumns = fkIndex.getReferencedColumnNames();
                    String referencedTable = fkIndex.getTable().getName();

                    for (int i = 0; i < columnNames.size(); i++) {
                        ForeignKeyModel fkModel = new ForeignKeyModel();
                        fkModel.setColumn(columnNames.get(i));
                        fkModel.setReferencedTable(referencedTable);
                        fkModel.setReferencedColumn(referencedColumns.get(i));
                        table.getForeignKeys().add(fkModel);
                    }
                }
            }
        }

        return table;
    }
}
