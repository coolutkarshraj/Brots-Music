import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";
import {RevivewServices} from "../services/RevivewServices"

export class RevivewController extends BaseController {
    private revivewServices: RevivewServices;
    private sqlService: SqlService;

    constructor() {
        super();
        this.revivewServices = new RevivewServices();
        this.sqlService = new SqlService();
    }

    public addRevivewApi(req: Request, res: Response) {
        this.revivewServices.uploadMedia(req, res).subscribe((result)=>{
            console.log(result)
            if (_.isEmpty(result)) {
                    res.json({
                       status: "false",
                       message: `Media not uploaded`,
                       error:"false"        
                    })
                      
           }else{
            req.body.imageUrl = result;
              const a = {
                dest_id :req.body.dest_id,
                rating :req.body.rating,
                review :req.body.review,
                imageUrl :result,
                others :req.body.others,
                user_id :req.body.user_id
               }
              this.revivewServices.addRevivewToDatabase(a);
               res.json({
                   status: "true",
                   message: `Revivew added successfully`,
                   error:"true"        
                })
           }
       })
    }

    public addRevivewApiwithoutImages(req: Request, res: Response) {
           this.revivewServices.addRevivewToDatabase(req.body);
               res.json({
                   status: "true",
                   message: `Revivew added successfully`,
                   error:"true"        
                })
           
   
    }

    public editRevivewApiwithoutImages(req: Request, res: Response) {
        this.revivewServices.editRevivewToDatabase(req.body);
            res.json({
                status: "true",
                message: `Revivew edited successfully`,
                error:"true"        
             })
        

 }


    public editRevivewApi(req: Request, res: Response) {
        this.revivewServices.uploadMedia(req, res).subscribe((result)=>{
            console.log(result)
            if (_.isEmpty(result)) {
                    res.json({
                       status: "false",
                       message: `Media not uploaded`,
                       error:"false"        
                    })
                      
           }else{
            req.body.imageUrl = result;
              const a = {
                dest_id :req.body.dest_id,
                rating :req.body.rating,
                review :req.body.review,
                imageUrl :result,
                others :req.body.others,
                user_id :req.body.user_id
               

               }
              this.revivewServices.editRevivewToDatabase(a);
               res.json({
                   status: "true",
                   message: `Revivew edited successfully`,
                   error:"true"        
                })
           }
       })
    }

    public getAllRevivew(req: Request, res: Response) {
        const revivew = this.sqlService.executeQuery(`select * from ${Tables.revivew} where id = ${req.body.id} ORDER BY review Asc;`);
        this.sendResponse(revivew, res);     
       }

    public deleteRevivew(req: Request, res: Response) {
        const revivew = this.sqlService.executeQuery(`DELETE  from ${Tables.revivew} where id = ${req.body.id};`);
       this.sendResponse(revivew, res);     
   }  
}