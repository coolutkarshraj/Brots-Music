import { Request, Response } from "express";
import { BaseController } from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import { EditDestinationServices } from "../services/EditDestinationServices";
import { Tables } from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";

export class EditDistinationController extends BaseController {
    private sqlService: SqlService;
    private editDistinationServices: EditDestinationServices;

    constructor() {
        super();
        this.sqlService = new SqlService();
        this.editDistinationServices = new EditDestinationServices()
    }
    public editSpringFieldData(req: Request, res: Response) {
        this.editDistinationServices.uploadImages(req, res).subscribe((result) => {
            if (_.isEmpty(result)) {
                res.json({
                    status: "false",
                    message: `Image not uploaded`,
                    error: "false"
                });

            } else {
                req.body.imageUrl = result;
                const a = {
                    name: req.body.name,
                    overview: req.body.overview,
                    description: req.body.description,
                    followers: 0,
                    following: 0,
                    StarLevel: 0,
                    imageUrl: result,
                    itinerary: req.body.itinerary,
                    Short_itinerary: req.body.Short_itinerary,
                    inclusion: req.body.inclusion,
                    Exclusion: req.body.Exclusion,
                    TNC: req.body.TNC,
                    Others: req.body.Others,
                    hm_policy: req.body.hm_policy,
                    about: req.body.about,
                    t_price: req.body.t_price,
                    d_price: req.body.d_price,
                    id :req.body.id
                }
                const re1s  = this.editDistinationServices.editSpringFieldData(a);
                this.sendResponse(re1s,res)
            }
        })


    }

    public editSpringFieldDatawithoutImages(req: Request, res: Response) {
        const user = this.editDistinationServices.editSpringFieldData(req.body)
        this.sendResponseWithoutData(user, res)

    }

    
    public editContinentDatawithoutImages(req: Request, res: Response) {
        const user = this.editDistinationServices.editContinent(req.body)
        this.sendResponseWithoutData(user, res)

    }

    public editCountryDatawithoutImages(req: Request, res: Response) {
        const user = this.editDistinationServices.editCountry(req.body)
        this.sendResponseWithoutData(user, res)

    }

    public editStateDatawithoutImages(req: Request, res: Response) {
        const user = this.editDistinationServices.editStates(req.body)
        this.sendResponseWithoutData(user, res)

    }

    public editCityDatawithoutImages(req: Request, res: Response) {
        const user = this.editDistinationServices.editcities(req.body)
        this.sendResponseWithoutData(user, res)

    }

    public edittownsDatawithoutImages(req: Request, res: Response) {
        const user = this.editDistinationServices.editTowns(req.body)
        this.sendResponseWithoutData(user, res)

    }

    public editPlacesDatawithoutImages(req: Request, res: Response) {
        const user = this.editDistinationServices.editplaces(req.body)
        this.sendResponseWithoutData(user, res)

    }

    public editContinent(req: Request, res: Response) {
            this.editDistinationServices.uploadImages(req, res).subscribe((result) => {

                if (_.isEmpty(result)) {
                    res.json({
                        status: "false",
                        message: `Image not uploaded`,
                        error: "false"
                    })
                } else {
                    req.body.imageUrl = result;
                    const a = {
                        name: req.body.name,
                        overview: req.body.overview,
                        description: req.body.description,
                        followers: 0,
                        following: 0,
                        StarLevel: 0,
                        imageUrl: result,
                        itinerary: req.body.itinerary,
                        Short_itinerary: req.body.Short_itinerary,
                        inclusion: req.body.inclusion,
                        Exclusion: req.body.Exclusion,
                        TNC: req.body.TNC,
                        Others: req.body.Others,
                        hm_policy: req.body.hm_policy,
                        about: req.body.about,
                        t_price: req.body.t_price,
                        d_price: req.body.d_price,
                        springFieldId: 1,
                        continentsStatus: 0,
                        id:req.body.id

                    }
                   const resu =  this.editDistinationServices.editContinent(a);
                   this.sendResponse(resu,res)
                }
            })
        
    }

    public editCountry(req: Request, res: Response) {
            this.editDistinationServices.uploadImages(req, res).subscribe((result) => {
                console.log(result)
                if (_.isEmpty(result)) {
                    res.json({
                        status: "false",
                        message: `Image not uploaded`,
                        error: "false"
                    })
                } else {
                    req.body.imageUrl = result;
                    const a = {
                        country_name: req.body.country_name,
                        continentId: req.body.continentId,
                        overview: req.body.overview,
                        description: req.body.description,
                        followers: 0,
                        following: 0,
                        Star: 0,
                        imageUrl: result,
                        itinerary: req.body.itinerary,
                        Short_itinerary: req.body.Short_itinerary,
                        inclusion: req.body.inclusion,
                        Exclusion: req.body.Exclusion,
                        TNC: req.body.TNC,
                        Others: req.body.Others,
                        hm_policy: req.body.hm_policy,
                        about: req.body.about,
                        t_price: req.body.t_price,
                        d_price: req.body.d_price,
                        countryStatus: 0,
                        id:req.body.id
                    }
                   const conty =  this.editDistinationServices.editCountry(a);
                   this.sendResponse(conty , res)
                    // res.json({
                    //     status: "true",
                    //     message: `country added successfully`,
                    //     error: "true",
                    // });
                }
            })
        }

