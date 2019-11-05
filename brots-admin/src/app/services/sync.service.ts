import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { CONSTANT } from "./constant";
import {  Observable, ConnectableObservable} from "rxjs";
import { publish } from "rxjs/operators";
import {MatDialog} from '@angular/material';
import { OptverifyComponent } from "../optverify/optverify.component";

@Injectable({
  providedIn: "root"
})
export class SyncService {
  constructor(private http: HttpClient, private dialog: MatDialog) {}

  post(api_endpoint: string, data: any, parameter: any = null, token: string = null):any {
    
    let x = this ;
    return new Promise((resolve, reject) => {
      function perform_post() {
        
        let url = CONSTANT.URL + api_endpoint;
        var r = x.http
          .post(url, data)
          .toPromise()
          .then((res) => {
            var result: any = res;
            console.log("result :", res, result.status);
            if (result.status == "true") {
              resolve(res);
            } else {
              console.log("Data from post failed", res);
              reject(res);
            }
          })
          .catch((res) => {
            console.log("data failed", res);
            reject(res.error);
          });
      }
      perform_post();
    }); 
  }

  get(api_endpoint: string, data: any=undefined):any {
    
    let x = this ;
    return new Promise((resolve, reject) => {
      function perform_get() {
        
        let url = CONSTANT.URL + api_endpoint;
        var r = x.http
          .get(url, data)
          .toPromise()
          .then((res) => {
            var result: any = res;
            console.log("result :", res, result.status);
            if (result.status == "true") {
              resolve(res);
            } else {
              console.log("Data from post failed", res);
              reject(res);
            }
          })
          .catch((res) => {
            console.log("data failed", res);
            reject(res.error);
          });
      }
      perform_get();
    }); 
  }

  getImage(imageUrl){

    let x = this ;
    return new Promise((resolve, reject) => {
      function get_image() {
        
        x.http
        .get(imageUrl, {responseType: "arraybuffer"})
        .subscribe((res)=>{
          console.log("image is:", res) ;
        });
      }
      get_image();
    }); 
  }

  allFolowers(api_endpoint: string, data: any, parameter: any = null, token: string = null):any {
    
    let x = this ;
    return new Promise((resolve, reject) => {
      function perform_post() {
        
        let url = CONSTANT.URL + api_endpoint;
        var r = x.http
          .post(url, data)
          .toPromise()
          .then((res) => {
            var result: any = res;
            if (result.success == "true") {
              resolve(res);
            } else {
              reject(res);
            }
          })
          .catch((res) => {
            reject(res.error);
          });
      }
      perform_post();
    }); 
  }

  verifyemailedit(api_endpoint, data):Promise<any> {
    let x = this;
    return new Promise((resolve, reject) =>{
      let otp = Math.floor(1000 + Math.random() * 9000);
      function perform_verify() {
        console.log("Registering in...");
        let verifyUrl = CONSTANT.URL + api_endpoint ;
        var r = x.http
          .post(verifyUrl, data)
          .toPromise()
          .then((res) => {
            var result: any = res;
            console.log("result :", res);
            if (result.status == "true") {
              resolve(res);
            } 
          })
          .catch((res) => {
            console.log("Email Verification failed");
            reject(res);
          });
      }
      perform_verify();
    })
  }

  openDialog(name): any {
    const dialogRef = this.dialog.open(OptverifyComponent, {
      width: '250px',
      data: {name: name, otp: undefined}
    });

    return dialogRef.afterClosed() ;
  }

  uploadFile(file, file_field, data, upload_url): Observable<any> {
    
    
    let reader = new FileReader();

    let fd = new FormData();
    fd.append(file_field, file);
    console.log("Key:",file_field,file )
    Object.keys(data).forEach((key) => {false
      console.log("Key:",key,data[key] )
      if(data[key]!=undefined){
        fd.append(key, data[key].toString());
      }
    });
    let ret: ConnectableObservable<any> = <ConnectableObservable<any>>(
      this.http.post(CONSTANT.URL+upload_url, fd).pipe(publish())
    );

    ret.connect();

    return ret;
  }

  cutSong(file, file_field, data, upload_url): Observable<any> {
    let reader = new FileReader();

    let fd = new FormData();
    fd.append(file_field, file);

    Object.keys(data).forEach((key) => {
      fd.append(key, data[key].toString());
    });

    // let ret: ConnectableObservable<any> = <ConnectableObservable<any>>(
    //   this.http.post(upload_url, fd,).pipe(publish())
    // );

    return this.http.post(CONSTANT.URL+upload_url, fd,{
      responseType: "blob"
    })

  }

}
