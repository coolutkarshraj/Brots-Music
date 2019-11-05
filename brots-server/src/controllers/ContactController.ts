import { Request, Response } from "express";
import { BaseController } from "./common/BaseController";
import { SearchFriendServices } from "../services/SearchFriendServices";
import * as _ from "lodash";
import * as __ from "underscore";
import { ContactServices } from '../services/ContactServices';

export class ContactController extends BaseController {
    private contactServicer :ContactServices;
   

    constructor() {
        super();

        this.contactServicer =  new ContactServices();
      
    }

    
    public SentContactRequest(req: Request, res: Response) {
        if(req.body.email == null ){
            return res.json({
                "status":"false",
                "message":"Missing Parameter email",
                "error":"false",  
            });  
        }else if(req.body.title == null){
            return res.json({
                "status":"false",
                "message":"Missing Parameter title",
                "error":"false",  
            });  
        }else if(req.body.user_Id == null){
            return res.json({
                "status":"false",
                "message":"Missing Parameter user_Id",
                "error":"false",  
            });  
        }else if(req.body.messageBody == null){
            return res.json({
                "status":"false",
                "message":"Missing Parameter messageBody",
                "error":"false",  
            });  
        }else if(req.body.user_name == null){
            return res.json({
                "status":"false",
                "message":"Missing Parameter user_name",
                "error":"false",  
            });  
        }
        const contactform = this.contactServicer.putContactInformation(req);
        this.sendResponse(contactform,res)
    }

   


}
