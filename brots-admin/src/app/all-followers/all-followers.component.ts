import { Component, OnInit } from '@angular/core';
import { SyncService } from "../services/sync.service";
import { CONSTANT } from "../services/constant";
import { Follower } from "../models/followers";
@Component({
  selector: 'app-all-followers',
  templateUrl: './all-followers.component.html',
  styleUrls: ['./all-followers.component.scss']
})
export class AllFollowersComponent implements OnInit {

  constructor(private syncService:SyncService) { }
  followers :any = [];
  colorList:any[] = ["lightblue", "lightgreen","lightpink", "#DDBDF1", "rgb(168,103,221)"] ;
  ngOnInit() {
    let data = {
      "artistId":"1",
	    
    }
    this.syncService.allFolowers(CONSTANT.getAllfollower, data)
    .then((res)=>{
      console.log("Res is:",res) ;
      res.data.forEach(fll => {
        let follower:Follower = new Follower() ;
        follower.name = fll["Name"] ;
        follower.imageUrl = fll["imageUrl"];
        follower.id = fll["id"];
        let colorindex = this.getRandomArbitrary();
        follower.color = this.colorList[colorindex] ;
        this.followers.push(follower) ;
      });
    })

  }

  getRandomArbitrary():number {

    let num =  Math.floor(Math.random() * (this.colorList.length- 1) + 0);
    return num
  }

}
