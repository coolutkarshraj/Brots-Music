import { Request, Response } from "express";
import { BaseController } from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import * as _ from "lodash";
import * as __ from "underscore";
import {DestinationGalleryServices} from "../services/DestinationGalleryServices"
import {Tables} from "../database/Tables";

export class DestinationGalleryController extends BaseController {
    private sqlService: SqlService;
    private adminServices :DestinationGalleryServices

    constructor() {
        super();
        this.sqlService = new SqlService();
        this.adminServices = new DestinationGalleryServices()
    }

    public addContinentGallery(req: Request, res: Response) {
  
     this.adminServices.addPlacesImages(req, res).subscribe((result)=>{

         if (_.isEmpty(result)) {
                 res.json({
                    status: "false",
                    message: `Image not uploaded`,
                    error:"false"
                 })
        }else{
            req.body.imageUrl = result;
            const a = {
                title :req.body.title,
                description :req.body.description,
                imageUrl :result,
                continentId:req.body.continentId
             }
             this.adminServices.addContinentGallery(a);
            res.json({
                status: "true",
                message: `Continent added successfully`,
                error:"true"
             })
        }
    })
    }

    public addCountry(req: Request, res: Response) {
        this.adminServices.addPlacesImages(req, res).subscribe((result)=>{
            console.log(result)
            if (_.isEmpty(result)) {
                    res.json({
                       status: "false",
                       message: `Image not uploaded`,
                       error:"false"
                    })
           }else{
            req.body.imageUrl = result;
            const a = {
                title :req.body.title,
                description :req.body.description,
                imageUrl :result,
                countryId:req.body.countryId
               }
                this.adminServices.addCountryGallery(a);
               res.json({
                   status: "true",
                   message: `Gallery added successfully`,
                   error:"true",
                });
           }
       })
    }
    public addStates(req: Request, res: Response) {
        this.adminServices.addPlacesImages(req, res).subscribe((result)=>{
            console.log(result)
            if (_.isEmpty(result)) {
                    res.json({
                       status: "false",
                       message: `Image not uploaded`,
                       error:"false",
                    })
           }else{
            req.body.imageUrl = result;
            const a = {
                title :req.body.title,
                description :req.body.description,
                imageUrl :result,
                stateId:req.body.stateId
               }
                this.adminServices.addStatesGallery(a);
               res.json({
                   status: "true",
                   message: `Gallery added successfully`,
                   error:"true",
                })
           }
       })
    }
    public addcities(req: Request, res: Response) {
        this.adminServices.addPlacesImages(req, res).subscribe((result)=>{
            console.log(result)
            if (_.isEmpty(result)) {
                    res.json({
                       status: "false",
                       message: `Image not uploaded`,
                       error:"false",
                    });
           }else{
            req.body.imageUrl = result;
            const a = {
                title :req.body.title,
                description :req.body.description,
                imageUrl :result,
                cityId:req.body.cityId
               }
                this.adminServices.addcitiesGallery(a);
               res.json({
                   status: "true",
                   message: `Gallery added successfully`,
                   error:"true" ,
                })
           }
       })
    }
    public addTowns(req: Request, res: Response) {
        this.adminServices.addPlacesImages(req, res).subscribe((result)=>{
            console.log(result)
            if (_.isEmpty(result)) {
                    res.json({
                       status: "false",
                       message: `Image not uploaded`,
                       error:"false",
                    });
           }else{
            req.body.imageUrl = result;
            const a = {
                title :req.body.title,
                description :req.body.description,
                imageUrl :result,
                townId:req.body.townId
               }
                this.adminServices.addTownsGallery(a);
               res.json({
                   status: "true",
                   message: `Gallery added successfully`,
                   error:"true"        
                })
           }
       })
    }
    

    public addplaces(req: Request, res: Response) {
        this.adminServices.addPlacesImages(req, res).subscribe((result)=>{
            console.log(result)
            if (_.isEmpty(result)) {
                    res.json({
                       status: "false",
                       message: `Image not uploaded`,
                       error:"false",
                    });
           }else{
            req.body.imageUrl = result;
            const a = {
                title :req.body.title,
                description :req.body.description,
                imageUrl :result,
                placeId:req.body.placeId
               }
                this.adminServices.addplacesGallery(a);
               res.json({
                   status: "true",
                   message: `Gallery added successfully`,
                   error:"true"        
                })
           }
       })
    }

    public addSpringFieldData(req: Request, res: Response) {
        this.adminServices.addPlacesImages(req, res).subscribe((result)=>{
            console.log(result)
            if (_.isEmpty(result)) {
                    res.json({
                       status: "false",
                       message: `Image not uploaded`,
                       error:"false"        
                    })
                      
           }else{
            req.body.imageUrl = result;
              const a = {
                title :req.body.title,
                description :req.body.description,
                imageUrl :result,
                springFieldId:req.body.springFieldId
               }
              this.adminServices.addSpringFieldGalleryData(a);
               res.json({
                   status: "true",
                   message: `Gallery added successfully`,
                   error:"true"        
                })
           }
       })
    }

    public getContinentgallery(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.continentgallery} where continentId = ${req.body.id};`);
        this.sendResponse(user, res);     
       }

       public getCountrygallery(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.countryGallery} where countryId = ${req.body.id};`);
        this.sendResponse(user, res);     
       }

       public getStateGallery(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.stateGallery} where stateId = ${req.body.id};`);
        this.sendResponse(user, res);     
       }

       public getCitiesGallery(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.cityGalley} where cityId = ${req.body.id};`);
        this.sendResponse(user, res);     
       }

       public getTownsGallery(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.townsGalley} where townId = ${req.body.id};`);
        this.sendResponse(user, res);     
       }

       public getPlacesGallery(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.placeGalley} where placeId = ${req.body.id};`);
        this.sendResponse(user, res);     
       }

       public getSpringFieldgallery(req: Request, res: Response) {
        const user = this.sqlService.executeQuery(`select * from ${Tables.springFieldGallery} where springFieldId = ${req.body.id};`);
        this.sendResponse(user, res);     
       }

       public deleteSpringField(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.springFieldGallery} WHERE springFieldId = ${req.body.id};`
        const executeQuery =  this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)  
    }

    public deleteContinent(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.continentgallery} WHERE continentId = ${req.body.id};`
        const executeQuery =  this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)  
    }

    public deleteCountry(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.countryGallery} WHERE countryId = ${req.body.id};`
        const executeQuery =  this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)  
    }

    public deleteState(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.stateGallery} WHERE stateId = ${req.body.id};`
        const executeQuery =  this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)  
    }

    public deleteCity(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.cityGalley} WHERE cityId = ${req.body.id};`
        const executeQuery =  this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)  
    }

    public deleteTowns(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.townsGalley} WHERE townId = ${req.body.id};`
        const executeQuery =  this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)  
    }

    public deletePlaces(req: Request, res: Response) {
        const query =  `DELETE FROM  ${Tables.placeGalley} WHERE placeId = ${req.body.id};`
        const executeQuery =  this.sqlService.executeQuery(query)
        this.sendResponseWithoutData(executeQuery,res)  
    }

}