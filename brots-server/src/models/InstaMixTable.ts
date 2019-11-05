const SequelizeInstaMix = require('sequelize');
const InstaMixTable = (sequelize, type) => {
    return sequelize.define('InstaMixModel', {
        Name: {
            type: SequelizeInstaMix.STRING,
            field: 'name' 
        },
        SongName: {
            type: SequelizeInstaMix.STRING,
            field: 'song_name' 
        },
        ArtistId: {
            type: SequelizeInstaMix.STRING,
            field: 'artist_id' 
        },
        FullSongImageUrl: {
            type: SequelizeInstaMix.STRING,
            field: 'full_song_url' 
        },
        ArtistName: {
            type: SequelizeInstaMix.STRING,
            field: 'artist_name' 
        },
        Duration: {
            type: SequelizeInstaMix.STRING,
            field: 'songduration' 
        },
        ImageUrl: {
            type: SequelizeInstaMix.STRING,
            field: 'song_img_url' 
        },
        AlbumName: {
            type: SequelizeInstaMix.STRING,
            field: 'album_name' 
        },
        SongId: {
            type: SequelizeInstaMix.INTEGER,
            field:'songId'
        }, 
        UserId: {
            type: SequelizeInstaMix.INTEGER,
            field:'userId'
        },
        BannerImage: {
            type: SequelizeInstaMix.STRING,
            field:'bannerImage'
        },Memory: {
            type: SequelizeInstaMix.INTEGER,
            field:'memory'
        }
    },{
        freezeTableName: true 
    })
}

module.exports = {
    InstaMixTable
}