const userRouter = require('express').Router()
const userService = require('../../controller/user')
const token = require('../../util/jwt')
const multer = require('multer')


userRouter.head("/me", token.authenticateRefreshToken, userService.updateToken)
userRouter.get("/me", token.authenticateAccessToken, userService.getInfo);
userRouter.post("/me", userService.login);
userRouter.patch("/me", multer().single('Image'), token.authenticateAccessToken, userService.updateInfo);

userRouter.get("/new", userService.duplicateCheck);
userRouter.post("/new", userService.register);

module.exports = userRouter;