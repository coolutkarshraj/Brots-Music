import { type } from "os";

const Sequelize = require('sequelize');
const CategoryModel = (sequelize, type) => {
    return sequelize.define('category', {
        Name: {
            type: type.STRING,
            field: 'name'
        },
        Type: {
            type: type.STRING,
            field: 'type'
        }
    }, {
            freezeTableName: true
        })
}

const SubCategoryModel = (sequelize, type) => {
    return sequelize.define('subcategory', {
        Name: {
            type: type.STRING,
            field: 'name'
        },
        SubType: {
            type: type.STRING,
            field: 'type'
        }
    }, {
            freezeTableName: true
        })
}

const CategoryThroughModel = (sequelize) => {
    return sequelize.define('CategoryThrough')
}



const SongModel = (sequelize, type) => {
    return sequelize.define('song', {
        Name: {
            type: type.STRING,
            field: 'name'
        },
        SongName: {
            type: type.STRING,
            field: 'song_name'
        },
        ArtistName: {
            type: type.STRING,
            field: 'artist_name'
        },
        ArtistId: {
            type: type.STRING,
            field: 'artistId'
        },
        Duration: {
            type: type.STRING,
            field: 'songduration'
        },
        ImageUrl: {
            type: type.STRING,
            field: 'song_img_url'
        },
        FullSongUrl: {
            type: type.STRING,
            field: 'full_song_url'
        },
        AlbumName: {
            type: type.STRING,
            field: 'album_name'
        },
        Like: {
            type: type.INTEGER,
            field: 'like'
        },
        DisLike: {
            type: type.INTEGER,
            field: 'disLike'
        },
        CoverImage: {
            type: type.STRING,
            field: 'coverImage'
        }, Memory: {
            type: type.INTEGER,
            field: 'memory'
        },
        IsTrending: {
            type: type.STRING,
            field: 'isTrending'
        },
        IsDispopular: {
            type: type.STRING,
            field: 'isDispopular'
        }, 
        TrendingDate: {
            type: type.STRING,
            field: 'trendingDate'
        },
        SongHearCount:{
            type: type.INTEGER,
            field: 'SongHearCount'  

        },IsSongFullyUploaded :{
            type:type.STRING,
            field:'isSongFullyUploaded'
        }
    }, {
            freezeTableName: true
        })
}

const SongThroughModel = (sequelize, type) => {
    return sequelize.define('SongThrough', {
        Like: {
            type: Sequelize.INTEGER,
            field: 'like',
            defaultValue: 0
        },
        DisLike: {
            type: Sequelize.INTEGER,
            field: 'disLike',
            defaultValue: 0
        },
    })
}

const SonghearModel = (sequelize, type) => {
    return sequelize.define('songHearthrough', {
        IsSongHeared: {
            type: Sequelize.STRING,
            field: 'isSongHeared',
            defaultValue: 0
        },
      
    })
}



module.exports = {
    CategoryModel,
    SubCategoryModel,
    CategoryThroughModel,
    SongModel,
    SongThroughModel,
    SonghearModel
}
