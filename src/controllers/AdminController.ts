import { Request, Response } from "express";
import { BaseController } from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import { Tables } from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";
import {UserModel} from "../models/UserModel";
import {ErrorModel} from "../models/ErrorModel";
import {SucessModel} from "../models/SucessModel";
import {AdminServices} from "../services/AdminServices"
import {Logger} from "../services/common/Logger";

export class AdminController extends BaseController {
    private sqlService: SqlService;
    private adminServices :AdminServices

    constructor() {
        super();
        this.sqlService = new SqlService();
    }

    public loginAdmin(req: Request, res: Response) {
        console.log(req.body)
        var query;
        const email = req.body.email;
        const password = req.body.password;
        const type = req.body.type
        if(req.body.type == null||req.body.type == ''){
            return res.json({
                    status: "false",
                    message: `Missing Paramater Type`,
                    error:"false"    
            })
        }
        if(req.body.email == null||req.body.email == ''){
            return res.json({
                    status: "false",
                    message: `Missing Paramater email`,
                    error:"false"    
            })
        }
        if(req.body.password == null||req.body.password == ''){
            return res.json({
                    status: "false",
                    message: `Missing Paramater password`,
                    error:"false"    
            })
        }
        if(type == "1"){
            query = `select * from ${Tables.admin} where email = '${email}' and password = '${password}'`;
        }else if(type == "2"){
            query = `select * from ${Tables.admin} where userName = '${email}' and password = '${password}'`;
        }
        const promise = this.sqlService.getSingle(query);
        promise.subscribe((result: UserModel) => {
            console.log(result)
            if (_.isEmpty(result)) {
                const error: ErrorModel = {
                    status: "false",
                    message: `User with email ${req.body.email} and Password ${req.body.password} doesn't exists.`,
                    error:"false"           
                }
                res.send(error);
                return;
            }
            if (this.isAlreadyLoggedIn(result)) {
                const sucessButAlreadyLoggedin: SucessModel = {
                    status: "false",
                    message: `User with email ${req.body.email} already Logged in From Device.`,
                    error:"false" ,
                    data:result.id 

                }
                res.send(sucessButAlreadyLoggedin);
                return;
            }
            this.updateLoginStatus( 1,1,result.id, (err, success) => {
               
                if (err == null) {
                    const sucess: SucessModel = {
                        status: "true",
                        message: `User with email ${req.body.email}  Logged inSuccessfull.`,
                        error:"true" ,
                        data: result.id         
                    }
                    res.send(sucess)
                }
            });

        }, (error) => null, null);
    }

    public addContinent(req: Request, res: Response) {
     const uploadsongurl = this.adminServices.addContinent(req, res);
     this.sendResponse(uploadsongurl, res)
        
    }

    public addCountry(req: Request, res: Response) {
      
    }
    public addStates(req: Request, res: Response) {
      
    }
    public addcities(req: Request, res: Response) {
      
    }
    public addTowns(req: Request, res: Response) {
      
    }
    public addSpringFieldData(req: Request, res: Response) {
      
    }

    private isAlreadyLoggedIn(user: UserModel): boolean {
        return user.isLoggedIn.toString() === "1";
    }
private updateLoginStatus(onlineStatus: number, loggedInStatus: number, id:number, callback) {
        const query = `update ${Tables.admin} set isLoggedIn = ${loggedInStatus} ,onlineStatus = ${onlineStatus} where id = ${id};`;
        this.sqlService.executeQuery(query).subscribe((result) => {
            if (result !== null) {
                callback(null, result);
            }
        }, (error) => null, null);
    }

    

}