import { Component, OnInit, Output, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { SyncService } from "../services/sync.service";
import { CONSTANT } from '../services/constant';
import { AlertService } from "../services/alert.service";
import {FormControl} from '@angular/forms';
import { Song } from '../models/song';
import { DataService } from "../services/data.service";
import { Category } from "../models/category";
import { User } from '../models/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-upload-song',
  templateUrl: './upload-song.component.html',
  styleUrls: ['./upload-song.component.scss'],
})

export class UploadSongComponent implements OnInit {

  someRange = 5;
  @ViewChild('slider', {read: ElementRef}) slider: ElementRef;

  someKeyboardConfig: any = {
    connect: true,
    start: [0, 30],
    step: 0.5,
    range: {
      min: 0,
      max: 100
    },
    behaviour: 'drag',
  };
  startCut ;
  endCut;
  startCutS="0.0" ;
  endCutS="0.0";
  totalcut:any = 0;

  basa ;
  upla  ;
  cuta ;

  selected = new FormControl(0);
  songtime="songUrl"
  start = 0 ;
  end:any;
  progress;
  songId ;
  // selectedCut:string = "0:0"
  // cuttime = 0;
  total = 0;
  loading = false ;
  music:boolean = true;
  play = false;
  maxv ;

  artistName;
  @Output() songIndex = new EventEmitter();
  @Output() usong = new EventEmitter();
  song:Song ;
  
