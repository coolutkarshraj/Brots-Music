import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import {UserGalleryServices} from "../services/UserGalleryServices"
import SqlService from "../services/common/SQLService"; 
import QueryBuilderService from "../services/common/QueryBuilderService"; 
import {UserImageGallery} from "../models/UserImageGallery";
import * as _ from "lodash";
import * as __ from "underscore";
import {Tables} from "../database/Tables";




export class UserGalleryController extends BaseController {
    private sqlService: SqlService;
    private userGalleryServices:UserGalleryServices;

    constructor() {
        super();
        this.sqlService = new SqlService();
        this.userGalleryServices =  new UserGalleryServices()
    }

    public addGalleryFolder(req: Request, res: Response) {
        const user =  this.userGalleryServices.createGalleryFolder(req.body)
        this.sendResponseWithoutData(user,res)  
    }

    public uploadGalleryImages(req: Request, res: Response) {
     this.userGalleryServices.uploadImages(req,res).subscribe((result1)=>{
        if (!_.isEmpty(result1)) {
            req.body.imageIcon = result1
             const user =  this.userGalleryServices.insertImage(req.body)
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

    public getGalleryImages(req: Request, res: Response) {
const query = `select * from ${Tables.userimagegallery} where userId = ${req.body.userId} ORDER BY imageTitle LIMIT ${req.body.offsetLimit};`
       const executeQuery = this.sqlService.executeQuery(query)
       this.sendResponse(executeQuery,res)  
    }

    public getBookMarkedImages(req: Request, res: Response) {
        const user =  this.userGalleryServices.createGalleryFolder(req.body)
        this.sendResponseWithoutData(user,res)  
    }
    public createBookMarkedImages(req: Request, res: Response) {
        const user =  this.userGalleryServices.createGalleryFolder(req.body)
        this.sendResponseWithoutData(user,res)  
    }

    public getImageWithCategories(req: Request, res: Response) {
        const user =  this.userGalleryServices.createGalleryFolder(req.body)
        this.sendResponseWithoutData(user,res)  
    }
    


}