import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {ErrorModel} from "../models/ErrorModel";
import {UserGalleryModel} from "../models/UserGalleryModel";
import {Tables} from "../database/Tables";
const uploadProfileImage = require('../s3Services/S3Services');
const profileImageUpload = uploadProfileImage.single('imageIcon');
import {UserImageGallery} from "../models/UserImageGallery";
import {BookMarkModel} from "../models/BookMarkModel";


export class UserGalleryServices extends ServiceBase {

    constructor() {
        UserGalleryServices
        super();      
    }

    public createGalleryFolder(model: UserGalleryModel): Rx.Observable<any> {
        return this.userExists(model.userId)
            .flatMap((userExistsResult) => {
                if (!_.isEmpty(userExistsResult)) {
                    const query = this.queryBuilderService.getInsertQuery(Tables.createUserGallery, model);
                    return this.sqlService.executeQuery(query); 
                }
                    const error: ErrorModel = {
                        status: "false",
                        message: `User with email does not exists.`,
                        error:"false"           
                    }
                return Rx.Observable.throw(error);
            })
    }
    public createBookMarkedImages(model: BookMarkModel): Rx.Observable<any> {
        return this.userExists(model.user_Id)
            .flatMap((userExistsResult) => {
                if (!_.isEmpty(userExistsResult)) {
                    const query = this.queryBuilderService.getInsertQuery(Tables.userBookMarkedImage, model);
                    return this.sqlService.executeQuery(query); 
                }
                    const error: ErrorModel = {
                        status: "false",
                        message: `User with email does not exists.`,
                        error:"false"           
                    }
                return Rx.Observable.throw(error);
            })
    }

    

    public insertImage(model: UserImageGallery): Rx.Observable<any> {
        return this.userExists(model.userId)
            .flatMap((userExistsResult) => {
                if (!_.isEmpty(userExistsResult)) {
                    console.log("djskjfhkdsjfhkjdfhdjkfhdjk")
                    console.log(model.imageIcon)
                    console.log("fcdiofdhfhdhfdhfhdfhdhfhdhfdkfhdhfhd")
                    const query = `INSERT INTO ${Tables.userimagegallery} (imageTitle, imageIcon, createdDate,total_like,total_comment,total_share,gallery_Id,isBookMarked )
                    VALUES ('${model.imageTitle}', '${model.imageIcon}', ${model.createdDate}, ${model.total_like},${model.total_comment},${model.total_share},
                        ${model.gallery_Id}, ${model.isBookMarked});` 
                        console.log(this.sqlService.executeQuery(query))  
                 return this.sqlService.executeQuery(query); 
                }
                    const error: ErrorModel = {
                        status: "false",
                        message: `User with email does not exists.`,
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

    public createBookMarkImages(model: UserGalleryModel): Rx.Observable<any> {
        return this.userExists(model.userId)
            .flatMap((userExistsResult) => {
                if (!_.isEmpty(userExistsResult)) {
                    const query = this.queryBuilderService.getInsertQuery(Tables.createUserGallery, model);
                    return this.sqlService.executeQuery(query); 
                }
                    const error: ErrorModel = {
                        status: "false",
                        message: `User with email does not exists.`,
                        error:"false"           
                    }
                return Rx.Observable.throw(error);
            })
    }

    public userExists(id): Rx.Observable<any> {
        let query = `select id from ${Tables.user} where id = "${id}";`;
      return this.sqlService.executeQuery(query);
  }



}
