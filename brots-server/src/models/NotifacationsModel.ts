const SequilizeNotification = require('sequelize');
const NotifacationsModel = (sequelize, type) => {
    return sequelize.define('NotifacationsModel', {
        Name: {
            type: SequilizeNotification.STRING,
            field: 'name' 
        },
        Title: {
            type: SequilizeNotification.STRING,
            field: 'title' 
        },
        NotificationTime: {
            type: SequilizeNotification.STRING,
            field: 'NotificationTime' 
        },
        Description: {
            type: SequilizeNotification.STRING,
            field: 'Description' 
        },
        userId: {
            type: SequilizeNotification.STRING,
            field: 'userId' 
        },
        userType: {
            type: SequilizeNotification.STRING,
            field: 'userType' 
        },
        img_url: {
            type: SequilizeNotification.STRING,
            field: 'img_url' 
        },
       
    },{
        freezeTableName: true 
    })
}

module.exports = {
    NotifacationsModel
}