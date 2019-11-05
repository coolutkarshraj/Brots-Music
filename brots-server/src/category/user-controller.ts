const {user, Friend,  Artist, Follower,sequelize } =require("./sqlService")
const Sequelize = require('sequelize');
import { FriendStatus } from "./constant";


export const sendFriendRequest = (req, res) => {
    let senderId = req.body.senderId ;
    let receiverId = req.body.receiverId ;
    return checkAlreadyCreated(senderId,receiverId)
    .then((result)=>{
        console.log('djskfhdkjhfdjhfkljdhfiudhfdkjfhdk')
        console.log(result)
        if (result!=null){
            let promise = new Promise((resolve, reject)=>{
                let val = {
                    "success":"false",
                    "msg":"Already have Friend Request from this User" ,
                    "error":"false"
                }
                reject(val)
            }) 
            return promise 
        }else{
            return Friend.create({senderId:senderId, receiverId:receiverId,Status:FriendStatus[0]})
        }
    })
    .catch((error)=>{
        console.log("Send Request Create error:",error)
        let promise = new Promise((resolve, reject)=>{
            if (error["success"]=="false"){
                reject(error)  
            } else{
                let val = {
                    "success":"false",
                    "msg":"Friend Request not sent",
                    "error":"false"
                }
                reject(val)
            }
            
        }) 
        return promise
    })
    
}

const checkAlreadyCreated = (senderId,receiverId)=>{
    return Friend.findOne({where:{senderId:senderId, receiverId:receiverId}})
}


export const acceptFriendRequest = (req, res) => {
    let friendRequestId = req.body.friendRequestId ;
    return Friend.findOne({where: {friendId: friendRequestId,Status:FriendStatus[1]}})
    .then((res)=>{
        if (res!=null){
            let promise = new Promise((resolve, reject)=>{
                let val = {
                    "success":"false",
                    "msg":"Friend Request already accpeted" ,
                     "error":"false"
                }
                resolve(val)
            }) 
            return promise
        }else{
            return Friend.update({Status:FriendStatus[1]}, { where:{
                friendId: friendRequestId,
                }
            })
            .then((friend)=>{
                return updateUserFriendCount(friendRequestId)
                .then((result)=>{
                    let promise = new Promise((resolve, reject)=>{
                        let val = {
                            "success":"true",
                            "msg":"Friend Request accpeted" ,
                             "error":"true"
                        }
                        resolve(val)
                    }) 
                    return promise
                })
                .catch((result)=>{
                    let promise = new Promise((resolve, reject)=>{
                        reject(result)
                    }) 
                    return promise
                })
            })
            .catch((error)=>{
                console.log("Inside this Error")
                let promise = new Promise((resolve, reject)=>{
                    let val = {
                        "success":"false",
                        "msg":"No Such Friend Request Found" ,
                        "error":"False"
                    }
                    reject(val)
                }) 
                return promise
            })
            .then((result)=>{
                console.log("Inside this 1")
                let promise = new Promise((resolve, reject)=>{
                   resolve(result)
                }) 
                return promise
            })
            .catch((error)=>{
                console.log("Inside this Error 1")
                let promise = new Promise((resolve, reject)=>{
                    reject(error)
                }) 
                return promise
            })
        }
        
    })
    .catch((error)=>{
        console.log("error is:", error)
        let promise = new Promise((resolve, reject)=>{
            reject(error)
        }) 
        return promise
        
    })  
    .then((result)=>{
        console.log("Result is:", result)
        let promise = new Promise((resolve, reject)=>{
            resolve(result)
        }) 
        return promise
    })  
}

