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
import { TownsController } from "../controllers/TownsController";
import {UserProfileController} from "../controllers/UserProfileController"
import {EditProfileController} from "../controllers/EditProfileController"
import {DeleteUserProfileController} from "../controllers/DeleteUserProfileController"
import {UserGalleryController} from "../controllers/UserGalleryController"

export class AppRouter {
    private userController: UserController;
    private springField :springField;
    private continentController :continentController;
    private CountryController :CountryController;
    private StateController :StateController;
    private cityController :CityController;
    private placesController :PlacesController;
    private bestOffer :BestOffer;
    private townController :TownsController;
    private userProfileController :UserProfileController;
    private editProfileController :EditProfileController;
    private deleteUserProfileController :DeleteUserProfileController;
    private userGalleryController : UserGalleryController;
   

    constructor(app: express.Application) {
        this.userController = new UserController();
        this.springField = new springField();
        this.continentController =  new continentController
        this.CountryController =  new CountryController
        this.StateController = new StateController
        this.cityController = new CityController
        this.placesController = new PlacesController()
        this.bestOffer =  new BestOffer();
        this.townController =  new TownsController();
        this.userProfileController = new UserProfileController();
        this.editProfileController =  new EditProfileController();
        this.deleteUserProfileController =  new DeleteUserProfileController()
        this.userGalleryController = new UserGalleryController();
 
        app.get("/v1/ping", (req: Request, res: Response) => this.userController.ping(req, res));

        /** user  Basic Details Api*/
        app.post("/v1/user/registrationVerify", (req: Request, res: Response) => this.userController.verifyRegistration(req, res));
        app.post("/v1/user/register", (req: Request, res: Response) => this.userController.registerUser(req, res));
        app.post("/v1/user/uploadProfilePic", (req: Request, res: Response) => this.userController.uploadProfilePic(req, res));
        app.post("/v1/user/login", (req: Request, res: Response) => this.userController.loginUser(req, res));
        app.get("/v1/user/logout/:id", (req: Request, res: Response) => this.userController.logoutUser(req, res));
        app.get("/v1/getAllUsers", (req: Request, res: Response) => this.userProfileController.getAllUsers(req, res));
        app.post("/v1/user/updateDeviceToken", (req: Request, res: Response) => this.userController.updateDeviceToken(req, res));
        app.post("/v1/user/sendforgetPasswordLink", (req: Request, res: Response) => this.userController.sendForgetPasswordLink(req, res));
        app.post("/v1/user/updatePassword", (req: Request, res: Response) => this.userController.updatePassword(req, res));
        app.post("/v1/user/checkForUserNameExistOrNot", (req: Request, res: Response) => this.userController.checkUserNameExistOrNot(req, res)); 
       
    
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
        app.post("/v1/user/getAlltownsOnbasiOfTrandingPopularDispopular", (req: Request, res: Response) => this.townController.getAllTownsData(req, res));
        app.post("/v1/user/getHomeData", (req: Request, res: Response) => this.placesController.getHomeData(req, res));
        app.post("/v1/user/getPlaceInformation", (req: Request, res: Response) => this.placesController.getPlaceInFormation(req, res));
      
       /** user  Profile Details Api*/
       app.post("/v1/user/getSingleUserData", (req: Request, res: Response) => this.userProfileController.getSingleUserData(req, res)); 
       app.post("/v1/user/getPlaceUserData",(req:Request,res:Response) => this.userProfileController.getUserPlaceData(req,res));
       app.post("/v1/user/addPlaceUserData",(req:Request,res:Response) => this.userProfileController.addUserPlaceData(req,res));
       app.post("/v1/user/getUserEducationData",(req:Request,res:Response) => this.userProfileController.getUsereducationData(req,res));
       app.post("/v1/user/addUserEducationData",(req:Request,res:Response) => this.userProfileController.addUsereducationData(req,res));
       app.post("/v1/user/getUserpublicTagData",(req:Request,res:Response) => this.userProfileController.getUserpublicTagData(req,res));
       app.post("/v1/user/addUserpublicTagData",(req:Request,res:Response) => this.userProfileController.addUserpublicTagData(req,res));


       /** user  Profile Edit Api*/
       app.post("/v1/user/editBasicDetails", (req: Request, res: Response) => this.editProfileController.editBasicDetails(req, res)); 
       app.post("/v1/user/updateProfilePic", (req: Request, res: Response) => this.editProfileController.updateProfilePic(req, res)); 
       app.post("/v1/user/editPublicTag",(req:Request,res:Response) => this.editProfileController.editUserPublicData(req,res));
       app.post("/v1/user/editPlaceData",(req:Request,res:Response) => this.editProfileController.editUserPlaceData(req,res));
       app.post("/v1/user/editUserEducation",(req:Request,res:Response) => this.editProfileController.editUsereducationData(req,res));
       app.post("/v1/user/deletePlaceData",(req:Request,res:Response) => this.deleteUserProfileController.deleteUserPlaceData(req,res));
       app.post("/v1/user/deleteUserEducation",(req:Request,res:Response) => this.deleteUserProfileController.deleteUserPublicData(req,res));   
       app.post("/v1/user/updateAdharCard",(req:Request,res:Response) => this.editProfileController.updateAdharCard(req,res));   
       app.post("/v1/user/shareCode",(req:Request,res:Response) => this.editProfileController.shareCode(req,res));
       app.post("/v1/user/createGallery",(req:Request,res:Response)=>this.userGalleryController.addGalleryFolder(req,res))   
       app.post("/v1/user/getGalleryFolder",(req:Request,res:Response)=>this.userGalleryController.getGalleryFolder(req,res))   
       app.post("/v1/user/uploadImage",(req:Request,res:Response)=>this.userGalleryController.uploadGalleryImages(req,res))   
       app.post("/v1/user/getGalleryImages",(req:Request,res:Response)=>this.userGalleryController.getGalleryImages(req,res))   
       app.post("/v1/user/bookmarkImage",(req:Request,res:Response)=>this.userGalleryController.getBookMarkedImages(req,res))   
       app.post("/v1/user/createBookmarkImage",(req:Request,res:Response)=>this.userGalleryController.createBookMarkedImages(req,res))   
       app.post("/v1/user/deleteBookmarkImage",(req:Request,res:Response)=>this.userGalleryController.deleteBookMarkedImages(req,res))   
       app.post("/v1/user/getAllImagesAcToAlbum",(req:Request,res:Response)=>this.userGalleryController.getImageAccordingToAlbumFolder(req,res))   
       app.post("/v1/user/deleteUserProfileImages",(req:Request,res:Response)=>this.userGalleryController.deleteUserProfileImages(req,res))   
    
       /** Admin Destination Api Not Tested*/
       app.post("/v1/user/addspringField", (req: Request, res: Response) => this.springField.addSpringFieldData(req, res));
      
    
    }
}
