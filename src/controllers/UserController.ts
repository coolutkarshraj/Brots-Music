import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {UserService} from "../services/UserService";
import {UserModel} from "../models/UserModel";
import {Logger} from "../services/common/Logger";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";
import * as os from "os";

export class UserController extends BaseController {
    private userService: UserService;
    private sqlService: SqlService;

    constructor() {
        super();
        this.userService = new UserService();
        this.sqlService = new SqlService();
    }

    public ping(req: Request, res: Response): void {
        const result = {
            "Hostname": os.hostname(),
            "Operating system": os.type() + ", " + os.arch(),
            "Current time": new Date().toUTCString()
        };
        res.send(result);
    }

    public registerUser(req: Request, res: Response) {
        const user = this.userService.registerUser(req.body);
        this.sendResponse(user, res);
    }

    public loginUser(req: Request, res: Response) {
        const email = req.body.email;
        const password = req.body.password;
        const query = `select * from ${Tables.user} where email = '${email}' and password = '${password}'`;

        const promise = this.sqlService.getSingle(query);
        promise.subscribe((result: UserModel) => {
            if (_.isEmpty(result)) {
                res.sendStatus(401);
                return;
            }
            if (this.isAlreadyLoggedIn(result)) {
                res.sendStatus(403);
                return;
            }
            this.updateLoginStatus(result.id, 1, (err, success) => {
                if (err == null) {
                    if (result.imageUrl !== undefined) {
                        result.imageUrl = super.getImageUrl(result.id, result.imageUrl);
                    }
                    Logger.logInfo(result, "login response => ");
                    res.json(result);
                }
            });

        }, (error) => null, null);
    }

    public logoutUser(req: Request, res: Response) {
        const id = req.params.id;
        const query = `update ${Tables.user} set isLoggedIn = 0, onlineStatus = 0 where id = ${id};`;
        const promise = this.sqlService.getSingle(query);
        this.sendResponseWithStatus(promise, res);
    }

    public getAllUsers(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.user};`);
        this.sendResponse(user, res);
    }

    public updateOnlineStatus(req: Request, res: Response) {
        const userId = req.body.userId;
        const onlineStatus = req.body.onlineStatus;
        const query = `update ${Tables.user} set onlineStatus = ${onlineStatus} where id = ${userId};`;
        const promise = this.sqlService.executeQuery(query);
        this.sendResponseWithStatus(promise, res);
    }

    public updateDeviceToken(req: Request, res: Response) {
        const userId = req.body.userId;
        const token = req.body.token;
        const query = `update ${Tables.user} set deviceToken = '${token}' where id = ${userId};`;
        const promise = this.sqlService.executeQuery(query);
        this.sendResponseWithStatus(promise, res);
    }

    public updatePassword(req: Request, res: Response) {
        const email = req.body.email;
        const password = req.body.password;
        const query = `update ${Tables.user} set password = '${password}' where email = ${email};`;
        const promise = this.sqlService.executeQuery(query);
        this.sendResponseWithStatus(promise, res);
    }

    public sendForgetPasswordLink(req: Request, res: Response) {
        const email_number = req.body.forgetPasswordData;
        const token = req.body.token;
        const query = `update ${Tables.user} set deviceToken = '${token}' where id = ${email_number};`;
        const promise = this.sqlService.executeQuery(query);
        this.sendResponseWithStatus(promise, res);
    }

    private isAlreadyLoggedIn(user: UserModel): boolean {
        return user.isLoggedIn.toString() === "1";
    }

    private updateLoginStatus(userId: number, loggedInStatus: number, callback) {
        const query = `update ${Tables.user} set isLoggedIn = ${loggedInStatus} where id = ${userId};`;
        this.sqlService.executeQuery(query).subscribe((result) => {
            if (result !== null) {
                callback(null, result);
            }
        }, (error) => null, null);
    }

    public verifyRegistration(req: Request, res: Response) {
        let data = req.body;
        let email = data['email']
        let name = data["name"]
        let otp = this.getFourDigitRandomNumber();
        if(req.body.constructor === Object && Object.keys(req.body).length === 0){
            return res.json({
                "status":"false",
                "message":"Missing All Parameter",
                "error":"false",  
            });  
        } else if(email == null){
            return res.json({
                "status":"false",

                "message":"Missing Paramenter email",
                "error":"false",  
            });  
        }else if(name == null){
            return  res.json({
                "status":"false",
                "message":"Missing Paramenter Name",
                "error":"false",  
            });     
        }
    
        this.userService.userExists(email,req.body.username).subscribe((user) => {
            if((_.isEmpty(user))){
                this.userService.sendMailToVerifyRegistration(email,name,otp)
                res.json({
                    "status":"true",
                    "message":"Email Sent Succesfully",
                    "error":"true",
                    "date":{
                        "OTP": req.body.otp
                    }  
                });    
            }else{
                res.json({
                    "status":"false",
                    "message":"Email Already Exist",
                    "error":"false", 
                });   
            }
           
        });
     }


     public getFourDigitRandomNumber(min = 1000, max = 9999) {
        const numberInString = Math.floor(Math.random() * (max - min + 1) + min);
        return parseInt(numberInString.toString());
    }
}
