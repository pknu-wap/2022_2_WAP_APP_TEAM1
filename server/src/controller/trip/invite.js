'use strict';
const models = require("../../models")
const token = require("../../util/jwt")
const { raw2str } = require("../../util/rawtostr")

module.exports = {
    //Join Plan(PUT)
    async joinTrip(req, res) {
        if (req.body.TripId === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        let isMember = await models.Trip.isMemberOf(models, req.body.TripId, req.token.UserId)

        if (isMember) {
            return res.status(403).send({
                message: "Forbidden"
            });
        }
        let trip = await models.Trip.findOne({
            where: {
                TripId: req.body.TripId
            }
        });
        if (trip === null) {
            return res.status(404).send({
                message: "Not Found"
            });
        }
        let tripParticipant = await models.TripParticipant.create({
            TripId: req.body.TripId,
            UserId: req.token.UserId
        });
        return res.status(200).send({
            message: { status: true, reason: "일정 참여 성공" },
        });
    },
    //Leave Plan(DELETE)
    async leaveTrip(req, res) {
        if (req.body.TripId === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        let isMember = await models.Trip.isMemberOf(models, req.body.TripId, req.token.UserId)
        if (!isMember) {
            return res.status(403).send({
                message: "Forbidden"
            });
        }
        let trip = await models.Trip.findOne({
            where: {
                TripId: req.body.TripId
            }
        });
        if (trip === null) {
            return res.status(404).send({
                message: "Not Found"
            });
        }
        let result = await models.TripParticipant.destroy({
            where: {
                TripId: req.body.TripId,
                UserId: req.token.UserId
            }
        })

        return res.status(200).send({
            message: { status: true, reason: "일정 탈퇴 성공" },
        });
    },
}
