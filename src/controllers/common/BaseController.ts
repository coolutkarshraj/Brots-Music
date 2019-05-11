import * as Rx from "rxjs/Rx";
import {Logger} from "../../services/common/Logger";
import {Config} from "../../Config";
import * as gcm from "node-gcm";

export class BaseController {
    protected sendResponse(observable: Rx.Observable<any>, res) {
        observable.subscribe((result) => {
            const ResponseData = {
                "status":"true",
                "Code":200,
                "error":"true",
                "data":result
        }
            res.send(ResponseData);
        }, (err) => {
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {
                const ResponseData = {
                    "status":"false",
                    "Code":404,
                    "error":"false",
            }
                res.send(ResponseData)
            } else {
                const ResponseData = {
                    "status":"false",
                    "Code":500,
                    "error":"false",
            }
                res.send(ResponseData)
            }
        }, null);
    }

    protected sendResponseWithonlystatusCodeError(observable: Rx.Observable<any>, res) {
        observable.subscribe((result) => {
            const registrationResponse = {
                "status":"true",
                "Code":200,
                "error":"true"
        }
            res.json(registrationResponse);
        }, (err) => {
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {
               res.json({
                "status":"False",
                "Code":404,
                "error":"False",  
              })
            } else {
               res.json({
                "status":"False",
                "Code":422,
                "error":"False", 
              })
            }
        }, null);
    }
    protected sendRegistrationResponse(observable: Rx.Observable<any>, res) {
        observable.subscribe((result) => {
            const registrationResponse = {
                "status":"true",
                "Code":200,
                "error":"true",
                "data":result.id
        }
            res.json(registrationResponse);
        }, (err) => {
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {
               res.json({
                "status":"False",
                "Code":404,
                "error":"False",  
              })
            } else {
               res.json({
                "status":"False",
                "Code":422,
                "error":"False", 
              })
            }
        }, null);
    }
    protected sendResponseWithoutData(observable: Rx.Observable<any>, res) {
        observable.subscribe((result) => {
            const registrationResponse = {
                "status":"true",
                "Code":200,
                "error":"true",
        }
            res.json(registrationResponse);
        }, (err) => {
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {
               res.json({
                "status":"False",
                "Code":404,
                "error":"False",  
              })
            } else {
               res.json({
                "status":"False",
                "Code":500,
                "error":"False", 
              })
            }
        }, null);
    }
   

    protected sendResponseWithKey(key: string, observable: Rx.Observable<any>, res) {
        observable.subscribe((result) => {
            res.json({[key]: result});
        }, (err) => {
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {
                res.status(404).json({error: err});
            } else {
                res.status(500).json({error: err});
            }
        }, null);
    }

    protected sendResponseWithStatus(observable: Rx.Observable<any>, res) {
        observable.subscribe((result) => {
            res.sendStatus(200);
        }, (err) => {
            Logger.logError(err);
            res.sendStatus(400);
        }, null);
    }

    protected sendGCMNotification(message: object, tokens, callback) {
        const sender = new gcm.Sender(Config.gcmAPIKey);
        sender.send(message, {registrationTokens: tokens}, (err, resp) => {
            if (err) {
                Logger.logError(err, "error in sending notification");
                callback(false);
                return;
            }
            Logger.logInfo(resp, "notification sent successfully");
            callback(true);
        });
    }

    protected geGCMMessage(body?: object) {
        const message = new gcm.Message({
            data: {
                messageId: "1",
                messageType: "inbox",
                message: "You got a new order"
            }
        });
        return message;
    }

    protected getImageUrl(userId: number, url: string): string {
        if (url === undefined || url === null || url.length === 0) {
            return `${Config.site.baseUrl}storage/common/default_user.png`;
        } else {
            return `${Config.site.baseUrl}storage/distributor/${userId}/${url}`;
        }
    }
}
