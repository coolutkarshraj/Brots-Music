import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {EditProfileServices} from "../services/EditProfileServices";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class DeleteDestinationController extends BaseController {
    private sqlService: SqlService;
    private editProfileServices :EditProfileServices;

    constructor() {
        super();
        this.sqlService = new SqlService();
        this.editProfileServices = new EditProfileServices()
    }
    public deleteSpringFieldData(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.springfield} WHERE id = ${req.body.id};`
        const executeQuery = this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)
       } 
       public deleteContinent(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.continents} WHERE id = ${req.body.id};`
        const executeQuery = this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)
       }

       public deleteCountry(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.countriesList} WHERE id = ${req.body.id};`
        const executeQuery = this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)
       }

       public deleteStates(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.states} WHERE id = ${req.body.id};`
        const executeQuery = this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)
       }

       public deletecities(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.cities} WHERE id = ${req.body.id};`
        const executeQuery = this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res) 
       }
       public deleteTowns(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.towns} WHERE id = ${req.body.id};`
        const executeQuery = this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)
       }

       public deleteplaces(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.places} WHERE id = ${req.body.id};`
        const executeQuery = this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)
       }
    
}