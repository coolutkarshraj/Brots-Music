import { Component, OnInit } from '@angular/core';
import { SyncService } from "../services/sync.service";
import { CONSTANT } from "../services/constant";
import { Artist } from "../models/artist";
import { User } from "../models/user";
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  artist:Artist ;
  biography;
  gender ;
  follower;
  likes ;
  dislikes ;
  thumbnail:any;
  music:boolean = false;
  constructor(private syncService:SyncService) { 
    this.artist = new Artist() ;
  }

  ngOnInit() {
    this.thumbnail = CONSTANT.PROFILE_URL;
    this.getartistDetail() ;
  }

  getartistDetail(){
    let data = {
      "id":User.id,
	    "userType":"2"
    }
    this.syncService.post(CONSTANT.artistProfile,data)
    .then((res)=>{
      this.artist.setDetail(res.data);
      console.log("Artist:", this.artist);
      if (this.artist.gender==1){
        this.gender = "Male";
      }else{
        this.gender  = "Female" ;
      }
      if(this.artist.dislikes!=undefined){
        this.dislikes = this.artist.dislikes;
      }else{
        this.dislikes = 0 ;
      }
      if(this.artist.about!=undefined){
        this.biography = this.artist.about;
      }else{
        this.biography = "Default BioGraphy because about is not set in Profile"
      }
      this.likes = this.artist.likes;
      this.follower = this.artist.followerCount ;
      this.thumbnail = this.artist.imageUrl ;
    })
  }

}
