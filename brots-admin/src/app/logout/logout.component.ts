import { Component, OnInit } from '@angular/core';
import { LoginRegister } from "../services/login-register";
import { Router } from "@angular/router";
import { AuthGuardService } from "../services/auth-guard.service";

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private loginRegister:LoginRegister,
    private router: Router, 
    private authGuardService:AuthGuardService) { }

  ngOnInit() {
    this.logout() ;
    this.router.navigate(["mainpane"]);
  }

  logout(){
    this.loginRegister.logout(); 
  }

}
