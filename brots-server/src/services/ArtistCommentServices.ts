import * as Rx from "rxjs/Rx";
import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
const { AdminCommentModel } = require("../category/sqlService");
export class ArtistCommentServices extends ServiceBase {

    constructor() {
        super();
    }

     public removeComment(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            AdminCommentModel.destroy({
                where: {
                    id:req.body.commentId
                }}) .then((result)=>{
                    const ArtistSongComment = AdminCommentModel.find({
                        where: {
                        ArtistSongId:req.body.artistSongId
                        } 
                    })
                    resolve(ArtistSongComment)
                    }).catch((err)=>{
                        reject(err)
                    })  
        });
        return Rx.Observable.fromPromise(promise);
    }

    public EditComment(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            AdminCommentModel.update(
                 { CommentBody: req.body.commentbody,
                    CommentTime:req.body.updatedTime},
                { where: { 
                    id:req.body.commentId
                }})
                .then((result)=>{
                    const ArtistSongComment = AdminCommentModel.find({
                        where: {
                            id:req.body.commentId
                        } 
                    })
                    resolve(ArtistSongComment)
                    }).catch((err)=>{
                        reject(err)
                    })
        });
        return Rx.Observable.fromPromise(promise);
    }
        
    public AddComent(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
         AdminCommentModel.create({ArtistSongId:req.body.artistSongId,
                UserId:req.body.userId,CommentBody:req.body.commentbody,
                ArtistId:req.body.artistId,CommentTime:req.body.CommentTime})
                .then((result)=>{
                const ArtistSongComment = AdminCommentModel.find({
                    where: {
                        ArtistSongId:req.body.artistSongId,
                        CommentTime:req.body.CommentTime
                    } 
                })
                resolve(ArtistSongComment)
                }).catch((err)=>{
                    reject(err)
                })
                
        });
        return Rx.Observable.fromPromise(promise);
    }

    public getAllComment(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const ArtistSongComment = AdminCommentModel.findAll({
                where: {
                    ArtistSongId:req.body.artistSongId
                } 
            })
                resolve(ArtistSongComment);
        });
        return Rx.Observable.fromPromise(promise);
    }

}
