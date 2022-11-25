'use strict';
const planRouter = require('express').Router({mergeParams: true})
const tripService = require('../../../controller/trip')
const { tripMiddleware } = require('../../../controller/trip/middleware')
const planService = require('../../../controller/trip/plan')
const token = require('../../../util/jwt')

// Plan
planRouter.use(token.authenticateAccessToken);
planRouter.use(tripMiddleware);

function wrapAsync(fn) {
    return function (req, res, next) {
        fn(req, res, next).catch(next);
    };
}

planRouter.put("/days/:Day/memo", wrapAsync(planService.addMemo));
planRouter.patch("/:PlanId/memo", wrapAsync(planService.editMemo));
planRouter.put("/days/:Day/place", wrapAsync(planService.addPlace));
planRouter.post("/")
module.exports = planRouter;