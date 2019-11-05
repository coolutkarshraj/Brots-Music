import { Injectable } from "@angular/core";
import { CONSTANT } from "./constant";
import { HttpClient } from "@angular/common/http";
import { User } from "../models/user";
import { Router } from "@angular/router";
import { AuthGuardService } from "../services/auth-guard.service";

@Injectable({
  providedIn: "root"
})
export class LoginRegister {
  access_token: string;
  private loggedIn = {
    state: false
  };
  currentUser: any;

  constructor(private http: HttpClient, private router: Router, 
    private authGuardService:AuthGuardService) {}

  isAuthenticated(): boolean {
    return this.loggedIn.state;
  }

  verifyUserRegister(email, name):Promise<any> {
    let x = this;
    return new Promise((resolve, reject) =>{
      let otp = Math.floor(1000 + Math.random() * 9000);
      function perform_verify() {
        console.log("Registering in...");
        let verifyUrl = CONSTANT.URL +  CONSTANT.registraionVerify;
        var r = x.http
          .post(verifyUrl, {
            email:email,
            name:name,
            otp:otp 
          })
          .toPromise()
          .then((res) => {
            var result: any = res;
            console.log("result :", res);
            if (result.status == "true") {
              resolve(res);
            }else{
              reject(res);
            } 
          })
          .catch((res) => {
            console.log("Registration verifiction failed", res.error);
            reject(res);
          });
      }
      perform_verify();
    })
  }

  register(userData): Promise<any> {
    let x = this;
    return new Promise((resolve, reject) => {
      function perform_register() {
        console.log("Registering in...");
        let registerUrl = CONSTANT.URL  + CONSTANT.registerApiEndpoint;
        var r = x.http
          .post(registerUrl, userData)
          .toPromise()
          .then((res) => {
            var result: any = res;
            console.log("result :", res);
            if (result.status == "true") {
              resolve(res);
            } 
          })
          .catch((res) => {
            console.log("Registration failed", res);
            reject(res);
          });
      }
      perform_register();
    });
  }

  forgotP(userData): Promise<any> {
    let x = this;
    return new Promise((resolve, reject) => {
      function forgotPassword() {
        console.log("Registering in...");
        let presetUrl = CONSTANT.URL  + CONSTANT.passwordresetapi;
        var r = x.http
          .post(presetUrl, userData)
          .toPromise()
          .then((res) => {
            var result: any = res;
            console.log("result :", res);
            if (result.status == "true") {
              resolve(res);
            } 
          })
          .catch((res) => {
            console.log("Registration failed", res);
            reject(res);
          });
      }
      forgotPassword();
    });
  }

  login(email, password, device_key = undefined): Promise<any> {
    let x = this;
    return new Promise((resolve, reject) => {
      function perform_login(device_key) {
        console.log("Logging in...");
        let loginUrl = CONSTANT.URL + CONSTANT.loginApiEndPoint;
        var r = x.http
          .post(loginUrl, {
            email: email,
            password: password
          })
          .toPromise()
          .then((res) => {
            var result: any = res;
            console.log("result :", res, result.status, typeof result.status);
            if (result.status === "true") {
              x.saveTolocalstorage(result["data"])
              resolve(true) ;
            } else {
              console.log("Login failed", res);
              reject(res);
            }
          })
          .catch((res) => {
            console.log("Login failed 1", res.error);
            reject(res.error);
          });
      }
      perform_login(device_key);
    });
  }

  saveTolocalstorage(data) {
    
    localStorage.setItem("brotsartist", "true")
    localStorage.setItem("brotsartistdata", JSON.stringify(data)) ;
    User.setDetail(data) ;
    console.log("Data is:", data)
  }

  logout() {
    localStorage.removeItem("brotsartist")
    localStorage.removeItem("brotsartistdata") ;
    User.resetUserDetail();
    
    return true
  }

  updateLocalStorage(data){
    localStorage.removeItem("brotsartistdata") ;
    localStorage.setItem("brotsartistdata", JSON.stringify(data)) ;
  }

  setData(){
    let data = JSON.parse(localStorage.getItem("brotsartistdata"));
    User.setDetail(data)
  }
}
