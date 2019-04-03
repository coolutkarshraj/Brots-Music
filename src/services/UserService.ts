import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {ErrorModel} from "../models/ErrorModel";
import {UserModel} from "../models/UserModel";
import {Tables} from "../database/Tables";

export class UserService extends ServiceBase {

    constructor() {
        super();
    }

    public registerUser(model: UserModel): Rx.Observable<any> {
        return this.userExists(model.email)
            .flatMap((userExistsResult) => {
                if (_.isEmpty(userExistsResult)) {
                    const query = this.queryBuilderService.getInsertQuery(Tables.user, model);
                    return this.sqlService.executeQuery(query);
                }
                const error: ErrorModel = {
                    code: "email_exists",
                    message: `User with email ${model.email} already exists.`
                }
                return Rx.Observable.throw(error);
            })
            .flatMap((result) => {
                model.password = "";
                model.id = result.insertId;
                return Rx.Observable.create((observer) => {
                    observer.next(model);
                    observer.complete();
                });
            });
    }

    private userExists(email): Rx.Observable<any> {
        const query = `SELECT id FROM ${Tables.user} u where u.email = "${email}";`;
        return this.sqlService.executeQuery(query);
    }
}
