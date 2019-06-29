import * as express from "express";
import {Server as HttpServer} from "http";
import {AppRouter} from "./routes/AppRouter";
import * as bodyParser from "body-parser";
import * as path from "path";
import {Config} from "./Config";
import {Logger} from "./services/common/Logger";

 const app: express.Application = express();

app.use(function defaultContentTypeMiddleware(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    req.headers["content-type"] = req.headers["content-type"] || "application/json";
    next();
});

app.use(bodyParser.urlencoded({limit: "10mb", extended: true, parameterLimit: 1000000}));
app.use(bodyParser.json({limit: "10mb"}));

app.use("/storage", express.static(path.join(__dirname, "storage")));

new AppRouter(app);

const httpServer: HttpServer = app.listen(Config.site.port, "18.208.183.9", () => {
    const {address, port} = httpServer.address();
    Logger.log(`Listening on ${address}:${port}`);
});
