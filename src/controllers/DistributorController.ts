import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {DistributorService} from "../services/DistributorService";
import {Logger} from "../services/common/Logger";
import * as _ from "underscore";
import {Config} from "../Config";
import {StockRequestModal} from "../models/request/StockRequestModal";
import {$Query} from "../queries/$Query";
import {$Model} from "../models/$Model";
import * as moment from "moment";
import * as Rx from "rxjs/Rx";
import {Observable} from "rxjs/Observable";
import {Tables} from "../database/Tables";

export class DistributorController extends BaseController {
    private distributorService: DistributorService;
    private sqlService: SqlService;

    constructor() {
        super();
        this.distributorService = new DistributorService();
        this.sqlService = new SqlService();
    }

    public getAllProducts(req: Request, res: Response) {
        const request: StockRequestModal = {
            groupBy: req.body.groupBy,
            distributorId: req.body.distributorId
        };
        this.processStock(request, res);
    }

    public getAllOrders(req: Request, res: Response) {
        const query = $Query.getQuery2(req.params.distributorId);
        const orders = this.sqlService.executeQuery(query);
        this.sendResponseWithKey("orders", orders, res);
    }

    public sendGCMNotification(req: Request, res: Response) {
        const message = super.geGCMMessage();
        this.getUserDeviceTokens((tokens) => {

            if (tokens.length === 0) {
                res.send("no user available");
                return;
            }

            super.sendGCMNotification(message, tokens, (isNotificationSent) => {
                if (isNotificationSent) {
                    res.sendStatus(200);
                }
            });
        });
    }

    public getInbox(req: Request, res: Response) {
        const query = $Query.getQuery3(req.params.distributorId);
        this.sqlService.executeQuery(query).subscribe((notifications) => {
            _.each(notifications, (notification) => {
                notification.date = moment(notification.date).format("MMMM Do YYYY");
            });
            res.json({inbox: notifications});
        }, (error) => {
            Logger.logSqlError(error);
        }, () => null);
    }

    private processStock(request: StockRequestModal, res: Response) {
        switch (request.groupBy) {
            case Config.groupByOptions.brand:
                this.getProductsGroupByBrand(request.distributorId, res);
                break;
            case Config.groupByOptions.category:
                this.getProductsGroupByCategory(request.distributorId, res);
                break;
            case Config.groupByOptions.sale:
                this.getProductsGroupBySale(request.distributorId, res);
                break;
            default:
                break;
        }
    }

    private getProductsGroupByBrand(distributorId: number, res: Response) {
        const query = $Query.getQuery1(distributorId);
        const observable = this.sqlService.executeQuery(query)
            .flatMap((products: Array<object>) => {
                const result = [];
                const brands = $Model.model2(products);
                _.each(brands, (b) => {
                    const model = $Model.model1(b, products);
                    result.push(model);
                });
                return Observable.create((observer) => {
                    observer.next(result);
                    observer.complete();
                });
            });
        super.sendResponseWithKey('products', observable, res);
    }

    private getProductsGroupByCategory(distributorId: number, res: Response) {
    }

    private getProductsGroupBySale(distributorId: number, res: Response) {
    }

    private getUserDeviceTokens(callback) {
        const query = `select deviceToken from user where deviceToken is not null;`;
        this.sqlService.executeQuery(query).subscribe((users) => {
            if (users != null) {

                const tokens = [];
                _.each(users, (user) => {
                    tokens.push(user.deviceToken);
                });

                Logger.logInfo(`${tokens.length} users available to notify`);
                callback(tokens);
            }
        });
    }
}
