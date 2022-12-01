const models = require("../../models")
const {raw2str} = require("../../util/rawtostr")

async function tripMiddleware(req, res, next) {
    const {UserId} = req.token;
    const {TripId} = req.params;
    let trip = raw2str(await models.Trip.findByPk(TripId));
    if (trip == null) {
        return res.status(404).send({status: false, reason: `tripMiddleware: ${TripId} not found`});
    }
    if (!await models.Trip.isMemberOf(models, TripId, UserId)) {
        return res.status(403).send({status: false, reason: `tripMiddleware: ${UserId} is not a member of ${TripId}`});
    }
    req.trip = trip;
    next();
}

module.exports = {tripMiddleware}