'use strict';

const { QueryTypes } = require("sequelize");
const db = require("../util/database");
const buf2hex = require("../util/rawtostr").sqlswap;
class Plan {
    constructor(pData) {
        this.PlanId = pData.PlanId;
        this.OwnerId = pData.OwnerId;
        this.StartDate = pData.StartDate;
        this.EndDate = pData.EndDate;
        this.Name = pData.Name;
    };

    async create() {
        try {
            const data = await db.query(`INSERT INTO DB_PLAN (OWNER_ID, START_DATE, END_DATE, NAME) VALUES ('${this.OwnerId}', '${this.StartDate}', '${this.EndDate}', '${this.Name}')`, { type: QueryTypes.INSERT });
            data = await buf2hex(data);
            return (data.length == 0) ?
                { status: false, reason: "일정을 생성하는 도중 오류가 발생했습니다." } :
                { status: true, reason: "일정을 생성하는 데 성공하였습니다." };
        }
        catch (err) {
            console.log('Plan->create: Error @ ', err);
            return { status: false, reason: "일정을 생성하는 도중 오류가 발생했습니다." };
        }
    }
    async delete() {
        try {
            const data = await db.query(`DELETE FROM DB_PLAN WHERE PLAN_ID = '${this.PlanId}'`, { type: QueryTypes.DELETE });
            data = await buf2hex(data);
            return (data.length == 0) ?
                { status: false, reason: "일정을 삭제하는 도중 오류가 발생했습니다." } :
                { status: true, reason: "일정을 삭제하는 데 성공하였습니다." };
        }
        catch (err) {
            console.log('Plan->delete: Error @ ', err);
            return { status: false, reason: "일정을 삭제하는 도중 오류가 발생했습니다." };
        }
    }
    async update() {
        try {
            const data = await db.query(`UPDATE DB_PLAN SET START_DATE = '${this.StartDate}', END_DATE = '${this.EndDate}', NAME = '${this.Name}' WHERE PLAN_ID = '${this.PlanId}'`, { type: QueryTypes.UPDATE });
            data = await buf2hex(data);
            return (data.length == 0) ?
                { status: false, reason: "일정을 수정하는 도중 오류가 발생했습니다." } :
                { status: true, reason: "일정을 수정하는 데 성공하였습니다." };
        }
        catch (err) {
            console.log('Plan->update: Error @ ', err);
            return { status: false, reason: "일정을 수정하는 도중 오류가 발생했습니다." };
        }
    }
    async getPlanList() {
        try {
            var data = await db.query(`SELECT * FROM DB_PLAN WHERE OWNER_ID = '${this.OwnerId}'`, { type: QueryTypes.SELECT });
            data = await buf2hex(data);
            return (data.length == 0) ?
                { status: false, reason: "일정을 불러오는 도중 오류가 발생했습니다." } :
                { status: true, reason: "일정을 불러오는 데 성공하였습니다.", data: data };
        }
        catch (err) {
            console.log('Plan->getPlanList: Error @ ', err);
            return { status: false, reason: "일정을 불러오는 도중 오류가 발생했습니다." };
        }
    }
    static async getPlan(planId) {
        try {
            const data = await db.query(`SELECT * FROM DB_PLAN WHERE PLAN_ID = '${planId}'`, { type: QueryTypes.SELECT });
            data = await buf2hex(data);
            return (data.length == 0) ?
                { status: false, reason: "일정을 불러오는 도중 오류가 발생했습니다." } :
                { status: true, reason: "일정을 불러오는 데 성공하였습니다.", data: data[0] };
        }
        catch (err) {
            console.log('Plan->getPlan: Error @ ', err);
            return { status: false, reason: "일정을 불러오는 도중 오류가 발생했습니다." };
        }
    }
}

module.exports = Plan;