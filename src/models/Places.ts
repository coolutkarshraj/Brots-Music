export interface Places{
    id ?:number;
    place_name?:String;
    overview?:String; 
    description?:string; 
    state_id?:number; 
    country_id?:number; 
    imageUrl?: String;
    placeStatus?: number;
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
    city_id?:string;
    town_id?: string;
}