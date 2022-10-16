const userRouter = require('express').Router()
const userController = require('./user.controller')
const token = require('../../util/jwt')
const upload = require('../../util/multer')
userRouter.post("/login", userController.login);
userRouter.post("/register", userController.register);
userRouter.get("/duplicate", userController.duplicate_id);
userRouter.get("/me", token.authenticateAccessToken, userController.getInfo);
userRouter.post("/me", upload.single('Image'), token.authenticateAccessToken, userController.editInfo);

module.exports = userRouter;