import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class continentController extends BaseController {
    private sqlService: SqlService;

    constructor() {
        super();
        this.sqlService = new SqlService();
    }

    public getAllContinentData(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.continents} ORDER BY name Asc;`);
        this.sendResponse(user, res);     
       }

       public getAllContinentDataforAndroid(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.continents} where springFieldId = ${req.body.springFieldId} ORDER BY name Asc;`);
        this.sendResponse(user, res);     
       }   


}