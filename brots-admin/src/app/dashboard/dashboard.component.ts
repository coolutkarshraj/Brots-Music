
import {Component, Output, EventEmitter, OnInit} from '@angular/core';
import { MediaChange, MediaObserver } from '@angular/flex-layout';
import { Subscription } from 'rxjs';
import { User } from '../models/user';
import { BrotsService } from "../services/brots.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  watcher: Subscription;
  songtime = "songUrl"
  music:boolean = true;
  columns: number = 4;
  @Output() songIndex = new EventEmitter();
  @Output() usong = new EventEmitter();
  @Output() songtimeOp = new EventEmitter();

  constructor(mediaObserver: MediaObserver,
    private brotsService:BrotsService ) {

    this.watcher = mediaObserver.media$.subscribe((change: MediaChange) => {
      if (change) {
        if (change.mqAlias == 'xs') {
          this.columns = 1;
        } else if (change.mqAlias == 'sm') {
          this.columns = 2;
        } else {
          this.columns = 3;
        }
      }
    });
  }
  pictures: any[] ;
  followers = 0;
  likes = 0;
  dislikes = 0;
  totalSongs = 0;
  tracks: any[] = [];

  colorList:any[] = ["lightblue","lightpink", "#DDBDF1", "rgb(168,103,221)"] ;

  ngOnInit(){
    let data = {
      "artistId":User.id
    }
    this.brotsService.getDashboardData(data)
    .then((res)=>{
      if(res["followers"])  this.followers = res["followers"];
      if(res["likes"])  this.likes = res["likes"] ;
      if(res["dislikes"])  this.dislikes = res["dislikes"] ;
      if(res["tracks"])  this.tracks = res["tracks"] ;
      if(res["totalSongs"])  this.totalSongs = res["totalSongs"] ;
      this.usong.emit(this.tracks);
    })
    
  }
  
  songSelect(i){
    this.songIndex.emit(i);
  }
  selct30SecSong(){
    this.songtimeOp.emit("halfSongUrl");
  }
  selctFullSong(){
    this.songtimeOp.emit("songUrl");
  }
}
