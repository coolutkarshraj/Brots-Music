import { Request, Response } from "express";
import { BaseController } from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import { Tables } from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class BestOffer extends BaseController {
    private sqlService: SqlService;

    constructor() {
        super();
        this.sqlService = new SqlService();
    }

    public getAllBestOffer(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.bestOffer} where city_id = ${req.body.city_id} ORDER BY title Asc;`);
        this.sendResponse(user, res);
    }

    

}