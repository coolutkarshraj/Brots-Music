import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {AdminUserAction} from "../services/AdminUserAction"
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";
import { from } from "rxjs/observable/from";

export class AdminUserrAction extends BaseController {
    private sqlService: SqlService;
    private adminUserAction :AdminUserAction;

    constructor() {
        super();
        this.sqlService = new SqlService();
        this.adminUserAction = new AdminUserAction();
    }

    public blockedUser(req: Request, res: Response) {
        const query = `update ${Tables.user} set isBlockedUserr = '${`1`}' where id = ${req.body.id};`;
        const UpdatedData = this.sqlService.executeQuery(query);
        this.sendResponseWithoutData(UpdatedData, res);     
       }
       public unBlockedUser(req: Request, res: Response) {
        const query = `update ${Tables.user} set isBlockedUserr = '${`0`}' where id = ${req.body.id};`;
        const UpdatedData = this.sqlService.executeQuery(query);
        this.sendResponseWithoutData(UpdatedData, res);     
       }

       public getBlockedUser(req: Request, res: Response) {

        const query = this.sqlService.executeQuery(`select * from ${Tables.user} where isBlockedUserr = ${`1`} ORDER BY name Asc;`);
        console.log(query)
        this.sendResponse(query, res);   
       }  
       
       public getUnBlockedUser(req: Request, res: Response) {
        const query = this.sqlService.executeQuery(`select * from ${Tables.user} where isBlockedUserr = ${`0`} ORDER BY name Asc;`);
        console.log(query)
        this.sendResponse(query, res);    
       }  

       public ContactUser(req: Request, res: Response) {
        const userquery = this.adminUserAction.userQuery(req.body);
        this.sendResponse(userquery, res);    
       }  

       public deleteUser(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.user} WHERE id = ${req.body.id};`
        const executeQuery =  this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)  
    }
    


}