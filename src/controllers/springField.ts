import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {SpringFieldServices} from "../services/SpringFieldServices";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class springField extends BaseController {
    private springServices: SpringFieldServices;
    private sqlService: SqlService;

    constructor() {
        super();
        this.springServices = new SpringFieldServices();
        this.sqlService = new SqlService();
    }

    public getAllSpringFieldData(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.springfield};`);
        this.sendResponse(user, res);     
       }

       public addSpringFieldData(req: Request, res: Response) {
        if(req.body.constructor === Object && Object.keys(req.body).length === 0){
            return res.json({
                "status":"false",
                "message":"Missing All Parameter",
                "error":"false",  
            });  
        }
        this.springServices.addSpringFieldData(req.body,res,(err, success)=>{
            if(err == null){
              return res.json({
                status: "true",
                message: `Spring Data Added SuccessFully.`,
                error:"true"        
              })  
            }
           
        }) 
       
       }
}