export class Artist {
    
    id:number;
    userName: string;
    firstName: string;
    middleName:string;
    lastName: string;
    email: string;
    mobile:string ;
    lastLogin ;
    userType:number;
    password: string;
    gender:number;
    dob:string;
    city:string;
    state:string ;
    country:string;
    address:string ;
    registrationDate:string;
    deviceType:number;
    deviceToken:number ;
    imageUrl:string ;
    followerCount:number;
    addNote;
    about;
    addNoteDate ;
    likes;
    dislikes;

    setDetail(data){
    
        this.firstName = data["FirstName"] ;
        this.lastName = data["LastName"] ;
        this.userName = this.firstName +" " +this.lastName ;
        this.mobile = data["phone"] ; 
        this.about = data["About"];
        this.city = data["City"] ;
        this.country = data["Country"] ;
        this.gender = data["Gender"] ;
        this.imageUrl = data["imageUrl"] ;
        this.id = data["id"] ;
        this.address = data["Address"] ;
        this.dob = data["DOB"]
        this.userType = data["UserType"] ;
        this.email = data["Email"] ;
        this.password = data["Password"] ;
        this.addNote = data["AddNote"] ;
        this.addNoteDate = data["AddNoteDate"] ;
        this.followerCount = data["followerCount"];
        this.likes = data["TotalLike"];
        this.dislikes = data["TotalDislike"] ;
    }
    
}  


