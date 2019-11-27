import * as Rx from "rxjs/Rx";
import {Logger} from "../../services/common/Logger";
import {Config} from "../../Config";
import * as gcm from "node-gcm";
import * as path from "path";
const jwt = require("jsonwebtoken");

export class BaseController {
    protected sendResponse(observable: Rx.Observable<any>, res) {
        observable.subscribe((result) => {
           const data = {
                "status":"true",
                "message":"200",
                "error":"true",
                "data":result  
            }
        res.json(data);
        }, (err) => {
        
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {

              res.json({
                "status":"False",
                "message":"404",
                "error":"False",  
              })
            } else {
                console.log(err)
                res.json({
                    "status":"False",
                    "message":"500",
                    "error":"False", 
                }) 
               // res.status(500).json({error: err});
            }
        }, null);
    }


    protected sendResponsewithLike(likeSong, res,like: Rx.Observable<any>) {
        like.subscribe((result1) => { 
           
           const data = {
                "status":"true",
                "message":"200",
                "error":"true",
                "data":likeSong,
                "memory":result1  
                
            }
        res.json(data);
        }, (err) => {
        
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {

              res.json({
                "status":"False",
                "message":"404",
                "error":"False",  
              })
            } else {
                console.log(err)
                res.json({
                    "status":"False",
                    "message":"500",
                    "error":"False", 
                }) 
                res.status(500).json({error: err});
            }
        }, null);
    }

    protected sendResponsewithdisLike(req, res,like: Rx.Observable<any>) {
        like.subscribe((result1) => { 
            const singleSongMemory = +req.body.singleSaveMemory;
            const total = +req.body.InstaMixMemory;
             const jsonresult= {
              "songId":result1.dataValues.id,
              "Like":result1.dataValues.Like,
              "DisLike":result1.dataValues.DisLike,
              "Memory":result1.dataValues.Memory,
              "InstanceMemory": total
            }
           const data = {
                "status":"true",
                "message":"200",
                "error":"true",
                "data":jsonresult,  
                
            }
        res.json(data);
        }, (err) => {
        
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {

              res.json({
                "status":"False",
                "message":"404",
                "error":"False",  
              })
            } else {
                console.log(err)
                res.json({
                    "status":"False",
                    "message":"500",
                    "error":"False", 
                }) 
                res.status(500).json({error: err});
            }
        }, null);
    }


    
    protected sendResponsewithupdatedisLike(req, res,like: Rx.Observable<any>) {
        like.subscribe((result1) => { 
            const singleSongMemory = +req.body.singleSaveMemory;
            const total = +req.body.InstaMixMemory;
             const jsonresult= {
              "songId":result1.dataValues.id,
              "Like":result1.dataValues.Like,
              "DisLike":result1.dataValues.DisLike,
              "Memory":result1.dataValues.Memory,
              "InstanceMemory": total -singleSongMemory
            }
           const data = {
                "status":"true",
                "message":"200",
                "error":"true",
                "data":jsonresult,  
                
            }
        res.json(data);
        }, (err) => {
        
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {

              res.json({
                "status":"False",
                "message":"404",
                "error":"False",  
              })
            } else {
                console.log(err)
                res.json({
                    "status":"False",
                    "message":"500",
                    "error":"False", 
                }) 
                res.status(500).json({error: err});
            }
        }, null);
    }




    protected sendResponseLogin(observable: Rx.Observable<any>, res) {
        observable.subscribe((result) => {
            if(result.dataValues.IsLoggedIn == 1){
                return res.json({
                    "status":"false",
                    "message":"Your account has been suspended Please contact to admin support@broots.live",
                    "error":"false",  
                })
            }
           const data = {
                "status":"true",
                "message":"200",
                "error":"true",
                "data":result  
           }
        res.json(data);
        }, (err) => {
        
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {

              res.json({
                "status":"False",
                "message":"404",
                "error":"False",  
              })
            } else {
                console.log(err)
                res.json({
                    "status":"False",
                    "message":"500",
                    "error":"False", 
                }) 
               // res.status(500).json({error: err});
            }
        }, null);
    }


    protected sendResponsewith1stcat(observable: Rx.Observable<any>, res) {
        observable.subscribe((result) => {
           const data = {
                "status":"true",
                "message":"200",
                "error":"true",
                "data":result[0]  
            }
        res.json(data);
        }, (err) => {
        
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {

              res.json({
                "status":"False",
                "message":"404",
                "error":"False",  
              })
            } else {
                console.log(err)
                res.json({
                    "status":"False",
                    "message":"500",
                    "error":"False", 
                }) 
               // res.status(500).json({error: err});
            }
        }, null);
    }

