import { type } from 'os';
import { Request, Response } from 'express';
import { BaseController } from '../controllers/common/BaseController';
import { AdminSongServices } from '../services/AdmiSongServices';
import {ArtistServices} from '../services/ArtistServices'
import * as _ from 'lodash';
import SqlService from '../services/common/SQLService';
import QueryBuilderService from '../services/common/QueryBuilderService';
import { from } from 'rxjs/observable/from';
const MP3Cutter = require('mp3-cutter');


export class AdminSongController extends BaseController {
    protected queryBuilderService: QueryBuilderService;
    protected adminsongServices: AdminSongServices;
    private artistServices: ArtistServices;
    constructor() {
        super();
        this.adminsongServices = new AdminSongServices();
        this.artistServices = new ArtistServices();
        this.queryBuilderService = new QueryBuilderService();
    }
    public getAllCategoriessubCategories(req: any, res: Response) {
        const catsubcat = this.adminsongServices.getcatSubCat(req);
        this.sendResponse(catsubcat, res);
    }
    public getAllCategorie(req: any, res: Response) {
        const catsubcat = this.adminsongServices.getcat(req);
        this.sendResponse(catsubcat, res);
    }

    
    public deleteSong(req: any, res: Response) {
        const catsubcat = this.adminsongServices.deleteSong(req);
        this.sendResponse(catsubcat, res);
    }
    

