import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class DeleteUserProfileController extends BaseController {
    private sqlService: SqlService;

    constructor() {
        super();
        this.sqlService = new SqlService();
       } 
       public deleteUserPlaceData(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`DELETE  from ${Tables.userProfilePlace} where id = ${req.body.id};`);
        this.sendResponse(cities, res);     
       }

       public deleteUserPublicData(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`DELETE  from ${Tables.userProfilepublicTag} where id = ${req.body.id};`);
        this.sendResponse(cities, res);     
       }

       public deleteUsereducationData(req: Request, res: Response) {
        const cities = this.sqlService.executeQuery(`DELETE  from ${Tables.userProfileeducation} where id = ${req.body.id};`);
        this.sendResponse(cities, res);     
       }
    
}