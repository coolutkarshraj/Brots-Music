import { 
    sendFriendRequest,
    acceptFriendRequest,
    rejectFriendRequest,
    allSendPendingRequest,
    allReceivedPendingRequest,
    allAcceptedFriends,
    removeFriend,
    followArtist,
    getAllFollowers,
    getAllFollowing,
    unFollowArtist
} from "../../category/user-controller";
import {Request, Response} from "express";
const userRoute =  require('express').Router();

/**
 * @api {post} /send-friend-request Send Friend Request
 * @apiName SendFriendRequest
 * @apiGroup User
 * @apiPermission user
 * @apiParam {Params} senderId  
 * @apiParam {Params} receiverId  
 * @apiUse listParams
 * @apiSuccess Create Friend Through model.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 User access only.
 */
userRoute.post('/send-friend-request', (req:Request, res:Response) => {
    sendFriendRequest(req,res)
    .then((result)=>{
        res.send({
            "success":"true",
            "msg":"Friend request sent",
            "error":"true"
        })
    })
    .catch((err)=>{
        console.log("Err is:", err)
        res.send(err)
    })
});

/**
 * @api {post} /accept-friend-request Send Friend Request
 * @apiName AcceptFriendRequest
 * @apiGroup User
 * @apiPermission user
 * @apiParam {Params} friendRequestID  
 * @apiUse listParams
 * @apiSuccess Update Friend Through model and Sender and Receiver Model.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 User access only.
 */
userRoute.post('/accept-friend-request', (req:Request, res:Response) => {
    acceptFriendRequest(req,res)
    .then((result)=>{
        res.json(result)
    })
    .catch((error)=>{
        res.json(error)
    }) 
    
});

/**
 * @api {post} /reject-friend-request Send Friend Request
 * @apiName AcceptFriendRequest
 * @apiGroup User
 * @apiPermission user
 * @apiParam {Params} friendRequestID  
 * @apiUse listParams
 * @apiSuccess Update Friend Through model and Sender and Receiver Model.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 User access only.
 */
userRoute.post('/reject-friend-request', (req:Request, res:Response) => {
    rejectFriendRequest(req, res)
    .then((result)=>{
        console.log('fdjhgbfdhghfdhhfdhfjkdhfdhghjfdhjf')
     if(result == 0){
        let val = {
            "success":"false",
            "message":"No Such Friend Request Found" ,
            "error":"false"
        }
        res.json(val)
     }else{
        let val = {
            "success":"true",
            "message":"Friend Request rejected" ,
            "error":"true"
        }
        res.json(val)
     }
  
    })
    .catch((error)=>{
        let val = {
            "success":"false",
            "message":"Friend Request not rejected" ,
            "error":"false"
        }
        res.json(val)
    }) 
    
});

/**
 * @api {post} /send-pending-friendlist Send Friend Request
 * @apiName AcceptFriendRequest
 * @apiGroup User
 * @apiPermission user
 * @apiParam {Params} friendRequestID  
 * @apiUse listParams
 * @apiSuccess Update Friend Through model and Sender and Receiver Model.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 User access only.
 */
userRoute.post('/send-pending-friendlist', (req:Request, res:Response) => {
    allSendPendingRequest(req, res)
    .then((result)=>{
        res.json({
            "success":"true",
            "message":"List of send Pending Friend List" ,
            "error":"true",
            "data":result   
        })
    })
    .catch((error)=>{
        console.log("Error is:",error)
        let val = {
            "success":"false",
            "message":"No send Pending Request Found Regarding this id" ,
            "error":"false"
            
        }
        res.json(val)
    }) 
    
});

/**
 * @api {post} /received-pending-friendlist Send Friend Request
 * @apiName AcceptFriendRequest
 * @apiGroup User
 * @apiPermission user
 * @apiParam {Params} friendRequestID  
 * @apiUse listParams
 * @apiSuccess Update Friend Through model and Sender and Receiver Model.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 User access only.
 */
userRoute.post('/received-pending-friendlist', (req:Request, res:Response) => {
    allReceivedPendingRequest(req, res)
    .then((result)=>{
        res.json({
            "success":"true",
            "message":"List of Pending Friend List" ,
            "error":"true",
            "data":result   
        })
    })
    .catch((error)=>{
        console.log("Error is:",error)
        let val = {
            "success":"false",
            "message":"No Pending Request Found Regarding this id" ,
            "error":"false"
        }
        res.json(val)
    }) 
    
});

/**
 * @api {post} /all-friendlist Send Friend Request
 * @apiName AcceptFriendRequest
 * @apiGroup User
 * @apiPermission user
 * @apiParam {Params} friendRequestID  
 * @apiUse listParams
 * @apiSuccess Update Friend Through model and Sender and Receiver Model.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 User access only.
 */
