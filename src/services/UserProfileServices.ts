import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {SucessModel} from "../models/SucessModel";
import {SpringFieldModel} from "../models/SpringFieldModel"
import {Tables} from "../database/Tables";
import { EmailService } from './EmailServices';
import { from } from "rxjs/observable/from";
import {userProfilePlace } from "../models/userProfilePlace"
import {userProfilepublicTag } from "../models/userProfilepublicTag"
import {userProfileeducation } from "../models/userProfileeducation"

export class UserProfileServices extends ServiceBase {
   
    constructor() {
        super();
       
    }

    public addUserPlaceData(model: userProfilePlace): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
           
           // resolve(song);
        });
        return Rx.Observable.fromPromise(promise);
    }


    public addUserPublicTacData(model: userProfilepublicTag): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
           
           // resolve(song);
        });
        return Rx.Observable.fromPromise(promise);
    }
    public addUserEducationData(model: userProfileeducation): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
           
           // resolve(song);
        });
        return Rx.Observable.fromPromise(promise);
    }


}
