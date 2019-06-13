import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
const uploadcategory = require('../s3Services/uploadcategory');
const uploadcategories = uploadcategory.single('imageUrl');
import {RevivewModel} from "../models/RevivewModel"
import { Tables } from "../database/Tables";
import {ErrorModel} from "../models/ErrorModel";

export class RevivewServices extends ServiceBase {
   
    constructor() {
        super();
       
    }

    public uploadMedia(req, res): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            uploadcategories (req, res, function (err) {
                if (err) {
                    console.log('error');
                    return res.status(422).send({ errors: [{ title: 'File Upload Error', detail: err.message }] });
                }
               let coverImage = req['file'].location ;
                resolve(coverImage)
            })
        });
        return Rx.Observable.fromPromise(promise);
    }

    public addRevivewToDatabase(model: RevivewModel): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
        const query = this.queryBuilderService.getInsertQuery(Tables.revivew, model);
         resolve(this.sqlService.executeQuery(query) )
     });
     return Rx.Observable.fromPromise(promise);     
     }


     public editRevivewToDatabase(model: RevivewModel): Rx.Observable<any> {
        return this.revivewExist(model.id)
            .flatMap((userExistsResult) => {
                console.log(userExistsResult);
                if (!_.isEmpty(userExistsResult)) {
                    const condition = 'where id = ' + model.id;
                    delete model.id;
                    const query = this.queryBuilderService.getUpdateQuery(Tables.revivew, model,condition);
                    console.log(query)
                    return this.sqlService.executeQuery(query); 
                }
                    const error: ErrorModel = {
                        status: "false",
                        message: `destination with this ${model.id} not exists.`,
                        error:"false"           
                    } 
                return Rx.Observable.throw(error);
            })
    }

    public revivewExist(id): Rx.Observable<any> {
        let query = `select * from ${Tables.revivew} where id = "${id}";`;
      return this.sqlService.executeQuery(query);
  }
    
}
