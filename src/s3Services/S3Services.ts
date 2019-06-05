var aws = require('aws-sdk');
var multer = require('multer');
var multerS3 = require('multer-s3');
var path = require('path')


aws.config.update({
    secretAccessKey: 'FvCiMmR3QxMT1i+Yg7lD/drMVig0WpGo2T2F/bd4', 
    accessKeyId: 'AKIAJQS2ZF3V7VJO342A',  
	  region: 'ap-south-1'
});
 
var s3 = new aws.S3({ })
    const fileFilterimage = (req, file, cb) => {
      if (file.mimetype === 'image/jpg' || file.mimetype === 'image/png' ||file.mimetype === 'image/jpeg' ) {
          cb(null, true)
      } else {
          cb(new Error('Invalid Mime Type, only JPEG and PNG'), false);
      }
    }

 const uploadProfileImage = multer({
    storage: multerS3({
      fileFilterimage,
      s3,
      bucket: 'high-mountains-app-icon',
      acl: 'public-read',
      metadata: function (req, file, cb) {
        cb(null, {fieldName: 'TESTING_META_DATA1!'});
      },
      key: function (req, file, cb) {
        cb(null, Date.now().toString()+file['originalname'])
      }
    })
  })

  

module.exports = uploadProfileImage;