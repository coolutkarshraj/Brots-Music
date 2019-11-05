import { Component, OnInit, Output, EventEmitter  } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import {ChangeDetectorRef} from '@angular/core'

@Component({
  selector: 'app-mainpane',
  templateUrl: './mainpane.component.html',
  styleUrls: ['./mainpane.component.css']
})
export class MainpaneComponent implements OnInit {

  signin: boolean;
  signup: boolean;
  logged :boolean = false;
  returnUrl: string;
  constructor(
    private route: ActivatedRoute, 
    private router: Router,
    private changeDetectorRef: ChangeDetectorRef) { 
    this.returnUrl = this.route.snapshot.queryParams["returnUrl"] || "/";
  }

  ngOnInit() {
    console.log("dbddsfdsf")
    this.signup = false;
    this.signin = true;
  }

  signinfun() {
    this.signup = false;
    this.signin = true;
    console.log("button login :", this.signin, this.signup);
  }
  signupfun() {
    this.signup = true;
    this.signin = false;
    console.log("button register :", this.signin, this.signup);
  }

  handleLogged(event){
    console.log("evnt isds:", event);
    this.signin= undefined ;
    this.signup = undefined ;
    this.logged = true;
    // this.changeDetectorRef.detectChanges();
    this.router.navigate([this.returnUrl]);
  }

  handleRegistered(event){
    this.signin = !event;
    this.signup = event ;
  }
}
