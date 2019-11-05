import { Injectable } from "@angular/core";
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, Data } from "@angular/router";
import { User } from "../models/user";

@Injectable({
  providedIn: "root"
})
export class AuthGuardService {
  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot=undefined, state: RouterStateSnapshot=undefined) {
    if (localStorage.getItem("brotsartist")=="true" && localStorage.getItem("brotsartistdata")!=undefined) {
      let data = JSON.parse(localStorage.getItem("brotsartistdata"));
      User.setDetail(data)
      return true;
    }
    this.router.navigate(["/mainpane"], { queryParams: { returnUrl: state.url } });
    return false;
  }
}
