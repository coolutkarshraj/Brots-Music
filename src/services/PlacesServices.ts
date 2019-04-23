import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {SucessModel} from "../models/SucessModel";
import {SpringFieldModel} from "../models/SpringFieldModel"
import {Tables} from "../database/Tables";
import { EmailService } from './EmailServices';
import { from } from "rxjs/observable/from";

export class PlacesServices extends ServiceBase {
   
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
