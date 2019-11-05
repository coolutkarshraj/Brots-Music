import * as Rx from "rxjs/Rx";
import { ServiceBase } from "./common/ServiceBase";
import * as _ from "lodash";
const { Category, SubCategory, Song, SongThrough, CategoryThrough, InstaMixSong } = require("../category/sqlService")
const { user, Artist, AdminSave, AdminSaveThrough ,isSongHeared,sukhmehalMode} = require("../category/sqlService")

const upload = require('../s3Services/SongUpload');
const uploadImage = require('../s3Services/FileUpload')

const uploadImag = uploadImage.single('image');
const uploadFullSongs = upload.single('image');

const Sequelize = require('sequelize');
export class AdminSongServices extends ServiceBase {
    checkSongMemory: number = 0;
    total: number = 0;
    memory: number = 0;

    constructor() {
        super();

    }
    public getcatSubCat(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const catsubcat = Category.findAll({
                attributes: ['id', 'Name', 'createdAt', 'Type'],
                include: {
                    model: SubCategory,
                    through: { attributes: ['id', 'Name', 'createdAt', 'SubType'] }
                }
            })
            resolve(catsubcat)

        });
        return Rx.Observable.fromPromise(promise);
    }

    public getcat(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const catsubcat = Category.findAll({
                attributes: ['id', 'Name', 'createdAt', 'Type'],
            })
            resolve(catsubcat)

        });
        return Rx.Observable.fromPromise(promise);
    }
    public deleteSong(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const catsubcat = Song.destroy({
               where:{
                   id:req.body.song_id,
                   ArtistId:req.body.artist_id

               }
            })
            resolve(catsubcat)

        });
        return Rx.Observable.fromPromise(promise);
    }
    
    
    public editSong(req, res): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            uploadImag(req, res, function (err) {
                if (err) {
                    console.log('error');
                    return res.status(422).send({ errors: [{ title: 'File Upload Error', detail: err.message }] });
                }
                let data = req.body;
                let categoryId = data['categoryId']
                let subCategortyId = data["subcategoryId"]
                let songName = data["song_name"]
                let ArtistName = data["artist_name"]
                let albumName = data["album_name"]
                
                let artistId = data['artistId']
                if(req['file']!=undefined){
                    let coverImage = req['file'].location
                    let song = Song.update({
                        Name: songName, categoryId: categoryId,
                        subcategoryId: subCategortyId,
                        SongName: songName, ArtistName: ArtistName,
                        AlbumName: albumName,
                        CoverImage: coverImage,
                        ArtistId:artistId
        
                    },
                    {
                        where: { id: req.body.songId }
                    })
                    resolve(song)
                }else{
                    let song = Song.update({
                        Name: songName, categoryId: categoryId,
                        subcategoryId: subCategortyId,
                        SongName: songName, ArtistName: ArtistName,
                        AlbumName: albumName,
                        ArtistId:artistId
        
                    },
                    {
                        where: { id: req.body.songId }
                    })
                    resolve(song)
                }
                

                

            })


        });
        return Rx.Observable.fromPromise(promise);
    }
    public uploadAdminSongBasicDetails(req, res): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            uploadImag(req, res, function (err) {
                if (err) {
                    console.log('error');
                    return res.status(422).send({ errors: [{ title: 'File Upload Error', detail: err.message }] });
                }
                let data = req.body;
                let categoryId = data['categoryId']
                let subCategortyId = data["subcategoryId"]
                let songName = data["song_name"]
                let ArtistName = data["artist_name"]
                let albumName = data["album_name"]
                let coverImage = req['file'].location
                let artistId = data['artistId']
                let song = Song.create({
                    Name: songName, categoryId: categoryId,
                    subcategoryId: subCategortyId,
                    SongName: songName, ArtistName: ArtistName,
                    AlbumName: albumName, Like: '0', DisLike: '0',
                    CoverImage: coverImage, IsTrending: 0, IsDispopular: 0, TrendingDate: '',
                    Memory: 0,ArtistId:artistId, IsSongFullyUploaded :"0"
                })
                resolve(song)

            })


        });
        return Rx.Observable.fromPromise(promise);
    }

    public uploadsukhmahalImages(req, res): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            uploadImag(req, res, function (err) {
                if (err) {
                    console.log('error');
                    return res.status(422).send({ errors: [{ title: 'File Upload Error', detail: err.message }] });
                }
                let data = req.body;
                let categoryId = data['categoryId']
                let coverImage = req['file'].location
                let sukhmehalMod = sukhmehalMode.create({
                    categoryId: categoryId, imageUrl: coverImage,
                    
                })
                resolve(sukhmehalMod)

            })


        });
        return Rx.Observable.fromPromise(promise);
    }

    public uploadAdminSong(req, res): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            uploadFullSongs(req, res, function (err) {
                if (err) {
                    console.log('error');
                    return res.status(422).send({ errors: [{ title: 'File Upload Error', detail: err.message }] });
                }
                let data = req.body;
                let duration = data["songduration"]
                let fileSize = data["fileSize"]
                let full_song_url = req['file'].location
                let id = data['songId']
                let song = Song.update({
                    FullSongUrl: full_song_url,
                    Duration: duration,
                    Memory: fileSize,
                    IsSongFullyUploaded :"0"
                },
                    {
                        where: { id: id }
                    })
                resolve(song)

            })


        });
        return Rx.Observable.fromPromise(promise);
    }

    public uploadAdmin30secSong(req, res): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            uploadFullSongs(req, res, function (err) {
                if (err) {
                    console.log('error');
                    return res.status(422).send({ errors: [{ title: 'File Upload Error', detail: err.message }] });
                }
                let data = req.body;
                let songURl = req['file'].location 
                let id = data['songId']
                let song = Song.update({
                    ImageUrl: songURl,
                    IsSongFullyUploaded :"1"
                },
                    {
                        where: { id: id }
                    })
                  resolve(song)

            })
        });
        return Rx.Observable.fromPromise(promise);
    }




    public getAllSong(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            console.log(req.body)
            const catsubcat = Song.findAll({
                where: {
                    categoryId: req.body.categoryId,
                    subcategoryId: req.body.subcategoryId,
                    IsSongFullyUploaded:"1"
                }, order: [
                    ['name', 'ASC'],
                ],
                include: [{
                    model: user,
                    as: 'likedUser',
                    attributes: ['id'],
                    through: {
                        where: {
                            userId: req.body.userId,
                        },
                    },
                },
                {

                    model: user,
                    as: 'SaveAdminUser',
                    attributes: ['id'],
                    through: {
                        where: {
                            userId: req.body.userId,
                        },
                    },
                   
                },
                {

                    model: user,
                    as: 'SongHearedByUser',
                    attributes: ['id'],
                    through: {
                        where: {
                            userId: req.body.userId,
                        },
                    },
                   
                }
                ]

            })

            resolve(catsubcat)

        });
        return Rx.Observable.fromPromise(promise);
    }



    public getAllSingleArtistSong(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const catsubcat = Song.findAll({
                where: {
                    ArtistId: req.body.artistId,
                    IsSongFullyUploaded:"1"

                }, order: [
                    ['name', 'ASC'],
                ],
                include: [{
                    model: user,
                    as: 'likedUser',
                    attributes: ['id'],
                    through: {
                        where: {
                            userId: req.body.userId,
                        },
                    },
                },
                {

                    model: user,
                    as: 'SaveAdminUser',
                    attributes: ['id'],
                    through: {
                        where: {
                            userId: req.body.userId,
                          
                        },
                    },
                }
                ]

            })

            resolve(catsubcat)

        });
        return Rx.Observable.fromPromise(promise);
    }




    public likeSong(req): Rx.Observable<any> {
        const likes = +req.body.likes;
        const dislike = +req.body.disLikes;
        const totalLikes = likes + 1;
        const totalLikesDislikes = (totalLikes) + dislike;
        const promise = new Promise((resolve, reject) => {
            SongThrough.create({
                userId: req.body.userId,
                songId: req.body.songId, Like: req.body.like,
                DisLike: req.body.disLike
            })
                .then((result2) => {
                    Song.update(
                        {
                            Like: Sequelize.literal(+req.body.likes + 1
                            )
                        },
                        { where: { id: req.body.songId } }
                    ).then((result3) => {
                        Song.find({
                            where: { id: req.body.songId }
                        }).then((result) => {
                            InstaMixSong.create({
                                SongName: result.dataValues['Name'], Name: result.dataValues['Name'],
                                ArtistName: result.dataValues['ArtistName'], Duration: result.dataValues['Duration']
                                , ImageUrl: result.dataValues['ImageUrl'], AlbumName: result.dataValues['AlbumName'],
                                SongId: req.body.songId,
                                UserId: req.body.userId,
                                BannerImage: result.dataValues['CoverImage'], Memory: result.dataValues['Memory'],
                                ArtistId: result.dataValues['ArtistId'], FullSongImageUrl: result.dataValues['FullSongUrl']
                            }).then((result1) => {
                                user.find({
                                    where: { id: req.body.userId },
                                    attributes: ['instamixMemory']
                                }).then((result4) => {
                                    user.update({
                                        InstaMixMemory: result.dataValues['Memory'] + result4.dataValues['instamixMemory']
                                    },
                                        {
                                            where: { id: req.body.userId }
                                        })
                                }).then((result8) => {
                                    Song.findAll({
                                        where: {
                                            ArtistId: req.body.artistId
                                        },
                                        attributes: [[Sequelize.fn('SUM', Sequelize.col('like')), 'total_like'
                                        ], [Sequelize.fn('SUM', Sequelize.col('disLike')), 'total_disLike'
                                        ]],
                                    }).then((result9) => {
                                        Artist.update({
                                            TotalLike: result9[0].dataValues['total_like'],
                                            TotalDislike: result9[0].dataValues['total_disLike']
                                        },
                                            {
                                                where: {
                                                    id: req.body.artistId
                                                }
                                            },
                                        ).then((result10) => {
                                            if (totalLikes == 100) {
                                                const SongPercentage = +totalLikes * 100 / +totalLikesDislikes
                                                if (SongPercentage > 69) {
                                                    Song.update(
                                                        {
                                                            IsTrending: 1,
                                                            TrendingDate: new Date().toUTCString()
                                                        },
                                                        { where: { id: req.body.songId } }
                                                    )
                                                }
                                            }
                                        }).then((result) => {
                                            const tlike = Song.find({
                                                where: { id: req.body.songId }
                                                , attributes: ['id', 'Like', 'DisLike', 'Memory']
                                            })
                                            resolve(tlike)
                                        }).catch((err) => {
                                            reject(err)
                                        })
                                            .catch((err) => {
                                                reject(err)
                                            })
                                    }).catch((err) => {
                                        reject(err)
                                    })
                                })
                            }).catch((err) => {
                                reject(err)
                            })
                        }).catch((err) => {
                            reject(err)
                        })

                    }).catch((err) => {
                        reject(err)
                    })

                }).catch((err) => {
                    reject(err)
                })

        })
        return Rx.Observable.fromPromise(promise);
    }


    public deleteHalfUploadedSong(req): Rx.Observable<any> {
        console.log(req.body)
        const promise = new Promise((resolve, reject) => {
            const song = user.destroy({
                    where: {
                        id: req.body.songId
                    }
                })

            resolve(song)
        });
        return Rx.Observable.fromPromise(promise);
    }



    public getUserInstanceMemory(req): Rx.Observable<any> {
        console.log(req.body)
        const promise = new Promise((resolve, reject) => {
            const like = user.find(
                {
                    where: {
                        id: req.body.userId
                    }, attributes: ['instamixMemory']
                }


            )

            resolve(like)
        });
        return Rx.Observable.fromPromise(promise);
    }


    public checkLikeExist(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const like = SongThrough.find(
                {
                    where: {
                        songId: req.body.songId,
                        userId: req.body.userId
                    }
                }
            )

            resolve(like)


        });
        return Rx.Observable.fromPromise(promise);
    }


    public updatelikeSong(req): Rx.Observable<any> {
        const likes = +req.body.likes;
        const dislike = +req.body.disLikes;
        const totalLikes = likes + 1;
        const totalLikesDislikes = (totalLikes) + dislike;
        const promise = new Promise((resolve, reject) => {
            SongThrough.update(
                {
                    Like: req.body.like,
                    DisLike: req.body.disLike
                },
                {
                    where: {
                        userId: req.body.userId,
                        songId: req.body.songId
                    }
                }
            )
                .then((result2) => {
                    Song.update(
                        {
                            Like: Sequelize.literal(+req.body.likes + 1),
                            DisLike: Sequelize.literal(+req.body.disLikes - 1)

                        },
                        { where: { id: req.body.songId } }
                    ).then((result3) => {
                        Song.find({
                            where: { id: req.body.songId }
                        }).then((result) => {
                            InstaMixSong.create({
                                SongName: result.dataValues['Name'], Name: result.dataValues['Name'],
                                ArtistName: result.dataValues['ArtistName'], Duration: result.dataValues['Duration']
                                , ImageUrl: result.dataValues['ImageUrl'], AlbumName: result.dataValues['AlbumName'],
                                SongId: req.body.songId,
                                UserId: req.body.userId,
                                BannerImage: result.dataValues['CoverImage'], Memory: result.dataValues['Memory'],
                                ArtistId: result.dataValues['ArtistId'], FullSongImageUrl: result.dataValues['FullSongUrl']
                            }).then((result1) => {
                                user.find({
                                    where: { id: req.body.userId },
                                    attributes: ['instamixMemory']
                                }).then((result4) => {
                                    user.update({
                                        InstaMixMemory: result.dataValues['Memory'] + result4.dataValues['instamixMemory']
                                    },
                                        {
                                            where: { id: req.body.userId }
                                        })
                                }).then((result8) => {
                                    Song.findAll({
                                        where: {
                                            ArtistId: req.body.artistId
                                        },
                                        attributes: [[Sequelize.fn('SUM', Sequelize.col('like')), 'total_like'
                                        ], [Sequelize.fn('SUM', Sequelize.col('disLike')), 'total_disLike'
                                        ]],
                                    }).then((result9) => {
                                        Artist.update({
                                            TotalLike: result9[0].dataValues['total_like'],
                                            TotalDislike: result9[0].dataValues['total_disLike']
                                        },
                                            {
                                                where: {
                                                    id: req.body.artistId
                                                }
                                            },
                                        ).then((result10) => {
                                            if (totalLikes == 100) {
                                                const SongPercentage = +totalLikes * 100 / +totalLikesDislikes
                                                if (SongPercentage > 69) {
                                                    Song.update(
                                                        {
                                                            IsTrending: 1,
                                                            IsDispopular: 0,
                                                            TrendingDate: new Date().toUTCString()
                                                        },
                                                        { where: { id: req.body.songId } }
                                                    )
                                                }
                                            }
                                        }).then((result) => {
                                            const tlike = Song.find({
                                                where: { id: req.body.songId }
                                                , attributes: ['id', 'Like', 'DisLike', 'Memory']
                                            })
                                            resolve(tlike)
                                        }).catch((err) => {
                                            reject(err)
                                        })
                                            .catch((err) => {
                                                reject(err)
                                            })
                                    }).catch((err) => {
                                        reject(err)
                                    })
                                })
                            }).catch((err) => {
                                reject(err)
                            })
                        }).catch((err) => {
                            reject(err)
                        })

                    }).catch((err) => {
                        reject(err)
                    })

                }).catch((err) => {
                    reject(err)
                })

        })
        return Rx.Observable.fromPromise(promise);
    }



    public dislikeSong(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const likes = +req.body.likes;
            const dislike = +req.body.disLikes;
            const total_dislike = dislike + 1;
            const total_like_dislike = likes + total_dislike
            SongThrough.create({ userId: req.body.userId, songId: req.body.songId, Like: req.body.like, DisLike: req.body.disLike })
                .then((result) => {
                    Song.update(
                        {
                            DisLike: Sequelize.literal(+req.body.disLikes + 1)
                        },

                        { where: { id: req.body.songId } }
                    ).then((result) => {

                        if (total_dislike == 100) {

                            const SongPercentage = total_dislike * 100 / total_like_dislike


                            if (SongPercentage > 69) {
                                console.log("njaJsakssjshdfkjshfskfhshfshjfsjhfshfjho")
                                Song.update(
                                    {
                                        IsDispopular: 1,
                                        IsTrending: 0,
                                        TrendingDate: new Date().toUTCString()
                                    },
                                    { where: { id: req.body.songId } }
                                )
                            }
                        }

                    }).then((result) => {
                        const tlike = Song.find({
                            where: { id: req.body.songId }
                            , attributes: ['id', 'Like', 'DisLike', 'Memory']
                        })
                        resolve(tlike)
                    }).catch((err) => {
                        reject(err)
                    })
                        .catch((err) => {
                            reject(err)
                        });


                }).catch((err) => {
                    reject(err)
                });

        });
        return Rx.Observable.fromPromise(promise);
    }


    public updatedislikeSong(req): Rx.Observable<any> {
        const likes = +req.body.likes - 1;
        const dislike = +req.body.disLikes;
        const total_dislike = dislike + 1;
        const total_like_dislike = likes + total_dislike
        const promise = new Promise((resolve, reject) => {
            SongThrough.update(
                {
                    Like: req.body.like,
                    DisLike: req.body.disLike
                },
                {
                    where: {
                        userId: req.body.userId,
                        songId: req.body.songId
                    }
                }
            )
                .then((result2) => {
                    Song.update(
                        {
                            Like: Sequelize.literal(+req.body.likes - 1),
                            DisLike: Sequelize.literal(+req.body.disLikes + 1)
                        },
                        { where: { id: req.body.songId } }
                    ).then((result3) => {
                        Song.find({
                            where: { id: req.body.songId }
                        }).then((result) => {
                            InstaMixSong.destroy({
                                where: {
                                    userId: req.body.userId,
                                    SongId: req.body.songId
                                }
                            }).then((result1) => {
                                user.find({
                                    where: { id: req.body.userId },
                                    attributes: ['instamixMemory']
                                }).then((result4) => {
                                    user.update({
                                        InstaMixMemory: result4.dataValues['instamixMemory'] - result.dataValues['Memory']
                                    },
                                        {
                                            where: { id: req.body.userId }
                                        })
                                }).then((result8) => {
                                    Song.findAll({
                                        where: {
                                            ArtistId: req.body.artistId
                                        },
                                        attributes: [[Sequelize.fn('SUM', Sequelize.col('like')), 'total_like'
                                        ], [Sequelize.fn('SUM', Sequelize.col('disLike')), 'total_disLike'
                                        ]],
                                    }).then((result9) => {
                                        Artist.update({
                                            TotalLike: result9[0].dataValues['total_like'],
                                            TotalDislike: result9[0].dataValues['total_disLike']
                                        },
                                            {
                                                where: {
                                                    id: req.body.artistId
                                                }
                                            },
                                        ).then((result10) => {
                                            if (total_dislike == 100) {

                                                const SongPercentage = total_dislike * 100 / total_like_dislike


                                                if (SongPercentage > 69) {
                                                    Song.update(
                                                        {
                                                            IsDispopular: 1,
                                                            IsTrending: 0,
                                                            TrendingDate: new Date().toUTCString()
                                                        },
                                                        { where: { id: req.body.songId } }
                                                    )
                                                }
                                            }
                                        }).then((result) => {
                                            const tlike = Song.find({
                                                where: { id: req.body.songId }
                                                , attributes: ['id', 'Like', 'DisLike', 'Memory']
                                            })
                                            resolve(tlike)
                                        }).catch((err) => {
                                            reject(err)
                                        })
                                            .catch((err) => {
                                                reject(err)
                                            })
                                    }).catch((err) => {
                                        reject(err)
                                    })
                                })
                            }).catch((err) => {
                                reject(err)
                            })
                        }).catch((err) => {
                            reject(err)
                        })

                    }).catch((err) => {
                        reject(err)
                    })

                }).catch((err) => {
                    reject(err)
                })

        })
        return Rx.Observable.fromPromise(promise);
    }

    public SaveSongAdmin(req): Rx.Observable<any> {
        const singleSongMemory = +req.body.singleSaveMemory;
        const total = +req.body.savedSongMemory;
        const promise = new Promise((resolve, reject) => {
            AdminSave.create({
                SongName: req.body.song_name, Name: req.body.song_name, ArtistName: req.body.artist_name, Duration: req.body.songduration
                , ImageUrl: req.body.song_img_url, AlbumName: req.body.album_name, SongId: req.body.songId,
                UserId: req.body.userId, BannerImage: req.body.bannerImage, Memory: req.body.singleSaveMemory, IsDeleted: '0',
                ArtistId: req.body.artist_id, FullSongImageUrl: req.body.full_song_url
            }).then((result) => {
                AdminSaveThrough.create({
                    userId: req.body.userId, songId: req.body.songId, isSaved: req.body.isSaved
                }).then((result) => {
                    user.update(
                        {
                            SavedSongMemory: singleSongMemory + total
                        },
                        { where: { id: req.body.userId } }
                    )
                    resolve(result)
                }).catch((err) => {
                    reject(err)
                });
            }).catch((err) => {
                reject(err)
            });



        });
        return Rx.Observable.fromPromise(promise);
    }

    public isSongExist(songId, UserId): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const song = AdminSave.findAll({
                where: {
                    SongId: songId,
                    userId: UserId
                }
            })
            resolve(song);
        });
        return Rx.Observable.fromPromise(promise);
    }

    public getSongList(songId, UserId): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const song = AdminSave.findAll({
                where: {
                    userId: UserId
                }
            })
            resolve(song);
        });
        return Rx.Observable.fromPromise(promise);
    }

    public getSongListAlphabatically(songId, UserId): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const song = AdminSave.findAll({
                where: {
                    userId: UserId
                }, order: [
                    ['song_name', 'ASC'],
                ]
            })
            resolve(song);
        });
        return Rx.Observable.fromPromise(promise);
    }


    public removeSong(req): Rx.Observable<any> {
        const singleSongMemory = +req.body.singleSaveMemory;
        const total = +req.body.savedSongMemory;
        const promise = new Promise((resolve, reject) => {
            AdminSave.destroy({
                where: {
                    userId: req.body.userId,
                    SongId: req.body.songId
                }
            }).then((result) => {
                AdminSaveThrough.destroy({
                    where: {
                        userId: req.body.userId,
                        SongId: req.body.songId

                    }
                }).then((result1) => {
                    const song = user.update(
                        {
                            SavedSongMemory: total - singleSongMemory
                        },
                        { where: { id: req.body.userId } }
                    )
                    resolve(result1)
                })
                    .catch((err) => {
                        reject(err)
                    })
            }).catch((err) => {
                reject(err)

            });

        });
        return Rx.Observable.fromPromise(promise);
    }
    public get1stCatId(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const song = CategoryThrough.findAll({
                where: {
                    categoryId: req.body.catId
                }
            })
            resolve(song);
        });
        return Rx.Observable.fromPromise(promise);
    }
    public getSukhmehalImages(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const song = sukhmehalMode.findAll({
                where: {
                    categoryId: req.params.catId
                }
            })
            resolve(song);
        });
        return Rx.Observable.fromPromise(promise);
    }

    public deleteSukhmehalImages(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const song = sukhmehalMode.destroy({
                where: {
                    id: req.params.imageId
                }
            })
            resolve(song);
        });
        return Rx.Observable.fromPromise(promise);
    }


    public removeAdminSelfSong(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const song = Song.destroy({
                where: {
                    id: req.body.songId
                }
            }).then((result) => {
                AdminSave.update(
                    {
                        IsDeleted: 1
                    },
                    {
                        where: {
                            SongId: req.body.songId
                        }
                    }
                )

                resolve(result)
            }).catch((err) => {
                reject(err)
            });
        });
        return Rx.Observable.fromPromise(promise);
    }

    public removeAdminLibraryDeletedSong(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const singleSongMemory = +req.body.singleSaveMemory;
            const total = +req.body.savedSongMemory;
            AdminSave.destroy({
                where: {
                    id: req.body.savesongId,
                    SongId: req.body.songId,
                    UserId: req.body.userId
                }
            }).then((result) => {
                user.update(
                    {
                        SavedSongMemory: total - singleSongMemory
                    },
                    { where: { id: req.body.userId } }
                )
                resolve(result)
            }).catch((err) => {
                reject(err)
            });
        });
        return Rx.Observable.fromPromise(promise);
    }

    public removeFirstSavedSong(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            user.find({
                where: { id: req.body.userId },
                attributes: ['instamixMemory']
            }).then((result13) => {
                if (result13.dataValues['instamixMemory'] > 479) {
                    this.total = result13.dataValues['instamixMemory']
                    InstaMixSong.findAll({
                        order: [['id', 'ASC']]
                    }).then((result) => {
                        if (result[0] == null) {

                        } else {
                            this.memory = result[0].dataValues.Memory
                            InstaMixSong.destroy({
                                where: {
                                    id: result[0].dataValues.id
                                }
                            }).then((result) => {
                                user.update(
                                    {
                                        InstaMixMemory: this.total - this.memory
                                    },
                                    { where: { id: req.body.userId } }
                                )
                            }).then((result) => {
                                this.removeFirstSavedSong(req);

                            }).catch((err) => {
                                reject(err)
                            })
                                .catch((err) => {
                                    reject(err)
                                })
                        }
                    }).catch((err) => {
                        reject(err)
                    })
                } else {
                    resolve(result13)
                }
            })


        });
        return Rx.Observable.fromPromise(promise);
    }
    public checkInstanceSongExistOrNot(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const like = InstaMixSong.find(
                {
                    where: {
                        songId: req.body.songId,
                        userId: req.body.userId
                    }
                }
            ).then((result) => {
                resolve(result)

            }).catch((err) => {
                reject(err)
            })



        });
        return Rx.Observable.fromPromise(promise);
    }
    public getInstanceSong(userId): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const song = InstaMixSong.findAll({
                where: {
                    UserId: userId
                }
            })
            resolve(song);
        });
        return Rx.Observable.fromPromise(promise);
    }
    
    public getTop3Song(artistId): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const song = Song.findAll({
                limit: 3,
                where: {
                    ArtistId: artistId
                }
            })
            resolve(song);
        });
        return Rx.Observable.fromPromise(promise);
    }
    
    public increaseSongCount(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            Song.find({
                where:{
                    id:req.body.songId
                },attributes:['SongHearCount']
            }).then((result)=>{
                const song = Song.update({
                    SongHearCount:result.dataValues['SongHearCount'] + 1, 
                    where: {
                       id: req.body.songId
                   }   
                   })
                   resolve(song);
            })
         
           
        });
        return Rx.Observable.fromPromise(promise);
    }

    public getArtistMostHearedSong(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const song = Song.findAll({
                limit: 3,
                where: {
                    ArtistId: req.body.artist
                },
                order: [[ 'SongHearCount', 'ASC' ]]
            })
            resolve(song);
        });
        return Rx.Observable.fromPromise(promise);
    }
    
    
    public updateSongHeared(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
           isSongHeared.create({
                 IsSongHeared:"1",
                 songId: req.body.songId ,
                 userId :req.body.userId 
            }).then((song)=>{
                resolve(song);
            }).catch((err)=>{
                reject(err)
            })
           
        });
        return Rx.Observable.fromPromise(promise);
    }

}

// order: [[ 'id', 'DESC' ]]
