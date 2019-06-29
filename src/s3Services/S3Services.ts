var aws = require('aws-sdk');
var multer = require('multer');
var multerS3 = require('multer-s3');
var path = require('path')


aws.config.update({
    secretAccessKey: 'X2bSgV7W0+mimqOsuSytN/0XyZVObH5iu0vL5qQ1', 
    accessKeyId: 'AKIAISYCY2TUA5K4DV7Q',  
	  region: 'us-east-1'
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
      bucket: 'highmountainsimages',
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