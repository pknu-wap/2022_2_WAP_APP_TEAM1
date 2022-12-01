'use strict';
const models = require("../../models")
const token = require("../../util/jwt")
const {raw2str} = require("../../util/rawtostr")

module.exports = {
    //Join Plan(PUT)
    async joinTrip(req, res) {
        const {TripId} = req.params;
        const {UserId} = req.token;
        if (TripId == undefined) {
            return res.status(400).send({status: false, reason: `TripId: ${TripId} is not valid`});
        }
        console.log(`joinTrip: userId : ${UserId}, tripId : ${TripId}`);
        let isMember = await models.Trip.isMemberOf(models, TripId, UserId);
        if (isMember) {
            return res.status(403).send({
                status: false,
                reason: `User: ${UserId} is already a member of Trip: ${TripId}`
            });
        }
        let trip = await models.Trip.findOne({
            where: {
                TripId: TripId
            }
        });
        if (trip == null) {
            return res.status(404).send({status: false, reason: `Trip: ${TripId} does not exist`});
        }
        if (trip.OwnerId == UserId) {
            return res.status(403).send({status: false, reason: `User: ${UserId} is a owner of Trip: ${TripId}`});
        }
        await models.TripParticipant.create({
            TripId: TripId,
            UserId: UserId
        });
        return res.status(200).send({status: true, reason: "일정 참여 성공"});
    },
    /*//Leave Plan(DELETE)
    async leaveTrip(req, res) {
        const { TripId } = req.params;
        const { UserId } = req.token;
        if (TripId == undefined) {
            return res.status(400).send({ status: false, reason: `TripId: ${TripId} is not valid` });
        }
        let isMember = await models.Trip.isMemberOf(models, TripId, UserId)
        if (!isMember) {
            return res.status(403).send({ status: false, reason: `User: ${UserId} is not a member of Trip: ${TripId}` });
        }
        let trip = await models.Trip.findOne({
            where: {
                TripId: TripId
            }
        });
        if (trip == null) {
            return res.status(404).send({ status: false, reason: `Trip: ${TripId} does not exist` });
        }
        if (trip.OwnerId == UserId) {
            return res.status(403).send({ status: false, reason: `User: ${UserId} is the owner of Trip: ${TripId}` });
        }
        await models.TripParticipant.destroy({
            where: {
                TripId: TripId,
                UserId: UserId
            }
        });
        return res.status(200).send({status: true, reason: "일정 탈퇴 성공"});
    },*/
}
