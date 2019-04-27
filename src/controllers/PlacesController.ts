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

    public getAllplaces(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.places} where city_id = ${req.body.city_id} ORDER BY places desc;`);
        this.sendResponse(user, res);     
       }
       
       public getAllplacesOnbasiOfTrandingPopularDispopular(req: Request, res: Response) {
        const trending = this.sqlService.executeQuery(`select * from ${Tables.places} where townsStatus = '2' ORDER BY places Asc;`);
        trending.subscribe((result) => {
            const featured = this.sqlService.executeQuery(`select * from ${Tables.places} where townsStatus = '3' ORDER BY places Asc;`);
            featured.subscribe((result1) => {
                const allCountry = this.sqlService.executeQuery(`select * from ${Tables.places} ORDER BY places Asc;`);
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

       public getHomeData(req: Request, res: Response) {
        const trending = this.sqlService.executeQuery(`select * from ${Tables.cities} where stateStatus = '2' ORDER BY name Asc;`);
        trending.subscribe((result) => {
            const featured = this.sqlService.executeQuery(`select * from ${Tables.countriesList} where countryStatus = '3' ORDER BY country_name Asc;`);
            featured.subscribe((result1) => {
                const allCountry = this.sqlService.executeQuery(`select * from ${Tables.states} where stateStatus = '3' ORDER BY places Asc;`);
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