const planRouter = require('express').Router()
const planController = require('./plan.controller')
const token = require('../../util/jwt')
const upload = require('../../util/multer')

planController.

userRouter.get("/me", token.authenticateAccessToken, userController.getInfo);
userRouter.post("/me", userController.login);
userRouter.patch("/me", upload.single('Image'), token.authenticateAccessToken, userController.editInfo);
userRouter.head("/me", token.authenticateRefreshToken, userController.updateToken);

userRouter.get("/new", userController.duplicate_id);
userRouter.post("/new", userController.register);

module.exports = userRouter;