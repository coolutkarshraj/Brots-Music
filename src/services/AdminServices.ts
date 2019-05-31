import {ServiceBase} from "./common/ServiceBase";
import * as _ from "lodash";
import { EmailService } from '../services/EmailServices';
import { Config } from "../Config";
import * as Rx from "rxjs/Rx";
const uploadcategory = require('../s3Services/uploadcategory');
const uploadcategories = uploadcategory.single('image');

export class AdminServices extends ServiceBase {
    private emailService: EmailService;
    constructor() {
        super();
        this.emailService = new EmailService();
       
    }

    public sendlogggedInMail(email,name,city,country,address) {
        const userEmail = email;
            const emailData = {
                email: userEmail,
                subject: 'Welcome To High Mountains',
            };
            const templateModal = {
                name: name,
                City: city+" "+ country,
                email:email,
                address:address
            };
            this.emailService.sendMail(emailData, templateModal, Config.mailTemplate.loggedInMail); 
    }

    public addContinent(req: Request, res: Response) {
      
    }

    public uploadAdminSongBasicDetails(req, res): Rx.Observable<any> {
        const promise = new Promise((resolve, reject) => {
            uploadcategories (req, res, function (err) {
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

            })


        });
        return Rx.Observable.fromPromise(promise);
    }

    public addCountry(req: Request, res: Response) {
      
    }
    public addStates(req: Request, res: Response) {
      
    }
    public addcities(req: Request, res: Response) {
      
    }
    public addTowns(req: Request, res: Response) {
      
    }
    public addSpringFieldData(req: Request, res: Response) {
      
    }
}
