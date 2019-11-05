import { Routes, RouterModule } from "@angular/router";

import { AuthGuardService } from "./services/auth-guard.service";
import { LoginComponent } from "./login/login.component";
import { RegisterComponent } from "./register/register.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { UploadSongComponent } from "./upload-song/upload-song.component";
import { AllSongComponent } from "./all-song/all-song.component";
import { EditProfileComponent } from "./edit-profile/edit-profile.component";
import { AllFollowersComponent } from "./all-followers/all-followers.component";
import { MainpaneComponent } from "./mainpane/mainpane.component";
import { PassresetComponent } from "./passreset/passreset.component";
import { ProfileComponent } from "./profile/profile.component";
import { EditSongComponent } from "./edit-song/edit-song.component";
import { CutSongComponent } from "./cut-song/cut-song.component";
import { LogoutComponent } from "./logout/logout.component";
import { AddNoteComponent } from "./add-note/add-note.component";
import { StartappComponent } from "./startapp/startapp.component";
import { ContactComponent } from "./contact/contact.component";

const appRoutes: Routes = [
  { path: "startapp", component: StartappComponent,canActivate: [AuthGuardService],
    children:[
      { path:"dashboard", component:DashboardComponent },
      { path: "note-add", component: AddNoteComponent},
      { path: "song",
        children:[
          { path: "upload", component: UploadSongComponent},
          { path: "cut-song", component: CutSongComponent},
          { path: "all-songs", component: AllSongComponent},
          { path: "edit-song", component: EditSongComponent }
        ]
      },
      { path: "profile",
        children:[
          { path: "view", component: ProfileComponent},
          { path: "edit", component: EditProfileComponent},
        ]
      },
      { path: "contact", component: ContactComponent},
      { path: "logout", component: LogoutComponent, canActivate: [AuthGuardService]},
    ] 
  },
  { path: "mainpane", component: MainpaneComponent},
  
  { path: "forgotp", component: PassresetComponent },
  // { path: "all-followers", component: AllFollowersComponent,canActivate: [AuthGuardService] },
  
  { path: "", redirectTo: "startapp/dashboard", pathMatch:"full", canActivate: [AuthGuardService] },
  // { path: "**", redirectTo: "home", canActivate: [AuthGuardService] },
];
export const routing = RouterModule.forRoot(appRoutes);
