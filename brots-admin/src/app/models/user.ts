import { Gender } from "./gender";

export class User {
  static id:number;
  static userName: string;
  static firstName: string;
  static middleName:string;
  static lastLogin ;
  static lastName: string;
  static userType:number;
  static password: string;
  static gender:number;
  static dob:string;
  static city:string;
  static state:string ;
  static country:string;
  static address:string ;
  static registrationDate:string;
  static deviceType:number;
  static email: string;
  static deviceToken:number ;
  static isTncAccepted:number
  static otp :number ;
  static about:string ;
  static mobile
  static imageUrl;
  

  static setUserDetail(firstName, lastName, userType, password, gender, city, country,
       lat, lon, bday, email) {

    User.firstName = firstName ;
    User.lastName = lastName ;
    User.userName = User.firstName + " " + User.lastName ;
    User.userType = userType
    User.password = password ;
    let g:any = Gender[gender] ;
    User.gender = g ;
    User.city = city ;
    User.country = country ;
    User.deviceToken = undefined ;
    User.address = String(lat) + "__" + String(lon)
    User.dob = bday ;
    User.deviceType = 2 ;
    User.email =  email ;
    console.log("User Detail:")
    console.log(User)
  }

  static setUserRegistrationDate(){
    User.registrationDate = new Date().toISOString() ;
  }
  static setDeviceType(deviceType){
    User.deviceType = deviceType
  }
  static setTncAccepted(tnc){
    if (tnc){
      User.isTncAccepted = 1 ;
    }else{
      User.isTncAccepted = 0;
    }
    
  }
  static resetUserDetail() {
    User.firstName = undefined ;
    User.middleName =undefined ;
    User.lastName = undefined ;
    User.userName = undefined ;
    User.userType = undefined
    User.password = undefined ;
    User.gender = undefined ;
    User.city = undefined ;
    User.country = undefined ;
    User.deviceToken = undefined ;
    User.address = undefined
    User.dob = undefined ;
    User.deviceType = undefined ;
    User.email =  undefined ;
    User.registrationDate = undefined ;
    User.deviceType = undefined ; 
    User.isTncAccepted = undefined ;
    User.imageUrl =undefined ;
    User.mobile = undefined ; 
  }

  static getUserRegisterDetail(){
    let userDetail = {
      "name":User.userName,
      "firstName":User.firstName,
      "lastname":User.lastName,
      "userType":User.userType,
      "password":User.password,
      "gender":String(User.gender),
      "dob":User.dob,
      "city":User.city,
      "country":User.country,
      "address":User.address,
      "registratioDate":User.registrationDate,
      "isTncAccepted": String(User.isTncAccepted),
      "email":User.email,
      "deviceType": String(User.deviceType) 
    }
    //registratioDate, lastname need to be checked 
    return userDetail
  }

  static setDetail(data){
    
    User.id = data["id"];
    User.firstName = data["FirstName"] ;
    User.lastName = data["LastName"] ;
    User.middleName = data["MiddileName"]
    User.userName = User.firstName +" " +User.lastName ;
    User.mobile = data["phone"] ; 
    User.about = data["About"];
    User.city = data["City"] ;
    User.country = data["Country"] ;
    User.gender = data["Gender"] ;
    User.imageUrl = data["imageUrl"] ;
    User.id = data["id"] ;
    User.address = data["Address"]
    User.userType = data["UserType"] ;
    User.email = data["Email"] ;
    User.password = data["Password"] ;
    User.deviceToken = data["DeviceToken"] ;
    User.deviceType = data["DeviceType"] ;
  }
}
