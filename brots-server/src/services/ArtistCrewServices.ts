import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
export class ArtistCrewServices extends ServiceBase {

    constructor() {
        super();
    }

     public addPoint(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
          
        });
        return Rx.Observable.fromPromise(promise);
    }

  
}
