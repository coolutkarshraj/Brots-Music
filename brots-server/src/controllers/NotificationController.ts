import { Request, Response } from "express";
import { BaseController } from "./common/BaseController";
import { SearchFriendServices } from "../services/SearchFriendServices";
import * as _ from "lodash";
import * as __ from "underscore";
import { NotificationServices } from '../services/NotificationServices';
import * as gcm from "node-gcm";
import SqlService from "../services/common/SQLService";
import {Logger} from "../services/common/Logger";

export class NotificationController extends BaseController {
    private notificationServices :NotificationServices;
    private sqlService: SqlService;

    constructor() {
        super();
        this.sqlService = new SqlService();
        this.notificationServices =  new NotificationServices();
      
    }

    
    public sendAddNoteNotificationToUser(req: Request, res: Response) {
        const Notification = this.notificationServices.sendAddNoteNotificationToUser(req);
        const message = this.geGCMMessage(req);
        this.getUserDeviceTokens((tokens) => {

            if (tokens.length === 0) {
                return res.json({
                    "status": "false",
                    "message": "No USER FOUND",
                    "error": "false"
                })
                
            }

            super.sendAddNoteGCMNotification(message, tokens, (isNotificationSent) => {
                if (isNotificationSent) {
                    res.json({
                        "status": "true",
                        "message": "Notification Sent Successfully",
                        "error": "true"
                    });
                }else
                {
                    return res.json({
                        "status": "false",
                        "message": "No USER FOUND",
                        "error": "false"
                    })  
                }
            });
        });

    }


    public sendUploadSongeNotificationToUser(req: Request, res: Response) {
        const message = this.geGCMMessageUpload(req);
        this.getUserDeviceTokens((tokens) => {

            if (tokens.length === 0) {
                return res.json({
                    "status": "false",
                    "message": "No USER FOUND",
                    "error": "false"
                })
                
            }

            super.sendAddNoteGCMNotification(message, tokens, (isNotificationSent) => {
                if (isNotificationSent) {
                    res.json({
                        "status": "true",
                        "message": "Notification Sent Successfully",
                        "error": "true"
                    });
                }else
                {
                    return res.json({
                        "status": "false",
                        "message": "No USER FOUND",
                        "error": "false"
                    })  
                }
            });
        });

    }

    protected geGCMMessage(req) {
        const message = new gcm.Message({
            data: {
                messageId: "1",
                messageType: "Add Note",
                Title: "New Note Has been Added",
                Name:req.body.Name,
                UserId:req.body.userId,
                imageUrl:req.body.imageUrl,
                userType:req.body.userType,
                Time:new Date().toUTCString()
            }
        });
        return message;
    }


    protected geGCMMessageUpload(req) {
        const message = new gcm.Message({
            data: {
                messageId: "1",
                messageType: "New Song Uploaded",
                Title: "New Song Has been Uploaded Please hear",
                Name:req.body.Name,
                UserId:req.body.userId,
                imageUrl:req.body.imageUrl,
                userType:req.body.userType,
                Time:new Date().toUTCString()
            }
        });
        return message;
    }

    private getUserDeviceTokens(callback) {
        const query = `select deviceToken from user where deviceToken is not null;`;
        this.sqlService.executeQuery(query).subscribe((users) => {
            if (users != null) {

                const tokens = [];
                _.each(users, (user) => {
                    tokens.push(user.deviceToken);
                });

                Logger.logInfo(`${tokens.length} users available to notify`);
                callback(tokens);
            }
        });
    }


}