const updateUserFriendCount  = (friendId)=>{
    
    return Friend.findById(friendId)
    .then((result)=>{
        let senderId = result['dataValues']['senderId']
        let receiverId = result['dataValues']['receiverId']
        console.log("Id is :",senderId, receiverId)
        user.update({friendCount:Sequelize.literal('friendCount + 1')}, 
        { where:
            {
                id: senderId
            }
        })
        user.update({friendCount:Sequelize.literal('friendCount + 1')}, 
        { where:
            {
                id: receiverId
            }
        })
        let promise = new Promise((resolve, reject)=>{
            let val = {
                "success":true,
                "msg":"Friend Request accpeted" 
            }
            resolve(val)
        })
        return promise
    })
    .error(()=>{
        let promise = new Promise((resolve, reject)=>{
            let val = {
                "success":false,
                "msg":"Friend Request not accpeted" 
            }
            reject(val)
        })
        return promise
    })
} 

export const rejectFriendRequest = (req, res) => {
    let friendId = req.body.friendId ;
    return Friend.destroy({
        where: {
            friendId: friendId,
            Status: FriendStatus[0]
        }
    })
}

export const allSendPendingRequest = (req, res) => {
    let userId = req.body.userId ;
    return user.findOne({where:{id:userId}, attributes: ['id', 'Name','createdAt'],
        include: [{
            model: user,
            as: 'sender',
            through:{ where: { Status: FriendStatus[0], senderId:userId } 
                ,attributes: ['friendId', 'receiverId','senderId']  },
            attributes: ['id', 'Name'],
            require:false  
        }]
    })
    .then((user)=>{
        let friends:any[] = []
        for (const friend of user["dataValues"]["sender"]) {
            friends.push(friend)
        }
        return friends
    })
    
}

export const allReceivedPendingRequest = (req, res) => {
    let userId = req.body.userId ;
    return user.findOne({where:{id:userId}, attributes: ['id', 'Name','createdAt'],
        include: [
        {
            model: user,
            as: 'receiver',
            through:{ where: { Status: FriendStatus[0], receiverId:userId }
                ,attributes: ['friendId', 'receiverId','senderId'] },
            attributes: ['id', 'Name'],
            require:false  
        }]
    })
    .then((user)=>{
        let friends:any[] = []
        for (const friend of user["dataValues"]["receiver"]) {
            friends.push(friend)
        }
        return friends
    })
    
}

export const allAcceptedFriends = (req, res) => {
    let userId = req.body.userId ;
    return user.find({where:{id:userId}, attributes: ['id', 'Name','createdAt'],
        include: [{
            model: user,
            as: 'sender',
            through:{ where: { Status: FriendStatus[1] } ,attributes: ['friendId', 'receiverId','senderId']},
            attributes: ['id', 'Name','email','gender','dob','imageUrl','city','country','phone'],
            require:false  
        },
        {
            model: user,
            as: 'receiver',
            through:{ where: { Status: FriendStatus[1] } ,attributes: ['friendId', 'receiverId','senderId']},
            attributes: ['id', 'Name'],
            require:false  
        }]
    })
    .then((user)=>{
        let friends:any[] = []
        for (const friend of user["dataValues"]["sender"]) {
            friends.push(friend)
        }
        for (const friend of user["dataValues"]["receiver"]) {
            friends.push(friend)
        }
        return friends
    })
}

export const removeFriend = (req, res) => {
    let friendRequestId = req.body.friendRequestId ;

    return decrementFriendCount(friendRequestId)
    .then((result)=>{
        if (result==true){
            return Friend.destroy({
                where: {
                    friendId: friendRequestId
                }
            })
        }else{
            let promise = new Promise((resolve, reject)=>{
                let val = {
                    "success":false,
                }
                reject(val)
            }) 
            return promise 
        }
    })
    
}

export const decrementFriendCount = (friendId)=>{

    return Friend.findById(friendId)
    .then((result)=>{
        let senderId = result['dataValues']['senderId']
        let receiverId = result['dataValues']['receiverId']

        user.findById(senderId)
        .then((user)=>{
            user.decrement('friendCount',{by: 1}) 
        })
        
        user.findById(receiverId)
        .then((user)=>{
            user.decrement('friendCount',{by: 1}) 
        })
        
        let promise = new Promise((resolve, reject)=>{
            resolve(true)
        })
        return promise
    })
    .error(()=>{
        let promise = new Promise((resolve, reject)=>{
            resolve(false)
        })
        return promise
    })
}