    public getAdminSong(req: Request, res: Response) {
        if (req.body.constructor === Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing All Parameter",
                "error": "false",
            });
        }
        if (req.body.categoryId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter categoryId",
                "error": "false",
            })
        }
        if (req.body.subcategoryId == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter subcategoryId",
                "error": "false",
            })

        } if (req.body.userId == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter userId",
                "error": "false",
            })

        }
        const adminSong = this.adminsongServices.getAllSong(req);
        this.sendResponse(adminSong, res);
    }

    public getSingleArtistSong(req: Request, res: Response) {
        if (req.body.constructor === Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing All Parameter",
                "error": "false",
            });
        }

        else if (req.body.artistId == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter artistId",
                "error": "false",
            })

        }
        const adminSong = this.adminsongServices.getAllSingleArtistSong(req);
        this.sendResponse(adminSong, res);
    }

     public uploadAdminSongBasicDetails(req: any, res: Response) {
        const uploadsongurl = this.adminsongServices.uploadAdminSongBasicDetails(req, res);
        console.log(uploadsongurl)
        this.sendResponse(uploadsongurl, res)
    }
    public uploadsukhmalImage(req: any, res: Response) {
        const uploadsongurl = this.adminsongServices.uploadsukhmahalImages(req, res);
        console.log(uploadsongurl)
        this.sendResponse(uploadsongurl, res)
    }
    
    public getSukhmehalImages(req: Request, res: Response) {
        if (req.params.catId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter userId",
                "error": "false",
            })
        }
        const get1stCatId = this.adminsongServices.getSukhmehalImages(req);
        this.sendResponse(get1stCatId, res)
    }

    public deleteSukhmehalImages(req: Request, res: Response) {
        if (req.params.imageId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter userId",
                "error": "false",
            })
        }
        const get1stCatId = this.adminsongServices.deleteSukhmehalImages(req);
        this.sendResponse(get1stCatId, res)
    }

    public uploadAdminFullSong(req: any, res: Response) {
        const uploadsongurl = this.adminsongServices.uploadAdminSong(req, res);
        console.log(uploadsongurl)
        this.sendResponse(uploadsongurl, res)
    }
	public cutAdminSong30sec(req:any, res:any) {
        
        let file = req.files[0] ;
        let data = req.body ;
        let songName = file.originalname ;
        let folder =  file.destination ;
        let source = folder + songName ;
        let dest = folder+songName.split('.mp3',1)[0] +"_30sec.mp3";
        // console.log("music is:", data);
        let start = data.start ;
        let end =  data.end ;
        MP3Cutter.cut({
            src: source,
            target: dest,
            start: start,
            end: end  
        })

        res.download(dest);
	} 
    public uploadAdminSong30sec(req: any, res: Response) {
     this.adminsongServices.uploadAdmin30secSong(req, res).subscribe((uploadsongurl)=>{
        if ((_.isEmpty(uploadsongurl))) {
            res.send({
                "status": "true",
                 "message": "Song Uploaded failed",
                 "error": "true", 
         })
        }else{
            res.send({
                "status": "true",
                 "message": "Song Uploaded Successfully",
                 "error": "true", 
         })
        }
     });
       
    }

    
    public editSong(req: any, res: Response) {
        const uploadsongurl = this.adminsongServices.editSong(req, res);
        console.log(uploadsongurl)
        this.sendResponse(uploadsongurl, res)
    }

    public deleteHalfUploadedSong(req: any, res: Response) {
        if(req.body.songId == null){
            return res.json({
                "status": "false",
                "message": "Missing  Parameter SongId",
                "error": "false",
            })
        }
       this.adminsongServices.deleteHalfUploadedSong(req).subscribe((result)=>{
        if ((_.isEmpty(result))) {
            return res.json({
                "status": "false",
                "message": "Something Went Wrong",
                "error": "false",
            }) 
        }else{
            return res.json({
                "status": "true",
                "message": "Song Deleted Successfully",
                "error": "true",
            }) 
        } 
       })

    }


    public getFirstSubcatId(req: Request, res: Response) {
        if (req.body.catId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter userId",
                "error": "false",
            })
        }
        const get1stCatId = this.adminsongServices.get1stCatId(req);
        this.sendResponsewith1stcat(get1stCatId, res)
    }
    public likeAdminSong(req: any, res: Response) {
        var insta = +req.body.InstaMixMemory;
        var single = +req.body.singleSaveMemory;
        var memory = insta + single;
        if (req.body.userId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter userId",
                "error": "false",
            })
        }
        else if (req.body.songId == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter songId",
                "error": "false",
            })

        } else if (req.body.like == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter like",
                "error": "false",
            })

        }
        else if (req.body.disLikes == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter dislike",
                "error": "false",
            })

        }
        else if (req.body.likes == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter likes",
                "error": "false",
            })

        }
        else if (req.body.disLikes == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter dislikes",
                "error": "false",
            })

        }
        else if (memory > 479) {
            var i = 0;
            this.adminsongServices.removeFirstSavedSong(req);
        }
        this.adminsongServices.checkInstanceSongExistOrNot(req).subscribe((instanceSong) => {
            if ((_.isEmpty(instanceSong))) {
                this.adminsongServices.checkLikeExist(req).subscribe((like) => {
                    if ((_.isEmpty(like))) {
                        this.adminsongServices.likeSong(req).subscribe((likeSong => {
                            const memory = this.adminsongServices.getUserInstanceMemory(req)
                            this.sendResponsewithLike(likeSong, res, memory)
                        }))

                    } else {
                        if (like.dataValues["Like"] == 1) {
                            return res.json({
                                "status": "false",
                                "message": "Already have Liked Song",
                                "error": "false",
                            })
                        } else {

                            // this.adminsongServices.updatelikeSong(req).subscribe((likeSong) => {
                            //     const memory = this.adminsongServices.getUserInstanceMemory(req)
                            //     this.sendResponsewithLike(likeSong, res, memory)
                            // })

                            return res.json({
                                "status": "false",
                                "message": "Already have Dis-Liked Song",
                                "error": "false",
                            })

                        }


                    }
                })
            } else {
                return res.json({
                    "status": "false",
                    "message": "Already have Liked Song",
                    "error": "false",
                })
            }

        })
    }
    public unLikeAdminSong(req: any, res: Response) {
        if (req.body.userId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter userId",
                "error": "false",
            })
        }
        else if (req.body.songId == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter songId",
                "error": "false",
            })

        } else if (req.body.like == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter like",
                "error": "false",
            })

        }
        else if (req.body.disLike == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter dislike",
                "error": "false",
            })

        }
        this.adminsongServices.checkInstanceSongExistOrNot(req).subscribe((song) => {
            if ((_.isEmpty(song))) {
                this.adminsongServices.checkLikeExist(req).subscribe((checklike) => {
                    if ((_.isEmpty(checklike))) {
                        this.adminsongServices.dislikeSong(req).subscribe((likeSong) => {
                            if ((_.isEmpty(likeSong))) {
                                return res.json({
                                    "status": "false",
                                    "message": "Something Went Wrong",
                                    "error": "false",
                                })
                            } else {
                                const memory = this.adminsongServices.getUserInstanceMemory(req)
                                this.sendResponsewithLike(likeSong, res, memory)
                            }


                        });

                    } else {
                     if (checklike.dataValues["Like"] == 0) {
                            return res.json({
                                "status": "false",
                                "message": "Already have Dislike Song",
                                "error": "false",
                            })
                                
                            } else {
                                // this.adminsongServices.updatedislikeSong(req).subscribe((likeSong)=>{
                                //     const memory = this.adminsongServices.getUserInstanceMemory(req)
                                //    this.sendResponsewithLike(likeSong, res, memory)
                                //    })

                                return res.json({
                                    "status": "false",
                                    "message": "Already have like Song",
                                    "error": "false",
                                })
                                
                               
                            }

 

                    }
                })
            } else {
                this.adminsongServices.checkLikeExist(req).subscribe((checklike) => {
                    if (checklike.dataValues["Like"] == 0) {
                        return res.json({
                            "status": "false",
                            "message": "Already have Dislike Song",
                            "error": "false",
                        })  

                    } else {
                        //  this.adminsongServices.updatedislikeSong(req).subscribe((likeSong)=>{
                        //     const memory = this.adminsongServices.getUserInstanceMemory(req)
                        //     this.sendResponsewithLike(likeSong, res, memory)   
                        // });

                        return res.json({
                            "status": "false",
                            "message": "Already have like Song",
                            "error": "false",
                        })  
                       
                    }

                })

            }
        })

    }

    public saveAdminSong(req: any, res: Response) {
        var memory = +req.body.savedSongMemory
        console.log(memory)
        console.log(memory)
        if (req.body.song_name == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter Song Name",
                "error": "false",
            })
        }
        else if (req.body.songduration == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter Song Duration",
                "error": "false",
            })

        } else if (req.body.song_img_url == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter Song Image Url",
                "error": "false",
            })

        }
        else if (req.body.songId == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter Song Id",
                "error": "false",
            })

        }
        else if (req.body.userId == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter UserId",
                "error": "false",
            })

        }
        else if (memory > 500) {
            return res.json({
                "status": "false",
                "message": "You can't Save Song because Your Free Saved Memory is full Please Upgrade Plan Or Remove existing Song Before Save",
                "error": "false",
            })
        }

        else if (req.body.bannerImage == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter Banar Banner Images",
                "error": "false",
            })
        }
        else if (req.body.artist_name == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter Banar Artist Name",
                "error": "false",
            })
        }
        else if (req.body.album_name == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter Album Name",
                "error": "false",
            })
        }
        else if (req.body.artist_id == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter artist_id",
                "error": "false",
            })
        }
        else if (req.body.full_song_url == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter full_song_url",
                "error": "false",
            })
        }
        this.adminsongServices.isSongExist(req.body.songId, req.body.userId).subscribe((song) => {
            if ((_.isEmpty(song))) {
                console.log('empty')
                const singleSongMemory = +req.body.singleSaveMemory;
                const total = +req.body.savedSongMemory;
                this.adminsongServices.SaveSongAdmin(req);
                res.json({
                    "status": "true",
                    "message": "Song Saved Successfully",
                    "error": "true",
                    "savedMemory": singleSongMemory + total
                })
            } else {
                return res.json({
                    "status": "false",
                    "message": "You already have Saved Song",
                    "error": "false",
                    "savedMemory": "false"
                });
            }

        });

    }

    public removeAdminSong(req: any, res: Response) {
        if (req.body.userId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter userId",
                "error": "false",
            })
        }
        else if (req.body.songId == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter songId",
                "error": "false",
            })

        }
        else if (req.body.savedSongMemory == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter savedSongMemory",
                "error": "false",
            })

        }
        this.adminsongServices.removeSong(req).subscribe((result) => {
            if (result == 0) {
                return res.json({
                    "status": "false",
                    "message": "Something Went Wrong",
                    "error": "false",
                    "savedMemory": 'false'
                })
            } else {
                const singleSongMemory = +req.body.singleSaveMemory;
                const total = +req.body.savedSongMemory;
                const savedmemor = total - singleSongMemory
                return res.json({
                    "status": "true",
                    "message": "Song Removed Successfully",
                    "error": "true",
                    "savedMemory": savedmemor
                })
            }

        });
    }

    public getAdminSaveSong(req: any, res: Response) {

        if (req.body.userId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter userId",
                "error": "false",
            })
        }
        const likeSong = this.adminsongServices.getSongList(req.body.songId, req.body.userId);
        this.sendResponse(likeSong, res)
    }
    public getAdminSaveSongAlphabetically(req: any, res: Response) {

        if (req.body.userId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter userId",
                "error": "false",
            })
        }
        const likeSong = this.adminsongServices.getSongListAlphabatically(req.body.songId, req.body.userId);
        this.sendResponse(likeSong, res)
    }


    public removeAdminSelfSong(req: any, res: Response) {

        if (req.body.songId == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter songId",
                "error": "false",
            })

        }

        this.adminsongServices.removeAdminSelfSong(req).subscribe((result) => {
            if (result == 0) {
                return res.json({
                    "status": "false",
                    "message": "Something Went Wrong",
                    "error": "false",
                })
            } else {
                return res.json({
                    "status": "true",
                    "message": "Song Removed Successfully",
                    "error": "true",
                })
            }

        });
    }

    public removeUserSongFromLibrary(req: any, res: Response) {

        if (req.body.savesongId == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter savesongId",
                "error": "false",
            })

        }
        if (req.body.userId == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter userId",
                "error": "false",
            })

        }
        if (req.body.songId == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter songId",
                "error": "false",
            })

        }

        this.adminsongServices.removeAdminLibraryDeletedSong(req).subscribe((result) => {
            if (result == 0) {
                return res.json({
                    "status": "false",
                    "message": "Something Went Wrong",
                    "error": "false",
                    "savedMemory": 'false'
                })
            } else {
                const singleSongMemory = +req.body.singleSaveMemory;
                const total = +req.body.savedSongMemory;
                const savedmemor = total - singleSongMemory
                return res.json({
                    "status": "true",
                    "message": "Song Removed Successfully",
                    "error": "true",
                    "savedMemory": savedmemor
                })
            }

        });
    }

    public getInstanceSong(req: any, res: Response) {

        if (req.body.userId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter userId",
                "error": "false",
            })
        }
        const instanceSong = this.adminsongServices.getInstanceSong(req.body.userId);
        this.sendResponse(instanceSong, res)
    }
    public getProfileorTop3Song(req: any, res: Response) {

        if (req.body.artistId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter userId",
                "error": "false",
            })
        }
        const userType = "2"
     this.artistServices.getUserUsingid(req.body.artistId,userType).subscribe((artist)=>{

        const Top3Song = this.adminsongServices.getTop3Song(req.body.artistId);
        this.sendResponseWithArtistProfileorTop3Song(Top3Song,res,artist)

        });
 
    }


    
    
    public increaseSongCount(req: any, res: Response) {

        if (req.body.SongId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter SongId",
                "error": "false",
            })
        }
        const instanceSong = this.adminsongServices.increaseSongCount(req);
        this.sendResponse(instanceSong, res)
    }
    public getArtistMostHearedSong(req: any, res: Response) {

        if (req.body.artistId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter SongId",
                "error": "false",
            })
        }
        const instanceSong = this.adminsongServices.getArtistMostHearedSong(req);
        this.sendResponse(instanceSong, res)
    }
     
    public isSongHeared(req: any, res: Response) {

        if (req.body.songId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter SongId",
                "error": "false",
            })
        }
       else if (req.body.userId == null) {
            return res.json({
                "status": "false",
                "message": "Missing  Parameter SongId",
                "error": "false",
            })
        }
        const instanceSong = this.adminsongServices.updateSongHeared(req);
        this.sendResponse(instanceSong, res)
    }

}
