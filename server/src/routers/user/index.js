const userRouter = require('express').Router()
const userService = require('../../controller/user')
const token = require('../../util/jwt')
const multer = require('multer')
function wrapAsync(fn) {
    return function (req, res, next) {
        fn(req, res, next).catch(next);
    };
}

function multipartImage(req, res, next) {
    multer().single('Image')(req, res, function (err) {
        return res.status(400).send("Invalid image");
    });
}

userRouter.head("/me", token.authenticateRefreshToken, wrapAsync(userService.updateToken));
userRouter.get("/me", token.authenticateAccessToken, wrapAsync(userService.getInfo));
userRouter.post("/me", wrapAsync(userService.login));
userRouter.patch("/me", [multipartImage, token.authenticateAccessToken], wrapAsync(userService.updateInfo));
userRouter.get("/new", wrapAsync(userService.duplicateCheck));
userRouter.post("/new", wrapAsync(userService.register));

module.exports = userRouter;