'use strict';
const tripRouter = require('express').Router({mergeParams: true})
const tripService = require('../../controller/trip')
const tripInviteService = require('../../controller/trip/invite')
const { tripMiddleware } = require('../../controller/trip/middleware')
const token = require('../../util/jwt')
tripRouter.use(token.authenticateAccessToken);

function wrapAsync(fn) {
    return function (req, res, next) {
        fn(req, res, next).catch(next);
    };
}

// Trip
tripRouter.get("/:TripId", tripMiddleware, wrapAsync(tripService.getTrip));
tripRouter.delete("/:TripId", tripMiddleware, wrapAsync(tripService.deleteTrip));
tripRouter.put("/", wrapAsync(tripService.createTrip));
tripRouter.get("/", wrapAsync(tripService.getTripList));

// Trip Participants
tripRouter.put("/:TripId/participants", wrapAsync(tripInviteService.joinPlan));
tripRouter.delete("/:TripId/participants", wrapAsync(tripInviteService.leavePlan));

// Trip Plan Settings
const planRouter = require('./plan')
tripRouter.use('/:TripId/plan', planRouter);

// Trip Chat Settings
const chatRouter = require('./chat')
tripRouter.use('/:TripId/chat', chatRouter);

module.exports = tripRouter;