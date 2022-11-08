'use strict';
const models = require("../../models")
const token = require("../../util/jwt")
const { raw2str } = require("../../util/rawtostr")

module.exports = {
    //Join Plan(PUT)
    joinPlan(req, res) {
        if (req.body.PlanId === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        models.Plan.isMemberOf(models, req.body.PlanId, req.token.UserId).then(isMember => {
            if (isMember) {
                return res.status(403).send({
                    message: "Forbidden"
                });
            }
        }).catch(err => {
            return res.status(500).send({
                message: err.message
            });
        });
        models.Plan.findOne({
            where: {
                PlanId: req.body.PlanId
            }
        }).then(plan => {
            if (plan === null) {
                return res.status(404).send({
                    message: "Not Found"
                });
            }
            models.PlanParticipant.create({
                PlanId: req.body.PlanId,
                UserId: req.token.UserId
            }).then(planParticipant => {
                return res.status(200).send({
                    message: { status: true, reason: "일정 참여 성공" },
                });
            }).catch(err => {
                return res.status(500).send({
                    message: err.message
                });
            });
        }).catch(err => {
            return res.status(500).send({
                message: err.message
            });
        });
    },
    //Leave Plan(DELETE)
    leavePlan(req, res) {
        if (req.body.PlanId === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        models.Plan.isMemberOf(models, req.body.PlanId, req.token.UserId).then(isMember => {
            if (!isMember) {
                return res.status(403).send({
                    message: "Forbidden"
                });
            }
        }).catch(err => {
            return res.status(500).send({
                message: err.message
            });
        });
        models.Plan.findOne({
            where: {
                PlanId: req.body.PlanId
            }
        }).then(plan => {
            if (plan === null) {
                return res.status(404).send({
                    message: "Not Found"
                });
            }
            models.PlanParticipant.destroy({
                where: {
                    PlanId: req.body.PlanId,
                    UserId: req.token.UserId
                }
            }).then(planParticipant => {
                return res.status(200).send({
                    message: { status: true, reason: "일정 탈퇴 성공" },
                });
            }).catch(err => {
                return res.status(500).send({
                    message: err.message
                });
            });
        }).catch(err => {
            return res.status(500).send({
                message: err.message
            });
        });
    }
}