    public editStates(req: Request, res: Response) {
            this.editDistinationServices.uploadImages(req, res).subscribe((result) => {
                console.log(result)
                if (_.isEmpty(result)) {
                    res.json({
                        status: "false",
                        message: `Image not uploaded`,
                        error: "false",
                    })
                } else {
                    req.body.imageUrl = result;
                    const a = {
                        name: req.body.name,
                        country_id: req.body.country_id,
                        overview: req.body.overview,
                        description: req.body.description,
                        followers: 0,
                        following: 0,
                        Star: 0,
                        imageUrl: result,
                        itinerary: req.body.itinerary,
                        Short_itinerary: req.body.Short_itinerary,
                        inclusion: req.body.inclusion,
                        Exclusion: req.body.Exclusion,
                        TNC: req.body.TNC,
                        Others: req.body.Others,
                        hm_policy: req.body.hm_policy,
                        about: req.body.about,
                        t_price: req.body.t_price,
                        d_price: req.body.d_price,
                        stateStatus: 0,
                        id:req.body.id
                    }
                   const states =  this.editDistinationServices.editStates(a);
                    // res.json({
                    //     status: "true",
                    //     message: `Continent added successfully`,
                    //     error: "true",
                    // })

                    this.sendResponse(states,res);
                }
            })
        }

    public editcities(req: Request, res: Response) {
            this.editDistinationServices.uploadImages(req, res).subscribe((result) => {
                console.log(result)
                if (_.isEmpty(result)) {
                    res.json({
                        status: "false",
                        message: `Image not uploaded`,
                        error: "false",
                    });
                } else {
                    req.body.imageUrl = result;
                    const a = {
                        name: req.body.name,
                        state_id: req.body.state_id,
                        overview: req.body.overview,
                        description: req.body.description,
                        followers: 0,
                        following: 0,
                        Star: 0,
                        imageUrl: result,
                        itinerary: req.body.itinerary,
                        Short_itinerary: req.body.Short_itinerary,
                        inclusion: req.body.inclusion,
                        Exclusion: req.body.Exclusion,
                        TNC: req.body.TNC,
                        Others: req.body.Others,
                        hm_policy: req.body.hm_policy,
                        about: req.body.about,
                        t_price: req.body.t_price,
                        d_price: req.body.d_price,
                        citieStatus: 0,
                        id:req.body.id
                    }
                   const cities =  this.editDistinationServices.editcities(a);
                   this.sendResponse(cities,res)
                    // res.json({
                    //     status: "true",
                    //     message: `Continent added successfully`,
                    //     error: "true",
                    // })
                }
            })
        }
    

    public editplaces(req: Request, res: Response) {
            this.editDistinationServices.uploadImages(req, res).subscribe((result) => {
                console.log(result)
                if (_.isEmpty(result)) {
                    res.json({
                        status: "false",
                        message: `Image not uploaded`,
                        error: "false",
                    });
                } else {
                    req.body.imageUrl = result;
                    const a = {
                        place_name: req.body.place_name,
                        state_id: req.body.state_id,
                        overview: req.body.overview,
                        description: req.body.description,
                        followers: 0,
                        following: 0,
                        Star: 0,
                        imageUrl: result,
                        itinerary: req.body.itinerary,
                        Short_itinerary: req.body.Short_itinerary,
                        inclusion: req.body.inclusion,
                        Exclusion: req.body.Exclusion,
                        TNC: req.body.TNC,
                        Others: req.body.Others,
                        hm_policy: req.body.hm_policy,
                        about: req.body.about,
                        t_price: req.body.t_price,
                        d_price: req.body.d_price,
                        placeStatus: 0,
                        country_id: req.body.country_id,
                        city_id: req.body.city_id,
                        id:req.body.id
                    }
                    const place = this.editDistinationServices.editplaces(a);
                    this.sendResponse(place,res)
                    // res.json({
                    //     status: "true",
                    //     message: `Continent added successfully`,
                    //     error: "true"
                    // })
                }
            })
        }

    public editTowns(req: Request, res: Response) {
            this.editDistinationServices.uploadImages(req, res).subscribe((result) => {
                console.log(result)
                if (_.isEmpty(result)) {
                    res.json({
                        status: "false",
                        message: `Image not uploaded`,
                        error: "false",
                    });
                } else {
                    req.body.imageUrl = result;
                    const a = {
                        towns_name: req.body.towns_name,
                        city_id: req.body.city_id,
                        overview: req.body.overview,
                        description: req.body.description,
                        followers: 0,
                        following: 0,
                        Star: 0,
                        imageUrl: result,
                        itinerary: req.body.itinerary,
                        Short_itinerary: req.body.Short_itinerary,
                        inclusion: req.body.inclusion,
                        Exclusion: req.body.Exclusion,
                        TNC: req.body.TNC,
                        Others: req.body.Others,
                        hm_policy: req.body.hm_policy,
                        about: req.body.about,
                        t_price: req.body.t_price,
                        d_price: req.body.d_price,
                        townsStatus: 0,
                        state_id: req.body.state_id,
                        id:req.body.id
                    }
                    const town = this.editDistinationServices.editTowns(a);
                    this.sendResponse(town,res)
                    // res.json({
                    //     status: "true",
                    //     message: `Continent added successfully`,
                    //     error: "true"
                    // })
                }
            })
        }

    }
