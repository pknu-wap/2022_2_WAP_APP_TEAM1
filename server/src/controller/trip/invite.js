'use strict';
const models = require("../../models")
const token = require("../../util/jwt")
const { raw2str } = require("../../util/rawtostr")

module.exports = {
    //Join Plan(PUT)
    async joinPlan(req, res) {
        if (req.body.PlanId === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        let isMember = await models.Plan.isMemberOf(models, req.body.PlanId, req.token.UserId)

        if (isMember) {
            return res.status(403).send({
                message: "Forbidden"
            });
        }
        let plan = await models.Plan.findOne({
            where: {
                PlanId: req.body.PlanId
            }
        });
        if (plan === null) {
            return res.status(404).send({
                message: "Not Found"
            });
        }
        let planParticipant = await models.PlanParticipant.create({
            PlanId: req.body.PlanId,
            UserId: req.token.UserId
        });
        return res.status(200).send({
            message: { status: true, reason: "일정 참여 성공" },
        });
    },
    //Leave Plan(DELETE)
    async leavePlan(req, res) {
        if (req.body.PlanId === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        let isMember = await models.Plan.isMemberOf(models, req.body.PlanId, req.token.UserId)
        if (!isMember) {
            return res.status(403).send({
                message: "Forbidden"
            });
        }
        let plan = await models.Plan.findOne({
            where: {
                PlanId: req.body.PlanId
            }
        });
        if (plan === null) {
            return res.status(404).send({
                message: "Not Found"
            });
        }
        let result = await models.PlanParticipant.destroy({
            where: {
                PlanId: req.body.PlanId,
                UserId: req.token.UserId
            }
        })

        return res.status(200).send({
            message: { status: true, reason: "일정 탈퇴 성공" },
        });
    },
}
