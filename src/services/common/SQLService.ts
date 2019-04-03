import * as mysql from "mysql";
import {Config} from "../../Config";
import * as Rx from "rxjs/Rx";
import {Logger} from "./Logger";

const pool = mysql.createPool(Config.db);

export default class SQLService {
    public executeQuery(query): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            Logger.logSql(query);
            pool.getConnection((err, connection) => {
                if (err) {
                    Logger.logError(err, Config.messages.connectionError);
                    reject(null);
                    return;
                }
                connection.query(query, (err1, rows) => {
                    if (err1) {
                        Logger.logSqlError(err1);
                        connection.release();
                        reject(err1);
                        return;
                    }
                    const str = JSON.stringify(rows);
                    const result = JSON.parse(str);
                    connection.release();
                    resolve(result);
                });
            });
        });
        return Rx.Observable.fromPromise(promise);
    }

    public executeQuery1(query): any {
        const promise = new Promise((resolve, reject) => {
            pool.getConnection((err, connection) => {
                Logger.logSql(query);
                if (err) {
                    Logger.logError(err, Config.messages.connectionError);
                    reject(null);
                    return;
                }
                connection.query(query, (err1, rows) => {
                    if (err1) {
                        Logger.logSqlError(err1);
                        connection.release();
                        reject(err1);
                        return;
                    }
                    const str = JSON.stringify(rows);
                    const result = JSON.parse(str);
                    connection.release();
                    resolve(result);
                });
            });
        });
        return promise;
    }

    public getSingle(query): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            Logger.logSql(query);
            pool.getConnection((err, connection) => {
                if (err) {
                    Logger.logError(err, Config.messages.connectionError);
                    reject(null);
                    return;
                }
                connection.query(query, (err1, rows) => {
                    if (err1) {
                        Logger.logSqlError(err1);
                        connection.release();
                        reject(null);
                        return;
                    }
                    if (rows && rows.length > 0) {
                        const str = JSON.stringify(rows[0]);
                        const result = JSON.parse(str);
                        connection.release();
                        resolve(result);
                    } else {
                        connection.release();
                        resolve(null);
                    }
                });
            });
        });
        return Rx.Observable.fromPromise(promise);
    }

    public executeBulkQuery(query, values): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            pool.getConnection((err, connection) => {
                connection.query(query, [values], (err1, rows) => {
                    if (err1) {
                        Logger.logSqlError(err1);
                        connection.release();
                        reject(err1);
                        return;
                    }
                    const str = JSON.stringify(rows);
                    const result = JSON.parse(str);
                    connection.release();
                    Logger.logInfo(result);
                    resolve(result);
                });
            });
        });
        return Rx.Observable.fromPromise(promise);
    };
}
