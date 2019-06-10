import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {SpringFieldServices} from "../services/SpringFieldServices";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class DestinationController extends BaseController {
    private springServices: SpringFieldServices;
    private sqlService: SqlService;

    constructor() {
        super();
        this.springServices = new SpringFieldServices();
        this.sqlService = new SqlService();
    }

       public getAllSpringFieldDatabyid(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.springfield} where id = ${req.body.id};`);
        this.sendResponse(user, res);     
       }

       public getAllcontinentFieldDatabyid(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.continents} where id = ${req.body.id};`);
        this.sendResponse(user, res);     
       }

       public getAllcountryFieldDatabyid(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.countriesList} where id = ${req.body.id};`);
        this.sendResponse(user, res);     
       }

       public getAllstateFieldDatabyid(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.states} where id = ${req.body.id};`);
        this.sendResponse(user, res);     
       }

       public getAllcityFieldDatabyid(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.cities} where id = ${req.body.id};`);
        this.sendResponse(user, res);     
       }

       public getAlltownsFieldDatabyid(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.towns} where id = ${req.body.id};`);
        this.sendResponse(user, res);     
       }

       public getAllplacesFieldDatabyid(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.places} where id = ${req.body.id};`);
        this.sendResponse(user, res);     
       }

}