export const followArtist = (req, res) =>{

    let userId = req.body.userId ;
    let artistId = req.body.artistId ;
    return sequelize.transaction(function(t) {
        return checkAlreadyFollowed(userId, artistId)
        .then((result)=>{
            if (result==true){
                return Follower.create({userId:userId, artistId:artistId})
                .then((result)=>{
                    console.log("Result is:",result["dataValues"])
                    return incrementFollowCount(result["dataValues"]["followId"])
                })
            }else{
                let promise = new Promise((resolve, reject)=>{
                    reject(false)
                })
                return promise
            }
        })
    })
    
    
}

const checkAlreadyFollowed = (userId, artistId)=>{
    return Follower.findOne({where:{userId:userId, artistId:artistId}}) 
    .then((result)=>{
        if (result==null){
            return true
        }else{
            return false
        }
    })
}


export const getAllFollowers = (req,res)=>{

    let artistId = req.body.artistId;
    return sequelize.transaction(function(params) {
        return Artist.findOne({where:{id:artistId}, attributes: ['id', 'Name','createdAt'],
            include: [{
                model: user,
                as: 'followers',
                attributes: ['id', 'Name','email','gender','dob','imageUrl','city','country','phone'],
                through:{ attributes: ['followId', 'userId','artistId']},
                require:false  
            }]
        })
        .then((user)=>{
            let followers:any[] = []
            for (const follower of user["dataValues"]["followers"]) {
                followers.push(follower)
            }
            return followers
        })
    })
}


export const getAllFollowing = (req,res)=>{

    let userId = req.body.userId ;
    return sequelize.transaction(function(params) {
        return user.findOne({where:{id:userId}, 
            attributes: 
            
            [['id', 'Name','email','gender','dob','imageUrl','city','country','phone']],
            include: [{
                model: Artist,
                as: 'following',
                attributes: ['id', 'Name','email','gender','dob','imageUrl','city','country','phone'],
                through:{ attributes: ['followId', 'userId','artistId']},
                require:false  
            }]
        })
        .then((user)=>{
            let followings:any[] = []

            for (const following of user["dataValues"]["following"]) {
                followings.push(following)
            }
            return followings
        })
    })
    
}

export const unFollowArtist = (req, res) =>{

    let followId = req.body.followId
    return decrementFollowCount(followId)
    .then((result)=>{
        return Follower.destroy({where:{
            followId:followId
        }})
    })
    
}

export const incrementFollowCount = (followerId)=>{

    return Follower.findById(followerId)
    .then((result)=>{
        let userId = result['dataValues']['userId']
        let artistId = result['dataValues']['artistId']

        user.findById(userId)
        .then((user)=>{
            user.increment('followingCount',{by: 1}) 
        })
        
        Artist.findById(artistId)
        .then((artist)=>{
            artist.increment('followerCount',{by: 1}) 
        })
        
        let promise = new Promise((resolve, reject)=>{
            resolve(true)
        })
        return promise
    })
    .error(()=>{
        let promise = new Promise((resolve, reject)=>{
            resolve(false)
        })
        return promise
    })
}

export const decrementFollowCount = (followerId)=>{

    return Follower.findById(followerId)
    .then((result)=>{
        let userId = result['dataValues']['userId']
        let artistId = result['dataValues']['artistId']

        user.findById(userId)
        .then((user)=>{
            user.decrement('followingCount',{by: 1}) 
        })
        
        Artist.findById(artistId)
        .then((artist)=>{
            artist.decrement('followerCount',{by: 1}) 
        })
        
        let promise = new Promise((resolve, reject)=>{
            resolve(true)
        })
        return promise
    })
    .error(()=>{
        let promise = new Promise((resolve, reject)=>{
            resolve(false)
        })
        return promise
    })
}


