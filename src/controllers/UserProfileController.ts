import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import {UserProfileServices} from "../services/UserProfileServices"
import SqlService from "../services/common/SQLService";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";


export class UserProfileController extends BaseController {
    private sqlService: SqlService;
    private userProfileServices:UserProfileServices;

    constructor() {
        super();
        this.sqlService = new SqlService();
        this.userProfileServices =  new UserProfileServices()
    }

    public getAllUsers(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.user};`);
        this.sendResponse(user, res);
    }
    public getSingleUserData(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.user} where id = ${req.body.id};`);
        this.sendResponse(user, res);
    }
    public getUserPlaceData(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.userProfilePlace} where userId = ${req.body.userId};`);
        this.sendResponse(user, res);
    }
    public getUsereducationData(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.userProfileeducation} where userId = ${req.body.userId};`);
        this.sendResponse(user, res);
    }
    public getUserpublicTagData(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.userProfilepublicTag} where userId = ${req.body.userId};`);
        this.sendResponse(user, res);
    }

    public addUserPlaceData(req: Request, res: Response) {
        const user = this.userProfileServices.addUserPlaceData(req.body)  
        this.sendResponse(user,res)
    }
    public addUsereducationData(req: Request, res: Response) {
        const user = this.userProfileServices.addUserEducationData(req.body)  
        this.sendResponse(user,res)
    }
    public addUserpublicTagData(req: Request, res: Response) {
        const user = this.userProfileServices.addUserPublicTacData(req.body)  
        this.sendResponse(user,res)
    }
    

}