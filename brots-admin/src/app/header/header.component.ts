import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { LoginRegister } from "../services/login-register";


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(private loginRegister:LoginRegister) { }
  @Output() toggle = new EventEmitter();
  ngOnInit() {
  }

  toggleSideBar(){
    // this.toggle.emit(1) ;
  }

  logout(){
    this.loginRegister.logout()
  }

}
