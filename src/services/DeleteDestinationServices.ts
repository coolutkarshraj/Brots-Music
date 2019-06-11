import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {ErrorModel} from "../models/ErrorModel";
import {UserModel} from "../models/UserModel";
import {Tables} from "../database/Tables";
import { userProfilepublicTag } from "../models/userProfilepublicTag";
import { userProfilePlace } from "../models/userProfilePlace";
import { userProfileeducation } from "../models/userProfileeducation";
const uploadProfileImage = require('../s3Services/S3Services');
const profileImageUpload = uploadProfileImage.single('imageUrl');

export class DeleteDestinationServices extends ServiceBase {
    usermodel: UserModel;
    constructor() {
        super();
       
    }

    public editBasicDetails(model: UserModel): Rx.Observable<any> {
        return this.userExists(model.id)
            .flatMap((userExistsResult) => {
                if (!_.isEmpty(userExistsResult)) {
                    const condition = 'where id = ' + model.id;
                    delete model.id;
                    const query = this.queryBuilderService.getUpdateQuery(Tables.user, model,condition);
                    return this.sqlService.executeQuery(query); 
                }
                    const error: ErrorModel = {
                        status: "false",
                        message: `User with email ${model.email} not exists.`,
                        error:"false"           
                    } 
                return Rx.Observable.throw(error);
            })
    }

    public userExists(id): Rx.Observable<any> {
        let query = `select id from ${Tables.user} where id = "${id}";`;
      return this.sqlService.executeQuery(query);
  }
    

    public editEductionDetails(model: userProfileeducation):Rx.Observable<any> {
        return this.userExists(model.userId)
        .flatMap((userExistsResult) => {
            if (!_.isEmpty(userExistsResult)) {
                const condition = 'where id = ' + model.id;
                delete model.id;
                const query = this.queryBuilderService.getUpdateQuery(Tables.userProfileeducation, model,condition);
                return this.sqlService.executeQuery(query); 
            }
                const error: ErrorModel = {
                    status: "false",
                    message: `User doesn't exists.`,
                    error:"false"           
                } 
            return Rx.Observable.throw(error);
        })
    }

    public editPublicTagData(model: userProfilepublicTag):Rx.Observable<any> {
        return this.userExists(model.userId)
        .flatMap((userExistsResult) => {
            if (!_.isEmpty(userExistsResult)) {
                const condition = 'where id = ' + model.id;
                delete model.id;
                const query = this.queryBuilderService.getUpdateQuery(Tables.userProfilepublicTag, model,condition);
                return this.sqlService.executeQuery(query); 
            }
                const error: ErrorModel = {
                    status: "false",
                    message: `User doesn't exists.`,
                    error:"false"           
                } 
            return Rx.Observable.throw(error);
        })
    }

    public editPlacePlaceData(model: userProfilePlace):Rx.Observable<any> {
        return this.userExists(model.userId)
        .flatMap((userExistsResult) => {
            if (!_.isEmpty(userExistsResult)) {
                const condition = 'where id = ' + model.id;
                delete model.id;
                const query = this.queryBuilderService.getUpdateQuery(Tables.userProfilePlace, model,condition);
                return this.sqlService.executeQuery(query); 
            }
                const error: ErrorModel = {
                    status: "false",
                    message: `User doesn't exists.`,
                    error:"false"           
                } 
            return Rx.Observable.throw(error);
        })
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
    public updateProfileImage(model: UserModel,imageUrl): Rx.Observable<any> {
        return this.userExists(model.id)
            .flatMap((userExistsResult) => {
                if (!_.isEmpty(userExistsResult)) {
                 
                    const query1= `UPDATE ${Tables.user}
                    SET imageUrl =  "${imageUrl}"
                    WHERE id = ${model.id};`
                    // model.imageUrl = imageUrl
                    // const condition = 'where id = ' + model.id;
                    // delete model.id;
                    // console.log("dskjdhsjkdhsjdhshdsjkhdsjdhsdhs")
                    // console.log(model)
                    // const query = this.queryBuilderService.getUpdateQuery(Tables.user, model,condition);
                    // console.log(query)
                    return this.sqlService.executeQuery(query1); 
                }
                    const error: ErrorModel = {
                        status: "false",
                        message: `User with email ${model.email} not exists.`,
                        error:"false"           
                    } 
                return Rx.Observable.throw(error);
            })
    }


}
