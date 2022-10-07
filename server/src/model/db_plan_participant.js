'use strict';

const { QueryTypes } = require("sequelize");
const db = require("../util/database");

class PlanParticipant {
    constructor(pData) {
        this.PlanId = pData.PlanId;
        this.UserId = pData.UserId;
    }

    async create() {
        try {
            const data = await db.query(`INSERT INTO DB_PLAN_PARTICIPANT (PLAN_ID, USER_ID) VALUES ('${this.PlanId}', '${this.UserId}')`, { type: QueryTypes.INSERT });
            return (data.length == 0) ?
                { status: false, reason: "일정 참가자를 추가하는 도중 오류가 발생했습니다." } :
                { status: true, reason: "일정 참가자를 추가하는 데 성공하였습니다." };
        }
        catch (err) {
            console.log('PlanParticipant->create: Error @ ', err);
            return { status: false, reason: "일정 참가자를 추가하는 도중 오류가 발생했습니다." };
        }
    }

    async delete() {
        try {
            const data = await db.query(`DELETE FROM DB_PLAN_PARTICIPANT WHERE PLAN_ID = '${this.PlanId}' AND USER_ID = '${this.UserId}'`, { type: QueryTypes.DELETE });
            return (data.length == 0) ?
                { status: false, reason: "일정 참가자를 삭제하는 도중 오류가 발생했습니다." } :
                { status: true, reason: "일정 참가자를 삭제하는 데 성공하였습니다." };
        }
        catch (err) {
            console.log('PlanParticipant->delete: Error @ ', err);
            return { status: false, reason: "일정 참가자를 삭제하는 도중 오류가 발생했습니다." };
        }
    }

    static async getParticipants(planId) {
        try {
            const data = await db.query(`SELECT * FROM DB_PLAN_PARTICIPANT WHERE PLAN_ID = '${planId}'`, { type: QueryTypes.SELECT });
            return (data.length == 0) ?
                { status: false, reason: "일정 참가자를 가져오는 도중 오류가 발생했습니다." } :
                { status: true, reason: "일정 참가자를 가져오는 데 성공하였습니다.", data: data };
        }
        catch (err) {
            console.log('PlanParticipant->getParticipants: Error @ ', err);
            return { status: false, reason: "일정 참가자를 가져오는 도중 오류가 발생했습니다." };
        }
    }
}

module.exports = PlanParticipant;