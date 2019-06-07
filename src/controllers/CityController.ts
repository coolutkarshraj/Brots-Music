import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class CityController extends BaseController {
    private sqlService: SqlService;

    constructor() {
        super();
        this.sqlService = new SqlService();
    }

    public getAllCities(req: Request, res: Response) {
        // const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} where state_id = ${req.body.state_id} ORDER BY name Asc;`);
        const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} ORDER BY name Asc;`);
        this.sendResponse(cities, res);     
       }

       public getAllCitiesForAndroid(req: Request, res: Response) {
         const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} where state_id = ${req.body.state_id} ORDER BY name Asc;`);
       // const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} ORDER BY name Asc;`);
        this.sendResponse(cities, res);     
       }

       public getAllcityOnbasiOfTrandingPopularDispopular(req: Request, res: Response) {
        const trending = this.sqlService.executeQuery(`select * from ${Tables.cities} where citieStatus = '2' ORDER BY name Asc;`);
        trending.subscribe((result) => {
            const featured = this.sqlService.executeQuery(`select * from ${Tables.cities} where citieStatus = '3' ORDER BY name Asc;`);
            featured.subscribe((result1) => {
                const allCountry = this.sqlService.executeQuery(`select * from ${Tables.cities} ORDER BY name Asc;`);
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