userRoute.post('/all-friendlist', (req:Request, res:Response) => {
    allAcceptedFriends(req, res)
    .then((result)=>{
        res.json({
            "success":"true",
            "message":"List of All Friend" ,
            "error":"true",
            "data":result    
        })
    })
    .catch((error)=>{
        console.log("Error is:",error)
        let val = {
            "success":"false",
            "message":"No Friend List Regarding this id" ,
            "error":"false"
        }
        res.json(val)
    }) 
    
});

/**
 * @api {post} /remove-friend Send Friend Request
 * @apiName AcceptFriendRequest
 * @apiGroup User
 * @apiPermission user
 * @apiParam {Params} friendRequestID  
 * @apiUse listParams
 * @apiSuccess Update Friend Through model and Sender and Receiver Model.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 User access only.
 */
userRoute.post('/remove-friend', (req:Request, res:Response) => {
    removeFriend(req, res)
    .then((result)=>{
        let val = {
            "success":"true",
            "message":"Friend Removed SuccessFully",
            "error":"true"
        }
        res.json(val)
    })
    .catch((error)=>{
        console.log("Error is:",error)
        let val = {
            "success":"false",
            "message":"Friend Not Removed No Such Friend Found",
            "error":"false"
        }
        res.json(val)
    }) 
    
});

/**
 * @api {post} /follow-artist Send Friend Request
 * @apiName FollowArtist
 * @apiGroup User
 * @apiPermission user
 * @apiParam {Params} friendRequestID  
 * @apiUse listParams
 * @apiSuccess Update Friend Through model and Sender and Receiver Model.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 User access only.
 */
userRoute.post('/follow-artist', (req:Request, res:Response) => {
    followArtist(req, res)
    .then((result)=>{
        
        let val = {
            "success":"true",
            "message":"You have succesfully followed Artist",
            "errorr":"true"
        }
        res.json(val)
    })
    .catch((error)=>{
        console.log("Error is:",error)
        let val = {
            "success":"false",
            "message":"You have not followe artist . Something Went Wrong",
            "error":"false"
        }
        res.json(val)
    }) 
    
});


/**
 * @api {post} /all-followers Send Friend Request
 * @apiName FollowArtist
 * @apiGroup User
 * @apiPermission user
 * @apiParam {Params} friendRequestID  
 * @apiUse listParams
 * @apiSuccess Update Friend Through model and Sender and Receiver Model.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 User access only.
 */
userRoute.post('/all-followers', (req:Request, res:Response) => {
    getAllFollowers(req, res)
    .then((result)=>{
        
        res.json({
            "success":"true",
            "message":"List of All Follower" ,
            "error":"true",
            "data":result
        })
    })
    .catch((error)=>{
        console.log("Error is:",error)
        let val = {
            "success":"false",
            "message":"No such follower found regarding this id" ,
            "error":"false"        }
        res.json(val)
    }) 
    
});


/**
 * @api {post} /all-following Send Friend Request
 * @apiName FollowArtist
 * @apiGroup User
 * @apiPermission user
 * @apiParam {Params} friendRequestID  
 * @apiUse listParams
 * @apiSuccess Update Friend Through model and Sender and Receiver Model.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 User access only.
 */
userRoute.post('/all-following', (req:Request, res:Response) => {
    getAllFollowing(req,res)
    .then((result)=>{
        
        res.json({
            "success":"true",
            "message":"List of All Following" ,
            "error":"true",
            "data":result
        })
    })
    .catch((error)=>{
        console.log("Error is:",error)
        let val = {
            "success":"false",
            "message":"No such following found regarding this id" ,
            "error":"false"   
        }
        res.json(val)
    }) 
    
});

/**
 * @api {post} /all-following Send Friend Request
 * @apiName FollowArtist
 * @apiGroup User
 * @apiPermission user
 * @apiParam {Params} friendRequestID  
 * @apiUse listParams
 * @apiSuccess Update Friend Through model and Sender and Receiver Model.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 User access only.
 */
userRoute.post('/unfollow', (req:Request, res:Response) => {
    unFollowArtist(req,res)
    .then((result)=>{
        let val = {
            "success":"true",
            "message":"successfully un-Followed the Artist",
            "error":"true"
        }
        res.json(val)
    })
    .catch((error)=>{
        console.log("Error is:",error)
        let val = {
            "success":"false",
            "message":"Not successfully un-followed Artist",
            "error":"false"
        }
        res.json(val)
    }) 
    
});




module.exports = userRoute
