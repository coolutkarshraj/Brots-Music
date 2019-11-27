import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {UserService} from "../services/UserService";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";
import * as os from "os";
import * as Rx from 'rxjs/Rx';
import { Config } from "../Config";
import { EmailService } from '../services/EmailServices';
const { user} = require("../category/sqlService")



export class UserController extends BaseController {
    private userService: UserService;
    private sqlService: SqlService;
    private emailService: EmailService;
    

    constructor() {
        super();
        this.userService = new UserService();
        this.sqlService = new SqlService();
        this.emailService = new EmailService();
    }

    public ping(req: Request, res: Response): void {
        const result = {
            "Hostname": os.hostname(),
            "Operating system": os.type() + ", " + os.arch(),
            "Current time": new Date().toUTCString()
        };
        res.send(result);
    }
    public verifyRegistration(req: Request, res: Response) {
        let data = req.body;
        let email = data['email']
        let otp = data["otp"]
        let name = data["name"]
        console.log(req.body);
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
        }else if(otp == null){
            return res.json({
                "status":"false",
                "message":"Missing Paramenter OTP",
                "error":"false",  
            }); 
                
        }else if(name == null){
            return  res.json({
                "status":"false",
                "message":"Missing Paramenter Name",
                "error":"false",  
            });     
        }
        this.userService.userExists(email).subscribe((user) => {
            if((_.isEmpty(user))){
                this.userService.sendMailAfterLogin(email,name,otp)
                res.json({
                    "status":"true",
                    "message":"Email Sent Succesfully",
                    "error":"true",
                    "date":{
                        passcode: req.body.otp,
                        "message":"Email Sent Successfully"
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
   public registerUser(req: Request, res: Response) {
 
        if(req.body.constructor === Object && Object.keys(req.body).length === 0){
            return res.json({
                "status":"false",
                "message":"Missing All Parameter",
                "error":"false",  
            });  
        }
      else if(req.body.email == null || req.body.name == null
        || req.body.firstName == null || req.body.userType == null
        || req.body.gender == null || req.body.dob == null || 
        req.body.password == null ){
            return  res.json({
                "status":"false",
                "message":"Missing Paramenter",
                "error":"false",  
            });   

        }
        else { 
            if(req.body.lastName == null){
                req.body.lastName = "";
            }
            const user1 = this.userService.registerUser(req.body);
            this.sendResponseAfterLogin(user1, res);
        }
      
    }

    public loginUser(req: Request, res: Response) {
     
        const email = req.body.email;
        const password = req.body.password;
        if(req.body.constructor === Object && Object.keys(req.body).length === 0){
            return res.json({
                "status":"false",
                "message":"Missing All Parameter",
                "error":"false",  
            });  
        } 
        else if(email== null){
            return res.json({
                "status":"false",
                "message":"Missing Parrameter Email",
                "error":"false",   
            });;   
        }
        else if(password== null){
            return res.json({
                "status":"false",
                "message":"Missing Parrameter Password",
                "error":"false",   
            });;   
        }
        const user1 = this.userService.loginUser(req.body);
        //this.userService.updateLoggedinStatus(req.body.email)
        console.log(user1);
            this.sendResponseLogin(user1, res);
    
      
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

    public blockUser(req: Request, res: Response) {
        console.log(req.body)
        const userId = req.body.userId;
        const isLoggedIn = req.body.isLoggedIn;
        if(req.body.userId ==  null){
         return res.json({
                "status":"false",
                "message":"Missing Parrameter userId",
                "error":"false",   
           })
        }else if(req.body.isLoggedIn == null){
            return res.json({
                "status":"false",
                "message":"Missing Parrameter isLoggedIn",
                "error":"false",     
           })
        }
        const promise = this.userService.block_unblock_User(userId,isLoggedIn);
        this.sendResponseWithStatus(promise, res);
    }
    public unblockUser(req: Request, res: Response) {
        const userId = req.body.userId;
        const isLoggedIn = req.body.isLoggedIn;
        if(userId ==  null){
           return res.json({
            "status":"false",
            "message":"Missing Parrameter userId",
            "error":"false",   
           })
        }else if(isLoggedIn == null){
            return res.json({
                "status":"false",
                "message":"Missing Parrameter isLoggedIn",
                "error":"false",     
           })
        }
        const promise = this.userService.block_unblock_User(userId,isLoggedIn);
        this.sendResponseWithStatus(promise, res);
    }
    public updateDeviceToken(req: Request, res: Response) {
        if(req.body.constructor === Object && Object.keys(req.body).length === 0){
            return res.json({
                "status":"false",
                "message":"Missing All Parameter",
                "error":"false",  
            });  
        } 
        else if(req.body.id== null){
            return res.json({
                "status":"false",
                "message":"Missing Parrameter id",
                "error":"false",   
            });;   
        }
        else if(req.body.deviceToken== null){
            return res.json({
                "status":"false",
                "message":"Missing Parrameter Token",
                "error":"false",   
            });;   
        }
        console.log(req.body);
        const userId = req.body.id;

        const token = req.body.deviceToken;
        const promise = this.userService.updateDeviceToken(userId,token);
        this.sendResponseWithStatus(promise, res);
    }

 

    private updateLoginStatus(userId: number, loggedInStatus: number, callback) {
        const promise = new Promise((resolve, reject) => { 
        loggedInStatus = 1;
        const resule = user.findById(userId).then(user =>{
            user.isLoggedIn = loggedInStatus;
         })
         resolve(resule);
            if (resule !== null) {
                callback(null, resule);
            }
  
    });
   
      return Rx.Observable.fromPromise(promise);
    }

    public getPasswordResetCode(req: Request, res: Response) {
        const userEmail = req.body.email;
        const userType = req.body.userType;
        console.log(req.body);
        console.log(req.body.userType);
        console.log(req.body.email);
        if(req.body.constructor === Object && Object.keys(req.body).length === 0){
            return res.json({
                "status":"false",
                "message":"Missing All Parameter",
                "error":"false",  
            });  
        }else if(req.body.email == null){
            return res.json({
                "status":"false",
                "message":"Missing Paramenter email",
                "error":"false",  
            });     
        }else if(req.body.userType == null){
            return res.json({
                "status":"false",
                "message":"Missing Paramenter userType",
                "error":"false",  
            });     
        }
        this.getUserUsingEmail(userEmail, userType).subscribe((user) => {
            if (_.isEmpty(user)) {
                return res.json({
                    "status":"false",
                    "message":"Email Not Found",
                    "error":"false",   
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
                "status":"true",
                "Code":"200",
                "message":"Email Sent Sucessfull",
                "error":"true", 
                "data":{
                    passcode: userCode
                }
                
            });
        });
    }
    public getUserUsingEmail(email, userType): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
        const user1 =  user.findAll({
            where: {
                Email:email,
                userType: userType
            }})  
        resolve(user1);
    });
       return Rx.Observable.fromPromise(promise);
    } 

    public getSixDigitRandomNumber(min = 1000, max = 9999) {
        const numberInString = Math.floor(Math.random() * (max - min + 1) + min);
        return parseInt(numberInString.toString());
    }
   
    public updatePassword(req: Request, res: Response) {
        console.log(req.body);
        if(req.body.constructor === Object && Object.keys(req.body).length === 0){
            return res.json({
                "status":"false",
                "message":"Missing All Parameter",
                "error":"false",  
            });  
        }else if(req.body.email == null){
            return res.json({
                "status":"false",
                "message":"Missing Paramenter email",
                "error":"false",  
            });     
        }else if(req.body.userType == null){
            return res.json({
                "status":"false",
                "message":"Missing Paramenter userType",
                "error":"false",  
            });     
        }
        else if(req.body.password == null){
            return res.json({
                "status":"false",
                "message":"Missing Paramenter Password",
                "error":"false",  
            });     
        }

        this.getUserUsingEmail(req.body.email, req.body.userType).subscribe((user) => {
            if (_.isEmpty(user)) {
                return res.json({
                    "status":"false",
                    "message":"Email Not Found",
                    "error":"false",   
                });;
            }
            const getUserquery = this.userService.updatePassword(req.body.email,req.body.userType,req.body.password);
             this.sendResponse(getUserquery,res);
                
        });

     
    }


    public getProfile(req: Request, res: Response) {
        console.log(req.body);
        if(req.body.constructor === Object && Object.keys(req.body).length === 0){
            return res.json({
                "status":"false",
                "message":"Missing All Parameter",
                "error":"false",  
            });  
        }else if(req.body.id == null){
            return res.json({
                "status":"false",
                "message":"Missing Paramenter id",
                "error":"false",  
            });     
        }else if(req.body.userType == null){
            return res.json({
                "status":"false",
                "message":"Missing Paramenter userType",
                "error":"false",  
            });     
        }

        this.userService.getUserUsingid(req.body.id, req.body.userType).subscribe((user) => {
            if (_.isEmpty(user)) {
                return res.json({
                    "status":"false",
                    "message":"Email Not Found",
                    "error":"false",   
                });;
            }
            let result = this.userService.getUserUsingid(req.body.id, req.body.userType);
            this.sendResponse(result,res);
                
        });

     
    }

  

    
    public isUserExist(req: Request, res: Response) {
        console.log(req.body);
        if(req.body.constructor === Object && Object.keys(req.body).length === 0){
            return res.json({
                "status":"false",
                "message":"Missing All Parameter",
                "error":"false",  
            });  
        }else if(req.body.email == null){
            return res.json({
                "status":"false",
                "message":"Missing Paramenter email",
                "error":"false",  
            });     
        }else if(req.body.userType == null){
            return res.json({
                "status":"false",
                "message":"Missing Paramenter userType",
                "error":"false",  
            });     
        }

        this.userService.getUserUsingEmaiAndUserType(req.body.email, req.body.userType).subscribe((user) => {
            if (_.isEmpty(user)) {
                return res.json({
                    "status":"false",
                    "message":"Email Not Found",
                    "error":"false",   
                });;
            }
            let query = `select isLoggedIn from ${Tables.user} where email = "${req.body.email}";`;
            let result = this.sqlService.executeQuery(query);
            this.sendResponse(result,res)
                
        });

     
    }
}
