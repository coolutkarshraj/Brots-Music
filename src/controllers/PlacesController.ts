import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class PlacesController extends BaseController {
    private sqlService: SqlService;

    constructor() {
        super();
        this.sqlService = new SqlService();
    }

    public getAllSpringFieldData(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.springfield};`);
        this.sendResponse(user, res);     
       }


}