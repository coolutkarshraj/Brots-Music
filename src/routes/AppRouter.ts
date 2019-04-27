import * as express from "express";
import {Request, Response} from "express";
import {UserController} from "../controllers/UserController";
import {springField} from "../controllers/springField"
import { continentController } from "../controllers/continentController";
import { CountryController } from "../controllers/CountryController";
import { StateController } from "../controllers/StateController";
import { CityController } from "../controllers/CityController";
import { PlacesController } from "../controllers/PlacesController";
import { BestOffer } from "../controllers/BestOffer";


export class AppRouter {
    private userController: UserController;
    private springField :springField;
    private continentController :continentController;
    private CountryController :CountryController;
    private StateController :StateController;
    private cityController :CityController;
    private placesController :PlacesController;
    private bestOffer :BestOffer;
   

    constructor(app: express.Application) {
        this.userController = new UserController();
        this.springField = new springField();
        this.continentController =  new continentController
        this.CountryController =  new CountryController
        this.StateController = new StateController
        this.cityController = new CityController
        this.placesController = new PlacesController()
        this.bestOffer =  new BestOffer();
 
        app.get("/v1/ping", (req: Request, res: Response) => this.userController.ping(req, res));

        /** user  Basic Details Api*/
        app.post("/v1/user/registrationVerify", (req: Request, res: Response) => this.userController.verifyRegistration(req, res));
        app.post("/v1/user/register", (req: Request, res: Response) => this.userController.registerUser(req, res));
        app.post("/v1/user/login", (req: Request, res: Response) => this.userController.loginUser(req, res));
        app.get("/v1/user/logout/:id", (req: Request, res: Response) => this.userController.logoutUser(req, res));
        app.get("/v1/getAllUsers", (req: Request, res: Response) => this.userController.getAllUsers(req, res));
        app.post("/v1/user/updateDeviceToken", (req: Request, res: Response) => this.userController.updateDeviceToken(req, res));
        app.post("/v1/user/sendforgetPasswordLink", (req: Request, res: Response) => this.userController.sendForgetPasswordLink(req, res));
        app.post("/v1/user/updatePassword", (req: Request, res: Response) => this.userController.updatePassword(req, res));
        app.post("/v1/user/checkForUserNameExistOrNot", (req: Request, res: Response) => this.userController.checkUserNameExistOrNot(req, res)); 
        app.post("/v1/user/getSingleUserData", (req: Request, res: Response) => this.userController.getSingleUserData(req, res)); 
    
          /** User Destination continent Api*/
          app.post("/v1/user/springField", (req: Request, res: Response) => this.springField.getAllSpringFieldData(req, res));
          app.post("/v1/user/getAllContinent", (req: Request, res: Response) => this.continentController.getAllContinentData(req, res));
          app.post("/v1/user/getAllCountryOnthebasisofContinent", (req: Request, res: Response) => this.CountryController.getAllCountryDataonTheBasisofContinent(req, res));
          app.post("/v1/user/getAllStateOnThebasisOfContry", (req: Request, res: Response) => this.StateController.getAllState(req, res));
          app.post("/v1/user/getAllCities", (req: Request, res: Response) => this.cityController.getAllCities(req, res));
          app.post("/v1/user/getAllPlaces", (req: Request, res: Response) => this.placesController.getAllplaces(req, res));
          app.post("/v1/user/city/bestOffer", (req: Request, res: Response) => this.bestOffer.getAllBestOffer(req, res));

        /** User Destination Country Api*/
        app.post("/v1/user/getAllCountryOnbasiOfTrandingPopularDispopular", (req: Request, res: Response) => this.CountryController.getAllCountryOnbasiOfTrandingPopularDispopular(req, res));
        app.post("/v1/user/getAllstatesOnbasiOfTrandingPopularDispopular", (req: Request, res: Response) => this.StateController.getAllstatesOnbasiOfTrandingPopularDispopularv(req, res));
        app.post("/v1/user/getAllcitiesOnbasiOfTrandingPopularDispopular", (req: Request, res: Response) => this.cityController.getAllcityOnbasiOfTrandingPopularDispopular(req, res));
        app.post("/v1/user/getAllplacesOnbasiOfTrandingPopularDispopular", (req: Request, res: Response) => this.placesController.getAllplacesOnbasiOfTrandingPopularDispopular(req, res));
        app.post("/v1/user/getHomeData", (req: Request, res: Response) => this.placesController.getHomeData(req, res));


         /** Admnin Destination Api Not Tested*/
          app.post("/v1/user/addspringField", (req: Request, res: Response) => this.springField.addSpringFieldData(req, res));
      
    
    }
}
