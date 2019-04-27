import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class TownsController extends BaseController {
    private sqlService: SqlService;

    constructor() {
        super();
        this.sqlService = new SqlService();
    }
       public getAllTownsData(req: Request, res: Response) {
        const trending = this.sqlService.executeQuery(`select * from ${Tables.towns} where townsStatus = '2' ORDER BY towns_name Asc;`);
        trending.subscribe((result) => {
            const featured = this.sqlService.executeQuery(`select * from ${Tables.towns} where townsStatus = '3' ORDER BY towns_name Asc;`);
            featured.subscribe((result1) => {
                const allCountry = this.sqlService.executeQuery(`select * from ${Tables.towns} ORDER BY towns_name Asc;`);
                allCountry.subscribe((result3) => {
                    res.json({
                        "status": "true",
                        "Code": "true",
                        "error": "true",
                        "trending": result,
                        "featured":result1,
                        "allCountry":result3

                    })
                })

            })
        })  
       }

}