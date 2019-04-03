import * as express from "express";
import {Request, Response} from "express";
import {UserController} from "../controllers/UserController";


export class AppRouter {
    private userController: UserController;
   

    constructor(app: express.Application) {
        this.userController = new UserController();
 
        app.get("/v1/ping", (req: Request, res: Response) => this.userController.ping(req, res));

        /** user */
        app.post("/v1/user/registrationVerify", (req: Request, res: Response) => this.userController.verifyRegistration(req, res));
        app.post("/v1/user/register", (req: Request, res: Response) => this.userController.registerUser(req, res));
        app.post("/v1/user/login", (req: Request, res: Response) => this.userController.loginUser(req, res));
        app.get("/v1/user/logout/:id", (req: Request, res: Response) => this.userController.logoutUser(req, res));
        app.get("/v1/getAllUsers", (req: Request, res: Response) => this.userController.getAllUsers(req, res));
        app.post("/v1/user/updateOnlineStatus", (req: Request, res: Response) => this.userController.updateOnlineStatus(req, res));
        app.post("/v1/user/updateDeviceToken", (req: Request, res: Response) => this.userController.updateDeviceToken(req, res));
        app.post("/v1/user/sendforgetPasswordLink", (req: Request, res: Response) => this.userController.updateDeviceToken(req, res));
        app.post("/v1/user/updatePassword", (req: Request, res: Response) => this.userController.updateDeviceToken(req, res));
    }
}
