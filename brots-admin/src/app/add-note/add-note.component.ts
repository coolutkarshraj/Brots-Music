import { Component, OnInit, Inject } from '@angular/core';
import { Validators, FormGroup, FormBuilder } from "@angular/forms";
import { SyncService } from "../services/sync.service"
import { User } from "../models/user";
import { CONSTANT } from "../services/constant";
import { AlertService } from "../services/alert.service";
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
@Component({
  selector: 'app-add-note',
  templateUrl: './add-note.component.html',
  styleUrls: ['./add-note.component.scss']
})
export class AddNoteComponent implements OnInit {

  noteForm:FormGroup ;
  loading;
  addnote;
  noteDate ;
  music:boolean = false;
  constructor( private formBuilder: FormBuilder,
      private syncService :SyncService,
      private alertService:AlertService,
      private dialog: MatDialog
    ) { }

  ngOnInit() {
    this.noteForm = this.formBuilder.group({
      note: ["", Validators.required],
    });
    this.getNoteDetail();
    this.loading = false ;
  }

  getNoteDetail(){
    let userData = {
      "id":User.id,
	    "userType":"2"
    }
    this.syncService.post(CONSTANT.artistProfile,userData)
    .then((res)=>{
      this.addnote = res.data["AddNote"];
      this.noteDate = res.data["addNoteDate"] ;
    })
  }

  get f() {
    return this.noteForm.controls;
  }

  onSubmit(){
    
    let time = new Date().toUTCString();
    let note = this.f.note.value ;
    let data = {
      addnote:note,
      addnoteDate:time,
      email:User.email,
      id:User.id
    }
    this.loading = true ;
    this.syncService.post(CONSTANT.addNote, data)
    .then((res)=>{
      this.loading = false ;
      this.alertService.openModal("Note Added", res.message)
    })
    .catch((err)=>{
      this.loading = false ; 
      this.alertService.openModal("Note Added", err.message)
    })
  }

  deleteNote(){
    let data = {
      email:User.email,
      id:User.id
    }
    
    this.openDialog()
    .subscribe(result => {
      if(result===1){
        this.loading = true ;
        this.syncService.post(CONSTANT.deleteNote, data)
        .then((res)=>{
          this.loading = false ;
          this.alertService.openModal("Note Deleted", res.message) ;
          this.addnote = undefined
          this.getNoteDetail();
        })
        .catch((err)=>{
          this.loading = false ; 
          this.alertService.openModal("Note Deleted", err.message)
        })
      }
    })
    
  }

  openDialog(): any {
  
    const dialogRef = this.dialog.open(ConfirmDialog, {
      width: '250px',
      data: {name: User.userName, delete: undefined}
    });

    return dialogRef.afterClosed() ;
  }

}

@Component({
  selector: 'confirm-dialog',
  templateUrl: 'confirm-dialog.html',
})
export class ConfirmDialog {

  constructor(public dialogRef: MatDialogRef<ConfirmDialog>,
    @Inject(MAT_DIALOG_DATA) public data
  ) {}

}
