import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {ErrorModel} from "../models/ErrorModel";
const uploadProfileImage = require('../s3Services/S3Services');
const profileImageUpload = uploadProfileImage.single('imageIcon');
import {UserModel} from "../models/UserModel";
import {Tables} from "../database/Tables";
import { EmailService } from '../services/EmailServices';
import { Config } from "../Config";

export class UserService extends ServiceBase {
    private emailService: EmailService;
    constructor() {
        super();
        this.emailService = new EmailService();
    }

    public registerUser(model: UserModel): Rx.Observable<any> {
        return this.userExists(model.email,model.userName)
            .flatMap((userExistsResult) => {
                if (_.isEmpty(userExistsResult)) {
                    const query = this.queryBuilderService.getInsertQuery(Tables.user, model);
                    return this.sqlService.executeQuery(query); 
                }
                    const error: ErrorModel = {
                        status: "false",
                        message: `User with email ${model.email} already exists.`,
                        error:"false"           
                    }
                
               
                return Rx.Observable.throw(error);
            })
            .flatMap((result) => {
                model.id = result.insertId;
                this.updateLoginStatus(1, 1, model.id,(err, success)=>{
                    if(err== null){
                        return this.sendSuccessfullyRegisteredMail(model.email,model.name,model.password)
                    }
                    const error: ErrorModel = {
                        status: "false",
                        message: `User with email ${model.email} already exists.`,
                        error:"false"           
                    }
                    return Rx.Observable.throw(error)
                   
                })
                return Rx.Observable.create((observer) => {
                    observer.next(model);
                    observer.complete();
                });
            });
    }

    private updateLoginStatus(onlineStatus: number, loggedInStatus: number, id:number, callback) {
        const query = `update ${Tables.user} set isLoggedIn = ${loggedInStatus} ,onlineStatus = ${onlineStatus} where id = ${id};`;
        this.sqlService.executeQuery(query).subscribe((result) => {
            if (result !== null) {
                callback(null, result);
            }
        }, (error) => null, null);
    }

    public userExists(email,userName): Rx.Observable<any> {
          let query = `select id from ${Tables.user} where email = "${email}" or userName = "${userName}";`;
        return this.sqlService.executeQuery(query);
    }

    public sendMailToVerifyRegistration(email,name,otp) {
        const userEmail = email;
            const emailData = {
                email: userEmail,
                subject: 'Welcome To High Mountains',
            };
            const templateModal = {
                name: name,
                sent_otp: otp,
            };
            this.emailService.sendMail(emailData, templateModal, Config.mailTemplate.registrationVerify);
          
    }


    public sendSuccessfullyRegisteredMail(email,name,Password) {
        const userEmail = email;
            const emailData = {
                email: userEmail,
                subject: 'Welcome To High Mountains',
            };
            const templateModal = {
                name: name,
                Password: Password,
                email:email
            };
            this.emailService.sendMail(emailData, templateModal, Config.mailTemplate.registrationSuccessfull);

    
    }

    public sendAlreadylogggedInMail(email,name,city,country,address) {
        const userEmail = email;
            const emailData = {
                email: userEmail,
                subject: 'Welcome To High Mountains',
            };
            const templateModal = {
                name: name,
                City: city+" "+ country,
                email:email,
                address:address
            };
            this.emailService.sendMail(emailData, templateModal, Config.mailTemplate.alreadyLoggedInMail);

    
    }

    public sendlogggedInMail(email,name,city,country,address) {
        const userEmail = email;
            const emailData = {
                email: userEmail,
                subject: 'Welcome To High Mountains',
            };
            const templateModal = {
                name: name,
                City: city+" "+ country,
                email:email,
                address:address
            };
            this.emailService.sendMail(emailData, templateModal, Config.mailTemplate.loggedInMail);

    
    }

    public forgetPasswordMail(email,name,otp) {
        const userEmail = email;
            const emailData = {
                email: userEmail,
                subject: 'Welcome To High Mountains',
            };
            const templateModal = {
                name: name,
                sent_otp: otp,
            };
            this.emailService.sendMail(emailData, templateModal, Config.mailTemplate.forgetPassword);
          
    }

    public SendupdatePasswordMail(email,name,password) {
        const userEmail = email;
            const emailData = {
                email: userEmail,
                subject: 'Welcome To High Mountains',
            };
            const templateModal = {
                name: name,
                password: password,
                email:email
            };
            this.emailService.sendMail(emailData, templateModal, Config.mailTemplate.updatePassword);
          
    }

    public uploadImages(req, res): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
         profileImageUpload(req, res, function (err) {
                if (err) {  
                    return res.json({
                         "status":"false",
                         "message":"File Upload Error",
                         "error":"false"
                    })
                }else{
                    if (req.file == undefined) {
                      reject(res.json({
                            "status":"false",
                            "message":"Something Went Wrong",
                            "error":"false"
                       })) 
                    }
                    resolve( req['file'].location )    
                    
                  
                }
            })
          
        });
        return Rx.Observable.fromPromise(promise);
    }


}
