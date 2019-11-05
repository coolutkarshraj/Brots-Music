const SequilizesukhmehalModel = require('sequelize');
const sukhmehalModel = (sequelize, type) => {
    return sequelize.define('sukhmehalModel', {
        categoryId: {
            type: SequilizesukhmehalModel.STRING,
            field: 'categoryId' 
        },
        imageUrl: {
            type: SequilizesukhmehalModel.STRING,
            field: 'imageUrl' 
        },
        
    },{
        freezeTableName: true 
    })
}

module.exports = {
    sukhmehalModel
}