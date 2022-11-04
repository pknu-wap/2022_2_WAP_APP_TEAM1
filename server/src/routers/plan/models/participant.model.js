'use strict';

const { QueryTypes } = require("sequelize");
const db = require("../../util/database");
const { raw2str } = require("../../util/rawtostr");
const token = require("../../util/jwt");
/**
 * @typedef {Object} PlanParticipant
 * @public
 * @property {string} PlanId - 일정의 고유 ID
 * @property {string} UserId - 참여자의 고유 ID
 * 
 * @private
 * @property {Object} dbsetup - sequelize DB 연결 객체
 * 
 * 이 클래스는 PlanParticipant 테이블에 대한 모델입니다.
 * 모듈로 export되지 않으며 오로지 Plan 테이블과의 연동을 위해 사용됩니다.
 */
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
    async exit() {
        try {
            let data = await db.query(`DELETE FROM DB_PLAN_PARTICIPANT WHERE PLAN_ID = '${this.PlanId}' AND USER_ID = '${this.UserId}'`, { type: QueryTypes.DELETE });
            return true;
        }
        catch (err) {
            console.log(err);
            return false;
        }
    }
    async join() {
        try {
            const data = await db.query(`INSERT INTO DB_PLAN_PARTICIPANT (PLAN_ID, USER_ID) VALUES ('${this.PlanId}', '${this.UserId}')`, { type: QueryTypes.INSERT });
            return (data.length == 0) ? false : true;
        }
        catch (err) {
            console.log('Plan->join: Error @ ', err);
            return false;
        }
    }
}
module.exports = PlanParticipant;