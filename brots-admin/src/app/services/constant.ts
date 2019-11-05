export class CONSTANT {
  // change this to your ip


  static readonly BASE_URL = "http://ec2-54-212-240-82.us-west-2.compute.amazonaws.com";
  // static readonly BASE_URL = "http://localhost";
  static readonly PORT = 9000;
  static readonly PROFILE_URL = "../../../assets/icons/profile.png"
  static readonly URL = CONSTANT.BASE_URL + ":" + CONSTANT.PORT + "/v1/" ;

  static readonly registraionVerify = "artist/registrationVerify/"
  static readonly registerApiEndpoint = "artist/register/";
  static readonly loginApiEndPoint = "artist/login";
  static readonly artistProfile = "artist/getProfile" ;
  static readonly editProfile = "artist/updateProfile" ;
  static readonly editEmailVerify = "artist/sendMailToEmailIfNotExit" ;
  static readonly updateEmail = "artist/updateemail" ;
  static readonly updatePassword = "artist/changepassword";
  static readonly getAllSongUrl = "artist/getSingleArtistAllSong" ;
  static readonly getDashboardData = "artist/getArtistDashboardData" ;
  static readonly getAllfollower = "friend-follow/all-followers" ;
  static readonly passwordresetapi = "artist/getPasswordResetCode" ;
  static readonly songBasicInfo = "artist/uploadSongBasic"
  static readonly uploadFullSong = "artist/uploadFullSong"
  static readonly cutSong = "artist/cut30secSong" ;
  static readonly upload30Song = "artist/upload30secSong" ;
  static readonly deleteSong = "artist/deleteSong" ;

  static readonly addNote = "artist/addNote" ;
  static readonly deleteNote = "artist/deleteNote" ;
  static readonly editSong = "artist/editSong" ;
  static readonly allCategories = "allCategories/subcategories" ;
  static readonly contactApi = "contactAdminm"

}
