import { Component, Output, EventEmitter, Input, OnInit } from '@angular/core';

@Component({
  selector: 'music-player',
  templateUrl: './music-player.component.html',
  styleUrls: ['./music-player.component.css'],
})
export class MusicPlayerComponent  {
  @Input() paused;
  @Output() backward = new EventEmitter();
  @Output() pauseplay = new EventEmitter();
  @Output() forward = new EventEmitter();
  @Output() random = new EventEmitter();
  @Output() stop = new EventEmitter();
  @Output() previous = new EventEmitter();
  @Output() next = new EventEmitter();
  @Output() nexttime = new EventEmitter();
  @Input() elapsed: string;
  @Input() total: string ;
  @Input() current: number;
  @Input() totalD: any;
  nexttimeInterval

  forwardSong(e){
    let clickedValue = 100 * (e.offsetX/ e.target.offsetWidth) ;
    console.log(clickedValue, this.totalD) ;
    this.nexttime.emit((clickedValue/100)*this.totalD) ;
  }
}

