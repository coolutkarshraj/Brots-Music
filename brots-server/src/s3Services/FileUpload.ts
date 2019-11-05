var aws = require('aws-sdk');
var multer = require('multer');
var multerS3 = require('multer-s3');
var path = require('path')


aws.config.update({
    // secretAccessKey: 'buajeG5I/xn97/9hahSjHRLoy+OgUtcdEcufwtPl', 
    // accessKeyId: 'AKIAJ2GR7RWNLYM2SWOQ',  
    // region: 'us-west-2'
    secretAccessKey: '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', 
    accessKeyId: 'AKIAJ4EY2E27YRYU5RUA',  
	  region: 'us-west-2'
});
 
var s3 = new aws.S3({ })



  const fileFilterimage = (req, file, cb) => {
    if (file.mimetype === 'image/jpg' || file.mimetype === 'image/png' ||file.mimetype === 'image/jpeg' ) {
        cb(null, true)
    } else {
        cb(new Error('Invalid Mime Type, only JPEG and PNG'), false);
    }
  }



 const uploadImage = multer({

    storage: multerS3({
      fileFilterimage,
      s3,
      bucket: 'brots-icon',
      acl: 'public-read',
     
      metadata: function (req, file, cb) {
        cb(null, {fieldName: 'TESTING_META_DATA1!'});
      },
      key: function (req, file, cb) {
        cb(null, Date.now().toString()+file['originalname'])
      }
    })
  })

  

module.exports = uploadImage;
