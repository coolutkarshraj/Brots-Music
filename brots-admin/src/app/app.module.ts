import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule,MatExpansionModule, MatIconModule, MatToolbarModule, MatButtonModule, MatFormFieldModule, MatInputModule } from '@angular/material';
import { FlexLayoutModule } from "@angular/flex-layout";
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatSelectModule} from '@angular/material/select';
// import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { MatDatepickerModule  } from '@angular/material/datepicker';
import { MatNativeDateModule } from "@angular/material";
import {MatDialogModule} from '@angular/material/dialog';
import { HttpClientModule, HttpHeaders } from "@angular/common/http";
import { MatSidenavModule } from "@angular/material/sidenav";
import {MatListModule} from '@angular/material';
import { Angular2FontawesomeModule } from 'angular2-fontawesome/angular2-fontawesome'
import {MatGridListModule} from '@angular/material/grid-list';
import { MatTabsModule } from "@angular/material/tabs";
import {MatRadioModule} from '@angular/material/radio';
import { VgCoreModule } from "videogular2/core";
import { VgControlsModule } from "videogular2/controls";
import { VgOverlayPlayModule } from "videogular2/overlay-play";
import {MatProgressBarModule, MatProgressSpinnerModule} from '@angular/material';
import { MatMenuModule } from '@angular/material/menu';
import { NouisliderModule } from 'ng2-nouislider';
import { DatePipe } from '@angular/common';

// Social Media Login Module 
import {
  AuthService,
  SocialLoginModule,
  AuthServiceConfig,
  GoogleLoginProvider,
  FacebookLoginProvider
} from 'angular-6-social-login';
import {  AlertService } from "./services/alert.service";
import { LoginRegister } from "./services/login-register";
import { AuthGuardService } from "./services/auth-guard.service";
import { NavService } from "./services/nav.service";
import { MusicService } from "./services/music.service";
import { DataService } from "./services/data.service";

//Component Import
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent,OtpDialog } from './register/register.component';
import { AlertComponent } from "./alert/alert.directive";
import { DashboardComponent } from './dashboard/dashboard.component';
import { HeaderComponent } from "./header/header.component";
import { MenuListItemComponent } from "./menu-list-item/menu-list-item.component";
//routing import
import { routing } from "./app-routing";
import { HomeComponent } from './home/home.component';
import { UploadSongComponent } from './upload-song/upload-song.component';
import { AllSongComponent } from './all-song/all-song.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { OptverifyComponent } from './optverify/optverify.component';
import { SyncService } from './services/sync.service';
import { MusicPlayerComponent } from './music-player/music-player.component';
import { MusicProgressComponent } from './music-progress/music-progress.component';
import { AllFollowersComponent } from './all-followers/all-followers.component';
import { MainpaneComponent } from './mainpane/mainpane.component';
import { PassresetComponent } from "./passreset/passreset.component";
import { ProfileComponent } from './profile/profile.component';
import { EditSongComponent } from './edit-song/edit-song.component';
import { CutSongComponent } from './cut-song/cut-song.component';
import { LogoutComponent } from './logout/logout.component';
import { AddNoteComponent, ConfirmDialog } from './add-note/add-note.component';
import { ProgressSpinnerComponent } from './progress-spinner/progress-spinner.component';
import { StartappComponent } from './startapp/startapp.component';
import { ContactComponent } from './contact/contact.component';


export function getAuthServiceConfigs() {
  const config = new AuthServiceConfig(
  [
    {
      id: FacebookLoginProvider.PROVIDER_ID,
      provider: new FacebookLoginProvider('261999671364789')
    },
    {
      id: GoogleLoginProvider.PROVIDER_ID,
      provider: new GoogleLoginProvider('1035010191553-nqjlv790s1hna6qcm7hsa9urhenq2b0s.apps.googleusercontent.com')
    }
  ]
  );
  return config;
}
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    OtpDialog,
    ConfirmDialog,
    AlertComponent,
    DashboardComponent,
    HeaderComponent,
    MenuListItemComponent,
    HomeComponent,
    UploadSongComponent,
    AllSongComponent,
    EditProfileComponent,
    OptverifyComponent,
    MusicPlayerComponent,
    MusicProgressComponent,
    AllFollowersComponent,
    MainpaneComponent,
    PassresetComponent,
    ProfileComponent,
    EditSongComponent,
    CutSongComponent,
    LogoutComponent,
    AddNoteComponent,
    ProgressSpinnerComponent,
    StartappComponent,
    ContactComponent
  ] ,
  imports: [
    routing,
    BrowserModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    SocialLoginModule,
    MatSelectModule,
    // NoopAnimationsModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule,
    HttpClientModule,
    MatSidenavModule,
    Angular2FontawesomeModule,
    MatExpansionModule,
    MatGridListModule,
    MatTabsModule,
    MatRadioModule,
    VgCoreModule,
    VgControlsModule,
    VgOverlayPlayModule,
    MatProgressBarModule,
    MatListModule,
    MatProgressSpinnerModule,
    MatMenuModule,
    NouisliderModule
  ],
  entryComponents: [OtpDialog, AlertComponent, OptverifyComponent, ConfirmDialog] ,
  providers: [AuthService,{
    provide: AuthServiceConfig,
    useFactory: getAuthServiceConfigs
    }, 
    
    MatDatepickerModule,
    AuthService,
    AlertService,
    LoginRegister, 
    AuthGuardService,
    NavService,
    SyncService,
    MusicService,
    DataService,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
