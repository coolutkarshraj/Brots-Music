export interface CountriesList{
    id ?:number;
    continentId?:String;
    country_code?:String; 
    country_name?:string; 
    countryStatus?:number; 
    imageUrl?: String;
    overview?: string;
    about?: string;
    description?: string;
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
