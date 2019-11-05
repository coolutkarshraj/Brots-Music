import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import { Config } from "../Config";
import { EmailService } from '../services/EmailServices';
const { Admin } = require("../category/sqlService")
const upload = require('../s3Services/FileUpload');
const singleUpload = upload.single('audio');
export class AdminServices extends ServiceBase {
    private emailService: EmailService;
    
    constructor() {
        super();
        this.emailService = new EmailService();
    }

    public registerUser(request): Rx.Observable<any> {
        console.log(request)
        return this.userExists(request.email)
            .flatMap((userExistsResult) => {
                if (_.isEmpty(userExistsResult)) {
                    const createUser = Admin.create({Name:request.name,FirstName:request.firstName,
                        LastName:request.lastname,UserType:request. userType,Password:request.password,
                        Gender:request.gender,DOB:request.dob,City:request.city,Country:request.country,
                        Address:request.address,RegistrrationDate:request.registratioDate,DeviceType:request.deviceType,
                        imageUrl:request.imageUrl,IsTncAccepted:request.isTncAccepted,Email:request.email,DeviceToken:request.deviceToken});
                    return createUser;
                }  
            })
            .flatMap((createUser) => {
                return Rx.Observable.create((observer) => {
                    observer.next(createUser);
                    observer.complete();
                });
            });
    }


    public loginUser(request): Rx.Observable<any> {
        console.log(request)
        return this.checkuserExistsWithEmailPassworsd(request.email,request.password)
            .flatMap((userExistsResultwithmailPass) => {
                if (!_.isEmpty(userExistsResultwithmailPass)) {
                    const user1 =  Admin.find({
                        where: {
                            Email:request.email,
                             Password: request.password
                        }})  
                    return user1;
                }  
            })
            .flatMap((user1) => {
                return Rx.Observable.create((observer) => {
                    observer.next(user1);
                    observer.complete();
                });
            });
    }



