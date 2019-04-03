import * as colors from "colors/safe";
import * as stringify from "json-stringify";

export class Logger {

    public static log(info) {
        console.log(colors.yellow.bold(stringify(info, null, 2)));
        console.log();
    }

    public static logInfo(info, message = "Info") {
        console.info(colors.cyan.bold(this.getMessage("+", message)));
        console.log(colors.cyan.bold(stringify(info, null, 2)));
        console.log();
    }

    public static logError(error, message = "Error") {
        console.error(colors.red.bold(this.getMessage("+", message)));
        console.log(colors.red.bold(stringify(error, null, 2)));
        console.log();
    }

    public static logSql(query: any) {
        console.info(colors.magenta.bold(this.getMessage("+", "SQL Query")));
        console.log(colors.magenta.bold(query));
        console.log();
    }

    public static logSqlError(error: any) {
        console.error(colors.red.bold(this.getMessage("x", "SQL Error")));
        console.log(colors.red.bold(stringify(error, null, 2)));
        console.log();
    }

    private static getMessage(symbol, message): string {
        let pattern = "";
        for (let i = 0; i < 12; i++) {
            pattern += symbol;
        }
        return pattern + " " + message + " " + pattern;
    }
}