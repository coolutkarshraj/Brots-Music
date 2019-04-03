import * as express from "express";
import {Request, Response} from "express";
import {UserController} from "../controllers/UserController";
import {ProductController} from "../controllers/ProductController";
import {DistributorController} from "../controllers/DistributorController";

export class AppRouter {
    private userController: UserController;
    private productController: ProductController;
    private distributorController: DistributorController;

    constructor(app: express.Application) {
        this.userController = new UserController();
        this.productController = new ProductController();
        this.distributorController = new DistributorController();

        app.get("/v1/ping", (req: Request, res: Response) => this.userController.ping(req, res));

        /** user */
        app.post("/v1/user/register", (req: Request, res: Response) => this.userController.registerUser(req, res));
        app.post("/v1/user/login", (req: Request, res: Response) => this.userController.loginUser(req, res));
        app.get("/v1/user/logout/:id", (req: Request, res: Response) => this.userController.logoutUser(req, res));
        app.get("/v1/getAllUsers", (req: Request, res: Response) => this.userController.getAllUsers(req, res));
        app.post("/v1/user/updateOnlineStatus", (req: Request, res: Response) => this.userController.updateOnlineStatus(req, res));
        app.post("/v1/user/updateDeviceToken", (req: Request, res: Response) => this.userController.updateDeviceToken(req, res));

        /** distributor */
        app.post("/v1/distributor/products/getAll", (req: Request, res: Response) => this.distributorController.getAllProducts(req, res));
        app.get("/v1/distributor/orders/getAll/:distributorId", (req: Request, res: Response) => this.distributorController.getAllOrders(req, res));
        app.post("/v1/sendGCMNotification", (req: Request, res: Response) => this.distributorController.sendGCMNotification(req, res));
        app.get("/v1/distributor/inbox/get/:distributorId", (req: Request, res: Response) => this.distributorController.getInbox(req, res));
    }
}
