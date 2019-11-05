const Sequelize = require('sequelize');


const UserModel = (sequelize, type) => {
    return sequelize.define('user', {
        Name: {
            type: Sequelize.STRING,
            field: 'name' 
        },
        FirstName: {
            type: Sequelize.STRING,
            field: 'firstName' 
        },
        MiddileName: {
            type: Sequelize.STRING,
            field: 'middleName' 
        },
        LastName: {
            type: Sequelize.STRING,
            field: 'lastName' 
        },
        Email: {
            type: Sequelize.STRING,
            field: 'email' 
        },
        Gender: {
            type: Sequelize.STRING,
            field: 'gender' 
        },
        DOB: {
            type: Sequelize.STRING,
            field: 'dob' 
        },
        imageUrl: {
            type: Sequelize.STRING,
            field:'imageUrl'
        },
        
        Password: {
            type: Sequelize.STRING,
            field: 'password' 
        },
        LastLogin: {
            type: Sequelize.STRING,
            field: 'lastLogin' 
        },
        City: {
            type: Sequelize.STRING,
            field: 'city' 
        },
        State: {
            type: Sequelize.STRING,
            field: 'state' 
        },
        Country: {
            type: Sequelize.STRING,
            field: 'country' 
        },
        Address: {
            type: Sequelize.STRING,
            field: 'address' 
        },
    
        UserType: {
            type: Sequelize.STRING,
            field: 'userType' 
        },
        DeviceToken: {
            type: Sequelize.STRING,
            field: 'deviceToken' 
        },
        Status: {
            type: Sequelize.STRING,
            field: 'status' 
        },
        IsLoggedIn: {
            type: Sequelize.STRING,
            field: 'isLoggedIn' 
        },
        IsTncAccepted: {
            type: Sequelize.INTEGER,
            field: 'isTncAccepted' 
        },
        RegistrrationDate: {
            type: Sequelize.STRING,
            field:'registratioDate'
        },
        DeviceType: {
            type: Sequelize.STRING,
            field: 'deviceType' 
        },
        OnlineStatus: {
            type:Sequelize.INTEGER,
            field: 'onlineStatus' 
        },
        friendCount: {
            type: Sequelize.INTEGER,
            field: 'friendCount' ,
            defaultValue:0
        },
        followingCount:{
            type: Sequelize.INTEGER,
            field: 'followingCount' ,
            defaultValue:0
        },
        phone: {
            type: Sequelize.STRING,
            field: 'phone' 
        },
        About: {
            type: Sequelize.STRING,
            field: 'about' 
        },
         SavedSongMemory: {
            type: Sequelize.INTEGER,
            field: 'savedSongMemory' 
        },
        InstaMixMemory: {
            type: Sequelize.INTEGER,
            field: 'instamixMemory' 
        },
        TotalLike: {
            type: Sequelize.INTEGER,
            field: 'total_like' 
        },
        TotalDislike: {
            type: Sequelize.INTEGER,
            field: 'total_disLike' 
        },
       
    },{
        freezeTableName: true 
    })
}

