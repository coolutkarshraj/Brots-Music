import { Request, Response } from "express";
import { BaseController } from "./common/BaseController";
import { SearchFriendServices } from "../services/SearchFriendServices";
import * as _ from "lodash";
import * as __ from "underscore";
import { AdminSongServices } from '../services/AdmiSongServices';

export class SearchFriendController extends BaseController {
    private searchFriendServices: SearchFriendServices;
    protected adminsongServices: AdminSongServices;

    constructor() {
        super();
        this.searchFriendServices = new SearchFriendServices();
        this.adminsongServices = new AdminSongServices();
    }

    public searchFriend(req: Request, res: Response) {
        var type;
        if (req.body.constructor === Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter",
                "error": "false",
            })
        } else if (req.body.id == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter userId",
                "error": "false",
            })
        }
        if(req.body.name == ""){
             console.log('djfhdjfhdjfhdjhfdhf')  
             type = "1"
        }else{
            console.log("rjueiurieruierueirueiriuiure")
            console.log(req.body.name)
            type = "0"  
        }
        this.searchFriendServices.searchFriend(req, type).subscribe((notifications) => {
            if (_.isEmpty(notifications)) {
                return res.send({
                    "status": "false",
                    "message": "User Not Found",
                    "error": "false",
                })
            } else if (!_.isEmpty(notifications)) {
                var i = 0;
                _.each(notifications, (notification) => {
                    this.searchFriendServices.checkForGetFriend(req.body.id, notification.id).subscribe((result) => {

                        if (_.isEmpty(result)) {
                            const result = this.searchFriendServices.checkForSendFriend(req.body.id, notification.id)
                            result.subscribe((sendFriend) => {

                                if ((_.isEmpty(sendFriend))) {
                                    notifications[i].dataValues["issendFriendRequest"] = "0"
                                    notifications[i].dataValues["isgetFriendRequest"] = "0"
                                    notifications[i].dataValues["StatusFriend"] = "noStatus"
                                    i++;
                                } else {
                                    notifications[i].dataValues["issendFriendRequest"] = "0"
                                    notifications[i].dataValues["isgetFriendRequest"] = "1"
                                    notifications[i].dataValues["StatusFriend"] = sendFriend.Status
                                    i++;
                                }
                                if (i == notifications.length) {
                                    res.json({
                                        "status": "true",
                                        "message": "List Of Friend",
                                        "error": "true",
                                        "data": notifications
                                    })
                                }
                            })

                        } else {
                            notifications[i].dataValues["issendFriendRequest"] = "1"
                            notifications[i].dataValues["isgetFriendRequest"] = "0"
                            notifications[i].dataValues["StatusFriend"] = result.Status
                            i++;
                        }

                        if (i == notifications.length) {
                            res.json({
                                "status": "true",
                                "message": "List Of Friend",
                                "error": "true",
                                "data": notifications
                            })
                        }



                    })
                },

                );
            }

        })


    }

    public getAllTrendingSong(req: Request, res: Response): void {
        const allSong = this.searchFriendServices.getAllTrendingSong(req)
        this.sendResponse(allSong, res);
    }

    public getAllDispopularSong(req: Request, res: Response): void {
        const allSong = this.searchFriendServices.getAllDispopularSong(req)
        this.sendResponse(allSong, res);
    }



    public getArtistDashboardData(req: Request, res: Response) {
        if (req.body.artistId == null) {
            return res.json({
                "status": "false",
                "message": "Missing Pararmeter Id",
                "error": "false"
            })
        }
        this.searchFriendServices.getAllDashboardData(req).subscribe((songData) => {
            if (_.isEmpty(songData)) {
                return res.json({
                    "status": "false",
                    "message": "No SONG FOUND",
                    "error": "false",
                })
            } else {
                this.adminsongServices.getAllSingleArtistSong(req).subscribe((listSong) => {
                    if (_.isEmpty(songData)) {

                    } else {
                        this.searchFriendServices.getAllArtistFollower(req).subscribe((totalFollowers) => {
                            return res.json({
                                "status": "true",
                                "message": "DashBoard Data",
                                "error": "true",
                                "total_followers": totalFollowers,
                                "totalData": songData,
                                "song": listSong
                            })
                        })
                    }
                })

            }


        })

    }


    public sendGCMNotification(req: Request, res: Response) {
        var message;
        if (req.body.type == 0) {
            message = super.geGCMMessageSentFriendRequest();
        } else if (req.body.type == 2) {
            message = super.geGCMMessageSentFriendRequest();
        } else if (req.body.type == 3) {
            message = super.geGCMMessageSentFriendRequest();
        } else if (req.body.type == 4) {
            message = super.geGCMMessageSentFriendRequest();
        } else {
            message = super.geGCMMessageSentFriendRequest();
        }
        this.searchFriendServices.getUserDeviceTokens(req).subscribe((token) => {
            console.log(token.dataValues['deviceToken'])
            if (_.isEmpty(token)) {
                return res.json({
                    "status": "false",
                    "message": "No USER FOUND",
                    "error": "false"
                })
            } else {
                super.sendGCMNotification(message, token.dataValues['deviceToken'], (isNotificationSent) => {
                    if (isNotificationSent) {

                        res.json({
                            "status": "true",
                            "message": "Notification Sent Successfully",
                            "error": "true"
                        });
                    }
                });
            }
        })
    }
}
