import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {SucessModel} from "../models/SucessModel";
import {SpringFieldModel} from "../models/SpringFieldModel"
import {Tables} from "../database/Tables";
import { EmailService } from './EmailServices';
import { from } from "rxjs/observable/from";

export class DeleteProfileServices extends ServiceBase {
   
    constructor() {
        super();
       
    }

    public deleteBasicDetails(model: SpringFieldModel,res, callback) {
        console.log(model)

        const query = this.queryBuilderService.getInsertQuery(Tables.springfield, model);
        this.sqlService.executeQuery(query).subscribe((result) => {
            if (result !== null) {
                callback(null, result);
            }
        }, (error) => null, null);
    }

    public deleteEductionDetails(model: SpringFieldModel,res, callback) {
        console.log(model)

        const query = this.queryBuilderService.getInsertQuery(Tables.springfield, model);
        this.sqlService.executeQuery(query).subscribe((result) => {
            if (result !== null) {
                callback(null, result);
            }
        }, (error) => null, null);
    }

    public deletePublicTagData(model: SpringFieldModel,res, callback) {
        console.log(model)

        const query = this.queryBuilderService.getInsertQuery(Tables.springfield, model);
        this.sqlService.executeQuery(query).subscribe((result) => {
            if (result !== null) {
                callback(null, result);
            }
        }, (error) => null, null);
    }

    public deletePlaceTagData(model: SpringFieldModel,res, callback) {
        console.log(model)

        const query = this.queryBuilderService.getInsertQuery(Tables.springfield, model);
        this.sqlService.executeQuery(query).subscribe((result) => {
            if (result !== null) {
                callback(null, result);
            }
        }, (error) => null, null);
    }


}
