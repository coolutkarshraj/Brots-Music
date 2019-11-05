import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
const { notificationModel } = require("../category/sqlService")
export class NotificationServices extends ServiceBase {

    constructor() {
        super();
    }

     public sendAddNoteNotificationToUser(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            let notification = notificationModel.create({
                Name: req.body.Name, Title: req.body.Title,
                NotificationTime:new Date().toUTCString(),
                Description: req.body.Description, userId: req.body.userId,
                userType :req.body.userType
               
            })
            resolve(notification)
        });
        return Rx.Observable.fromPromise(promise);
    }
    public sendUploadNotificationToUser(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            let notification = notificationModel.create({
                Name: req.body.Name, Title: req.body.Title,
                NotificationTime:new Date().toUTCString(),
                Description: req.body.Description, userId: req.body.userId,
                userType :req.body.userType
               
            })
            resolve(notification)
        });
        return Rx.Observable.fromPromise(promise);
    }
  
}
