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
            OwnerId: req.token.userId,
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
            if (plan.OwnerId !== req.token.userId) {
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
        models.Plan.isMemberOf(models, req.params.PlanId, req.token.userId).then(isMember => {
            if (isMember === false) {
                return res.status(403).send({
                    message: "Forbidden"
                });
            }
            models.Plan.findOne({
                where: {
                    PlanId: req.params.PlanId
                },
                include: [{
                    model: models.PlanParticipant,
                    attributes: ["UserId"],
                    include: [{
                        model: models.User,
                        attributes: ["UserId", "Nickname", "PhoneNum", "ProfileImage"]
                    }]
                }]
            }).then(plan => {
                plan = raw2str(plan);
                models.PlanDetail.findOne({
                    where: {
                        PlanId: plan.PlanId
                    },
                    include: [{
                        model: models.PlanMemo,
                        attributes: ["PlaceId", "Content"]
                    }, {
                        model: models.PlanPlace,
                        attributes: ["PlaceId"]
                    }]
                }).then(planDetail => {
                    planDetail = raw2str(planDetail);
                    return res.status(200).send(planDetail);
                }).catch(err => {
                    return res.status(500).send({
                        message: err.message
                    });
                });
                //plan = JSON.parse(JSON.stringify(raw2str(plan).dataValues));
                //plan.Participants = plan.PlanParticipants.map(participant => participant.User);
                //delete plan.PlanParticipants;
                //return res.status(200).send(plan);
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
    //Get Plan List(GET)
    getPlanList(req, res) {
        models.User.findAll({
            attributes: ["UserId"],
            where: {
                UserId: req.token.userId
            },
            include: [
                { model: models.Plan, attributes: ["PlanId", "StartDate", "EndDate", "Name", "Region"] },
                { model: models.PlanParticipant, attributes: ["PlanId"], include: [{ model: models.Plan, attributes: ["PlanId", "StartDate", "EndDate", "Name", "Region"] }] }
            ]
        }).then(plans => {
            plans = raw2str(plans);
            plans = plans.map(plan => plan.Plans).flat().concat(plans.map(plan => plan.PlanParticipants).flat().map(participant => participant.Plan));
            return res.status(200).send(plans);
        }).catch(err => {
            return res.status(500).send({
                message: err.message
            });
        });
    }
}
