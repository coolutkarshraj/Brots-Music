import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import {UserGalleryServices} from "../services/UserGalleryServices"
import SqlService from "../services/common/SQLService";
import * as _ from "lodash";
import * as __ from "underscore";


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
        const user =  this.userGalleryServices.createGalleryFolder(req.body)
        this.sendResponseWithoutData(user,res)  
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
  

}