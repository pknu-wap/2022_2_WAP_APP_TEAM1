const planRouter = require('express').Router()
const planService = require('../../controller/plan/service')
const token = require('../../util/jwt')
const upload = require('../../util/multer')

planRouter.get("/:PlanId", token.authenticateAccessToken, planService.getPlan);

module.exports = planRouter;