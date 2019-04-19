export class Config {
    public static site = {
        port: 9000,
        baseUrl: `http://localhost:9000/`
    };
    public static db: any = {
        // host: `testingdb.ctifvjah2djq.ap-south-1.rds.amazonaws.com`,
        // port:"3306",
        // user: "TestingDb",
        // database: "high_mountains",
        // password:"TestingDb"
         host: `127.0.0.1`,
         user:"root",
         password: '',
         database: "high_mountains",   
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
        registrationVerify: `registrationVerification.html`,
        registrationSuccessfull:`registration_successfull.html`,
        alreadyLoggedInMail:`alreadyloggedIn.html`,
        loggedInMail:`after_login.html`,
        updatePassword:`update_password.html`
    };
   
}
