import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import {UserGalleryServices} from "../services/UserGalleryServices"
import SqlService from "../services/common/SQLService"; 
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
       const query = `SELECT
       userimagegallery.id,userimagegallery.imageTitle,userimagegallery.imageIcon,userimagegallery.total_like,
       userimagegallery.total_comment,userBookMarkedImage.isBookMarked, userBookMarkedImage.bookMarkId
       FROM
       ${Tables.userimagegallery}
           INNER JOIN
           ${Tables.userBookMarkedImage} ON  ${Tables.userimagegallery}.id = ${Tables.userBookMarkedImage}.imageId where user_id = ${req.body.userId};`
       const executeQuery =this.sqlService.executeQuery(query)
       console.log(query)
           this.sendResponse(executeQuery,res)  
    }
    public createBookMarkedImages(req: Request, res: Response) {
        const user =  this.userGalleryServices.createGalleryFolder(req.body)
        this.sendResponseWithoutData(user,res)  
    }
    public deleteBookMarkedImages(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.userBookMarkedImage} WHERE bookMarkId = ${req.body.bookMarkId};`
        const executeQuery = this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)  
    }
    

    public getImageAccordingToAlbumFolder(req: Request, res: Response) {
        const query =  `select * from ${Tables.userimagegallery} where gallery_Id = ${req.body.gallery_Id} ORDER BY imageTitle Asc;`
        const executeQuery =  this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)  
    }
    
    public deleteUserProfileImages(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.userBookMarkedImage} WHERE id = ${req.body.imageId};`
        const executeQuery =  this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)  
    }
    
    


}