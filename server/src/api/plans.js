"use strict"
const Plan = require("../model/db_plan");
const PlanParticipant = require("../model/db_plan_participant");

const process = {
    create: async (req, res) => {
        let plan = new Plan(req.body);
        return res.send(await plan.create());
    },
    delete: async (req, res) => {
        let plan = new Plan(req.body);
        return res.send(await plan.delete());
    },
    update: async (req, res) => {
        let plan = new Plan(req.body);
        return res.send(await plan.update());
    },
    getPlan: async (req, res) => {
        return res.send(await Plan.getPlan(req.query.PlanId));
    },
    getPlanList: async (req, res) => {
        let plan = new Plan(req.query);
        return res.send(await plan.getPlanList());
    },
    getParticipantList: async (req, res) => {
        return res.send(await PlanParticipant.getParticipants(req.query.PlanId));
    },
    addParticipant: async (req, res) => {
        let participant = new PlanParticipant(req.body);
        return res.send(await participant.create());
    },
    deleteParticipant: async (req, res) => {
        let participant = new PlanParticipant(req.body);
        return res.send(await participant.delete());
    }
}

module.exports = process