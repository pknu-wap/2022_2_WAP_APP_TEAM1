'use strict';
const tripRouter = require('express').Router({mergeParams: true})
const tripService = require('../../controller/trip')
const tripInviteService = require('../../controller/trip/invite')
const { tripMiddleware } = require('../../controller/trip/middleware')

const token = require('../../util/jwt')
tripRouter.use(token.authenticateAccessToken);
// Trip
tripRouter.get("/:TripId", tripMiddleware, tripService.getTrip);
tripRouter.delete("/:TripId", tripMiddleware, tripService.deleteTrip);
tripRouter.put("/", tripService.createTrip);
tripRouter.get("/", tripService.getTripList);

// Trip Participants
tripRouter.put("/:TripId/participants", tripInviteService.joinPlan);
tripRouter.delete("/:TripId/participants", tripInviteService.leavePlan);

// Trip Plan Settings
const planRouter = require('./plan')
tripRouter.use('/:TripId/plan', planRouter);

module.exports = tripRouter;