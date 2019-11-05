export class Song{
    id;
    SongName:string;
    artistName:string ;
    album;
    artistId;
    duration;
    CoverImage:string ;
    songUrl ;
    halfSongUrl ;
    IsTrending;
    IsDispopular;
    color:string ;
    paused:boolean ;
    categoryId;
    subcategoryId;
    like;
    dislike ;
    constructor(){
        this.paused = true ;
    }
}