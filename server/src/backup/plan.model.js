'use strict';

const { QueryTypes } = require("sequelize");
const db = require("../../util/database");
const { raw2str } = require("../../util/rawtostr");
const PlanParticipant = require("./participant.model");

/**
 * @typedef {Object} Plan
 * @public
 * @property {string} PlanId - 일정의 고유 ID
 * @property {string} OwnerId - 일정의 소유자 ID
 * @property {string} Name - 일정의 이름
 * @property {string} StartDate - YYYY-MM-DD
 * @property {string} EndDate - YYYY-MM-DD 
 * 이 클래스는 Plan 테이블에 대한 모델입니다.
 */
class Plan {
    constructor(param) {
       
    }
    /**
     * 해당 계획의 참여자인지 여부를 반환합니다.
     * @param {string} planId 계획 ID
     * @param {string} userId 참여자 ID
     * @returns {boolean} 참여자 여부
     */
    static async isMemberOf(planId, userId) {
        
        let participant = new PlanParticipant({ PlanId: planId, UserId: userId });
        return await participant.isMember();
    }
    /**
     * 해당 계획에서 나갑니다.
     * @param {string} planId 
     * @param {string} userId 
     * @returns {json} {status: boolean, reason: string}
     */
    static async exitPlan(planId, userId) {
        try {
            let participant = new PlanParticipant({ PlanId: planId, UserId: userId });
            if (this.isMemberOf(planId, userId)) {
                let data = await participant.exit();
                return { status: true, reason: "일정에서 탈퇴하였습니다." };
            }
            else {
                return { status: false, reason: "일정에 참여하지 않았습니다." };
            }
        }
        catch (err) {
            console.log(err);
            return { status: false, reason: "일정에서 탈퇴하지 못하였습니다." };
        }
    }
    static async joinPlan(planId, userId) {
        try {
            let participant = new PlanParticipant({ PlanId: planId, UserId: userId });
            if (this.isMemberOf(planId, userId)) {
                return { status: false, reason: "이미 일정에 참여하였습니다." };
            }
            else {
                return await participant.join() ? { status: true, reason: "일정에 참여하였습니다." } : { status: false, reason: "일정에 참여하지 못하였습니다." };
            }
        }
        catch (err) {
            console.log(err);
            return { status: false, reason: "일정에 참여하지 못하였습니다." };
        }
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