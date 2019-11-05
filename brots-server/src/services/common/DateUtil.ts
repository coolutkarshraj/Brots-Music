import * as moment from "moment";
import {Logger} from "./Logger";

export class DateUtil {
    private static dateFormat = "YYYY-MM-DD";
    private static dateTimeFormat = DateUtil.dateFormat + " HH:mm:ss";

    public static formatToCurrentDate(): string {
        return moment(new Date()).format(DateUtil.dateFormat);
    }

    public static formatToCurrentDateTime(): string {
        return moment(new Date()).format(DateUtil.dateTimeFormat);
    }

    public static formatToDateTime(date: string | moment.Moment): string {
        return moment(date).format(DateUtil.dateTimeFormat);
    }

    public static Now(): moment.Moment {
        return moment(new Date());
    }

    public static toDate(date: string | moment.Moment): moment.Moment {
        return moment(date);
    }

    public static getDayOfWeek(date: moment.Moment): string {
        return moment(date).format("dddd");
    }

    public static getDateTime(datePart: string | moment.Moment, timePart: string | moment.Moment): moment.Moment {
        // Logger.logInfo1("DATEPART", datePart);
        // Logger.logInfo1("timePart", timePart);
        const time = moment(timePart);
        return moment(datePart).startOf("day").hour(time.hour()).minute(time.minute()).second(time.seconds())
    }
}