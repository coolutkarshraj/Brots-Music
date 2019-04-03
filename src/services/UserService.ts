import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import {ErrorModel} from "../models/ErrorModel";
import {UserModel} from "../models/UserModel";
import {Tables} from "../database/Tables";
import { EmailService } from '../services/EmailServices';
import { Config } from "../Config";

export class UserService extends ServiceBase {
    private emailService: EmailService;
    constructor() {
        super();
        this.emailService = new EmailService();
    }

    public registerUser(model: UserModel): Rx.Observable<any> {
        return this.userExists(model.email,model.userName)
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

    public userExists(email,userName): Rx.Observable<any> {
          let query = `select id from ${Tables.user} where email = "${email}" or userName = "${userName}";`;
        return this.sqlService.executeQuery(query);
    }

    public sendMailToVerifyRegistration(email,name,otp) {
        const userEmail = email;
            const emailData = {
                email: userEmail,
                subject: 'Welcome To High Mountains',
            };
            const templateModal = {
                name: name,
                sent_otp: otp,
            };
            this.emailService.sendMail(emailData, templateModal, Config.mailTemplate.registrationSuccessfull);

    
    }
}
