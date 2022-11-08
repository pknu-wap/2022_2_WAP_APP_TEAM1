const planRouter = require('express').Router()
const planService = require('../../controller/plan')
const planInviteService = require('../../controller/plan/invite')
const token = require('../../util/jwt')
const upload = require('../../util/multer')

// Plan
planRouter.get("/:PlanId", token.authenticateAccessToken, planService.getPlan);
planRouter.put("/", token.authenticateAccessToken, planService.createPlan);
planRouter.delete("/:PlanId", token.authenticateAccessToken, planService.deletePlan);

// Plan Member
planRouter.put("/:PlanId/participants", token.authenticateAccessToken, planInviteService.joinPlan);
planRouter.delete("/:PlanId/participants", token.authenticateAccessToken, planInviteService.leavePlan);
module.exports = planRouter;