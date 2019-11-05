import { Injectable } from '@angular/core';
import { SyncService } from '../services/sync.service';
import { CONSTANT } from '../services/constant';
import { Song } from '../models/song';
import { User } from "../models/user";
import { resolve } from 'url';
import { reject, allSettled } from 'q';
import { Category } from '../models/category';

@Injectable({
  providedIn: 'root'
})
export class BrotsService {

  constructor(private syncService:SyncService) { }
  colorList:any[] = ["lightblue","lightpink", "#DDBDF1", "rgb(168,103,221)"] ;

  getDashboardData(data):Promise<any>{
    let x = this ;
    let tracks:Song[] = []
    let followers;
    let likes;
    let dislikes;
    let totalSongs;
    return new Promise((resolve, reject)=>{
      function getDashboard(){
      x.syncService.post(CONSTANT.getDashboardData, data)
        .then((res)=>{
          followers = res["total_followers"]["total_follower"] ;
          likes = res["totalData"][0]["total_like"] ;
          dislikes = res["totalData"][0]["total_disLike"] ;
          for (const sg of res["song"]) {
            let song:Song = new Song() ;
            song.id = sg["id"];
            song.SongName = sg["SongName"];
            song.artistName = sg["ArtistName"];
            song.CoverImage = sg["CoverImage"] ;
            song.duration = sg["Duration"] ;
            song.IsDispopular = sg["IsDispopular"] ;
            song.IsTrending = sg["IsTrending"] ;
            song.songUrl = sg["FullSongUrl"] ; //"../../assets/songs/song1.mp3" ; 
            song.halfSongUrl = sg["ImageUrl"]
            song.like = sg["Like"] ;
            song.dislike = sg["DisLike"]
            let colorindex = x.getRandomArbitrary();
            song.color = x.colorList[colorindex] ;
            tracks.push(song) ;
            
          }
          totalSongs = tracks.length ;
          let data = {
            totalSongs:totalSongs,
            followers:followers,
            likes:likes,
            dislikes:dislikes,
            tracks:tracks
          }
          resolve(data)
        })
      }
      getDashboard()
    })
  }

  getAllSong():Promise<any>{
    let data = {
      "artistId": User.id
    }
    let tracks:Song[] = []
    let x = this 
    return new Promise((resolve, reject) =>{
      function allSong() {
        x.syncService.post(CONSTANT.getAllSongUrl, data)
        .then((res)=>{ 
          res.data.forEach(sg => {
            let song:Song = new Song() ;
            song.id = sg["id"];
            song.SongName = sg["SongName"];
            song.artistName = sg["ArtistName"];
            song.CoverImage = sg["CoverImage"] ;
            song.duration = sg["Duration"] ;
            song.IsDispopular = sg["IsDispopular"] ;
            song.IsTrending = sg["IsTrending"] ;
            song.categoryId = sg["categoryId"] ;
            song.subcategoryId = sg["subcategoryId"] ;
            song.album = sg["AlbumName"];
            song.artistId = sg["ArtistId"] ;
            song.songUrl = sg["FullSongUrl"] ;//"../../assets/songs/song1.mp3" ;//sg["FullSongUrl"] ;
            song.halfSongUrl = sg["ImageUrl"];
            song.like = sg["Like"] ;
            song.dislike = sg["DisLike"]
            let colorindex = x.getRandomArbitrary();
            song.color = x.colorList[colorindex] ; 
            tracks.push(song) ;
            resolve(tracks);
          });
        })
      }
      allSong();
    })
  }


  getAllCategories(){
    return new Promise((resolve, reject)=>{
      
      let x = this ;
      function allCategory(){
        
        x.syncService.get(CONSTANT.allCategories)
        .then((res)=>{
          let categories:Category[] =[];
          res.data.forEach(element => {
            let cat = new Category();
            cat.id = element["id"];
            cat.name = element["Name"] ;
            element["subcategories"].forEach(element1 => {
              let scat = new Category();
              scat.cat = false ;
              scat.id = element1["id"];
              scat.name = element1["Name"] ;
              cat.scat.push(scat) ;
            });
            cat.cat = true ;
            categories.push(cat);
          });
          resolve(categories);
        })
      }
      allCategory();
    })
  }

  getRandomArbitrary():number {

    let num =  Math.floor(Math.random() * (this.colorList.length- 1) + 0);
    return num
  } 
}
