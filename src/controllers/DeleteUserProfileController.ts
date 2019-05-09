import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {SpringFieldServices} from "../services/SpringFieldServices";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class DeleteUserProfileController extends BaseController {
    private sqlService: SqlService;

    constructor() {
        super();
        this.sqlService = new SqlService();
    }
    public deleteBasicDetails(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} where state_id = ${req.body.state_id} ORDER BY name Asc;`);
        this.sendResponse(cities, res);     
       } 
       public deleteUserPlaceData(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} where state_id = ${req.body.state_id} ORDER BY name Asc;`);
        this.sendResponse(cities, res);     
       }

       public deleteUserPublicData(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} where state_id = ${req.body.state_id} ORDER BY name Asc;`);
        this.sendResponse(cities, res);     
       }

       public deleteUsereducationData(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} where state_id = ${req.body.state_id} ORDER BY name Asc;`);
        this.sendResponse(cities, res);     
       }
    
}