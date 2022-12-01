'use strict';
const chatRouter = require('express').Router({mergeParams: true});
const chatService = require('../../../controller/trip/chat');

const multer = require('multer')
function multipartImage(req, res, next) {
    multer().single('Image')(req, res, function (err) {
        return res.status(400).send("Invalid image");
    });
}
chatRouter.post("/image", multipartImage, chatService.uploadImage);

module.exports = chatRouter;