  uploadForm: FormGroup;
  cutForm:FormGroup ;
  submitted = false;
  disable: boolean = true;
  selectedFile: File = undefined;
  musicFile: File;
  img_file_name: String;
  file_name: String;
  uploadDisable: boolean;
  downloadDisable: boolean;
  thumbnail;
  categoriesdata;
  categories:any[] = [] ;
  subcategories :any[] = [] ;
  constructor( private formBuilder: FormBuilder,
    private synService:SyncService,
    private alertService:AlertService,
    private dataService: DataService,
    private router:Router)
  { 
    this.basa= false;
    this.upla = true;
    this.cuta = true;
  }

 
  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      
      songName: ["", Validators.required],
      albumName:["", Validators.required],
      artistName:["", Validators.required],
      category: ["", Validators.required],
      subCategory:["", Validators.required]
    });
    this.cutForm = this.formBuilder.group({
      
      cTime: ["", Validators.required],
    });
    this.thumbnail = CONSTANT.PROFILE_URL ;
    this.file_name = "Select Song";
    this.artistName = "billu";
    this.dataService.getData().subscribe(data => {
      this.end = data ;
    })
    this.song = new Song() ;
    this.synService.get(CONSTANT.allCategories)
    .then((res)=>{
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
        this.categories.push(cat);
      });
    })
  }

  onSelect(event){
    this.subcategories = [] ;
    let val = event.value ;
    this.subcategories = this.categories.filter(cat=>cat.id==val)[0].scat ;
  }


  get f() {
    return this.uploadForm.controls;
  }

  get c() {
    return this.cutForm.controls;
  }

  onImgFileChanged(event) {
    this.selectedFile = event.target.files[0];
    if (this.selectedFile != undefined) {
      this.img_file_name = this.selectedFile.name;
    } 

    if (this.img_file_name.match(/.(jpg|jpeg|png|gif)$/i)) {
      let reader = new FileReader();
      reader.readAsDataURL(this.selectedFile);
      reader.onload = (event: any) => {
        this.thumbnail = event.target.result;
      };
    }
  }

  onFileChanged(event) {
    this.selectedFile = undefined ;
    this.selectedFile = event.target.files[0];
    console.log(event.target.files[0])
    if (this.selectedFile != undefined) {
      this.file_name = this.cutLength(this.selectedFile.name);
      this.createSong(this.selectedFile);
      this.uploadDisable = false;
    } 

  }

  createSong(selectedSong){
    this.song = undefined ;
    this.song = new Song() ;
    this.song.CoverImage = this.thumbnail ;
    this.song.songUrl = URL.createObjectURL(selectedSong);
    let slist:Song[] = []
    slist.push(this.song) ;
    this.usong.emit(slist);

  }

  onSubmit(){
    this.submitted = true ;
    this.loading = true ;
    // this.synService.getImage(this.thumbnail);
    
    // stop here if form is invalid
    if (this.uploadForm.invalid) {
      this.loading = false;
      this.alertService.openModal("Song Basic Info", "Invalid data")
      return;
    }
    

    if(this.selectedFile==undefined){
      this.loading = false;
      this.alertService.openModal("Song Basic Info", "Image not Selected")
      return ;
    }

    let data =  {
      artistId:User.id,
      song_name:this.f.songName.value ,
      album_name: this.f.albumName.value ,
      artist_name:this.f.artistName.value ,
      categoryId: String(this.f.category.value) ,
      subcategoryId : String(this.f.subCategory.value) ,
    }

    this.artistName = this.f.artistName.value;

    this.synService.uploadFile(this.selectedFile,"image", data, CONSTANT.songBasicInfo)
    .subscribe((res)=>{
      if(res.status="true"){
        this.loading= false ;
        this.songId  = res.data["id"];
        this.alertService.openModal("Song Basic Info ", "Successfully Added")
        this.basa = true;
        this.upla = false ;
        this.selected.setValue(1);
      }else{
        this.loading = false ;
        this.alertService.openModal("Song Basic Info ", "Not Added") ;
      }
    })

    
  }

  onUpload() {

    if (this.selectedFile==undefined){
      return 
    }
    console.log("this end:", this.end);
    
    let data = {
      songduration:this.end,
      fileSize:this.selectedFile.size,
      songId:this.songId
    }
    if(this.play==false || data["songduration"]==undefined){
      this.alertService.openModal("Play Song", "Please Play Song First");
      return ;
    }
    this.setMax(data["songduration"]);
    this.loading = true ;
    this.synService.uploadFile(this.selectedFile,"image", data, CONSTANT.uploadFullSong)
    .subscribe((res)=>{
      this.loading = false; 
      if(res.status="true"){
        this.alertService.openModal("Full Song", "Successfully Uploaded");
        this.cuta = false ;
        this.selected.setValue(2);
      }else{
        this.alertService.openModal("Full Song", "Not Uploaded")
      }
    })
  }

  songSelect(){
    this.play = true ;
    this.songIndex.emit(0);
  }

  setMax(duration){
    let min = duration.split(":", 2)[0]
    let sec = duration.split(":", 2)[1]
    let total = parseInt(min)*60+parseInt(sec) 
    this.maxv = ((total-30)*100)/(total)
  }

  cutSong(){

    let min = this.end.split(":", 2)[0]
    let sec = this.end.split(":", 2)[1]
    this.total = parseInt(min)*60+parseInt(sec) 
    if(this.play==false){
      this.alertService.openModal("Play Song", "Please Play Song First");
      return ;
    }
    if(this.totalcut!=30){
      this.alertService.openModal("30 Song Upload failed", "Duration not equal to 30") ;
      return 
    }
    let data =  {
      start:this.startCut,
      end:this.endCut
    }
    
    let songData = {
      songId:this.songId
    }
    
    this.loading = true ;
    this.synService.cutSong(this.selectedFile,"song", data, CONSTANT.cutSong)
    .subscribe((res)=>{
      
      let file = this.downLoadFile(res, "audio/mpeg", this.file_name);
      if(file.name==""){
        this.alertService.openModal("Corrupt File", "Not Uploaded") ;
        this.loading = false ;
        return ;
      }
      this.synService.uploadFile(file,"image", songData, CONSTANT.upload30Song)
      .subscribe((res)=>{
        this.loading = false ;
        if(res.status="true"){
          this.alertService.openModal("30 Song", "Successfully Uploaded") ;
          this.router.navigate(["startapp/all-songs"]);
        }else{
          this.alertService.openModal("30 Song", "Not Uploaded")
        }
      })
    })
  }

  cutTime(event){
    let s = event[0] ;
    let e = event[1] ;
    let min = this.end.split(":", 2)[0] ;
    let sec = this.end.split(":", 2)[1] ;
    let total = parseInt(min)*60+parseInt(sec)  ;
    this.startCut = Math.floor((s/100)*total) ;
    this.endCut = Math.floor((e/100)*total) ;
    this.startCutS = String(Math.floor(this.startCut/60))+ ":" + String(Math.round(this.startCut%60))
    this.endCutS = String(Math.floor(this.endCut/60))+ ":" + String(Math.round(this.endCut%60))
    // this.cuttime = this.endCut-this.startCut;
    this.totalcut = Math.round(this.endCut-this.startCut);
    // this.selectedCut = String(Math.floor(this.cuttime/60))+ ":" + String(Math.round(this.cuttime%60))
  }

  downLoadFile(blob: any, type: string ,name) {
    console.log("Name:",name,"Blob:", blob)
    var file = new File([blob], name, {type: type, lastModified: Date.now()}); 
    return file
  }
  cutLength(yourString){
    let maxLength = 30 // maximum number of characters to extract
    let trimmedString = yourString.substr(0, maxLength);
    return trimmedString.substr(0, Math.min(trimmedString.length, trimmedString.lastIndexOf(" ")))
  }

}
