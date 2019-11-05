export class Category{
    id ;
    name;
    scat:Category[];
    cat:boolean ;
    constructor(){
        this.scat = [];
        this.cat = false ;
    }

}