'use strict';
const models = require("../../models")
const token = require("../../util/jwt")
const { raw2str } = require("../../util/rawtostr")

module.exports = {
    //Create Plan(PUT)
    createPlan(req, res) {
        if (req.body.StartDate === undefined || req.body.EndDate === undefined || req.body.Name === undefined || req.body.Region === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        models.Plan.create({
            OwnerId: req.token.UserId,
            StartDate: req.body.StartDate,
            EndDate: req.body.EndDate,
            Name: req.body.Name,
            Region: req.body.Region
        }).then(plan => {
            return res.status(200).send({
                message: { status: true, reason: "일정 생성 성공", PlanId: plan.PlanId },
            });
        }).catch(err => {
            return res.status(500).send({
                message: err.message
            });
        });
    },
    //Delete Plan(DELETE)
    deletePlan(req, res) {
        if (req.body.PlanId === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
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
            if (plan.OwnerId !== req.token.UserId) {
                return res.status(403).send({
                    message: "Forbidden"
                });
            }
            plan.destroy();
            return res.status(200).send({
                message: { status: true, reason: "일정 삭제 성공" },
            });
        }).catch(err => {
            return res.status(500).send({
                message: err.message
            });
        });
    },
    //Get Plan(GET)
    getPlan(req, res) {
        if (req.params.PlanId === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        models.Plan.findOne({
            where: {
                PlanId: req.params.PlanId
            },
            include: [{
                model: models.PlanParticipant,
            }]
        }).then(plan => {
            plan = raw2str(plan);
            return res.status(200).send(plan);
        }).catch(err => {
            return res.status(500).send({
                message: err.message
            });
        }
        );
    }
}
