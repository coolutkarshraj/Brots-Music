var aws = require('aws-sdk');
var multer = require('multer');
var multerS3 = require('multer-s3');
var path = require('path')

var s3 = new aws.S3({ })

var fileFilter = (req, file, cb) => {
    if (file.mimetype === 'music/AAC' || file.mimetype === 'music/AVI' ||file.mimetype === 'music/mp3' ||file.mimetype === 'music/AVI') {
        cb(null, true)
    } else {
        cb(new Error('Invalid Mime Type, only mp3,AVI and AAC'), false);
    }
  }


const uploadcategory = multer({

    storage: multerS3({
    fileFilter,
      s3,
      bucket: 'high-mountains-app-icon',
      acl: 'public-read',
      metadata: function (req, file, cb) {
          console.log(file)
        cb(null, {fieldName: 'TESTING_META_DATA3!'});
      },
      key: function (req, file, cb) {
        cb(null, Date.now().toString()+file['originalname'])
      }
    })
  })


  module.exports = uploadcategory;