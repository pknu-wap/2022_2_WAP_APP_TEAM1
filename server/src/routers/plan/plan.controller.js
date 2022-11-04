"use strict";
const Plan = require("./plan.model")

const process = {
    /* NEED TOKEN */
    /* 
        Create Plan(POST)
            req.token.userId <= OwnerId
            req.body.StartDate <= StartDate
            req.body.EndDate <= EndDate
            req.body.Name <= Name
    */
    createPlan: async(req, res) => {
        req.body.OwnerId = req.token.userId;
        let plan = new Plan(req.body);
        return res.send(await plan.createPlan());
    },
    /*
        Get Plan(GET)
            req.token.userId <= UserId
            req.query.PlanId <= PlanId
    */
    getPlan: async(req, res) => {
        req.query.UserId = req.token.userId;
        let plan = new Plan(req.query);
        if (await plan.isMemberOf(req.query.userId)) {
            return res.send(await plan.getPlan());
        }
        else {
            return res.send({ status: false, reason: "일정에 참여하지 않았습니다." });
        }
    }
}