import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import {UserGalleryServices} from "../services/UserGalleryServices"
import SqlService from "../services/common/SQLService"; 
import QueryBuilderService from "../services/common/QueryBuilderService"; 
import * as _ from "lodash";
import * as __ from "underscore";
import {Tables} from "../database/Tables";




export class UserGalleryController extends BaseController {
    private sqlService: SqlService;
    private userGalleryServices:UserGalleryServices;
    private queryBuilderService:QueryBuilderService;

    constructor() {
        super();
        this.sqlService = new SqlService();
        this.userGalleryServices =  new UserGalleryServices()
        this.queryBuilderService = new QueryBuilderService()
    }

    public addGalleryFolder(req: Request, res: Response) {
        const user =  this.userGalleryServices.createGalleryFolder(req.body)
        this.sendResponseWithoutData(user,res)  
    }

    public uploadGalleryImages(req: Request, res: Response) {
     this.userGalleryServices.uploadImages(req,res,req.body).subscribe((result1)=>{
        if (!_.isEmpty(result1)) {
            const result = this.queryBuilderService.getInsertQuery(Tables.userimagegallery,req.body)
            // const result = `INSERT INTO ${Tables.userImageGallery} (imageTitle, imageIcon, createdDate,total_like,total_comment,total_share,userIdgallery_Id,isBookMarked )
            // VALUES (${req.body.imageTitle}, ${result1}, ${req.body.createdDate}, ${req.body.total_like},${req.body.total_comment},${req.body.total_share}
            //     ${req.body.userIdgallery_Id}, ${req.body.isBookMarked});` 
            this.sqlService.executeQuery(result)
            res.send
          
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
        const user =  this.userGalleryServices.createGalleryFolder(req.body)
        this.sendResponseWithoutData(user,res)  
    }

    public getBookMarkedImages(req: Request, res: Response) {
        const user =  this.userGalleryServices.createGalleryFolder(req.body)
        this.sendResponseWithoutData(user,res)  
    }

    public getImageWithCategories(req: Request, res: Response) {
        const user =  this.userGalleryServices.createGalleryFolder(req.body)
        this.sendResponseWithoutData(user,res)  
    }
    
    public userExists(id): Rx.Observable<any> {
        let query = `select id from ${Tables.user} where id = "${id}";`;
      return this.sqlService.executeQuery(query);
  }

}