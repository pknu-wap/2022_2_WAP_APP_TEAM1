'use strict';

const { QueryTypes } = require("sequelize");
const db = require("../../util/database");
const raw2str = require("../../util/rawtostr");
const token = require("../../util/jwt");
class PlanParticipant {
    constructor(param) {
        this.PlanId = param.PlanId;
        this.UserId = param.UserId;
        this.dbsetup = require("../../util/database");
    }
    async isMember() {
        try {
            let data = await db.query(`SELECT * FROM DB_PLAN_PARTICIPANT WHERE PLAN_ID = '${this.PlanId}' AND USER_ID = '${this.UserId}'`, { type: QueryTypes.SELECT });
            if (data.length == 0) {
                return false;
            }
            else {
                return true;
            }
        }
        catch (err) {
            console.log(err);
            return false;
        }
    }
}

class Plan {
    constructor(param) {
        this.PlanId = param.PlanId;
        this.OwnerId = param.OwnerId;
        this.StartDate = param.StartDate;
        this.EndDate = param.EndDate;
        this.Name = param.Name;
        this.dbsetup = require("../../util/database");
    }
    async isMemberOf(userId) {
        let participant = new PlanParticipant({ PlanId: this.PlanId, UserId: userId });
        return await participant.isMember();
    }
    async createPlan() {
        try {
            const data = await db.query(`INSERT INTO DB_PLAN (OWNER_ID, START_DATE, END_DATE, NAME) VALUES ('${this.OwnerId}', '${this.StartDate}', '${this.EndDate}', '${this.Name}')`, { type: QueryTypes.INSERT });
            return (data.length == 0) ?
                { status: false, reason: "일정을 생성하는 도중 오류가 발생했습니다." } :
                { status: true, reason: "일정을 생성하는 데 성공하였습니다." };
        }
        catch (err) {
            console.log('Plan->create: Error @ ', err);
            return { status: false, reason: "일정을 생성하는 도중 오류가 발생했습니다." };
        }
    }
    async deletePlan() {
        try {
            const data = await db.query(`DELETE FROM DB_PLAN WHERE PLAN_ID = '${this.PlanId}'`, { type: QueryTypes.DELETE });
            return (data.length == 0) ?
                { status: false, reason: "일정을 삭제하는 도중 오류가 발생했습니다." } :
                { status: true, reason: "일정을 삭제하는 데 성공하였습니다." };
        }
        catch (err) {
            console.log('Plan->delete: Error @ ', err);
            return { status: false, reason: "일정을 삭제하는 도중 오류가 발생했습니다." };
        }
    }
    async updatePlan() {
        try {
            const data = await db.query(`UPDATE DB_PLAN SET START_DATE = '${this.StartDate}', END_DATE = '${this.EndDate}', NAME = '${this.Name}' WHERE PLAN_ID = '${this.PlanId}'`, { type: QueryTypes.UPDATE });
            return (data.length == 0) ?
                { status: false, reason: "일정을 수정하는 도중 오류가 발생했습니다." } :
                { status: true, reason: "일정을 수정하는 데 성공하였습니다." };
        }
        catch (err) {
            console.log('Plan->update: Error @ ', err);
            return { status: false, reason: "일정을 수정하는 도중 오류가 발생했습니다." };
        }
    }
    async getPlan() {
        try {
            const data = await db.query(`SELECT * FROM DB_PLAN WHERE PLAN_ID = '${this.PlanId}'`, { type: QueryTypes.SELECT });
            return (data.length == 0) ?
                { status: false, reason: "일정을 불러오는 도중 오류가 발생했습니다." } :
                { status: true, reason: "일정을 불러오는 데 성공하였습니다.", data: data };
        }
        catch (err) {
            console.log('Plan->get: Error @ ', err);
            return { status: false, reason: "일정을 불러오는 도중 오류가 발생했습니다." };
        }
    }
    async getPlanList() {
        try {
            const data = await db.query(`SELECT * FROM DB_PLAN WHERE OWNER_ID = '${this.OwnerId}'`, { type: QueryTypes.SELECT });
            return (data.length == 0) ?
                { status: false, reason: "일정을 불러오는 도중 오류가 발생했습니다." } :
                { status: true, reason: "일정을 불러오는 데 성공하였습니다.", data: data };
        }
        catch (err) {
            console.log('Plan->get: Error @ ', err);
            return { status: false, reason: "일정을 불러오는 도중 오류가 발생했습니다." };
        }
    }
    
}

module.exports = Plan;