    protected sendResponsewithMulter(observable: Rx.Observable<any>, res) {
        observable.subscribe((result) => {
            if(result == null){
                return res.json({
                    "status":"false",
                    "message":"No Record Found",
                    "error":"false",    
                })
            }
            const data = {
                "status":"true",
                "message":"200",
                "error":"true",
                "data":result  
            }
        res.json(data);
        }, (err) => {
        
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {

              res.json({
                "status":"False",
                "message":"404",
                "error":"False",  
              })
            } else {
                console.log(err)
                res.json({
                    "status":"False",
                    "message":"500",
                    "error":"False", 
                }) 
               // res.status(500).json({error: err});
            }
        }, null);
    }

     protected sendResponseAfterLogin(observable: Rx.Observable<any>, res) {
        observable.subscribe((result) => {
           const tokenid =  jwt.sign({
               "email":result.email,
                "userid":result.userId

            },Config.envir.JWT_KEY,{
                expiresIn:"10h"
            })
            result.DeviceToken =tokenid;
            const registrationResponse = {
                "status":"true",
                "message":"Registration Done Succesfully",
                "error":"true",
                "data":result
        }
            res.json(registrationResponse);
        }, (err) => {
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {
               res.json({
                "status":"False",
                "message":"404",
                "error":"False",  
              })
            } else {
               res.json({
                "status":"False",
                "message":"Email Already Exist",
                "error":err, 
              })
            }
        }, null);
    }
   
   
    protected sendResponseWithKey(key: string, observable: Rx.Observable<any>, res) {
        observable.subscribe((result) => {
            res.json({[key]: result});
        }, (err) => {
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {
                res.status(404).json({error: err});
            } else {
                res.status(500).json({error: err});
            }
        }, null);
    }

    protected sendResponseWithStatus(observable: Rx.Observable<any>, res) {
        observable.subscribe((result) => {
            if(result == null){
               return res.json({
                "status":"true",
                "message":"User doesnot exists",
                "error":"true",
               })
            }
            const tokenres = {
                "status":"true",
                "Code":"200",
                "error":"true",
                "data":result
            }
            res.json(tokenres);
        }, (err) => {
            Logger.logError(err);
            const tokenres = {
                "status":"false",
                "Code":"500",
                "error":"false",
            }
            res.json(tokenres);
        }, null);
    }


    protected sendGCMNotification(message: object, tokens, callback) {
        console.log("oaeuoiwhdfkjshfdjkhfkjsdhfdjkhfdhjk")
        const sender = new gcm.Sender(Config.gcmAPIKey);
        sender.send(message, {to: tokens}, (err, resp) => {
            if (err) {
                console.log("aswaopisapeiuopeusouesioeosu")
                console.log(err)
                Logger.logError(err, "error in sending notification");
                callback(false);
                return;
            }
            console.log("ppspps[pr[pdprodoipropidiropoidr")
            console.log(err)
            Logger.logInfo(resp, "notification sent successfully");
            callback(true);
        });
    }
    protected sendAddNoteGCMNotification(message: object, tokens, callback) {
        const sender = new gcm.Sender(Config.gcmAPIKey);
        sender.send(message, {registrationTokens: tokens}, (err, resp) => {
            if (err) {
                Logger.logError(err, "error in sending notification");
                callback(false);
                return;
            }
            Logger.logInfo(resp, "notification sent successfully");
            callback(true);
        });
    }
    

    protected geGCMMessageSentFriendRequest(body?: object) {
        const message = new gcm.Message({
            data: {
                messageId: "1",
                messageType: "New Friend Request",
                message: "You got new Friend Request"
            }
        });
        return message;
    }

    protected geGCMMessageAcceptFriendRequest(body?: object) {
        const message = new gcm.Message({
            data: {
                messageId: "1",
                messageType: "Friend Request Accepted",
                message: "Your Friend Request has been Accepted"
            }
        });
        return message;
    }

    protected geGCMMessagesendNotificationToFollower(body?: object) {
        const message = new gcm.Message({
            data: {
                messageId: "1",
                messageType: "You are Followed",
                message: "Your are Successfully Followed"
            }
        });
        return message;
    }


    protected sendResponseWithArtistProfileorTop3Song(observable: Rx.Observable<any>, res,artistProfile) {
        observable.subscribe((result) => {

           const data = {
                "status":"true",
                "message":"200",
                "error":"true",
                "Song":result ,
                "Profile" :artistProfile
           }
        res.json(data);
        }, (err) => {
        
            Logger.logError(err);
            if (err && err.code && err.code.toString().toLowerCase() === Config.errorCodes.NotFound.toLowerCase()) {

              res.json({
                "status":"False",
                "message":"404",
                "error":"False",  
              })
            } else {
                console.log(err)
                res.json({
                    "status":"False",
                    "message":"500",
                    "error":"False", 
                }) 
               // res.status(500).json({error: err});
            }
        }, null);
    }


}
