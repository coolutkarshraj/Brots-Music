
import {Component, ViewChild, ElementRef, ViewEncapsulation, AfterViewInit, OnInit} from '@angular/core';
import {NavItem} from '../models/nav-item';
import {NavService} from '../services/nav.service';
import { MatSidenav } from '@angular/material';
import { Song } from '../models/song';
import { MusicService } from "../services/music.service";
import { DataService } from "../services/data.service";
import { User } from "../models/user";
import { navItems } from "../models/nav-list";
import { SyncService } from "../services/sync.service";
import { CONSTANT } from "../services/constant";
import { LoginRegister } from "../services/login-register";
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private dataService: DataService,
    private navService: NavService, 
    private musicService: MusicService,
    private syncService:SyncService,
    private loginRegister:LoginRegister) {
    
  }

  userName ;
  imgSrc 

  navItems: NavItem[] =[];
  
  component ;
  songIndex:number=-1 ;
  position;
  elapsed;
  duration;
  song ;
  paused = true;
  tracks: Song[] = [];
  fulTracks: Song[] = [];
  tracks30sec: Song[] = [];
  totald = 0;
  songtime ;
  music:boolean = false;

  @ViewChild('drawer') drawer: MatSidenav;

  ngOnInit() {
    // this.getArtistdetail()
    this.imgSrc = User.imageUrl;
    this.userName = User.userName ;
    
    if(this.imgSrc==undefined){
      this.imgSrc = CONSTANT.PROFILE_URL ;
    }
    if(this.userName==undefined){
      this.userName = ""
    }
    this.navItems = navItems;
    this.musicService.audio.onended = this.handleEnded.bind(this);
    this.musicService.audio.ontimeupdate = this.handleTimeUpdate.bind(this);
    
  }

  ngAfterViewInit() {
    this.navService.appDrawer = this.drawer;
  }

  getArtistdetail(){
    let userData = {
      "id":User.id,
	    "userType":"2"
    }
    this.syncService.post(CONSTANT.artistProfile,userData)
    .then((res)=>{
      this.loginRegister.updateLocalStorage(res);
      this.loginRegister.setData();
      this.userName = User.userName ;
      this.imgSrc = User.imageUrl ;
    })
  }

  resetSong(){
    this.songIndex=-1 ;
    this.position = 0;
    this.elapsed = 0;
    this.duration = NaN;
    this.song = undefined ;
    this.paused = true;
    this.tracks = [];
    this.totald = 0;
  }

  handleToggle(){
    this.drawer.toggle();
  }

  componentAdded(event){

    this.component = event ;
    if(this.component.music == true){
      this.music = true;
    }
    if(this.component.tracks!=undefined){
      this.tracks = this.component.tracks ;
    }
    if(this.component.usong!=undefined){
      this.component.usong.subscribe(res => {
        this.tracks = []
        this.tracks = res ;
        this.handleStop() ;
        this.handleChanged();
        console.log("Tracks:", this.tracks);

      });
    }
    
    if(this.component.songIndex!=undefined){
      this.component.songIndex.subscribe(res => {
        this.chooseSong(res);
      });
    }

    if(this.component.songtime!=undefined){
      this.songtime = this.component.songtime ;
      this.component.songtimeOp.subscribe((res)=>{
        this.songtime = res ;
        this.handleStop() ;
        this.handleChanged();
      })
    }
    if(this.component.profileData!=undefined){
      this.component.profileData.subscribe((res)=>{
        this.imgSrc = res["imageSrc"] ;
        this.userName = res["userName"] ;
      })
    }
    
  }


  componentRemoved(event){
    this.resetSong();
    this.handleStop();
    this.music = false;
  }

  chooseSong(i){
    if(i==this.songIndex){
      this.handlePausePlay()
      this.song.paused = this.paused ;
    }else{
      if(this.songIndex!=-1){
        this.song.paused = true ;
      }
      this.songIndex = i;
      this.paused = false;
      this.song = this.tracks[i];
      this.song.paused = false
      this.musicService.play(this.song[this.songtime]) ;
    }
  }

  handleEnded(e) {
    this.nextSong();
  }

  handleStop() {
    // this.musicService.audio.pause();
    this.musicService.audio.currentTime = 0;
    this.musicService.audio.src = undefined ;
    this.paused = true;
    if(this.song!=undefined)    this.song.paused = true ;
  }

  handleChanged(){
    this.songIndex=-1 ;
    this.position = 0;
    this.elapsed = 0;
    this.duration = NaN;
    this.song= undefined ;
    this.totald = 0;
  }

  nextSong(){
    this.chooseSong((this.songIndex + 1) % this.tracks.length) ;
  }

  previousSong(){
    if(this.songIndex==0){
      this.chooseSong((this.tracks.length-1) % this.tracks.length) ;
    }else{
      this.chooseSong((this.songIndex - 1) % this.tracks.length) ;
    }
    
  }

  handlePausePlay() {
    if(this.songIndex==-1){
      this.chooseSong(0);
    }else{
      if(this.musicService.audio.paused) {
        this.paused = false;
        this.song.paused = false
        this.musicService.audio.play()
      } else {
        this.paused = true;
        this.song.paused = true ;
        this.musicService.audio.pause()
      }
    }
    
  }

  handleTimeUpdate(e) {
    const elapsed =  this.musicService.audio.currentTime;
    const duration =  this.musicService.audio.duration;
    this.dataService.updateData(duration);
    this.totald = duration ;
    if (isNaN(duration)){
      this.position = 0;
      this.duration = 0 ;
    }else{
      this.position = elapsed / duration;
      this.duration = this.musicService.formatTime(duration);
    }
    this.dataService.updateData(this.duration);
    this.elapsed = this.musicService.formatTime(elapsed);
  }

  nextTime(event){
    if(this.component.usong!=undefined){
      this.dataService.updateData(event);
    }
    this.musicService.audio.currentTime = event ;
    this.handleTimeUpdate(this) ;
  }

}
