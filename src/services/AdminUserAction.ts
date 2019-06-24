import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {adminQuery} from "../models/adminQuery"
import {Tables} from "../database/Tables";
import { from } from "rxjs/observable/from";

export class AdminUserAction extends ServiceBase {
   
    constructor() {
        super();
       
    }

    public userQuery(model: adminQuery) {
        const promise = new Promise((resolve, reject) => {
            const query = this.queryBuilderService.getInsertQuery(Tables.adminQuery, model);
             resolve(this.sqlService.executeQuery(query) )
         });
         return Rx.Observable.fromPromise(promise); 
    }
}
