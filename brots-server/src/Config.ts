export class Config {
    public static site = {
       port: 9000,
        baseUrl: `http://localhost:9090/`
    };
    public static db: any = {

        // host: `brots.ck8fgawtqpcw.us-west-2.rds.amazonaws.com`,
        // port:"3306",
        // user: "brots",
        // database: "brots",
        // password:"Brots.4root"
        host: `127.0.0.1`,
        user: "root",
        database: "brots",
    };
    public static gcmAPIKey: string = "AAAAgYJ2mIM:APA91bFO4q73h9T4MUp3Uo5d16AxeeXjTTY32EU88ghgTzwS0pBv7MiG-wIpQAhzRu8yOFl3yxYEdp-VoBMu0AjJnXZrVN-sUXmYktBhXEKoCRf2ebWD69PAfy0GpSwhLm7i3Of2_TJB";
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
   

    public static envir: any = {
            "JWT_KEY":"Secret"
    };
}
