import {Request, Response} from 'express';
import {BaseController} from '../controllers/common/BaseController';
import * as _ from 'lodash';
import SqlService from '../services/common/SQLService';
import QueryBuilderService from '../services/common/QueryBuilderService';
import * as path from 'path';
import * as fs from 'fs';
import {Logger} from '../services/common/Logger';
import {Tables} from "../database/Tables";

export class imageuploadcontrollers extends BaseController {
    protected queryBuilderService: QueryBuilderService;
    private sqlService: SqlService;
    constructor() {
        super();
        this.sqlService = new SqlService();
        this.queryBuilderService = new QueryBuilderService();
    }
    public uploadPhoto(req: any, res: Response) {
        if (req.userPhotoFileName === undefined) {
            req.userPhotoFileName = '';
        }
        const query = `update ${Tables.user} set photo = '${req.userPhotoFileName}' where id = ${req.query.id};`;
        this.sqlService.executeQuery1(query)
            .then((result) => {
                console.log('==== success ====');
                console.log(result);
                res.sendStatus(200);
            }, (err) => {
                Logger.logError('Error uploading document', err);
                res.sendStatus(500);
            });
    }
}