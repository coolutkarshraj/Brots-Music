import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {Tables} from "../database/Tables";
import {userProfilePlace } from "../models/userProfilePlace"
import {userProfilepublicTag } from "../models/userProfilepublicTag"
import {userProfileeducation } from "../models/userProfileeducation"
import {ErrorModel} from "../models/ErrorModel";

export class UserProfileServices extends ServiceBase {
   
    constructor() {
        super();
       
    }
    public addUserPlaceData(model: userProfilePlace): Rx.Observable<any> {
        return this.userExists(model.userId)
            .flatMap((userExistsResult) => {
                if (!_.isEmpty(userExistsResult)) {
                    const query = this.queryBuilderService.getInsertQuery(Tables.userProfilePlace, model);
                    return this.sqlService.executeQuery(query); 
                }
                    const error: ErrorModel = {
                        status: "false",
                        message: `User doesnot exist.`,
                        error:"false"           
                    }
                 
                return Rx.Observable.throw(error);
            })
            .flatMap((result) => {
                let query = `select * from ${Tables.userProfilePlace} where userId = ${model.userId}`;
                const getQuery = this.sqlService.executeQuery(query)
                return getQuery;
            });
    }

    public userExists(id): Rx.Observable<any> {
        let query = `select id from ${Tables.user} where id = ${id}`;
      return this.sqlService.executeQuery(query);
  }
    
    public addUserPublicTacData(model: userProfilepublicTag): Rx.Observable<any> {
        return this.userExists(model.userId)
        .flatMap((userExistsResult) => {
            if (!_.isEmpty(userExistsResult)) {
                const query = this.queryBuilderService.getInsertQuery(Tables.userProfilepublicTag, model);
                return this.sqlService.executeQuery(query); 
            }
                const error: ErrorModel = {
                    status: "false",
                    message: `User doesnot exist.`,
                    error:"false"           
                }
             
            return Rx.Observable.throw(error);
        })
        .flatMap((result) => {
            let query = `select * from ${Tables.userProfilepublicTag} where userId = ${model.userId}`;
            const getQuery = this.sqlService.executeQuery(query)
            return getQuery;
        });
    }
    public addUserEducationData(model: userProfileeducation): Rx.Observable<any> {
        return this.userExists(model.userId)
        .flatMap((userExistsResult) => {
            if (!_.isEmpty(userExistsResult)) {
                const query = this.queryBuilderService.getInsertQuery(Tables.userProfileeducation, model);
                return this.sqlService.executeQuery(query); 
            }
                const error: ErrorModel = {
                    status: "false",
                    message: `User doesnot exist.`,
                    error:"false"           
                }
             
            return Rx.Observable.throw(error);
        })
        .flatMap((result) => {
            let query = `select * from ${Tables.userProfileeducation} where userId = ${model.userId}`;
            const getQuery = this.sqlService.executeQuery(query)
            return getQuery;
        });
       
    }


}
