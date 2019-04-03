import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {ProductService} from "../services/ProductService";

export class ProductController extends BaseController {
    private productService: ProductService;
    private sqlService: SqlService;

    constructor() {
        super();
        this.productService = new ProductService();
        this.sqlService = new SqlService();
    }
}
