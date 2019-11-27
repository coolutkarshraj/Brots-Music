const Sequelize = require('sequelize');
import { Config } from "../Config";
const {CategoryModel, SubCategoryModel,CategoryThroughModel, SongModel, SongThroughModel,SonghearModel } = require('./model')
const{UserModel, ArtistModel ,AdminModel,FriendModel,FollowerModel} = require('../models/UserModel')
const{AdminSaveSongThrough, AdminSaveSongModel} = require('../models/AdmisSaveSongModel')
const{ArtistSongCommentModel} = require('../models/ArtistSongCommentModel');
const {InstaMixTable} =  require('../models/InstaMixTable')
const {ContactModel} = require ('../models/ContactModel')
const {NotifacationsModel} = require ('../models/NotifacationsModel')
const {sukhmehalModel} = require ('../models/SukhmahalImages')
const sequelize = new Sequelize(Config.db['database'], Config.db.user, '', {

    host: Config.db.host,
    dialect: 'mysql',
    pool: {
        max: 5,
        min: 0,
        idle: 10000
    },
});
const Category = CategoryModel(sequelize,Sequelize)
const SubCategory = SubCategoryModel(sequelize,Sequelize)
const Song = SongModel(sequelize, Sequelize)
const sukhmehalMode = sukhmehalModel(sequelize, Sequelize)

const user = UserModel(sequelize, Sequelize)
const Friend = FriendModel(sequelize, Sequelize)
const Artist = ArtistModel(sequelize, Sequelize)
const Admin = AdminModel(sequelize, Sequelize)

const contactModel = ContactModel(sequelize,sequelize)
const notificationModel = NotifacationsModel(sequelize,sequelize)


const AdminCommentModel = ArtistSongCommentModel(sequelize, Sequelize)


const AdminSave = AdminSaveSongModel(sequelize, Sequelize)
const AdminSaveThrough = AdminSaveSongThrough(sequelize)

const InstaMixSong = InstaMixTable(sequelize, Sequelize)

const CategoryThrough = CategoryThroughModel(sequelize)
const Follower = FollowerModel(sequelize, Sequelize)
const SongThrough = SongThroughModel(sequelize)

const isSongHeared = SonghearModel(sequelize)
/**
 * Category and Subcategory M:N Asssociation
*/
Category.belongsToMany(Song,{through: CategoryThrough, allowNull:true})
Category.belongsToMany(SubCategory,{through: CategoryThrough, allowNull:true})
SubCategory.belongsToMany(Category,{through: CategoryThrough, allowNull:true})

/**
 * Song 1:N association with Category and 
*/
Category.hasMany(Song, {as: 'Songs',allowNull: true });
SubCategory.hasMany(Song, {as: 'Songs',allowNull: true });

/**
 * User to User M:N Asssociation using FriendModel
*/
user.belongsToMany(user, { as: 'sender', foreignKey: 'senderId', through: Friend });
user.belongsToMany(user, { as: 'receiver', foreignKey: 'receiverId', through: Friend });

/**
 * User To Artist M:N Asssociation using FollowerModel
*/
user.belongsToMany(Artist, { as: 'following', foreignKey: 'userId', through: Follower });
Artist.belongsToMany(user, { as: 'followers', foreignKey: 'artistId', through: Follower });

user.belongsToMany(Song,{as:'likedSong', through: SongThrough, allowNull:true})
Song.belongsToMany(user,{as:'likedUser',through: SongThrough, allowNull:true})


user.belongsToMany(Song,{as:'SavedAdminSong', through: AdminSaveThrough, allowNull:true})
Song.belongsToMany(user,{as:'SaveAdminUser',through: AdminSaveThrough, allowNull:true})


user.belongsToMany(Song,{as:'isSongHeared', through: isSongHeared, allowNull:true})
Song.belongsToMany(user,{as:'SongHearedByUser',through: isSongHeared, allowNull:true})


sequelize.sync({ force: true })
.then(() => {
    console.log("Database table created")
})
.catch((err) => {
    console.log('Failed to create database');
    console.log(err);
})
module.exports = {
    Category,
    SubCategory,
    CategoryThrough,
    Song,
    user,
    Artist,
    Admin,
    Friend,
    Follower,
    sequelize,
    SongThrough,
    AdminSave,
    AdminSaveThrough,
    AdminCommentModel,
    InstaMixSong,
    contactModel,
    notificationModel,
    isSongHeared,
    sukhmehalMode

}
