const Sequelizecomment = require('sequelize');
const ArtistSongCommentModel = (sequelize, type) => {
    return sequelize.define('ArtistSongCommentModel', {
        ArtistSongId: {
            type: Sequelizecomment.STRING,
            field: 'artistSongId' 
        },
        UserId: {
            type: Sequelizecomment.STRING,
            field: 'userId' 
        },
        CommentBody: {
            type: Sequelizecomment.STRING,
            field: 'commentbody' 
        }, 
        ArtistId: {
            type: Sequelizecomment.STRING,
            field: 'artistId' 
        },CommentTime: {
            type: Sequelizecomment.STRING,
            field: 'CommentTime' 
        }
    },{
        freezeTableName: true 
    })
}
module.exports = {
    ArtistSongCommentModel
}