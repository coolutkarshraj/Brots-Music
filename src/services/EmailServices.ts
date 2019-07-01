const fs = require('fs');
const vash = require('vash');
const path = require('path');
import {EmailData} from '../models/EmailData';
import {Logger} from './common/Logger';
var nodemailer = require('nodemailer');
var smtpTransport = require('nodemailer-smtp-transport');
var mailAccountUser = 'highmountainstest@gmail.com'
var mailAccountPassword = 'saurrav123'
var transport = nodemailer.createTransport(smtpTransport({
    service: 'gmail',
    host: 'smtp.gmail.com',
    port: 465,
    secure: true,
    auth: {
        user: mailAccountUser,
        pass: mailAccountPassword
    },
  
}))
export class EmailService {
    public sendMail(emailData: EmailData, templateModel, templateName: string){
            const filePath = path.resolve(__dirname, '../../', 'mailTemplate');
            fs.readFile(filePath + '/' + templateName, 'utf8', (err, htmlString) => {
                if (err) {
                    console.log('template not found');
                    console.log(err);
                   return;
                }
                const template = vash.compile(htmlString);
                const finalHtml = template(templateModel);
                const mailOptions: any = {
                    from: transport.mail,
                    to: emailData.email,
                    subject: emailData.subject,
                    html: finalHtml,
                    auth: transport.auth
                };
                transport.sendMail(mailOptions, (err1, responseStatus) => {
                    if (err) {
                        Logger.logInfo(`Error sending mail to ${emailData.email} successfully.`, err1);
                    }
                    Logger.logInfo(`email sent to ${emailData.email} successfully.`, responseStatus);
                });
            });
    }
}