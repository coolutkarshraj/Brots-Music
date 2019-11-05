import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators, FormGroup, FormBuilder } from "@angular/forms";
import { CONSTANT } from "../services/constant";
import { Artist } from "../models/artist";
import { SyncService } from "../services/sync.service";
import { AlertService } from "../services/alert.service";
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { OptverifyComponent } from "../optverify/optverify.component";
import { User } from '../models/user';
import { ErrorStateMatcher } from '@angular/material/core';
import { LoginRegister } from "../services/login-register";

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const invalidCtrl = !!(control && control.invalid && control.parent.dirty);
    const invalidParent = !!(control && control.parent && control.parent.invalid && control.parent.dirty);

    return (invalidCtrl || invalidParent);
  }
}

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {

  profileForm: FormGroup;
  emailForm:FormGroup ;
  passwordform:FormGroup ;
  artist:Artist ;

  selectedFile:any ;
  thumbnail:any;
  file_name:any ;
  submitted = false;
  emailsubmitted= false ;
  passwordSubmitted = false ;
  disable: boolean = true;
  ploading : boolean = false ;
  gloading : boolean = false ;
  paloading : boolean = false ;
  matcher = new MyErrorStateMatcher();
  @Output() profileData = new EventEmitter();
  music:boolean = false;
  constructor(
    private formBuilder: FormBuilder,
    private synService:SyncService,
    private alertService:AlertService,
    private dialog: MatDialog,
    private loginRegister:LoginRegister
    ) { }

  ngOnInit() {
    this.artist = new Artist() ;
    this.thumbnail = CONSTANT.PROFILE_URL
    this.profileForm = this.formBuilder.group({
      firstName: ["", Validators.required],
      surName:["", Validators.required],
      mobileNum:[""],
      about:[""],
      city:["", Validators.required],
      country:["", Validators.required],
      dob:["", Validators.required],
      gender:["", Validators.required]
    });

   
    this.emailForm = this.formBuilder.group({
      emailf: ["", Validators.required],
    });
    
    this.passwordform = this.formBuilder.group({
      opassword: ["", Validators.required],
      npassword: ["", Validators.required],
      rpassword: ["", Validators.required],
    },{ validator: this.checkPasswords });
    this.getartistDetail() ;
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

  getartistDetail(){
    let data = {
      "id":User.id,
	    "userType":"2"
    }
    this.synService.post(CONSTANT.artistProfile,data)
    .then((res)=>{
      this.artist.setDetail(res.data);
      this.thumbnail = this.artist.imageUrl ;
    })
  }

  get f() {
    return this.profileForm.controls;
  }

  get ef(){
    return this.emailForm.controls;
  }

  get pf(){
    return this.passwordform.controls;
  }

  onSubmit(){
    this.submitted = true ;
    this.synService.getImage(this.thumbnail);

    // stop here if form is invalid
    if (this.profileForm.invalid) {
      return;
    }
    this.ploading = true ;
    let name = this.f.firstName.value + " " + this.f.surName.value ;
    let data =  {
      firstName:this.f.firstName.value ,
      lastname: this.f.surName.value ,
      name: this.f.firstName.value + " " + this.f.surName.value ,
      gender:this.f.gender.value ,
      about:this.f.about.value,
      city: this.f.city.value ,
      country : this.f.country.value ,
      phone:this.f.mobileNum.value ,
      userType:this.artist.userType,
      id:this.artist.id ,
      address:this.artist.address 
    }

    console.log("data is:", data)

    this.synService.uploadFile(this.selectedFile,"image", data, CONSTANT.editProfile)
    .subscribe((res)=>{
      this.ploading = false;
      if(res.status="true"){
        this.alertService.openModal("Update Profile", "Successfully Updated");
        this.profileData.emit({
          "imageSrc":this.thumbnail,
          "userName":name
        })
        User.imageUrl = res["imageUrl"];
        User.userName = name;
        this.loginRegister.updateLocalStorage(res["data"]);
      }else{
        this.alertService.openModal("Update Profile", "Update Failed") ;
      }
    })
    
  }

  onEmailSubmit(){
    this.emailsubmitted = true ;

    
    //stop here if form is invalid
    if (this.emailForm.invalid) {
      return;
    }

    let data =  {
    
      userType:this.artist.userType,
      id:this.artist.id ,
      email:this.ef.emailf.value 
    }
    this.gloading = true;
    this.synService.verifyemailedit(CONSTANT.editEmailVerify, data)
    .then((res)=>{
      let otp = res.data.passcode ;
      this.synService.openDialog(this.artist.userName)
      .subscribe((inpotp)=>{
        if(otp==inpotp){
          
          this.synService.post(CONSTANT.updateEmail, data)
          .then((res)=>{
            this.gloading = false;
            this.artist.email = this.ef.emailf.value ;
            this.alertService.openModal("Email update happen", res.msg);
          })
          .catch((err)=>{
            this.gloading = false;
            this.alertService.openModal("Email update failed", err.msg);
          })
        }else{
          this.alertService.openModal("Email Verify failed","Wrong Otp")
        }
      })
    })
    .catch((err)=>{
      this.alertService.openModal("Email verify failed", err.msg);
    })
    
  }

  openDialog(name): any {
    const dialogRef = this.dialog.open(OptverifyComponent, {
      width: '250px',
      data: {name: name, otp: undefined}
    });

    return dialogRef.afterClosed() ;
  }

  onPasswordSubmit(){
    this.passwordSubmitted = false ;

    // stop here if form is invalid
    if (this.passwordform.invalid) {
      return;
    }

    if(this.pf.opassword.value != this.artist.password){
      this.alertService.openModal("Password Update Failed", "Pasword Mismatch");
    }

    if(this.pf.npassword.value!=this.pf.rpassword.value){
      this.alertService.openModal("Password Update Failed", "Pasword Mismatch")
    }
    let data =  {
      password:this.pf.npassword.value,
      userType:this.artist.userType,
      id:this.artist.id ,
      email:this.artist.email 
    }
    this.paloading = true;
    let password = String(this.pf.npassword.value) ; 
    // this.synService.verifyemailedit(CONSTANT.editEmailVerify, data)
    // .then((res)=>{
    //   let otp = res.data.passcode ;
    //   this.synService.openDialog(this.artist.userName)
    //   .subscribe((inpotp)=>{
    //     if(otp==inpotp){
    //       data["password"] = this.artist.password ;
    //       data["newPassword"] = password ;
          
    //     }else{
    //       this.alertService.openModal("Email Verify failed","Wrong Otp")
    //     }
    //   })
    // })
    // .catch((err)=>{
    //   this.alertService.openModal("Email verify failed", err.msg);
    // })
    this.synService.post(CONSTANT.updatePassword, data)
    .then((res)=>{
      this.paloading = false;
      this.alertService.openModal("Password update happen", res.msg);
    })
    .catch((err)=>{
      this.paloading = false;
      this.alertService.openModal("Password update failed", err.msg);
    })

  }

  checkPasswords(passwordform: FormGroup) { // here we have the 'passwords' group
    let pass = passwordform.controls.npassword.value;
    let confirmPass = passwordform.controls.rpassword.value;

    return pass === confirmPass ? null : { notSame: true }
  }

}
