export interface Towns{
    id ?:number;
    towns_name?:String;
    overview?:String; 
    description?:number; 
    city_id?:number; 
    state_id?:number; 
    imageUrl?: String;
    townsStatus?: number;
    about?: string;
    following?: number;
    followers?: number;
    itinerary?: string;
    Short_itinerary?: string;
    inclusion?: string;
    Exclusion?: string;
    TNC?: string;
    Others?: string;
    hm_policy?: number;
    t_price?: number;
    d_price?: number;
    adderss?:string;
    extra_information?: string;
}