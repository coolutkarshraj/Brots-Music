const Sequeliz = require('sequelize');
const AdminSaveSongModel = (sequelize, type) => {
    return sequelize.define('AdminSavesong', {
        Name: {
            type: Sequeliz.STRING,
            field: 'name' 
        },
        SongName: {
            type: Sequeliz.STRING,
            field: 'song_name' 
        },
        ArtistId: {
            type: Sequeliz.STRING,
            field: 'artist_id' 
        },
        FullSongImageUrl: {
            type: Sequeliz.STRING,
            field: 'full_song_url' 
        },
        ArtistName: {
            type: Sequeliz.STRING,
            field: 'artist_name' 
        },
        Duration: {
            type: Sequeliz.STRING,
            field: 'songduration' 
        },
        ImageUrl: {
            type: Sequeliz.STRING,
            field: 'song_img_url' 
        },
        AlbumName: {
            type: Sequeliz.STRING,
            field: 'album_name' 
        },
        SongId: {
            type: Sequeliz.INTEGER,
            field:'songId'
        }, 
        UserId: {
            type: Sequeliz.INTEGER,
            field:'userId'
        },
        BannerImage: {
            type: Sequeliz.STRING,
            field:'bannerImage'
        },Memory: {
            type: Sequeliz.INTEGER,
            field:'memory'
        },IsDeleted: {
            type: Sequeliz.INTEGER,
            field:'isDeleted'
        }
    },{
        freezeTableName: true 
    })
}
const AdminSaveSongThrough = (sequelize,type) => {
    return sequelize.define('AdminSaveSongThrough',{
        isSaved: {
            type: Sequeliz.STRING,
            field: 'IsSaved',
            defaultValue:0 
        }
    })
}

module.exports = {
    AdminSaveSongThrough,
    AdminSaveSongModel
}