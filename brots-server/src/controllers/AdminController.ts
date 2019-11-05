import { Request, Response } from "express";
import { BaseController } from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import { AdminServices } from "../services/AdminServices";
import { Tables } from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";
import * as Rx from 'rxjs/Rx';

import { Config } from "../Config";
import { EmailService } from '../services/EmailServices';
const { Admin } = require("../category/sqlService")



export class AdminController extends BaseController {
    private adminServices: AdminServices;
    private sqlService: SqlService;
    private emailService: EmailService;


    constructor() {
        super();
        this.adminServices = new AdminServices();
        this.sqlService = new SqlService();
        this.emailService = new EmailService();
    }
    public loginAdmin(req: Request, res: Response) {
        const email = req.body.email;
        const password = req.body.password;
        if (req.body.constructor === Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing All Parameter",
                "error": "false",
            });
        }
        else if (email == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parrameter Email",
                "error": "false",
            });;
        }
        else if (password == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parrameter Password",
                "error": "false",
            });;
        }
        const user1 = this.adminServices.loginUser(req.body);
        this.adminServices.updateLoggedinStatus(req.body.email)
        console.log(user1);
        this.sendResponse(user1, res);


    }

    public logoutAdmin(req: Request, res: Response) {
        const id = req.params.id;
        const query = `update ${Tables.admin} set isLoggedIn = 0, onlineStatus = 0 where id = ${id};`;
        const promise = this.sqlService.getSingle(query);
        this.sendResponseWithStatus(promise, res);
    }

    public getAll(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.admin};`);
        this.sendResponse(user, res);
    }

    public blockUser(req: Request, res: Response) {
        const userId = req.body.userId;
        const onlineStatus = req.body.onlineStatus;
        const promise = this.adminServices.block_unblock_User(userId, onlineStatus);
        this.sendResponseWithStatus(promise, res);
    }
    public unblockUser(req: Request, res: Response) {
        const userId = req.body.userId;
        const onlineStatus = req.body.onlineStatus;
        const promise = this.adminServices.block_unblock_User(userId, onlineStatus);
        this.sendResponseWithStatus(promise, res);
    }
    public updateDeviceToken(req: Request, res: Response) {
        if (req.body.constructor === Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing All Parameter",
                "error": "false",
            });
        }
        else if (req.body.id == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parrameter id",
                "error": "false",
            });;
        }
        else if (req.body.deviceToken == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parrameter Token",
                "error": "false",
            });;
        }
        const userId = req.body.id;
        const token = req.body.deviceToken;
        const promise = this.adminServices.updateDeviceToken(userId, token);
        this.sendResponseWithStatus(promise, res);
    }
    public getPasswordResetCode(req: Request, res: Response) {
        const userEmail = req.body.email;
        const userType = req.body.userType;
        if (req.body.constructor === Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing All Parameter",
                "error": "false",
            });
        } else if (req.body.email == null) {
            return res.json({
                "status": "false",
                "message": "Missing Paramenter email",
                "error": "false",
            });
        } else if (req.body.userType == null) {
            return res.json({
                "status": "false",
                "message": "Missing Paramenter userType",
                "error": "false",
            });
        }
        this.getUserUsingEmail(userEmail, userType).subscribe((user) => {
            if (_.isEmpty(user)) {
                return res.json({
                    "status": "false",
                    "message": "Email Not Found",
                    "error": "false",
                });;
            }
            const userName = user.firstName + ' ' + user.lastName;
            const userCode = this.getSixDigitRandomNumber();
            const emailData = {
                email: userEmail,
                subject: 'Welcome To Brots',
            };
            const templateModal = {
                name: userName,
                code: userCode,
            };
            this.emailService.sendMail(emailData, templateModal, Config.mailTemplate.forgetPassword);
            res.json({
                "status": "true",
                "Code": "200",
                "message": "Email Sent Sucessfull",
                "error": "true",
                "data": {
                    passcode: userCode
                }

            });
        });
    }
    public getUserUsingEmail(email, userType): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const user1 = Admin.findAll({
                where: {
                    Email: email,
                    userType: userType
                }
            })
            resolve(user1);
        });
        return Rx.Observable.fromPromise(promise);
    }

    public getSixDigitRandomNumber(min = 1000, max = 9999) {
        const numberInString = Math.floor(Math.random() * (max - min + 1) + min);
        return parseInt(numberInString.toString());
    }

    public updatePassword(req: Request, res: Response) {
        if (req.body.constructor === Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing All Parameter",
                "error": "false",
            });
        } else if (req.body.email == null) {
            return res.json({
                "status": "false",
                "message": "Missing Paramenter email",
                "error": "false",
            });
        } else if (req.body.userType == null) {
            return res.json({
                "status": "false",
                "message": "Missing Paramenter userType",
                "error": "false",
            });
        }
        else if (req.body.password == null) {
            return res.json({
                "status": "false",
                "message": "Missing Paramenter Password",
                "error": "false",
            });
        }

        this.getUserUsingEmail(req.body.email, req.body.userType).subscribe((user) => {
            if (_.isEmpty(user)) {
                return res.json({
                    "status": "false",
                    "message": "Email Not Found",
                    "error": "false",
                });;
            }
            const getUserquery = this.adminServices.updatePassword(req.body.email, req.body.userType, req.body.password);
            this.sendResponse(getUserquery, res);

        });


    }


    public getProfile(req: Request, res: Response) {
        if (req.body.constructor === Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing All Parameter",
                "error": "false",
            });
        } else if (req.body.id == null) {
            return res.json({
                "status": "false",
                "message": "Missing Paramenter id",
                "error": "false",
            });
        } else if (req.body.userType == null) {
            return res.json({
                "status": "false",
                "message": "Missing Paramenter userType",
                "error": "false",
            });
        }

        this.adminServices.getUserUsingid(req.body.id, req.body.userType).subscribe((user) => {
            if (_.isEmpty(user)) {
                return res.json({
                    "status": "false",
                    "message": "Email Not Found",
                    "error": "false",
                });;
            }
            let result = this.adminServices.getUserUsingid(req.body.id, req.body.userType);
            this.sendResponse(result, res);

        });


    }

    public updateProfile(req: Request, res: Response) {
        const user = this.adminServices.updateProfile(req, res);
        this.sendResponsewithMulter(user, res);

    }

    public ChangePassword(req: Request, res: Response) {
        console.log(req.body);
        if (req.body.constructor === Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing All Parameter",
                "error": "false",
            });
        }
        this.adminServices.getUserUsingEmailIdPasswordUserType(req).subscribe((user1) => {
            if (_.isEmpty(user1)) {
                return res.json({
                    "status": "false",
                    "message": "Email Not Found",
                    "error": "false",
                });
            }
            const getUserquery = this.adminServices.updatePassword(req.body.email, req.body.userType, req.body.newPassword);
            this.sendResponse(getUserquery, res)


        });
    }

}
