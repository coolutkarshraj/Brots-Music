import {Request, Response} from "express";
import {BaseController} from "./common/BaseController";
import SqlService from "../services/common/SQLService";
const { AdminCommentModel } = require("../category/sqlService");
import {Tables} from "../database/Tables";
import * as _ from "lodash";
import * as __ from "underscore";
import { ArtistCommentServices } from '../services/ArtistCommentServices';

export class ArtistCommentController extends BaseController {
    private sqlService: SqlService;
    private artistCommentServices: ArtistCommentServices;
    

    constructor() {
        super();
        this.sqlService = new SqlService();
        this.artistCommentServices = new ArtistCommentServices();
    }  
   
    public getAllComment(req: Request, res: Response) {
        if(req.body.artistSongId == null){
            return res.json({
                "status":"false",
                "message":"Missing Parameter Artist SongId",
                "error":"false", 
            })

        }
        const user = this.artistCommentServices.getAllComment(req);
        this.sendResponse(user, res);
    }

    public AddComent(req: Request, res: Response) {
        if(req.body.artistSongId == null){
            return res.json({
                "status":"false",
                "message":"Missing Parameter Artist SongId",
                "error":"false", 
            })

        }else if(req.body.userId == null){
              return res.json({
                "status":"false",
                 "message":"Missing Parameter UserId",
                 "error":"false",  
            })
        }else if(req.body.commentbody == null){
            return res.json({
                "status":"false",
                "message":"Missing Parameter Comment",
                "error":"false",    
            })
        }else if(req.body.artistId == null){
            return res.json({
                "status":"false",
                "message":"Missing Parameter ArtistId",
                "error":"false",   
            })
        }else if(req.body.CommentTime == null){
            return res.json({
                "status":"false",
                "message":"Missing Parameter CommentTime",
                "error":"false",   
            })
        }
        const promise = this.artistCommentServices.AddComent(req);
        this.sendResponseWithStatus(promise, res);
    }

    public EditComment(req: Request, res: Response) {
         if(req.body.CommentTime == null){
            return res.json({
                "status":"false",
                "message":"Missing Parameter CommentTime",
                "error":"false",   
            })
        }else if(req.body.commentbody == null){
            return res.json({
                "status":"false",
                "message":"Missing Parameter Comment",
                "error":"false",    
            })
        }else if(req.body.commentId == null){
            return res.json({
                "status":"false",
                "message":"Missing Parameter commenId",
                "error":"false",    
            })
        }

        const promise = this.artistCommentServices.EditComment(req);
        this.sendResponseWithStatus(promise, res);
    }

    public removeComment(req: Request, res: Response) {
        if(req.body.commentId == null){
            return res.json({
                "status":"false",
                "message":"Missing Parameter commenId",
                "error":"false",    
            })
        }
        if(req.body.artistSongId == null){
            return res.json({
                "status":"false",
                "message":"Missing Parameter Artist SongId",
                "error":"false", 
            })

        }
        const promise = this.artistCommentServices.removeComment(req);
        this.sendResponseWithStatus(promise, res);
    }
   

}
