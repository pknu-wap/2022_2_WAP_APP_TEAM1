'use strict';
const planRouter = require('express').Router({mergeParams: true})
const {tripMiddleware} = require('../../../controller/trip/middleware')
const planService = require('../../../controller/trip/plan')
const token = require('../../../util/jwt')

// Plan
planRouter.use(token.authenticateAccessToken);
planRouter.use(tripMiddleware);

planRouter.put("/days/:Day/memo", planService.addMemo);
planRouter.patch("/:PlanId/memo", planService.editMemo);
planRouter.put("/days/:Day/place", planService.addPlace);
planRouter.put("/flight", planService.addFlight);
planRouter.post("/", planService.commit);
module.exports = planRouter;