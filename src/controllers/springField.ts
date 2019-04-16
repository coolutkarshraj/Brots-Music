import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {SpringFieldServices} from "../services/SpringFieldServices";
import {UserModel} from "../models/UserModel";
import {Logger} from "../services/common/Logger";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";
import * as os from "os";
import {ErrorModel} from "../models/ErrorModel";
import {SucessModel} from "../models/SucessModel";
import { json } from "body-parser";

export class springField extends BaseController {
    private userService: SpringFieldServices;
    private sqlService: SqlService;

    constructor() {
        super();
        this.userService = new SpringFieldServices();
        this.sqlService = new SqlService();
    }

    public getAllSpringFieldData(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.springfield};`);
        this.sendResponse(user, res);
 
       
           
       }
}