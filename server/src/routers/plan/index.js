const planRouter = require('express').Router()
const planService = require('../../controller/plan/service')
const token = require('../../util/jwt')
const upload = require('../../util/multer')

planRouter.get("/:PlanId", token.authenticateAccessToken, planService.getPlan);
planRouter.put("/", token.authenticateAccessToken, planService.createPlan);
planRouter.delete("/:PlanId", token.authenticateAccessToken, planService.deletePlan);
module.exports = planRouter;