    public userExists(email): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const user1 =  Admin.findAll({
                where: {
                    email:email
                },attributes: ['id']})  
                resolve(user1);
        });
        return Rx.Observable.fromPromise(promise);
    }

    public checkuserExistsWithEmailPassworsd(email,password): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const user1 =  Admin.findAll({
                where: {
                    Email:email,
                    Password: password
                }})  
                resolve(user1);
        });
        return Rx.Observable.fromPromise(promise);
    }


    

    public sendMailAfterLogin(email,name,otp) {
        console.log(email)
        console.log(name)
        console.log(otp)
        const userEmail = email;
            const emailData = {
                email: userEmail,
                subject: 'Welcome To BROTS',
            };
            const templateModal = {
                name: name,
                sent_otp: otp,
            };
            this.emailService.sendMail(emailData, templateModal, Config.mailTemplate.registrationSuccessfull);

    
    }
    public updatePassword(email,usertype,password): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            Admin.update(
                    { Password: password },
                    { where: {  Email:email,
                        UserType: usertype} }
                  )
                    .then(result =>{
                        const user1 =  Admin.find({
                            where: {
                                Email:email,
                                Password: password
                            }}) 
                            resolve(user1) 
                    }
                        
                        
                    )
                    .catch(err =>
                        resolve(err)
                    )
               
        });
        return Rx.Observable.fromPromise(promise);
    }
    
    public block_unblock_User(userId,onlineStatus): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
             Admin.update(
                    { OnlineStatus: onlineStatus },
                    { where: {  id:userId} }
                  )
                    .then(result =>{
                       const user1 = Admin.find({
                        where: { id:userId} ,attributes: ['onlineStatus']})
                            resolve(user1) 
                    }
                        
                        
                    )
                    .catch(err =>
                        resolve(err)
                    )
               
        });
        return Rx.Observable.fromPromise(promise);
    }

    public updateDeviceToken(userId,deviceToken): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            Admin.update(
                    { DeviceToken: deviceToken },
                    { where: {  id:userId} }
                  )
                    .then(result =>{
                       const user1 = Admin.find({
                        where: { id:userId} ,attributes: ['deviceToken']})
                            resolve(user1) 
                    }
                        
                        
                    )
                    .catch(err =>
                        resolve(err)
                    )
               
        });
        return Rx.Observable.fromPromise(promise);
    }

    public getUserUsingid(id,userType): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const user1 =  Admin.find({
                where: {
                    id:id,
                    UserType:userType
                }})  
                resolve(user1);
        });
        return Rx.Observable.fromPromise(promise);
    }

    public getUserUsingEmai(userEmail,id): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const user1 =  Admin.find({
                where: {
                    Email:userEmail
                }})  
                resolve(user1);
        });
        return Rx.Observable.fromPromise(promise);
    }

    public updateEmail(id,userType,email): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            Admin.update(
                { Email: email},
                { where: {  
                    id:id,
                     UserType:userType }}
              )
                .then(result =>{
                   const user1 = Admin.find({
                    where: { id:id,
                        UserType:userType}})
                        resolve(user1) 
                }
                    
                    
                )
                .catch(err =>
                    resolve(err)
                )
           
        });
        return Rx.Observable.fromPromise(promise);
    }

    public getUserUsingEmaiAndUserType(userEmail,userType): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const user1 =  Admin.find({
                where: {
                    Email:userEmail,
                    UserType:userType
                },attributes:['isLoggedIn']})  
                resolve(user1);
        });
        return Rx.Observable.fromPromise(promise);
    }


    public updateLoggedinStatus(email): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            Admin.update(
                { IsLoggedIn: '0'},
                { where: {  
                     Email:email }})
                .then(result =>{
                   resolve(result) 
                }
                    
                    
                )
                .catch(err =>
                    resolve(err)
                )
           
        });
        return Rx.Observable.fromPromise(promise);
    }
    public updateProfile(req,res): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {

            singleUpload(req, res, function(err) {
                if (err) {
                    console.log('error'); 
                     return res.status(422).send({errors: [{title: 'File Upload Error', detail: err.message}] });
                   }
                   if(req.file == undefined){
                    Admin.update(
                        {   FirstName: req.body.firstName,
                            LastName:req.body.lastname,
                            Name:req.body.name,
                            About:req.body.about,
                            DOB:req.body.dob,
                            Gender:req.body.gender,
                            City:req.body.city,
                            Country:req.body.country,
                            phone:req.body.phone,
                            Address:req.body.address},
                           
                        { where: {  
                         id:req.body.id,
                         UserType:req.body.userType }}
                      )
                        .then(result =>{
                            const user1 = Admin.find({
                             where: {  
                                 id:req.body.id,
                                 UserType:req.body.userType }
                            })
                                resolve(user1) 
                        }     
                        )
                        .catch(err =>
                         reject(err)
                        )     
                   }else{
                    Admin.update(
                        {   FirstName: req.body.firstName,
                            LastName:req.body.lastname,
                            Name:req.body.name,
                            About:req.body.about,
                            DOB:req.body.dob,
                            Gender:req.body.gender,
                            imageUrl:req['file'].location,
                            City:req.body.city,
                            Country:req.body.country,
                            phone:req.body.phone,
                            Address:req.body.address},
                           
                        { where: {  
                         id:req.body.id,
                         UserType:req.body.userType }}
                      )
                        .then(result =>{
                            const user1 = Admin.find({
                             where: {  
                                 id:req.body.id,
                                 UserType:req.body.userType }
                            })
                                resolve(user1) 
                        }     
                        )
                        .catch(err =>
                         reject(err)
                        )     
                   }
               
                }) 
           });
           return Rx.Observable.fromPromise(promise);
       }
   
       public getUserUsingEmailIdPasswordUserType(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const user1 =  Admin.find({
                where: {
                    Email:req.body.email,
                    Password:req.body.password,
                    UserType:req.body.userType,
                    id:req.body.id
                }})  
                resolve(user1);
        });
        return Rx.Observable.fromPromise(promise);
    }

}
