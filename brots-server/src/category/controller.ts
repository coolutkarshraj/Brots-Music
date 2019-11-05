const { Category, SubCategory, Song , ArtistSong} = require("./sqlService")

export const getAllCategories = (req, res) => {

    return Category.findAll({attributes: ['id', 'Name','createdAt', 'Type'],
        include: {
            model: SubCategory,
            through: { attributes: ['id', 'Name','createdAt', 'SubType'] } 
        },
        
    })
  
}

export const getSubCategorySong = (req, res) => {
    let subcategoryId = req.query.subCategoryId
    let categoryId = req.query.categoryId
    if(subcategoryId == ''){
        return res.json({
            "status":"False",
            "message":"No Record sonf for this id",
            "error":"False",
           
        })
    }
    else if(categoryId == ''){
        return res.json({
            "status":"False",
            "message":"No Record sonf for this id",
            "error":"False",
           
        })
    }
    else if(subcategoryId == null){
        return res.json({
            "status":"False",
            "message":"No Record sonf for this id",
            "error":"False",
           
        })
    }
    else if(categoryId == null){
        return res.json({
            "status":"False",
            "message":"No Record sonf for this id",
            "error":"False",
           
        })
    }
    else if(subcategoryId == undefined){
        return res.json({
            "status":"False",
            "message":"No Record sonf for this id",
            "error":"False",
           
        })
    }
    else if(categoryId == undefined){
        return res.json({
            "status":"False",
            "message":"No Record sonf for this id",
            "error":"False",
           
        })
    }else{
        console.log("id is:", categoryId, subcategoryId)
        return Song.findAll({
            where: {
                categoryId:categoryId,
                subcategoryId: subcategoryId
            }
            ,attributes: ['id', 'Name','createdAt','FilePath']})
    }
     
}


export const uploadFile = (req, res)=>{
    
    let data = req.body ;
    let file = req.files[0]
    let categoryId = data['categoryId']
    let subCategortyId = data["subCategoryId"]
    let songName = data["song_name"]
    let songImageUrl = data["song_img_url"]
    let ArtistName = data["artist_name"]
    let duration = data["songduration"]
    let albumName = data["album_name"]
    console.log(subCategortyId)
    return Song.create({Name:file['originalname'],categoryId:categoryId, subcategoryId:subCategortyId, FilePath:file['path'],SongName:songName,ArtistName:ArtistName,Duration:duration,ImageUrl:songImageUrl,AlbumName:albumName})

}

export const uploadArtistFile = (req, res)=>{
    let data = req.body ;
    let file = req.files[0]
    let artistId = data['artistId']
    let categoryId = data['categoryId']
    let subCategortyId = data["subCategoryId"]
    let songName = data["song_name"]
    let songImageUrl = data["song_img_url"]
    let ArtistName = data["artist_name"]
    let duration = data["songduration"]
    let albumName = data["album_name"]
    console.log(subCategortyId)
    return ArtistSong.create({Name:file['originalname'],ArtistId:artistId,categoryId:categoryId, subcategoryId:subCategortyId, FilePath:file['path'],SongName:songName,ArtistName:ArtistName,Duration:duration,ImageUrl:songImageUrl,AlbumName:albumName})

}

export const getArtistSong = (req, res) => {
    let artistId = req.query.artistId
    console.log(artistId);
    console.log("id is:", artistId)
    return ArtistSong.findAll({
        where: {
            artistId:artistId,
           
        }
        ,attributes: ['id', 'Name','createdAt','FilePath','id', 'artistId','song_name','artist_name','songduration','song_img_url','album_name']}) 
}

export const downloadFile = (req, res)=>{
    let subcategoryId = req.query.subCategoryId
    let categoryId = req.query.categoryId
    let songId = req.query.songId
    console.log(songId, categoryId, subcategoryId)
    return Song.findAll({where: {
        id:songId,
        categoryId:categoryId,
        subcategoryId: subcategoryId
    },attributes: ['id', 'Name','createdAt','FilePath']})
}