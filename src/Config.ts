export class Config {
    public static site = {
        port: 9090,
        baseUrl: `http://localhost:9090/`
    };
    public static db: any = {
        host: `highmountains.ctifvjah2djq.ap-south-1.rds.amazonaws.com`,
        port:"3306",
        user: "high_mountains",
        database: "high_mountains",
        password:"high_mountains"
    
    };
    public static gcmAPIKey: string = "AAAAFlPX7b0:APA91bH6eWFzowQueJiUPPSsiMxr_1FAzQffkTKlOX98Uh3VVuyz4SaH5kJ9iLGNRep_bEw3kq-YSY7X2yTxidpeKkRiHNSIhYVfayCAyytTkV6Y2qgtguwUNjrhEbBwlg2boTkzDo_U";
    public static messages = {
        connectionError: "connection error"
    };
    public static errorCodes = {
        NotFound: "not_found"
    };
    public static mailTemplate: any = {
        forgetPassword: `forgot_password.html`,
        registrationSuccessfull: `registrationVerification.html`
    };
   
}
