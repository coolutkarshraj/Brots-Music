import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
const { contactModel } = require("../category/sqlService")
export class ContactServices extends ServiceBase {

    constructor() {
        super();
    }

     public putContactInformation(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
          
            let contactForm = contactModel.create({
                Email: req.body.email, Title: req.body.title,
                userId: req.body.user_Id,
                Body: req.body.messageBody, Name: req.body.user_name,
                UserType :req.body.userType
            })
            resolve(contactForm)

        

        });
        return Rx.Observable.fromPromise(promise);
    }

  
}
