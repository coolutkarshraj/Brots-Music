import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {SpringFieldModel} from "../models/SpringFieldModel"
import {Tables} from "../database/Tables";
import { from } from "rxjs/observable/from";

export class CitiesServices extends ServiceBase {
   
    constructor() {
        super();
       
    }

    public addSpringFieldData(model: SpringFieldModel,res, callback) {
        console.log(model)

        const query = this.queryBuilderService.getInsertQuery(Tables.springfield, model);
        this.sqlService.executeQuery(query).subscribe((result) => {
            if (result !== null) {
                callback(null, result);
            }
        }, (error) => null, null);
    }

}
