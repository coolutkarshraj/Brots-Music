import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {ErrorModel} from "../models/ErrorModel";
import {UserModel} from "../models/UserModel";
import {Tables} from "../database/Tables";
import { SpringFieldModel } from "../models/SpringFieldModel";
import { Continents } from "../models/Continents";
import { CountriesList } from "../models/CountriesList";
import { States } from "../models/States";
import { Cities } from "../models/Cities";
import { Towns } from "../models/Towns";
import { Places } from "../models/Places";
const uploadcategory = require('../s3Services/uploadcategory');
const uploadcategories = uploadcategory.single('imageUrl');


export class EditDestinationServices extends ServiceBase {
    usermodel: UserModel;
    constructor() {
        super();
       
    }

    public editSpringFieldData(model: SpringFieldModel): Rx.Observable<any> {
        console.log("dhsjdhsjdhsjhdsjdjs");
        console.log(model)
        return this.userExists(model.id)
            .flatMap((userExistsResult) => {
                if (!_.isEmpty(userExistsResult)) {
                    const condition = 'where id = ' + model.id;
                    delete model.id;
                    const query = this.queryBuilderService.getUpdateQuery(Tables.springfield, model,condition);
                    return this.sqlService.executeQuery(query); 
                }
                    const error: ErrorModel = {
                        status: "false",
                        message: `User with email ${model.id} not exists.`,
                        error:"false"           
                    } 
                return Rx.Observable.throw(error);
            })
    }

    public userExists(id): Rx.Observable<any> {
        let query = `select id from ${Tables.springfield} where id = "${id}";`;
      return this.sqlService.executeQuery(query);
  }
    

    public editContinent(model: Continents):Rx.Observable<any> {
        return this.userExists(model.id)
        .flatMap((userExistsResult) => {
            if (!_.isEmpty(userExistsResult)) {
                const condition = 'where id = ' + model.id;
                delete model.id;
                const query = this.queryBuilderService.getUpdateQuery(Tables.continents, model,condition);
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

    public editCountry(model: CountriesList):Rx.Observable<any> {
        return this.userExists(model.id)
        .flatMap((userExistsResult) => {
            if (!_.isEmpty(userExistsResult)) {
                const condition = 'where id = ' + model.id;
                delete model.id;
                const query = this.queryBuilderService.getUpdateQuery(Tables.countriesList, model,condition);
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

    public editStates(model: States):Rx.Observable<any> {
        return this.userExists(model.id)
        .flatMap((userExistsResult) => {
            if (!_.isEmpty(userExistsResult)) {
                const condition = 'where id = ' + model.id;
                delete model.id;
                const query = this.queryBuilderService.getUpdateQuery(Tables.states, model,condition);
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

    public editcities(model: Cities):Rx.Observable<any> {
        return this.userExists(model.id)
        .flatMap((userExistsResult) => {
            if (!_.isEmpty(userExistsResult)) {
                const condition = 'where id = ' + model.id;
                delete model.id;
                const query = this.queryBuilderService.getUpdateQuery(Tables.cities, model,condition);
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

    public editTowns(model: Towns):Rx.Observable<any> {
        return this.userExists(model.id)
        .flatMap((userExistsResult) => {
            if (!_.isEmpty(userExistsResult)) {
                const condition = 'where id = ' + model.id;
                delete model.id;
                const query = this.queryBuilderService.getUpdateQuery(Tables.towns, model,condition);
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

    public editplaces(model: Places):Rx.Observable<any> {
        return this.userExists(model.id)
        .flatMap((userExistsResult) => {
            if (!_.isEmpty(userExistsResult)) {
                const condition = 'where id = ' + model.id;
                delete model.id;
                const query = this.queryBuilderService.getUpdateQuery(Tables.places, model,condition);
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

    public uploadImages(req, res): Rx.Observable<any> {
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

}
