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
import {ErrorModel} from "../models/ErrorModel";
import {SucessModel} from "../models/SucessModel";
import { json } from "body-parser";

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
     const user =  this.userService.registerUser(req.body)
     this.sendResponseWithonlystatusCodeError(user,res)
        
    }

    public loginUser(req: Request, res: Response) {
        var query;
        const email = req.body.email;
        const password = req.body.password;
        const type = req.body.type
        if(type == "1"){
            query = `select * from ${Tables.user} where email = '${email}' and password = '${password}'`;
        }else if(type == "2"){
            query = `select * from ${Tables.user} where userName = '${email}' and password = '${password}'`;
        }
        const promise = this.sqlService.getSingle(query);
        promise.subscribe((result: UserModel) => {
            if (_.isEmpty(result)) {
                const error: ErrorModel = {
                    status: "false",
                    message: `User with email ${req.body.email} doesn't exists.`,
                    error:"false"           
                }
                res.send(error);
                return;
            }
            if (this.isAlreadyLoggedIn(result)) {
                this.userService.sendAlreadylogggedInMail(result.email,result.name,result.city,result.country,result.address)
                const error: ErrorModel = {
                    status: "false",
                    message: `User with email ${req.body.email} already Logged in From Device.`,
                    error:"false"           
                }
                res.send(error);
                return;
            }
            this.updateLoginStatus( 1,1,result.id, (err, success) => {
               
                if (err == null) {
                    this.userService.sendlogggedInMail(result.email,result.name,result.city,result.country,result.address)
                    Logger.logInfo(result, "login response => ");
                    const sucess: SucessModel = {
                        status: "true",
                        message: `User with email ${req.body.email}  Logged inSuccessfull.`,
                        error:"true"           
                    }
                    res.send(sucess)
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


    public updateDeviceToken(req: Request, res: Response) {
        const userId = req.body.userId;
        const token = req.body.token;
        const query = `update ${Tables.user} set deviceToken = '${token}' where id = ${userId};`;
        const promise = this.sqlService.executeQuery(query);
        promise.subscribe((result)=>{
            if(_.isEmpty(result)){
                return res.json({
                    status: "false",
                    message: `SomeThing Went Wrong`,
                    error:"false"    
                })
               

            }
            const query = `select * from ${Tables.user} where email = '${req.body.email}' and id = '${req.body.userId}'`;  
            const User = this.sqlService.getSingle(query) 
            User.subscribe((result)=>{
                if(_.isEmpty(result)){
                   return res.json({
                        status: "false",
                        message: `No User Found To Update Token`,
                        error:"false"
                    }) 
                }
                res.json({
                    status: "true",
                    message: `Token Updated Successfully`,
                    error:"true",
                    data:result 
                })
            })
           
        })     
    }

    public updatePassword(req: Request, res: Response) {
        const email = req.body.email;
        const type =  req.body.type
        const password = req.body.password;
        const name = req.body.name;
        const id = req.body.id;
        const query = `update ${Tables.user} set password = '${password}' where id = ${id};`;
        const promise = this.sqlService.executeQuery(query);
        promise.subscribe((result)=>{
            if(_.isEmpty(result)){
                return res.json({
                    status: "false",
                    message: `Password Not Updated`,
                    error:"false"
                }) 
            }
          this.userService.SendupdatePasswordMail(email,name,password)
         res.json({
            status: "true",
            message: `Password Updated SuccessFully`,
            error:"true",
         })
        })
    }

    public sendForgetPasswordLink(req: Request, res: Response) {
        const email = req.body.email_number;
        const type  = req.body.type
        const query = `select * from ${Tables.user} where email = '${email}'`; 
        const promise = this.sqlService.getSingle(query);
        promise.subscribe((result)=>{
            if(_.isEmpty(result)){
               return res.json({
                    "status":"false",
                    "message":"Email not Sent Succesfully",
                    "error":"false",
               })
            }
                let otp = this.getFourDigitRandomNumber();
                this.userService.forgetPasswordMail(result.email,result.name,otp)
                res.json({
                    "status":"true",
                    "message":"Email Sent Succesfully",
                    "error":"true",
                    "date":{
                        "OTP": otp,
                        "name":result.name,
                        "id":result.id
                    }  
                });    
        })
    
    }

    private isAlreadyLoggedIn(user: UserModel): boolean {
        return user.isLoggedIn.toString() === "1";
    }

    private updateLoginStatus(onlineStatus: number, loggedInStatus: number, id:number, callback) {
        const query = `update ${Tables.user} set isLoggedIn = ${loggedInStatus} ,onlineStatus = ${onlineStatus} where id = ${id};`;
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
