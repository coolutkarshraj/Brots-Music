import { Injectable } from '@angular/core';

@Injectable()
export class MusicService {

  audio;

  constructor(
  ) {
    this.audio = new Audio();
  }

  load(url) {
    this.audio.src = url ;
    this.audio.load();
  }

  play(url) {
    this.load(url);
    this.audio.play()
  }

  randomTrack(tracks) {
    const trackLength = tracks.length;
    // Pick a random number
    const randomNumber = Math.floor(Math.random() * (trackLength- 1) + 0);
    // Return a random track
    return tracks[randomNumber];
  }

  formatTime(seconds) {
    let minutes:any = Math.floor(seconds / 60);
    minutes = (minutes >= 10) ? minutes : "0" + minutes;
    seconds = Math.floor(seconds % 60);
    seconds = (seconds >= 10) ? seconds : "0" + seconds;
    return minutes + ":" + seconds;
  }

  xlArtwork(url) {
    return url.replace(/large/, 't500x500');
  }

}
