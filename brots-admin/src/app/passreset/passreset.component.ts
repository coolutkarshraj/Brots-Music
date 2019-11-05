import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators,FormControl, FormGroupDirective, NgForm } from "@angular/forms";
import { Router} from "@angular/router";
import { AlertService } from "../services/alert.service";
import { LoginRegister } from "../services/login-register";
import { ErrorStateMatcher } from '@angular/material/core';
import { SyncService } from "../services/sync.service";
import { CONSTANT } from "../services/constant";

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const invalidCtrl = !!(control && control.invalid && control.parent.dirty);
    const invalidParent = !!(control && control.parent && control.parent.invalid && control.parent.dirty);

    return (invalidCtrl || invalidParent);
  }
}

@Component({
  selector: 'passreset',
  templateUrl: './passreset.component.html',
  styleUrls: ['./passreset.component.css']
})
export class PassresetComponent implements OnInit {

  submitted :boolean ;
  osubmitted :boolean ;
  psubmitted :boolean ;
  emailForm: FormGroup;
  otpForm: FormGroup;
  passwordForm: FormGroup;
  otp ;
  email;
  returnUrl ;
  showOtpPage:boolean ;
  optVerify:boolean ;
  matcher = new MyErrorStateMatcher();
  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private alertService:AlertService,
      private loginRegister:LoginRegister,
      private syncService:SyncService
    ) { 
      this.showOtpPage= false ;
      this.optVerify = false ;
    }

  ngOnInit() {
    this.emailForm = this.formBuilder.group({
      email: ["", Validators.required]
    });
    this.otpForm = this.formBuilder.group({
      otp: ["", Validators.required]
    });
    this.passwordForm = this.formBuilder.group({
      password: ["", Validators.required],
      rpassword: ["", Validators.required]
    });
    this.returnUrl =  "/";
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.emailForm.controls;
  }
  get of() {
    return this.otpForm.controls;
  }
  get pf() {
    return this.passwordForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    if (this.emailForm.invalid) {
      return;
    }

    let email = this.f.email.value 
    let data = {
      "email":email,
      "userType":2
    }

    this.loginRegister.forgotP(data)
    .then((res)=>{
      this.otp = res.data["passcode"];
      this.email = email ;
      this.showOtpPage = true ;
    })
    .catch((err)=>{
      this.alertService.openModal("Sending Otp Failed", err.msg);
    })
    
  }

  onOtpSubmit(){
    this.osubmitted = true;

    if (this.otpForm.invalid) {
      return;
    }
    console.log(this.otp, this.of.otp.value, "Bilu")
    if(this.otp == this.of.otp.value){
      this.showOtpPage = false ;
      this.optVerify = true ;
    }
  }
  onPasswordSubmit(){
    this.psubmitted= false ;

    // stop here if form is invalid
    if (this.passwordForm.invalid) {
      return;
    }

    if(this.pf.password.value != this.pf.rpassword.value){
      this.alertService.openModal("Password Update Failed", "Pasword Mismatch");
    }

    let data =  {
    
      userType:"2",
      email:this.email  
    }
    let password = String(this.pf.password.value) ; 
    data["password"] = password ;
    this.syncService.post(CONSTANT.updatePassword, data)
    .then((res)=>{
      this.router.navigate(["mainpane"]);
    })
    .catch((err)=>{
      this.alertService.openModal("Password update failed", err.msg);
    })

  }

  checkPasswords(passwordform: FormGroup) { // here we have the 'passwords' group
    let pass = passwordform.controls.password.value;
    let confirmPass = passwordform.controls.rpassword.value;

    return pass === confirmPass ? null : { notSame: true }
  }

  onCancel(){
    this.router.navigate(["mainpane"]) ;
  }
  
}
