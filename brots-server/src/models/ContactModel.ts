const SequilizeContactModel = require('sequelize');
const ContactModel = (sequelize, type) => {
    return sequelize.define('ContactModel', {
        Email: {
            type: SequilizeContactModel.STRING,
            field: 'email' 
        },
        Title: {
            type: SequilizeContactModel.STRING,
            field: 'title' 
        },
        userId: {
            type: SequilizeContactModel.STRING,
            field: 'user_Id' 
        },
        Body: {
            type: SequilizeContactModel.STRING,
            field: 'messageBody' 
        },
        Name: {
            type: SequilizeContactModel.STRING,
            field: 'user_name' 
        },
        UserType: {
            type: SequilizeContactModel.STRING,
            field: 'userType' 
        },
        
    },{
        freezeTableName: true 
    })
}

module.exports = {
    ContactModel
}