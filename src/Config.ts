export class Config {
    public static site = {
        port: 9090,
        baseUrl: `http://localhost:9090/`
    };
    public static db: any = {
        host: `127.0.0.1`,
        user: "root",
        database: "edeals"
    };
    public static gcmAPIKey: string = "AAAAFlPX7b0:APA91bH6eWFzowQueJiUPPSsiMxr_1FAzQffkTKlOX98Uh3VVuyz4SaH5kJ9iLGNRep_bEw3kq-YSY7X2yTxidpeKkRiHNSIhYVfayCAyytTkV6Y2qgtguwUNjrhEbBwlg2boTkzDo_U";
    public static messages = {
        connectionError: "connection error"
    };
    public static errorCodes = {
        NotFound: "not_found"
    };
    public static groupByOptions = {
        sale: "sale",
        brand: "brand",
        category: "category"
    };
    public static viewAsOptions = {
        list: "list",
        grid: "grid"
    };
}
