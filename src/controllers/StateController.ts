import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class StateController extends BaseController {
    private sqlService: SqlService;

    constructor() {
        super();
        this.sqlService = new SqlService();
    }

    public getAllState(req: Request, res: Response) {
        // const user = this.sqlService.executeQuery(`select * from ${Tables.states} where country_id  = ${req.body.country_id}  ORDER BY name Asc;`);
        const user = this.sqlService.executeQuery(`select * from ${Tables.states}  ORDER BY name Asc;`);
        this.sendResponse(user, res);
       }

    public getAllStateforAndroid(req: Request, res: Response) {
         const user = this.sqlService.executeQuery(`select * from ${Tables.states} where country_id  = ${req.body.country_id}  ORDER BY name Asc;`);
         this.sendResponse(user, res);
       }

       public getAllstatesOnbasiOfTrandingPopularDispopularv(req: Request, res: Response) {
        const trending = this.sqlService.executeQuery(`select * from ${Tables.states} where stateStatus = '1' ORDER BY name Asc;`);
        trending.subscribe((result) => {
            const featured = this.sqlService.executeQuery(`select * from ${Tables.states} where stateStatus = '2' ORDER BY name Asc;`);
            featured.subscribe((result1) => {
                const allCountry = this.sqlService.executeQuery(`select * from ${Tables.states} ORDER BY name Asc;`);
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