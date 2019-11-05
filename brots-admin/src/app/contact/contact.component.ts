import { Component, OnInit } from '@angular/core';
import {  Validators, FormGroup, FormBuilder } from "@angular/forms";
import { User } from '../models/user';
import { SyncService } from "../services/sync.service";
import { CONSTANT } from '../services/constant';
import { AlertService } from '../services/alert.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  contactForm: FormGroup;
  submitted ;
  loading:boolean;
  music:boolean = false;
  constructor(private formBuilder: FormBuilder, 
    private syncService:SyncService,
    private alertService:AlertService,
    private router:Router
  ) { }

  ngOnInit() {
    this.contactForm = this.formBuilder.group({
      email: ["", Validators.required],
      tittle:["", Validators.required],
      subject:["", Validators.required]
    });
    this.submitted = false;
    this.loading = false ;
  }

  get f() {
    return this.contactForm.controls;
  }


  onSubmit(){
    this.submitted = true ;

    // stop here if form is invalid
    if (this.contactForm.invalid) {
      return;
    }
    this.loading = true ;

    let data =  {
      email:this.f.email.value ,
      title: this.f.tittle.value ,
      messageBody: this.f.subject.value,
      user_Id:User.id ,
      user_name:User.userName,
      userType:User.userType
    }

    this.syncService.post(CONSTANT.contactApi,data)
    .then((res)=>{
      this.loading = false;
      this.alertService.openModal("Contact Detail", "Successfully Submitted");
      this.router.navigate(["startapp/dashboard"]);
    })
    .catch((err)=>{
      this.loading = false;
      this.alertService.openModal("Contact Detail", "Not Submitted") ;
    })
    
  }


}
