import { Component, OnInit, Inject, Output, EventEmitter } from '@angular/core';
import {
  AuthService,
  FacebookLoginProvider,
  GoogleLoginProvider
} from 'angular-6-social-login';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { Router, ActivatedRoute } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { LoginRegister } from "../services/login-register";
import { User } from "../models/user";
import { AlertService } from "../services/alert.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  @Output() registered = new EventEmitter();
  loading = false;
  submitted = false;
  returnUrl: string;
  firstname:string ;
  lastname:string ;
  email:string ;
  lat ;
  lon ;
  city;
  country ; 
  public responseData: any;
  public userPostData = {
    email: '',
    name: '',
    provider: '',
    provider_id: '',
    provider_pic: '',
    token: ''
  };
  constructor(private loginRegister:LoginRegister, private socialAuthService: AuthService, 
        private formBuilder: FormBuilder, private dialog: MatDialog,
         private alertService:AlertService,
         private router: Router,) {}

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      firstName: ["", Validators.required],
      lastName: ["", Validators.required],
      city: ["", Validators.required],
      country: ["", Validators.required],
      email: ["", Validators.required],
      password: ["", Validators.required],
      rpassword: ["", Validators.required],
      gender: ["", Validators.required],
      bday: ["", Validators.required],
      tnc: ["", Validators.required]
    });
    
  }

  findMe() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.lat = position.coords.latitude ;
        this.lon = position.coords.longitude ;
        this.city = "ambur"
        this.country = "india"
      });
    } else {
      alert("Geolocation is not supported by this browser.");
    }
    
  }
  
  get f() {
    return this.registerForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.registerForm.invalid) {
      return;
    }
    if (this.f.tnc.value==false){
      this.alertService.openModal("Terms and Conditon", "Please Accept Terms and conditon")
      return ;
    }
    
    if (!this.checkPassword(this.f.password.value, this.f.rpassword.value)){
      // this.alertService.openModal("Mismatch Error","Password Dont Match")
      return 
    }
    this.setUserDetail();
    
    this.loginRegister.verifyUserRegister(User.email, User.userName)
    .then((res)=>{
      this.openDialog()
      .subscribe(result => {
        if (res.date.passcode==result){
          let userData =User.getUserRegisterDetail();
          this.loading = true;
          this.loginRegister.register(userData)
          .then((result)=>{
            this.loading = false;
            this.alertService.openModal("Registration Successful", result.message)
            this.registered.emit(false);
          })
          .catch((err)=>{
            this.loading = false ;
            this.alertService.openModal("Registration Failed", err.message)
          })
        }else{
          this.alertService.openModal("Registration Failed", "Otp not correct")
        }
        
      });
    })
    .catch((err)=>{
      this.alertService.openModal("Registration Failed",err.message)
    })
    
  }

  openDialog(): any {
    let otp :number 
    const dialogRef = this.dialog.open(OtpDialog, {
      width: '250px',
      data: {name: User.userName, otp: undefined}
    });

    return dialogRef.afterClosed() ;
  }

  public socialSignIn(socialPlatform: string) {
    let socialPlatformProvider;
    if (socialPlatform === 'facebook') {
       socialPlatformProvider = FacebookLoginProvider.PROVIDER_ID;
    } else if (socialPlatform === 'google') {
       socialPlatformProvider = GoogleLoginProvider.PROVIDER_ID;
    }
    
    this.socialAuthService.signIn(socialPlatformProvider).then(userData => {
      this.apiConnection(userData);
      this.findMe();
    });
    
  }

  apiConnection(data) {
    this.userPostData.email = data.email;
    this.userPostData.name = data.name;
    this.userPostData.provider = data.provider;
    this.userPostData.provider_id = data.id;
    this.userPostData.provider_pic = data.image;
    this.userPostData.token = data.token;
    let names =  this.userPostData.name.split(" ")
    this.firstname = names[0]
    this.lastname = names[1]
    this.email = data.email; 
  }

  checkPassword(password,rpassword){

    if (password!=rpassword) {
      return false
    }
    return true
  }

  setUserDetail() {
    User.setUserDetail(this.f.firstName.value, this.f.lastName.value, 2, this.f.password.value,
      this.f.gender.value, this.f.city.value, this.f.country.value, this.lat,this.lon,this.f.bday.value, this.f.email.value)
    User.setUserRegistrationDate();
    User.setTncAccepted(this.f.tnc.value) ;
    User.setDeviceType(1);
      // User.firstName = this.f.firstName.value ;
    // User.lastName = this.f.lastName.value ;
    // User.userName = User.firstName + " " + User.lastName ;
    // User.userType = 2 
    // User.password = this.f.password.value ;
    // let gender:any = Gender[this.f.gender.value] ;
    // User.gender = gender ;
    // User.city = this.f.city.value ;
    // User.country = this.f.country.value ;
    // User.deviceToken = undefined ;
    // User.address = String(this.lat) + "__" + String(this.lon)
    // User.dob = this.f.bday.value ;
    // User.registrationDate = new Date().toISOString() ;
    // User.deviceType = 1 ;
    // User.deviceType = 2 ;
    // User.email =  this.f.email.value ;
    // User.isTncAccepted = this.f.tnc.value ;
    // console.log("User Detail:")
    // console.log(User)
  }
}

@Component({
  selector: 'otp-dialog',
  templateUrl: 'otp-component.html',
})
export class OtpDialog {

  constructor(public dialogRef: MatDialogRef<OtpDialog>,
    @Inject(MAT_DIALOG_DATA) public data
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}


