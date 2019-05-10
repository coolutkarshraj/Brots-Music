import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {ErrorModel} from "../models/ErrorModel";
import {UserModel} from "../models/UserModel";
import {Tables} from "../database/Tables";
import { userProfilepublicTag } from "../models/userProfilepublicTag";
import { userProfilePlace } from "../models/userProfilePlace";
import { userProfileeducation } from "../models/userProfileeducation";

export class EditProfileServices extends ServiceBase {
   
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
        return this.userExists(model.id)
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
        return this.userExists(model.id)
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
        return this.userExists(model.id)
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

}
