import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {ErrorModel} from "../models/ErrorModel";
import {UserGalleryModel} from "../models/UserGalleryModel"
import {Tables} from "../database/Tables";

export class UserGalleryServices extends ServiceBase {
   
    constructor() {
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
    public userExists(id): Rx.Observable<any> {
        let query = `select id from ${Tables.user} where id = "${id}";`;
      return this.sqlService.executeQuery(query);
  }

}
