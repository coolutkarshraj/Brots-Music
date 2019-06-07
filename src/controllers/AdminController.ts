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
import { access } from "fs";

export class AdminController extends BaseController {
    private sqlService: SqlService;
    private adminServices :AdminServices

    constructor() {
        super();
        this.sqlService = new SqlService();
        this.adminServices = new AdminServices()
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
  
     this.adminServices.addContinent(req, res).subscribe((result)=>{

         if (_.isEmpty(result)) {
                 res.json({
                    status: "false",
                    message: `Image not uploaded`,
                    error:"false"
                 })
        }else{
            req.body.imageUrl = result;
            const a = {
              name :req.body.name,
              overview :req.body.overview,
              description :req.body.description,
              followers :0,
              following :0,
              StarLevel :0,
              imageUrl :result,
              itinerary:req.body.itinerary,
              Short_itinerary:req.body.Short_itinerary,
              inclusion:req.body.inclusion,
              Exclusion:req.body.Exclusion,
              TNC:req.body.TNC,
              Others:req.body.Others,
              hm_policy:req.body.hm_policy,
              about:req.body.about,
              t_price:req.body.t_price,
              d_price:req.body.d_price,
              springFieldId:1,
              continentsStatus:0

             }
             this.adminServices.addContinentToDatatoDatabase(a);
            res.json({
                status: "true",
                message: `Continent added successfully`,
                error:"true"
             })
        }
    })
    }

    public addCountry(req: Request, res: Response) {
        this.adminServices.addContinent(req, res).subscribe((result)=>{
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
                country_name :req.body.country_name,
                continentId :req.body.continentId,
                overview :req.body.overview,
                description :req.body.description,
                followers :0,
                following :0,
                Star :0,
                imageUrl :result,
                itinerary:req.body.itinerary,
                Short_itinerary:req.body.Short_itinerary,
                inclusion:req.body.inclusion,
                Exclusion:req.body.Exclusion,
                TNC:req.body.TNC,
                Others:req.body.Others,
                hm_policy:req.body.hm_policy,
                about:req.body.about,
                t_price:req.body.t_price,
                d_price:req.body.d_price,
                countryStatus:0
               }
                this.adminServices.addCountryToDatatoDatabase(a);
               res.json({
                   status: "true",
                   message: `country added successfully`,
                   error:"true",
                });
           }
       })
    }
    public addStates(req: Request, res: Response) {
        this.adminServices.addContinent(req, res).subscribe((result)=>{
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
                name :req.body.name,
                country_id :req.body.country_id,
                overview :req.body.overview,
                description :req.body.description,
                followers :0,
                following :0,
                Star :0,
                imageUrl :result,
                itinerary:req.body.itinerary,
                Short_itinerary:req.body.Short_itinerary,
                inclusion:req.body.inclusion,
                Exclusion:req.body.Exclusion,
                TNC:req.body.TNC,
                Others:req.body.Others,
                hm_policy:req.body.hm_policy,
                about:req.body.about,
                t_price:req.body.t_price,
                d_price:req.body.d_price,
                stateStatus:0
               }
                this.adminServices.addStates(a);
               res.json({
                   status: "true",
                   message: `Continent added successfully`,
                   error:"true",
                })
           }
       })
    }
    public addcities(req: Request, res: Response) {
        this.adminServices.addContinent(req, res).subscribe((result)=>{
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
                name :req.body.name,
                state_id :req.body.state_id,
                overview :req.body.overview,
                description :req.body.description,
                followers :0,
                following :0,
                Star :0,
                imageUrl :result,
                itinerary:req.body.itinerary,
                Short_itinerary:req.body.Short_itinerary,
                inclusion:req.body.inclusion,
                Exclusion:req.body.Exclusion,
                TNC:req.body.TNC,
                Others:req.body.Others,
                hm_policy:req.body.hm_policy,
                about:req.body.about,
                t_price:req.body.t_price,
                d_price:req.body.d_price,
                citieStatus:0,
                city_id: req.body.city_id,
               }
                this.adminServices.addcities(a);
               res.json({
                   status: "true",
                   message: `Continent added successfully`,
                   error:"true" ,
                })
           }
       })
    }
    public addTowns(req: Request, res: Response) {
        this.adminServices.addContinent(req, res).subscribe((result)=>{
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
                towns_name :req.body.towns_name,
                city_id :req.body.city_id,
                overview :req.body.overview,
                description :req.body.description,
                followers :0,
                following :0,
                Star :0,
                imageUrl :result,
                itinerary:req.body.itinerary,
                Short_itinerary:req.body.Short_itinerary,
                inclusion:req.body.inclusion,
                Exclusion:req.body.Exclusion,
                TNC:req.body.TNC,
                Others:req.body.Others,
                hm_policy:req.body.hm_policy,
                about:req.body.about,
                t_price:req.body.t_price,
                d_price:req.body.d_price,
                townsStatus:0,
                state_id: req.body.state_id,
               }
                this.adminServices.addTowns(a);
               res.json({
                   status: "true",
                   message: `Continent added successfully`,
                   error:"true"        
                })
           }
       })
    }
    

    public addplaces(req: Request, res: Response) {
        this.adminServices.addContinent(req, res).subscribe((result)=>{
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
                place_name :req.body.place_name,
                state_id :req.body.state_id,
                overview :req.body.overview,
                description :req.body.description,
                followers :0,
                following :0,
                Star :0,
                imageUrl :result,
                itinerary:req.body.itinerary,
                Short_itinerary:req.body.Short_itinerary,
                inclusion:req.body.inclusion,
                Exclusion:req.body.Exclusion,
                TNC:req.body.TNC,
                Others:req.body.Others,
                hm_policy:req.body.hm_policy,
                about:req.body.about,
                t_price:req.body.t_price,
                d_price:req.body.d_price,
                placeStatus:0,
                country_id: req.body.country_id,
                city_id:req.body.city_id
               }
                this.adminServices.addTowns(a);
               res.json({
                   status: "true",
                   message: `Continent added successfully`,
                   error:"true"        
                })
           }
       })
    }

    public addSpringFieldData(req: Request, res: Response) {
        this.adminServices.addContinent(req, res).subscribe((result)=>{
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
                name :req.body.name,
                overview :req.body.overview,
                description :req.body.description,
                followers :0,
                following :0,
                StarLevel :0,
                imageUrl :result,
                itinerary:req.body.itinerary,
                Short_itinerary:req.body.Short_itinerary,
                inclusion:req.body.inclusion,
                Exclusion:req.body.Exclusion,
                TNC:req.body.TNC,
                Others:req.body.Others,
                hm_policy:req.body.hm_policy,
                about:req.body.about,
                t_price:req.body.t_price,
                d_price:req.body.d_price

               }
              this.adminServices.addSpringFieldData(a);
               res.json({
                   status: "true",
                   message: `Continent added successfully`,
                   error:"true"        
                })
           }
       })
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