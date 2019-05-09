import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {SpringFieldServices} from "../services/SpringFieldServices";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class EditProfileController extends BaseController {
    private sqlService: SqlService;

    constructor() {
        super();
        this.sqlService = new SqlService();
    }
    public editBasicDetails(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} where state_id = ${req.body.state_id} ORDER BY name Asc;`);
        this.sendResponse(cities, res);     
       } 
       public editUserPlaceData(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} where state_id = ${req.body.state_id} ORDER BY name Asc;`);
        this.sendResponse(cities, res);     
       }

       public editUserPublicData(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} where state_id = ${req.body.state_id} ORDER BY name Asc;`);
        this.sendResponse(cities, res);     
       }

       public editUsereducationData(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} where state_id = ${req.body.state_id} ORDER BY name Asc;`);
        this.sendResponse(cities, res);     
       }

       public sendPasswordResetLink(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} where state_id = ${req.body.state_id} ORDER BY name Asc;`);
        this.sendResponse(cities, res);     
       }
       public updatePassword(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} where state_id = ${req.body.state_id} ORDER BY name Asc;`);
        this.sendResponse(cities, res);     
       }
       public updateAdharCard(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} where state_id = ${req.body.state_id} ORDER BY name Asc;`);
        this.sendResponse(cities, res);     
       }
       public shareCode(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`select * from ${Tables.cities} where state_id = ${req.body.state_id} ORDER BY name Asc;`);
        this.sendResponse(cities, res);     
       }
    
}