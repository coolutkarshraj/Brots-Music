import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {ErrorModel} from "../models/ErrorModel";
import {UserGalleryModel} from "../models/UserGalleryModel";
import {UserImageGallery} from "../models/UserImageGallery";
import {Tables} from "../database/Tables";
const uploadProfileImage = require('../s3Services/S3Services');
const profileImageUpload = uploadProfileImage.single('imageIcon');


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
                        message: `User with email already exists.`,
                        error:"false"           
                    }
                return Rx.Observable.throw(error);
            })
    }

    public uploadImages(req, res,model: UserImageGallery): Rx.Observable<any> {
        var status =  "true";

        const promise = new Promise((resolve, reject) => {
         profileImageUpload(req, res, function (err) {
            status = "false"
                if (err) {  
                    console.log('error');
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
                    model.imageIcon = req['file'].location 
                    
                    resolve( req['file'].location )    
                    
                  
                }
            })
          
        });
        return Rx.Observable.fromPromise(promise);
    }

    public userExists(id): Rx.Observable<any> {
        let query = `select id from ${Tables.user} where id = "${id}";`;
      return this.sqlService.executeQuery(query);
  }

  public insertImages(table,model: UserImageGallery): Rx.Observable<any> {
    const query =this.queryBuilderService.getInsertQuery(table, model)
    return this.sqlService.executeQuery(query);
}

}
