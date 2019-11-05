import * as express from "express";
import { Request, Response } from "express";
import { UserController } from "../controllers/UserController";
import { ArtistController } from "../controllers/ArtistController";
import { GetUserTypeControllers } from "../controllers/GetUserTypeControllers"
import { GetArtistTypeController } from "../controllers/GetArtistTypeController"
import { AdminSongController } from "../category/AdminSongController"
import { AdminController } from "../controllers/AdminController"
import { ArtistCommentController } from "../controllers/ArtistCommentController"
import { SearchFriendController } from "../controllers/SearchFriendController"
import {ContactController} from "../controllers/ContactController"
import {NotificationController} from "../controllers/NotificationController"
import * as __ from "underscore";
import * as _ from "lodash";
import { from } from "rxjs/observable/from";
const userRoute = require("./user/router");
const multer = require('multer')
var fs = require('fs');
var shell = require('shelljs');

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
      let dir = "./private/songs/"
      if (!fs.existsSync(dir)){
        shell.mkdir('-p', dir);
      }
      cb(null, dir)
    },
    filename: (req, file, cb) => {
      cb(null, file.originalname)
    }
})

var upload = multer({ storage });

export class AppRouter {
  private userController: UserController;
  private getUserTypeController: GetUserTypeControllers;
  private getArtistTypeControllerr: GetArtistTypeController;
  private artistController: ArtistController;
  private adminSongController: AdminSongController;
  private adminController: AdminController;
  private artistCommentController: ArtistCommentController;
  private searchFriendController: SearchFriendController;
  private ContactController : ContactController;
  private notificationController : NotificationController;
  constructor(app: express.Application) {
    this.userController = new UserController();
    this.getUserTypeController = new GetUserTypeControllers();
    this.artistController = new ArtistController();
    this.getArtistTypeControllerr = new GetArtistTypeController();
    this.adminSongController = new AdminSongController();
    this.adminController = new AdminController();
    this.artistCommentController = new ArtistCommentController();
    this.searchFriendController = new SearchFriendController();
    this.ContactController = new ContactController();
    this.notificationController = new NotificationController();

    app.get("/v1/ping", (req: Request, res: Response) => this.userController.ping(req, res));

    /** user Login-Registration Flow user*/
    app.post("/v1/user/registrationVerify", (req: Request, res: Response) => this.userController.verifyRegistration(req, res));
    app.post("/v1/user/register", (req: Request, res: Response) => this.userController.registerUser(req, res));
    app.post("/v1/user/login", (req: Request, res: Response) => this.userController.loginUser(req, res));
    app.get("/v1/user/logout/:id", (req: Request, res: Response) => this.userController.logoutUser(req, res));
    app.post('/v1/getPasswordResetCode', (req: Request, res: Response) => this.userController.getPasswordResetCode(req, res));
    app.post('/v1/updatePassword', (req: Request, res: Response) => this.userController.updatePassword(req, res));
    app.post("/v1/user/blockUser", (req: Request, res: Response) => this.userController.blockUser(req, res));
    app.post("/v1/user/unblockUser", (req: Request, res: Response) => this.userController.unblockUser(req, res));
    app.post("/v1/user/updateDeviceToken", (req: Request, res: Response) => this.userController.updateDeviceToken(req, res));

    /** update profile,Email user*/
    app.post("/v1/user/getProfile", (req: Request, res: Response) => this.userController.getProfile(req, res));
    app.post('/v1/sendMailToEmailIfNotExit', (req: Request, res: Response) => this.getUserTypeController.sendOtpIfEmailNotExit(req, res));
    app.post('/v1/updateemail', (req: Request, res: Response) => this.getUserTypeController.updateEmail(req, res));
    app.post('/v1/user/updateProfile', (req: Request, res: Response) => this.getUserTypeController.updateProfile(req, res));
    app.post('/v1/user/changepassword', (req: Request, res: Response) => this.getUserTypeController.ChangePassword(req, res));

    /**getUser**/
    app.post("/v1/user/isUserExist", (req: Request, res: Response) => this.userController.isUserExist(req, res));
    app.get("/v1/getAllUsers", (req: Request, res: Response) => this.userController.getAllUsers(req, res));
    app.post("/v1/getAllUsers/userType", (req: Request, res: Response) => this.getUserTypeController.getAllUsersTypeOnThBasisOFUserType(req, res));

    /** Artist Login-Registration Flow Artist*/

    app.post("/v1/artist/registrationVerify", (req: Request, res: Response) => this.artistController.verifyRegistration(req, res));
    app.post("/v1/artist/register", (req: Request, res: Response) => this.artistController.registerUser(req, res));
    app.post("/v1/artist/login", (req: Request, res: Response) => this.artistController.loginUser(req, res));
    app.get("/v1/artist/logout/:id", (req: Request, res: Response) => this.artistController.logoutUser(req, res));
    app.post('/v1/artist/getPasswordResetCode', (req: Request, res: Response) => this.artistController.getPasswordResetCode(req, res));
    app.post('/v1/artist/updatePassword', (req: Request, res: Response) => this.artistController.updatePassword(req, res));
    app.post("/v1/artist/blockUser", (req: Request, res: Response) => this.artistController.blockUser(req, res));
    app.post("/v1/artist/unblockUser", (req: Request, res: Response) => this.artistController.unblockUser(req, res));
    app.post("/v1/artist/updateDeviceToken", (req: Request, res: Response) => this.artistController.updateDeviceToken(req, res));

    /** update profile,Email Artist*/
    app.post("/v1/artist/getProfile", (req: Request, res: Response) => this.artistController.getProfile(req, res));
    app.post('/v1/artist/sendMailToEmailIfNotExit', (req: Request, res: Response) => this.getArtistTypeControllerr.sendOtpIfEmailNotExit(req, res));
    app.post('/v1/artist/updateemail', (req: Request, res: Response) => this.getArtistTypeControllerr.updateEmail(req, res));
    app.post('/v1/artist/updateProfile', (req: Request, res: Response) => this.getArtistTypeControllerr.updateProfile(req, res));
    app.post('/v1/artist/addNote', (req: Request, res: Response) => this.getArtistTypeControllerr.UpdateAddNote(req, res));
    app.post('/v1/artist/deleteNote', (req: Request, res: Response) => this.getArtistTypeControllerr.deleteNote(req, res));
    app.post('/v1/artist/changepassword', (req: Request, res: Response) => this.getArtistTypeControllerr.ChangePassword(req, res));

    /**getArtist**/
    app.post("/v1/artist/isUserExist", (req: Request, res: Response) => this.artistController.isUserExist(req, res));
    app.get("/v1/getAllArtist",(req: Request, res: Response) => this.artistController.getAllUsers(req, res));

    app.post("/v1/getAllArtist/ArtistType", (req: Request, res: Response) => this.getArtistTypeControllerr.getAllUsersTypeOnThBasisOFUserType(req, res));
    app.post("/v1/getAllArtist/onthebehalfof/like/dislike", (req: Request, res: Response) => this.getArtistTypeControllerr.getAllArtistOnThebehalOfLikeDislike(req, res));



    /** Admin Song Categories and subcategories*/
    app.get("/v1/allCategories/subcategories", (req: Request, res: Response) => this.adminSongController.getAllCategoriessubCategories(req, res));
    app.get("/v1/getAllcategories",(req: Request, res: Response) => this.adminSongController.getAllCategorie(req, res));
    app.post("/v1/artist/getAllSong", (req: Request, res: Response) => this.adminSongController.getAdminSong(req, res));
    app.post("/v1/artist/uploadSongBasic", (req: Request, res: Response) => this.adminSongController.uploadAdminSongBasicDetails(req, res));
    app.post("/v1/artist/sukhmahalImages", (req: Request, res: Response) => this.adminSongController.uploadsukhmalImage(req, res));
    app.get("/v1/artist/getSukhmehalImages/:catId",(req:Request,res:Response)=>this.adminSongController.getSukhmehalImages(req,res))
    app.get("/v1/artist/deleteSukhmehalImages/:imageId",(req:Request,res:Response)=>this.adminSongController.deleteSukhmehalImages(req,res))
    
    app.post("/v1/artist/uploadFullSong", (req: Request, res: Response) => this.adminSongController.uploadAdminFullSong(req, res));
	  app.post("/v1/artist/cut30secSong", upload.any(), (req: Request, res: Response) => this.adminSongController.cutAdminSong30sec(req,res));
    app.post("/v1/artist/upload30secSong", (req: Request, res: Response) => this.adminSongController.uploadAdminSong30sec(req, res));
    app.post("/v1/artist/deleteHalfUploadedSong", (req: Request, res: Response) => this.adminSongController.deleteHalfUploadedSong(req, res));
    app.post("/v1/artist/getFirstSubcatId", (req: Request, res: Response) => this.adminSongController.getFirstSubcatId(req, res));
    app.post("/v1/artist/getSingleArtistAllSong", (req: Request, res: Response) => this.adminSongController.getSingleArtistSong(req, res));
    app.post("/v1/artist/editSong", (req: Request, res: Response) => this.adminSongController.editSong(req, res));
    app.post("/v1/artist/deleteSong", (req: Request, res: Response) => this.adminSongController.deleteSong(req, res));


    /**Songs of Like/DisLike Admin Song */
    app.post("/v1/artistSong/Like", (req: Request, res: Response) => this.adminSongController.likeAdminSong(req, res));
    app.post("/v1/artistSong/disLike", (req: Request, res: Response) => this.adminSongController.unLikeAdminSong(req, res));


    /**Songs of Save/get Admin Song */
    app.post("/v1/artistSong/Save", (req: Request, res: Response) => this.adminSongController.saveAdminSong(req, res));
    app.post("/v1/artistSong/geSaveSong", (req: Request, res: Response) => this.adminSongController.getAdminSaveSong(req, res));
    app.post("/v1/artistSong/remove", (req: Request, res: Response) => this.adminSongController.removeAdminSong(req, res));
    app.post("/v1/artistSong/geSaveSongbyAlphabetically", (req: Request, res: Response) => this.adminSongController.getAdminSaveSongAlphabetically(req, res));
    app.post("/v1/artistSong/geInstanceSong", (req: Request, res: Response) => this.adminSongController.getInstanceSong(req, res));
    app.post("/v1/artist/profile/top3Song",(req:Request,res:Response)=>this.adminSongController.getProfileorTop3Song(req,res));
    
    /**Artist Song Comment */
    app.post("/v1/artistSong/AddComent", (req: Request, res: Response) => this.artistCommentController.AddComent(req, res));
    app.post("/v1/artistSong/editComment", (req: Request, res: Response) => this.artistCommentController.EditComment(req, res));
    app.post("/v1/artistSong/removeComment", (req: Request, res: Response) => this.artistCommentController.removeComment(req, res));
    app.post("/v1/artistSong/getAllComment", (req: Request, res: Response) => this.artistCommentController.getAllComment(req, res));


    /**User Friend And Artist Follower List */
    app.use('/v1/friend-follow', userRoute);
    app.post("/v1/user/searchFriend", (req: Request, res: Response) => this.searchFriendController.searchFriend(req, res));

    /**Artist-admin self Song Removed */
    app.post("/v1/artist-self-Song/remove", (req: Request, res: Response) => this.adminSongController.removeAdminSelfSong(req, res));


    /**User Removed Admin/Artist Deleted Song From Library */
    app.post("/v1/artist-deleted-Song/removeFromLibrary", (req: Request, res: Response) => this.adminSongController.removeUserSongFromLibrary(req, res));

    /**Trending Song List */
    app.post("/v1/artist/getAllTrendingSong", (req: Request, res: Response) => this.searchFriendController.getAllTrendingSong(req, res));
    app.post("/v1/artist/getAllDisPopularSong", (req: Request, res: Response) => this.searchFriendController.getAllDispopularSong(req, res));
    app.post("/v1/artist/getArtistDashboardData", (req: Request, res: Response) => this.searchFriendController.getArtistDashboardData(req, res));

    /**Admin Login Details */
    app.post("/v1/admin/Login", (req: Request, res: Response) => this.adminController.loginAdmin(req, res));
    app.post("/v1/admin/getProfile", (req: Request, res: Response) => this.adminController.getProfile(req, res));
    app.post("/v1/admin/updateProfile", (req: Request, res: Response) => this.adminController.updateProfile(req, res));
    app.post("/v1/admin/changePassword", (req: Request, res: Response) => this.adminController.ChangePassword(req, res));
    app.post("/v1/admin/Logout", (req: Request, res: Response) => this.adminController.logoutAdmin(req, res));
    app.post("/v1/admin/Force-Logout", (req: Request, res: Response) => this.adminController.logoutAdmin(req, res));

 
     /**Increase Song Count */
     app.post("/v1/IncreaseSongCount", (req: Request, res: Response) => this.adminSongController.increaseSongCount(req, res));
     app.post("/v1/getArtistMostHearedSong", (req: Request, res: Response) => this.adminSongController.getArtistMostHearedSong(req, res));
     app.post("/v1/artist/IsSongHeared", (req: Request, res: Response) => this.adminSongController.isSongHeared(req, res));
     
      /**Increase Song Count */
      app.post("/v1/contactAdminm",(req:Request,res:Response)=>this.ContactController.SentContactRequest(req,res))
    
         /**Send Gcm Notification */
    app.post("/v1/sendGCMNotification", (req: Request, res: Response) => this.searchFriendController.sendGCMNotification(req, res));
    app.post("/v1/addNode/SendNotification",(req:Request,res:Response)=>this.notificationController.sendAddNoteNotificationToUser(req,res))
    app.post("/v1/uploadSong/SendNotification",(req:Request,res:Response)=>this.notificationController.sendUploadSongeNotificationToUser(req,res))
    
    }
}
