import {Logger} from "./Logger";

export default class QueryBuilderService {

    public getInsertQuery(tableName: string, columnData: any): string {
        // var firstItem = JSON.parse(JSON.stringify(columnData));
        let sql = `insert into ${tableName}(`;
        for (const property in columnData) {
            if (columnData.hasOwnProperty(property)) {
                sql = sql + "`" + property + "`" + ",";
            }
        }
        sql = sql.slice(0, -1) + ") select ";

        for (const property in columnData) {
            if (columnData.hasOwnProperty(property)) {
                const propType = typeof columnData[property];
                if (columnData[property] == null || columnData[property] === undefined) {
                    sql = sql + "null, ";
                } else {
                    if (propType === "boolean") { // Convert Boolean to TinyInt
                        let val = 0;
                        if (columnData[property]) {
                            val = 1;
                        }
                        sql = sql + val + ", ";
                    } else if (propType === "string") {
                        sql = sql + "'" + columnData[property] + "', ";
                    } else if (propType === "number") {
                        sql = sql + columnData[property] + ", ";
                    }
                }
            }
        }
        sql = sql.slice(0, -2);
        return sql + ";";
    }

    public getUpdateQuery(tableName: string, columnData: any, condition?: string): string {
        let sql = `update ${tableName} set `;
        for (const property in columnData) {
            if (columnData.hasOwnProperty(property) && columnData[property] !== undefined && columnData[property]) {
                sql = sql + "`" + property + "`" + " = '" + columnData[property] + "', ";
            }
        }
        sql = sql.slice(0, -2);

        if (condition) {
            sql = sql + " " + condition + ";";
        }
        // Logger.logSql(sql);
        return sql;
    }

    public getGuid(): string {
        function s4() {
            return Math.floor((1 + Math.random()) * 0x10000)
                .toString(16)
                .substring(1);
        }

        return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
            s4() + '-' + s4() + s4() + s4();
    }
}
