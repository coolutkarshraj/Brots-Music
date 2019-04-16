import * as express from "express";
import {Request, Response} from "express";
import {UserController} from "../controllers/UserController";
import {springField} from "../controllers/springField"
import { from } from "rxjs/observable/from";


export class AppRouter {
    private userController: UserController;
    private springField :springField;
   

    constructor(app: express.Application) {
        this.userController = new UserController();
        this.springField = new springField();
 
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
    
          /** User Destination Apin*/
          app.post("/v1/user/springField", (req: Request, res: Response) => this.springField.getAllSpringFieldData(req, res));
      
    
    }
}
