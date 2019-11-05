import { BaseController } from "../controllers/common/BaseController"
import { Request, Response } from "express";
import { from } from "rxjs/observable/from";
import * as Rx from "rxjs/Rx";
import { Observable } from "rxjs/Observable";
import { Tables } from "../database/Tables";
import * as _ from "underscore";
import { Logger } from "../services/common/Logger";
import SqlService from "../services/common/SQLService";
import { EmailService } from '../services/EmailServices';
import { ArtistServices } from "../services/ArtistServices";
import { Config } from "../Config";
export class GetArtistTypeController extends BaseController {
    private sqlService: SqlService;
    private emailService: EmailService;
    private artistService: ArtistServices;

    constructor() {
        super();
        this.sqlService = new SqlService();
        this.emailService = new EmailService();
        this.artistService = new ArtistServices();
    }

    public getAllUsersTypeOnThBasisOFUserType(req: Request, res: Response) {
        console.log(req.body);
        if (req.body.constructor === Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing All Parameter",
                "error": "false",
            });
        }
        if (req.body.userType == null) {
            res.json({
                "status": "false",
                "message": "Missing Parameter userType",
                "error": "false",
            })
        }
        const user = this.sqlService.executeQuery(`select * from ${Tables.artist} where userType = "${req.body.userType}";`);
        this.sendResponse(user, res);
    }
    public getAllArtistSong(req: Request, res: Response) {
        console.log(req.body);
        const user = this.sqlService.executeQuery(`select * from ${Tables.artist} where artistId = "${req.body.artistId}";`);
        this.sendResponse(user, res);
    }

    public getAllSavedLibraries(req: Request, res: Response) {
        console.log(req.body);
        const user = this.sqlService.executeQuery(`select * from ${Tables.artist} where saveSongCatId = "${req.body.saveSongCatId}";`);
        this.sendResponse(user, res);
    }
    public sendOtpIfEmailNotExit(req: Request, res: Response) {
        const userEmail = req.body.email;
        const id = req.body.id;
        const name = req.body.name;
        if (req.body.constructor === Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing All Parameter",
                "error": "false",
            });
        } else if (req.body.id == null) {
            return res.json({
                "status": "false",
                "message": "Missing Paramenter id",
                "error": "false",
            });
        }
        else if (req.body.email == null) {
            return res.json({
                "status": "false",
                "message": "Missing Paramenter email",
                "error": "false",
            });
        } else if (req.body.userType == null) {
            return res.json({
                "status": "false",
                "message": "Missing Paramenter userType",
                "error": "false",
            });
        }
        this.artistService.getUserUsingEmai(userEmail, id).subscribe((user) => {
            if (_.isEmpty(user)) {
                const userName = name;
                const userCode = this.getSixDigitRandomNumber();
                const emailData = {
                    email: userEmail,
                    subject: 'Welcome To Brots',
                };
                const templateModal = {
                    name: userName,
                    code: userCode,
                };
                this.emailService.sendMail(emailData, templateModal, Config.mailTemplate.forgetPassword);
                res.json({
                    "status": "true",
                    "Code": "200",
                    "message": "Email Sent Sucessfull",
                    "error": "true",
                    "data": {
                        passcode: userCode
                    }

                });
            } else {
                return res.json({
                    "status": "false",
                    "message": "Email Already Found",
                    "error": "false",
                });;
            }



        });
    }

    public getUserUsingEmail(email, id): Rx.Observable<any> {
        let query = ``;
        query = `select * from ${Tables.artist} where email = "${email}" AND id = "${id}";`;
        return this.sqlService.getSingle(query);
    }

    public getSixDigitRandomNumber(min = 1000, max = 9999) {
        const numberInString = Math.floor(Math.random() * (max - min + 1) + min);
        return parseInt(numberInString.toString());
    }

    public updateEmail(req: Request, res: Response) {
        console.log(req.body);
        if (req.body.constructor === Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing All Parameter",
                "error": "false",
            });
        } else if (req.body.email == null) {
            return res.json({
                "status": "false",
                "message": "Missing Paramenter email",
                "error": "false",
            });
        } else if (req.body.userType == null) {
            return res.json({
                "status": "false",
                "message": "Missing Paramenter userType",
                "error": "false",
            });
        }
        else if (req.body.id == null) {
            return res.json({
                "status": "false",
                "message": "Missing Paramenter id",
                "error": "false",
            });
        }
        this.artistService.getUserUsingid(req.body.id, req.body.userType).subscribe((user) => {
            if (_.isEmpty(user)) {
                return res.json({
                    "status": "false",
                    "message": "Email Not Found",
                    "error": "false",
                });;
            }
            const getUserquery = this.artistService.updateEmail(req.body.id, req.body.userType, req.body.email);
            this.sendResponse(getUserquery, res);

        });


    }

    public updateProfile(req: Request, res: Response) {
        console.log(req)
        const user = this.artistService.updateProfile(req, res);
        this.sendResponsewithMulter(user, res);

    }

    public UpdateAddNote(req: Request, res: Response) {
        console.log(req.body.addnoteDate)
        if (req.body.constructor == Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing All Parameter",
                "error": "false",
            });
        }
        else if (req.body.id == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter id",
                "error": "false",
            });
        }
        else if (req.body.email == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter email",
                "error": "false",
            });
        }
        else if (req.body.addnote == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter addnote",
                "error": "false",
            });
        }
        else if (req.body.addnoteDate == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter addnoteDate",
                "error": "false",
            });
        }
        this.artistService.updateNote(req).subscribe((artist) => {
            if (_.isEmpty(artist)) {
                return res.json({
                    "status": "false",
                    "message": "Email Not Found",
                    "error": "false",
                });
            }
            res.json({
                "status": "true",
                "message": "Note Updated Successfully",
                "error": "true",
            });

        });
    }

    public deleteNote(req: Request, res: Response) {
        console.log(req.body.addnoteDate)
        if (req.body.constructor == Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing All Parameter",
                "error": "false",
            });
        }
        else if (req.body.id == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter id",
                "error": "false",
            });
        }
        else if (req.body.email == null) {
            return res.json({
                "status": "false",
                "message": "Missing Parameter email",
                "error": "false",
            });
        }
        this.artistService.deleteNote(req).subscribe((artist) => {
            if (_.isEmpty(artist)) {
                return res.json({
                    "status": "false",
                    "message": "Email Not Found",
                    "error": "false",
                });
            }
            res.json({
                "status": "true",
                "message": "Note deleted Successfully",
                "error": "true",
            });

        });
    }
    public ChangePassword(req: Request, res: Response) {
        console.log(req.body);
        if (req.body.constructor === Object && Object.keys(req.body).length === 0) {
            return res.json({
                "status": "false",
                "message": "Missing All Parameter",
                "error": "false",
            });
        }
        this.artistService.getUserUsingEmailIdPasswordUserType(req).subscribe((user1) => {
            if (_.isEmpty(user1)) {
                return res.json({
                    "status": "false",
                    "message": "Email Not Found",
                    "error": "false",
                });
            }
            const getUserquery = this.artistService.updatePassword(req.body.email, req.body.userType, req.body.newPassword);
            this.sendResponse(getUserquery, res)


        });
    }
    public getAllArtistOnThebehalOfLikeDislike(req: Request, res: Response) {

        this.artistService.getAllArtistOnThebehalOfLike(req).subscribe((user1) => {
            if (_.isEmpty(user1)) {
                return res.json({
                    "status": "false",
                    "message": "Email Not Found",
                    "error": "false",
                });
            } else {
                this.artistService.getAllArtistOnThebehalOfDisLike(req).subscribe((dislike) => {
                    if (_.isEmpty(dislike)) {
                        return res.json({
                            "status": "false",
                            "message": "Email Not Found",
                            "error": "false",
                        });
                    } else {
                        res.json({
                            "status": "true",
                            "message": "All Artist Data on The Behalf Of Like DisLike",
                            "error": "true",
                            "LikedArtistMost": user1,
                            "disLikeArtistMost": dislike
                        })
                    }
                })
            }
        });
    }


}
