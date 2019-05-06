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
        if(req.body.constructor === Object && Object.keys(req.body).length === 0){
            return res.json({
                "status":"false",
                "message":"Missing All Parameter",
                "error":"false",  
            });  
        }
        else if(req.body.imageUrl == null || req.body.name == null
            || req.body.firstName == null || req.body.lastName == null
            || req.body.userName == null || req.body.dob == null || 
            req.body.gender == null || req.body.email == null || req.body.phone  == null
            || req.body.password == null || req.body.followers == null
            || req.body.following == null || req.body.adhar_number == null || 
            req.body.adhar_number == null ){
                return  res.json({
                    "status":"false",
                    "message":"Missing Paramenter",
                    "error":"false",  
                });   
    
            }

            else if(req.body.imageUrl == '' || req.body.name == ''
                || req.body.firstName == '' || req.body.lastName == ''
                || req.body.userName == '' || req.body.dob == '' || 
                req.body.gender == '' || req.body.email == '' || req.body.phone  == ''
                || req.body.password == '' || req.body.followers == ''
                || req.body.following == '' || req.body.adhar_number == '' || 
                req.body.adhar_number == '' ){
                    return  res.json({
                        "status":"false",
                        "message":"Missing Paramenter",
                        "error":"false",  
                    });   
        
                }
     const user =  this.userService.registerUser(req.body)
     this.sendRegistrationResponse(user,res)
        
    }

    public loginUser(req: Request, res: Response) {
        console.log(req.body)
        var query;
        const email = req.body.email;
        const password = req.body.password;
        const type = req.body.type
        if(req.body.type == null||req.body.type == ''){
            return res.json({
                    status: "false",
                    message: `Missing Paramater Type`,
                    error:"false"    
            })
        }
        if(req.body.email == null||req.body.email == ''){
            return res.json({
                    status: "false",
                    message: `Missing Paramater email`,
                    error:"false"    
            })
        }
        if(req.body.password == null||req.body.password == ''){
            return res.json({
                    status: "false",
                    message: `Missing Paramater password`,
                    error:"false"    
            })
        }
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
                    message: `User with email ${req.body.email} and Password ${req.body.password} doesn't exists.`,
                    error:"false"           
                }
                res.send(error);
                return;
            }
            if (this.isAlreadyLoggedIn(result)) {
                this.userService.sendAlreadylogggedInMail(result.email,result.name,result.city,result.country,result.address)
                const sucessButAlreadyLoggedin: SucessModel = {
                    status: "false",
                    message: `User with email ${req.body.email} already Logged in From Device.`,
                    error:"false" ,
                    data:result.id 

                }
                res.send(sucessButAlreadyLoggedin);
                return;
            }
            this.updateLoginStatus( 1,1,result.id, (err, success) => {
               
                if (err == null) {
                    this.userService.sendlogggedInMail(result.email,result.name,result.city,result.country,result.address)
                    Logger.logInfo(result, "login response => ");
                    const sucess: SucessModel = {
                        status: "true",
                        message: `User with email ${req.body.email}  Logged inSuccessfull.`,
                        error:"true" ,
                        data: result.id         
                    }
                    res.send(sucess)
                }
            });

        }, (error) => null, null);
    }

    public logoutUser(req: Request, res: Response) {
        const id = req.params.id;
        if(id == null || id == ''){
            return res.json({
                status: "false",
                message: `User Not Found.`,
                error:"false"   
            })
        }
        const getUser = `select * from ${Tables.user} where id = ${id};`;
        const promisedata = this.sqlService.getSingle(getUser)
        promisedata.subscribe((result)=>{
            if(_.isEmpty(result)){
                     return res.json({
                        status: "false",
                        message: `User Not Found.`,
                        error:"true"   
                     })
            }else{
                const query = `update ${Tables.user} set isLoggedIn = 0, onlineStatus = 0 where id = ${id};`;
                const promise = this.sqlService.getSingle(query);
                this.sendResponseWithonlystatusCodeError(promise, res);
            }
        })
      
    }

    public getAllUsers(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.user};`);
        this.sendResponse(user, res);
    }
    public getSingleUserData(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.user} where id = ${req.body.id};`);
        this.sendResponse(user, res);
    }
    public getUserPlaceData(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.userProfilePlace} where id = ${req.params.userId};`);
        this.sendResponse(user, res);
    }
    public getUsereducationData(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.userProfileeducation} where id = ${req.params.userId};`);
        this.sendResponse(user, res);
    }
    public getUserpublicTagData(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.userProfilepublicTag} where id = ${req.params.userId};`);
        this.sendResponse(user, res);
    }
    
    

    public updateDeviceToken(req: Request, res: Response) {
        const userId = req.body.userId;
        const token = req.body.token;
        const getUser = `select * from ${Tables.user} where id = ${userId};`;
        const promisedata = this.sqlService.getSingle(getUser)
        promisedata.subscribe((result)=>{
            if(_.isEmpty(result)){
                return res.json({
                    status: "false",
                    message: `No User Found To Update Token`,
                    error:"false"
                }) 
            }else{
                const query = `update ${Tables.user} set deviceToken = '${token}' where id = ${userId};`;
                const promise = this.sqlService.executeQuery(query);
                this.sendResponseWithonlystatusCodeError(promise,res)
            }

        })
      
       
    }

    public updatePassword(req: Request, res: Response) {
        const password = req.body.password;
        const id = req.body.id;
        if(id == null || id == ''){
            return res.json({
                status: "false",
                message: `Missing Parameter id`,
                error:"false"
            }) 
        }else if(password == null ||password == ''){
            return res.json({
                status: "false",
                message: `Missing parameter password`,
                error:"false"
            }) 
        }
        const getUser = `select * from ${Tables.user} where id = ${id};`;
        const promisedata = this.sqlService.getSingle(getUser)
        promisedata.subscribe((result)=>{
            if(_.isEmpty(result)){
                return res.json({
                    status: "false",
                    message: `User Not Found`,
                    error:"false"
                }) 
            }else{
                const query = `update ${Tables.user} set password = '${password}' where id = ${id};`;
                const promise = this.sqlService.executeQuery(query);
                this.sendResponseWithonlystatusCodeError(promise,res)
            }
        })
      
    }

    public sendForgetPasswordLink(req: Request, res: Response) {
        const email = req.body.email_number;
        const type  = req.body.type
        if(email == null|| email == ''){
            return res.json({
                "status":"false",
                "message":"Missing Parameter email",
                "error":"false",
           })
        }else if(type == null || type == ''){
            return res.json({
                "status":"false",
                "message":"Missing Parameter type",
                "error":"false",
           })
        }
        const query = `select * from ${Tables.user} where email = '${email}'`; 
        const promise = this.sqlService.getSingle(query);
        promise.subscribe((result)=>{
            if(_.isEmpty(result)){
               return res.json({
                    "status":"false",
                    "message":"No User Found",
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
        } else if(email == null||email == ''){
            return res.json({
                "status":"false",
                "message":"Missing Paramenter email",
                "error":"false",  
            });  
        }else if(name == null|| name == ''){
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
                    "data":{
                        "OTP": otp
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

    public checkUserNameExistOrNot(req: Request, res: Response) {
        if(req.body.userName == null || req.body.userName == ''){
            return  res.json({
                "status":"false",
                "message":"Missing Parameter userName",
                "error":"false", 
        })
        }
        if(req.body.email == null ){
            res.json({
                "status":"false",
                "message":"Missing Parameter email",
                "error":"false", 
        })
        }
       const  query = `select * from ${Tables.user} where email = '${req.body.email}' or userName = '${req.body.userName}'`;
        this.sqlService.getSingle(query).subscribe((result)=>{
                console.log(result)
                if(_.isEmpty(result)){
                    return res.json({
                        "status":"true",
                        "message":"userName Exist",
                        "error":"true",
                    })
                }
                res.json({
                        "status":"false",
                        "message":"userName doesn't Exist",
                        "error":"false", 
                })
        })
       
           
       }
}
