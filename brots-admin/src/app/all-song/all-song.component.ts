import { Component, OnInit,Output, EventEmitter } from '@angular/core';
import { BrotsService } from "../services/brots.service";
@Component({
  selector: 'app-all-song',
  templateUrl: './all-song.component.html',
  styleUrls: ['./all-song.component.scss'],
  
})
export class AllSongComponent implements OnInit {

  @Output() songIndex = new EventEmitter();
  @Output() usong = new EventEmitter();
  @Output() songtimeOp = new EventEmitter();
  songtime = "songUrl"
  music:boolean = false;
  columns: number = 3;
  tracks: any[] = [];
  constructor(private brotsService: BrotsService) { }

  ngOnInit() {
    this.getAllSong();
  }  

  getAllSong(){
    this.brotsService.getAllSong()
    .then((res)=>{
      this.tracks = res;
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
