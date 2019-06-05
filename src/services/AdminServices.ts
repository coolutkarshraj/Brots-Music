import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import { EmailService } from '../services/EmailServices';
import { Config } from "../Config";
import * as Rx from "rxjs/Rx";
import { Continents } from "../models/Continents";
const uploadcategory = require('../s3Services/uploadcategory');
const uploadcategories = uploadcategory.single('imageUrl');
import {Tables} from "../database/Tables";
import { CountriesList } from "../models/CountriesList";
import { States } from "../models/States";
import { Cities } from "../models/Cities";
import { Towns } from "../models/Towns";
import { SpringFieldModel } from "../models/SpringFieldModel";

export class AdminServices extends ServiceBase {
    private emailService: EmailService;
    constructor() {
        super();
        this.emailService = new EmailService();
       
    }

    public sendlogggedInMail(email,name,city,country,address) {
        const userEmail = email;
            const emailData = {
                email: userEmail,
                subject: 'Welcome To High Mountains',
            };
            const templateModal = {
                name: name,
                City: city+" "+ country,
                email:email,
                address:address
            };
            this.emailService.sendMail(emailData, templateModal, Config.mailTemplate.loggedInMail); 
    }

    public addContinent(req, res): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            uploadcategories (req, res, function (err) {
              
                if (err) {
                    console.log('error');
                    return res.status(422).send({ errors: [{ title: 'File Upload Error', detail: err.message }] });
                }
               let coverImage = req['file'].location 
                resolve(coverImage)
            })
        });
        return Rx.Observable.fromPromise(promise);
    }

    public addContinentToDatatoDatabase(model: Continents): Rx.Observable<any> {
       const promise = new Promise((resolve, reject) => {
       const query = this.queryBuilderService.getInsertQuery(Tables.continents, model);
        resolve(this.sqlService.executeQuery(query) )
    });
    return Rx.Observable.fromPromise(promise);     
    }


    public addCountryToDatatoDatabase(model: CountriesList) {
       const promise = new Promise((resolve, reject) => {
       const query = this.queryBuilderService.getInsertQuery(Tables.countriesList, model);
        resolve(this.sqlService.executeQuery(query) )
    });
    return Rx.Observable.fromPromise(promise);  
    }
    public addStates(model: States) {
        const promise = new Promise((resolve, reject) => {
            const query = this.queryBuilderService.getInsertQuery(Tables.states, model);
             resolve(this.sqlService.executeQuery(query) )
         });
         return Rx.Observable.fromPromise(promise); 
    }
    public addcities(model :Cities) {
        const promise = new Promise((resolve, reject) => {
            const query = this.queryBuilderService.getInsertQuery(Tables.cities, model);
             resolve(this.sqlService.executeQuery(query) )
         });
         return Rx.Observable.fromPromise(promise);  
    }
    public addTowns(model:Towns) {
        const promise = new Promise((resolve, reject) => {
            const query = this.queryBuilderService.getInsertQuery(Tables.towns, model);
             resolve(this.sqlService.executeQuery(query) )
         });
         return Rx.Observable.fromPromise(promise); 
    }
    public addSpringFieldData(model:SpringFieldModel) {
        const promise = new Promise((resolve, reject) => {
            const query = this.queryBuilderService.getInsertQuery(Tables.springfield, model);
             resolve(this.sqlService.executeQuery(query) )
         });
         return Rx.Observable.fromPromise(promise); 
    }
    
}
