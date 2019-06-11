import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {EditProfileServices} from "../services/EditProfileServices";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class EditDistinationController extends BaseController {
    private sqlService: SqlService;
    private editProfileServices :EditProfileServices;

    constructor() {
        super();
        this.sqlService = new SqlService();
        this.editProfileServices = new EditProfileServices()
    }
    public editSpringFieldData(req: Request, res: Response) {
        if(req.body.imageUrl == null){

        }else{

        }
        const user =  this.editProfileServices.editBasicDetails(req.body)
        this.sendResponseWithoutData(user,res)
       } 
       public editContinent(req: Request, res: Response) {
        if(req.body.imageUrl == null){

        }else{
            
        }
        const user =  this.editProfileServices.editPlacePlaceData(req.body)
        this.sendResponseWithoutData(user,res)   
       }

       public editCountry(req: Request, res: Response) {
        if(req.body.imageUrl == null){

        }else{
            
        }
        const user =  this.editProfileServices.editPublicTagData(req.body)
        this.sendResponseWithoutData(user,res)  
       }

       public editStates(req: Request, res: Response) {
        if(req.body.imageUrl == null){

        }else{
            
        }
        const user =  this.editProfileServices.editEductionDetails(req.body)
        this.sendResponseWithoutData(user,res)   
       }

       public editcities(req: Request, res: Response) {
        if(req.body.imageUrl == null){

        }else{
            
        }
        const query = `update ${Tables.user} set adhar_number = '${req.body.updatedAdharCardNumber}' where id = ${req.body.id};`;
        const UpdatedData = this.sqlService.executeQuery(query);
        this.sendResponseWithoutData(UpdatedData, res);     
       }

       public editplaces(req: Request, res: Response) {
        if(req.body.imageUrl == null){

        }else{
            
        }
       const sharecode = "http://13.233.155.12:9000/"+req.body.userName 
       res.json({
        "status":"true",
        "Code":200,
        "error":"true",
         "data":sharecode
           
       })
       }

       public editTowns(req: Request, res: Response) {
        if(req.body.imageUrl == null){

        }else{
            
        }
        this.editProfileServices.uploadImages(req,res).subscribe((result1)=>{
           if (!_.isEmpty(result1)) {
            const user =  this.editProfileServices.updateProfileImage(req.body,result1)
            this.sendResponseWithoutData(user,res)  
             
           }else{
               return res.json({
                   "status":"false",
                   "message":"Something Went Wrong",
                   "error":"false"
               })
           }
        }) 
       }
    
}