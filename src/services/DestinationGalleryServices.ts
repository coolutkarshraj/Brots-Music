import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import { EmailService } from '../services/EmailServices';
import { Config } from "../Config";
import * as Rx from "rxjs/Rx";
import { CityGallery } from "../models/CityGallery";
import { SpringFieldGallery } from "../models/SpringFieldGallery";
import { ContinentGalleryModel } from "../models/ContinentGalleryModel";
import { CountryGallery } from "../models/CountryGallery";
import { StateGallery } from "../models/StateGallery";
import { TownsGallery } from "../models/TownsGallery";
import { PlaceGallery } from "../models/PlaceGallery";
const uploadcategory = require('../s3Services/uploadcategory');
const uploadcategories = uploadcategory.single('imageUrl');
import {Tables} from "../database/Tables";
export class DestinationGalleryServices extends ServiceBase {
    constructor() {
        super();     
    }

    public addPlacesImages(req, res): Rx.Observable<any> {
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

    public addContinentGallery(model: ContinentGalleryModel): Rx.Observable<any> {
       const promise = new Promise((resolve, reject) => {
       const query = this.queryBuilderService.getInsertQuery(Tables.continentgallery, model);
        resolve(this.sqlService.executeQuery(query) )
    });
    return Rx.Observable.fromPromise(promise);     
    }


    public addCountryGallery(model: CountryGallery) {
       const promise = new Promise((resolve, reject) => {
       const query = this.queryBuilderService.getInsertQuery(Tables.countryGallery, model);
        resolve(this.sqlService.executeQuery(query) )
    });
    return Rx.Observable.fromPromise(promise);  
    }
    public addStatesGallery(model: StateGallery) {
        const promise = new Promise((resolve, reject) => {
            const query = this.queryBuilderService.getInsertQuery(Tables.stateGallery, model);
             resolve(this.sqlService.executeQuery(query) )
         });
         return Rx.Observable.fromPromise(promise); 
    }
    public addcitiesGallery(model :CityGallery) {
        const promise = new Promise((resolve, reject) => {
            const query = this.queryBuilderService.getInsertQuery(Tables.cityGalley, model);
             resolve(this.sqlService.executeQuery(query) )
         });
         return Rx.Observable.fromPromise(promise);  
    }
    public addTownsGallery(model:TownsGallery) {
        const promise = new Promise((resolve, reject) => {
            const query = this.queryBuilderService.getInsertQuery(Tables.townsGalley, model);
             resolve(this.sqlService.executeQuery(query) )
         });
         return Rx.Observable.fromPromise(promise); 
    }

    public addplacesGallery(model:PlaceGallery) {
        const promise = new Promise((resolve, reject) => {
            const query = this.queryBuilderService.getInsertQuery(Tables.placeGalley, model);
             resolve(this.sqlService.executeQuery(query) )
         });
         return Rx.Observable.fromPromise(promise); 
    }
    public addSpringFieldGalleryData(model:SpringFieldGallery) {
        const promise = new Promise((resolve, reject) => {
            const query = this.queryBuilderService.getInsertQuery(Tables.springFieldGallery, model);
             resolve(this.sqlService.executeQuery(query) )
         });
         return Rx.Observable.fromPromise(promise); 
    }

}
