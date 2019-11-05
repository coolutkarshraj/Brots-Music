
import { Component, OnInit, NgModule, Output, EventEmitter } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { LoginRegister } from "../services/login-register";
import { AlertService } from "../services/alert.service";
import { DataService } from "../services/data.service";
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loading;
  submitted = false;
  returnUrl: string;
  color = 'warn';
  mode = 'indeterminate';

  @Output() logged = new EventEmitter();

  public responseData: any;
  public userPostData = {
    email: '',
    name: '',
    provider: '',
    provider_id: '',
    provider_pic: '',
    token: ''
  };
  constructor(
    private formBuilder: FormBuilder,
    private loginregister:LoginRegister,
    private alertService:AlertService,
    private route: ActivatedRoute,
    private dataservice:DataService
    ) {}

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ["", Validators.required],
      password: ["", Validators.required]
    });
    this.loading = undefined;
    this.returnUrl = this.route.snapshot.queryParams["returnUrl"] || "/";
  }

  
  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }
    this.loading = true;
    this.loginregister.login(this.f.email.value, this.f.password.value)
    .then((res)=>{
      this.logged.emit(true);
      // this.dataservice.updateData(true);
      this.loading = false;
    })
    .catch((err)=>{
      this.loading = undefined ;
      this.alertService.openModal("Login Falied", "Invalid Credential") ;
    })
  }

  

}