const ArtistModel = (sequelize, type) => {
    return sequelize.define('artist', {
        Name: {
            type: Sequelize.STRING,
            field: 'name' 
        },
        FirstName: {
            type: Sequelize.STRING,
            field: 'firstName' 
        },
        MiddileName: {
            type: Sequelize.STRING,
            field: 'middleName' 
        },
        LastName: {
            type: Sequelize.STRING,
            field: 'lastName' 
        },
        Email: {
            type: Sequelize.STRING,
            field: 'email' 
        },
        Gender: {
            type: Sequelize.STRING,
            field: 'gender' 
        },
        DOB: {
            type: Sequelize.STRING,
            field: 'dob' 
        },
        imageUrl: {
            type: Sequelize.STRING,
            field:'imageUrl'
        },
        Password: {
            type: Sequelize.STRING,
            field: 'password' 
        },
        LastLogin: {
            type: Sequelize.STRING,
            field: 'lastLogin' 
        },
        City: {
            type: Sequelize.STRING,
            field: 'city' 
        },
        State: {
            type: Sequelize.STRING,
            field: 'state' 
        },
        Country: {
            type: Sequelize.STRING,
            field: 'country' 
        },
        Address: {
            type: type.STRING,
            field: 'address' 
        },
    
        UserType: {
            type: Sequelize.STRING,
            field: 'userType' 
        },
        DeviceToken: {
            type: Sequelize.STRING,
            field: 'deviceToken' 
        },
        Status: {
            type: Sequelize.STRING,
            field: 'status' 
        },
        IsLoggedIn: {
            type: Sequelize.STRING,
            field: 'isLoggedIn' 
        },
        IsTncAccepted: {
            type: Sequelize.INTEGER,
            field: 'isTncAccepted' 
        },
        RegistrrationDate: {
            type: Sequelize.STRING,
            field:'registratioDate'
        },
        DeviceType: {
            type: Sequelize.STRING,
            field: 'deviceType' 
        },
        OnlineStatus: {
            type: Sequelize.INTEGER,
            field: 'onlineStatus' 
        },
        phone: {
            type: Sequelize.STRING,
            field: 'phone' 
        },
        AddNote: {
            type: Sequelize.STRING,
            field: 'addnote' 
        },
        followerCount: {
            type: Sequelize.STRING,
            field: 'followerCount' ,
            defaultValue:0
        },
        About: {
            type: Sequelize.STRING,
            field: 'about' 
        }, 
        
        AddNoteDate: {
            type: Sequelize.STRING,
            field:'addnoteDate'
        }, 
        SavedSongMemory: {
            type: Sequelize.INTEGER,
            field: 'savedSongMemory' 
        },
        TotalLike: {
            type: Sequelize.INTEGER,
            field: 'total_like' 
        },
        TotalDislike: {
            type: Sequelize.INTEGER,
            field: 'total_disLike' 
        }, InstaMixMemory: {
            type: Sequelize.INTEGER,
            field: 'instamixMemory' 
        },   
    },{
        freezeTableName: true 
    })
}

const AdminModel = (sequelize, type) => {
    return sequelize.define('admin', {
        Name: {
            type: Sequelize.STRING,
            field: 'name' 
        },
        FirstName: {
            type: Sequelize.STRING,
            field: 'firstName' 
        },
        MiddileName: {
            type: Sequelize.STRING,
            field: 'middleName' 
        },
        LastName: {
            type: Sequelize.STRING,
            field: 'lastName' 
        },
        Email: {
            type: Sequelize.STRING,
            field: 'email' 
        },
        Gender: {
            type: Sequelize.STRING,
            field: 'gender' 
        },
        DOB: {
            type: Sequelize.STRING,
            field: 'dob' 
        },
        imageUrl: {
            type: Sequelize.STRING,
            field:'imageUrl'
        },
        Password: {
            type: Sequelize.STRING,
            field: 'password' 
        },
        LastLogin: {
            type: Sequelize.STRING,
            field: 'lastLogin' 
        },
        City: {
            type: Sequelize.STRING,
            field: 'city' 
        },
        State: {
            type: Sequelize.STRING,
            field: 'state' 
        },
        Country: {
            type: Sequelize.STRING,
            field: 'country' 
        },
        Address: {
            type: Sequelize.STRING,
            field: 'address' 
        },
    
        UserType: {
            type: Sequelize.STRING,
            field: 'userType' 
        },
        DeviceToken: {
            type: Sequelize.STRING,
            field: 'deviceToken' 
        },
        Status: {
            type: Sequelize.STRING,
            field: 'status' 
        },
        IsLoggedIn: {
            type: Sequelize.STRING,
            field: 'isLoggedIn' 
        },
        IsTncAccepted: {
            type: Sequelize.INTEGER,
            field: 'isTncAccepted' 
        },
        RegistrrationDate: {
            type: type.STRING,
            field:'registratioDate'
        },
        DeviceType: {
            type: Sequelize.STRING,
            field: 'deviceType' 
        },
        OnlineStatus: {
            type: Sequelize.INTEGER,
            field: 'onlineStatus' 
        },
        Phone: {
            type: Sequelize.STRING,
            field: 'phone' 
        },
        About: {
            type: Sequelize.STRING,
            field: 'about' 
        },
          
    },{
        freezeTableName: true 
    })
}


/**
 * Friends Through Model 
*/
const FriendModel = (sequelize, type) => {
    return sequelize.define('friend', {
        friendId:{
            type: type.INTEGER,
            autoIncrement: true,
            primaryKey: true
        },
        Status: {
            type:   type.STRING,
        },
    })
}

/**
 * follower Through Model 
*/
const FollowerModel = (sequelize, type) => {
    return sequelize.define('follow', {
        followId:{
            type: type.INTEGER,
            autoIncrement: true,
            primaryKey: true
        },
        Status: {
            type:   type.STRING,
            defaultValue:"Follow"
        },
    })
}



module.exports = {
    UserModel,
    FriendModel,
    ArtistModel,
    AdminModel,
    FollowerModel
}

