import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { SyncService } from '../services/sync.service';
import { CONSTANT } from '../services/constant';
import { Song } from '../models/song';
import { BrotsService } from "../services/brots.service";
import { User } from '../models/user';
import { AlertService } from "../services/alert.service";
@Component({
  selector: 'app-cut-song',
  templateUrl: './cut-song.component.html',
  styleUrls: ['./cut-song.component.scss']
})
export class CutSongComponent implements OnInit {

  @Output() songIndex = new EventEmitter();
  columns: number = 4;
  colorList:any[] = ["lightblue","lightpink", "#DDBDF1", "rgb(168,103,221)"] ;
  tracks: any[] = [];
  constructor(private brotsService:BrotsService,
    private syncService:SyncService,
    private alertService: AlertService) { }

  ngOnInit() {
    this.getAllSong();
  }

  getAllSong(){
    this.brotsService.getAllSong()
    .then((res)=>{
      this.tracks = res;
    })
  }
  
  deleteSong(i){
    let song:Song = this.tracks[i];
    let data = {
      artist_id:User.id,
      song_id:song.id
    }
    this.syncService.post(CONSTANT.deleteSong,data)
    .then((res)=>{
      this.alertService.openModal("Delete Song", "Song Successfully Deleted") ;
      this.getAllSong();
    })
    .catch((res)=>{
      this.alertService.openModal("Delete Song", "Song Not Deleted") ;
    })
    
  }

}
