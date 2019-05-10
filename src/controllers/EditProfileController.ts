import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {EditProfileServices} from "../services/EditProfileServices";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class EditProfileController extends BaseController {
    private sqlService: SqlService;
    private editProfileServices :EditProfileServices;

    constructor() {
        super();
        this.sqlService = new SqlService();
        this.editProfileServices = new EditProfileServices()
    }
    public editBasicDetails(req: Request, res: Response) {
        const user =  this.editProfileServices.editBasicDetails(req.body)
        this.sendResponseWithoutData(user,res)
       } 
       public editUserPlaceData(req: Request, res: Response) {
        const user =  this.editProfileServices.editPlacePlaceData(req.body)
        this.sendResponseWithoutData(user,res)   
       }

       public editUserPublicData(req: Request, res: Response) {
        const user =  this.editProfileServices.editPublicTagData(req.body)
        this.sendResponseWithoutData(user,res)  
       }

       public editUsereducationData(req: Request, res: Response) {
        const user =  this.editProfileServices.editEductionDetails(req.body)
        this.sendResponseWithoutData(user,res)   
       }

       public updateAdharCard(req: Request, res: Response) {
        const query = `update ${Tables.user} set adhar_number = '${req.body.updatedAdharCardNumber}' where id = ${req.body.id};`;
        const UpdatedData = this.sqlService.executeQuery(query);
        this.sendResponseWithoutData(UpdatedData, res);     
       }
       public shareCode(req: Request, res: Response) {
       const sharecode = "http://13.233.155.12:9000/"+req.body.userName 
       res.json({
        "status":"true",
        "Code":200,
        "error":"true",
         "data":sharecode
           
       })
       }
    
}