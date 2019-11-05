import SQLService from "./SQLService";
import QueryBuilderService from "./QueryBuilderService";

export class ServiceBase {
    protected sqlService: SQLService;
    protected queryBuilderService: QueryBuilderService;

    constructor() {
        this.sqlService = new SQLService();
        this.queryBuilderService = new QueryBuilderService();
    }
}