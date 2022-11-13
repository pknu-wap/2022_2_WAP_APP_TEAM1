'use strict';
const models = require("../../models")
const token = require("../../util/jwt")
const { raw2str } = require("../../util/rawtostr")

module.exports = {
    //Add Memo(PUT)
    async addMemo(req, res) {
        const { PlanId } = req.params;
        const { Content } = req.body;
        const { userId } = req.token;
        let plan = await models.Plan.findByPk(PlanId);
        if (plan === null) {
            return res.status(404).send({
                message: "Not Found"
            });
        }
        let planDetail = await models.PlanDetail.create({
            PlanId: PlanId
        });

        let planMemo = await models.PlanMemo.create({
            PlanDetailId: planDetail.PlanDetailId,
            Content: Content
        })

        res.status(200).json({
            planMemo
        });
    },
    //Create Plan(PUT)
    async createPlan(req, res) {
        if (req.body.StartDate === undefined || req.body.EndDate === undefined || req.body.Name === undefined || req.body.Region === undefined) {
            return res.status(200).send({
                status: false, reason: "Bad Request"
            });
        }
        let plan = raw2str(await models.Plan.create({
            OwnerId: req.token.userId,
            StartDate: req.body.StartDate,
            EndDate: req.body.EndDate,
            Name: req.body.Name,
            Region: req.body.Region
        }));
        if (plan === null) {
            return res.status(200).send({
                status: false, reason: "계획 생성 도중 오류가 발생했습니다."
            });
        }
        return res.status(200).send({ status: true, reason: "일정 생성 성공", PlanId: plan.PlanId });
    },
    //Delete Plan(DELETE)
    async deletePlan(req, res) {
        if (req.body.PlanId === undefined) {
            return res.status(400).send({
                message: "Bad Request"
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
        if (plan.OwnerId !== req.token.userId) {
            return res.status(403).send({
                message: "Forbidden"
            });
        }
        let result = await plan.destroy();
        return res.status(200).send({
            message: { status: true, reason: "일정 삭제 성공" },
        });
    },
    //Get Plan(GET)
    async getPlan(req, res) {
        if (req.params.PlanId === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        console.log('getPlan: userId = ', req.token.userId);
        let isMember = await models.Plan.isMemberOf(models, req.params.PlanId, req.token.userId);
        if (!isMember) {
            return res.status(403).send({
                message: "해당 계획으로부터 참여자가 아닙니다."
            });
        }
        let plan = raw2str(await models.Plan.findByPk(req.params.PlanId));
        let planParticipants = raw2str(await models.PlanParticipant.findAll({
            where: {
                PlanId: plan.PlanId
            },
            include: [{
                model: models.User,
                attributes: ['UserId', 'Nickname', 'ProfileImage']
            }]
        }));
        planParticipants = planParticipants.map(participant => participant.User);
        let planDetails = raw2str(await models.PlanDetail.findAll({
            where: {
                PlanId: req.params.PlanId
            },
            attributes: ["PlanDetailId", "OrderIndex"],
            include: [{
                model: models.PlanMemo,
                attributes: ["Content"]
            }]
        }));
        planDetails = planDetails.sort((a, b) => {
            return a.OrderIndex - b.OrderIndex;
        });

        var result = new Object();
        result.Plan = plan;
        result.PlanParticipants = planParticipants;
        result.PlanDetails = planDetails;

        return res.status(200).send({
            message: { status: true, reason: "일정 조회 성공", result },
        });
    },
    //Get Plan List(GET)
    async getPlanList(req, res) {
        let plans = await models.User.findAll({
            attributes: ["UserId"],
            where: {
                UserId: req.token.userId
            },
            include: [
                { model: models.Plan, attributes: ["PlanId", "StartDate", "EndDate", "Name", "Region"] },
                { model: models.PlanParticipant, attributes: ["PlanId"], include: [{ model: models.Plan, attributes: ["PlanId", "StartDate", "EndDate", "Name", "Region"] }] }
            ]
        });
        plans = raw2str(plans);
        plans = plans.map(plan => plan.Plans).flat().concat(plans.map(plan => plan.PlanParticipants).flat().map(participant => participant.Plan));
        return res.status(200).send(plans);
    }
}
