import { Component, OnInit } from '@angular/core';
import { CONSTANT } from '../services/constant';
import { Song } from '../models/song';
import { BrotsService } from "../services/brots.service";
import { User } from '../models/user';
import { SyncService } from "../services/sync.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Category } from "../models/category";
import { AlertService } from "../services/alert.service";

@Component({
  selector: 'app-edit-song',
  templateUrl: './edit-song.component.html',
  styleUrls: ['./edit-song.component.scss']
})
export class EditSongComponent implements OnInit {

  editForm: FormGroup;
  columns: number = 4;
  editMode ;
  song:Song ;
  loading ;
  categories:any[] = [] ;
  subcategories :any[] = [] ;
  tracks: any[] = [];
  selectedFile ;
  file_name ;
  thumbnail;
  music:boolean = false;
  constructor(private syncService:SyncService, 
    private formBuilder: FormBuilder,
    private alertService:AlertService,
    private brotsService: BrotsService) { }


  ngOnInit() {
    this.loading = undefined ;
    this.editMode = false;
    this.editForm = this.formBuilder.group({
      SongName: ["", Validators.required],
      artistName: ["", Validators.required],
      album:["", Validators.required],
      category:["", Validators.required],
      subcategory:["", Validators.required]
    });
    this.allSongs();
    this.allCategories();
  }

  allCategories(){
    this.brotsService.getAllCategories()
    .then((res:any)=>{
      this.categories = res;
    })
  }
  allSongs(){
    this.brotsService.getAllSong()
    .then((res)=>{
      this.tracks = res;
    })
  }

  onSelect(event){
    this.subcategories = [] ;
    let val = event ;
    this.subcategories = this.categories.filter(cat=>cat.id==val)[0].scat ;
  }

  get f() {
    return this.editForm.controls;
  }

  editSong(i){
    this.editMode  = true;
    this.song = this.tracks[i];
    this.thumbnail = this.song.CoverImage ;
    this.selectedFile = undefined ;
  }


  onImgFileChanged(event) {
    this.selectedFile = event.target.files[0];
    if (this.selectedFile != undefined) {
      this.file_name = this.selectedFile.name;
    } 

    if (this.file_name.match(/.(jpg|jpeg|png|gif)$/i)) {
      let reader = new FileReader();
      reader.readAsDataURL(this.selectedFile);
      reader.onload = (event: any) => {
        this.thumbnail = event.target.result;
      };
    }
  }

  onSubmit(){
    let data = {
      categoryId:this.f.category.value,
      subcategoryId:this.f.subcategory.value,
      song_name:this.f.SongName.value ,
      artist_name:this.f.artistName.value,
      album_name:this.f.album.value,
      artistId:User.id ,
      songId:this.song.id
    }

    this.loading = true ;

    this.syncService.uploadFile(this.selectedFile, 'image', data,CONSTANT.editSong)
    .subscribe((res)=>{
      if(res.status="true"){
        this.loading = false;
        this.alertService.openModal("Song Info", "Song Detail Updated")
      }else{
        this.loading = undefined ;
        this.alertService.openModal("Song Info ", "Song Detail Not Updated")
      }
    })

  }

  
  closeEdit(){
    this.editMode = false;
    this.song = undefined ;
    this.thumbnail = undefined ;
    this.allSongs();
  }

}
