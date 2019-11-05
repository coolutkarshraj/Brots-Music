import * as Rx from 'rxjs/Rx';
import { ServiceBase } from './common/ServiceBase';
const { user, Friend, Song } = require("../category/sqlService")
const Sequelize = require('sequelize');
const { Artist } = require("../category/sqlService")


export class SearchFriendServices extends ServiceBase {

    constructor() {
        super();

    }

    public searchFriend(req, type): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            console.log('foidoifpodfidiopf')
            console.log(type)
            if (type == "0") {
                user.findAll({
                    where: { Name: req.body.name },
                    attributes: ['id', 'Name', 'email', 'gender', 'dob', 'imageUrl', 'city', 'country', 'phone'],

                }).then((userFriend) => {
                    if (userFriend[0] == null) {
                        user.findAll({
                            where: { Email: req.body.name },
                            attributes: ['id', 'Name', 'email', 'gender', 'dob', 'imageUrl', 'city', 'country', 'phone'],
                        }).then((userFriendemail) => {
                            if (userFriendemail[0] == null) {
                                user.findAll({
                                    where: { Phone: req.body.name },
                                    attributes: ['id', 'Name', 'email', 'gender', 'dob', 'imageUrl', 'city', 'country', 'phone'],
                                }).then((userFriendPhone) => {
                                    if (userFriendPhone[0] == null) {
                                        user.findAll({
                                            where: { city: req.body.name },
                                            attributes: ['id', 'Name', 'email', 'gender', 'dob', 'imageUrl', 'city', 'country', 'phone'],
                                        }).then((userFriendCity) => {
                                            if (userFriendCity[0] == null) {
                                                user.findAll({
                                                    where: { Country: req.body.name },
                                                    attributes: ['id', 'Name', 'email', 'gender', 'dob', 'imageUrl', 'city', 'country', 'phone'],
                                                }).then((userFriendCountry) => {
                                                    if (userFriendCountry[0] == null) {
                                                        user.findAll({
                                                            where: { State: req.body.name },
                                                            attributes: ['id', 'Name', 'email', 'gender', 'dob', 'imageUrl', 'city', 'country', 'phone'],
                                                        }).then((userFriendState) => {
                                                            resolve(userFriendState)
                                                        }).catch((err) => {
                                                            reject(err)
                                                        })
                                                    } else {
                                                        resolve(userFriendCountry)
                                                    }
                                                })
                                            } else {
                                                resolve(userFriendCity)
                                            }
                                        })
                                    } else {
                                        resolve(userFriendPhone)
                                    }
                                }).catch((err) => {
                                    reject(err)
                                })
                            } else {
                                resolve(userFriendemail)
                            }
                        }).catch((err) => {

                        })
                    } else {
                        resolve(userFriend)

                    }


                }).catch((err) => {
                    reject(err)
                })
            }else{
               const listUser =  user.findAll({
                   where: {
                        UserType:'1'
                   },
                                attributes: ['id', 'Name', 'email', 'gender', 'dob', 'imageUrl', 'city', 'country', 'phone'],
                   
                   

                })
                resolve(listUser)
            }


        })
        return Rx.Observable.fromPromise(promise);
    }

    public checkForSendFriend(id, userId): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const friend = Friend.find({
                where: {
                    receiverId: userId,
                    senderId: id
                }


            })
            resolve(friend)

        });
        return Rx.Observable.fromPromise(promise);
    }
    

    public getUserDeviceTokens(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const friend = user.find({
                where: {
                    id: req.body.userId,
               },
               attributes:['deviceToken']


            })
            resolve(friend)

        });
        return Rx.Observable.fromPromise(promise);
    }

    public checkForGetFriend(id, userId): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const friend = Friend.find({
                where: {
                    receiverId: id,
                    senderId: userId 
                }
            })
            resolve(friend)

        });
        return Rx.Observable.fromPromise(promise);
    }

    public getAllDashboardData(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const SongData = Song.findAll({
                where: {
                    ArtistId: req.body.artistId
                },
                attributes: [[Sequelize.fn('SUM', Sequelize.col('like')), 'total_like'
                ], [Sequelize.fn('SUM', Sequelize.col('disLike')), 'total_disLike'
                ], [Sequelize.fn('SUM', Sequelize.col('memory')), 'total_memory'
                ],],

            })
            resolve(SongData)
        });
        return Rx.Observable.fromPromise(promise);
    }

    public getAllTrendingSong(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const SongData = Song.findAll({
                limit: 100,
                where: {
                    IsTrending: "1"
                }, order: [
                    ['TrendingDate', 'ASC'],
                ],


            })
            resolve(SongData)

        });
        return Rx.Observable.fromPromise(promise);
    }
    public getAllDispopularSong(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const SongData = Song.findAll({
                limit: 100,
                where: {
                    IsDispopular: "1"
                }, order: [
                    ['TrendingDate', 'ASC'],
                ],


            })
            resolve(SongData)

        });
        return Rx.Observable.fromPromise(promise);
    }

    public getAllArtistFollower(req): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            const SongData = Artist.find({
                where: {
                    id: req.body.artistId
                },
                attributes: [[Sequelize.fn('SUM', Sequelize.col('followerCount')), 'total_follower']]

            })
            resolve(SongData)
        });
        return Rx.Observable.fromPromise(promise);
    }


}
