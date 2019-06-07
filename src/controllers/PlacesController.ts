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
        const user = this.sqlService.executeQuery(`select * from ${Tables.places} where city_id = ${req.body.city_id} ORDER BY place_name desc;`);
        this.sendResponse(user, res);     
       }
       public getAlltowns(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.towns}  ORDER BY towns_name desc;`);
        this.sendResponse(user, res);     
       }
       public getAllPlaces(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.places}  ORDER BY place_name desc;`);
        this.sendResponse(user, res);     
       }
       
       
       
       public getAllplacesOnbasiOfTrandingPopularDispopular(req: Request, res: Response) {
        const trending = this.sqlService.executeQuery(`select * from ${Tables.places} where placeStatus = '2' ORDER BY place_name Asc;`);
        trending.subscribe((result) => {
            const featured = this.sqlService.executeQuery(`select * from ${Tables.places} where placeStatus = '3' ORDER BY place_name Asc;`);
            featured.subscribe((result1) => {
                const allCountry = this.sqlService.executeQuery(`select * from ${Tables.places} ORDER BY place_name Asc;`);
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
                const allCountry = this.sqlService.executeQuery(`select * from ${Tables.states} where stateStatus = '3' ORDER BY name Asc;`);
                allCountry.subscribe((result3) => {
                    res.json({
                        "status": "true",
                        "Code": "true",
                        "error": "true",
                        "trendingCities": result,
                        "featuredCountry":result1,
                        "PopularStates":result3

                    })
                })

            })
        })  
       }
       
       public getPlaceInFormation(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.placeInformation} where place_id = ${req.body.place_id} ORDER BY place_name desc;`);
        this.sendResponse(user, res);     
       }
       
}