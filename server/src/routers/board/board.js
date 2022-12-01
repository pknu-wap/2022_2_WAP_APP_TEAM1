'use strict';

const { QueryTypes } = require("sequelize");
const db = require("../../util/database");

class Board{
    constructor(param){
        this.PlanId = param.PlanId;
        this.board = param.board;
        this.dbsetup = require("../../util/database");
    }
    async createBoard(){
        try{
            const data= await db.query(`INSERT INTO PLAN_MEMO (PLAN_ID, MEMO) VALUES ('${this.PlanId}', '${this.memo}')`, { type: QueryTypes.INSERT });
        }
        catch(err){
            console.log(err);
            return {status: false, reason: "메모 생성에 실패했습니다."};
        }
    }
    async getMemo(){
        try{
            const data = await db.query(`SELECT * FROM PLAN_MEMO WHERE PLAN_ID = '${this.PlanId}'`, { type: QueryTypes.SELECT });
            return (data.length == 0) ?
                {status: false, reason: "메모가 없습니다."} :
                {status: true, reason: "메모를 불러왔습니다.", data: data};
        }
        catch(err){
            console.log(err);
            return {status: false, reason: "메모 불러오기에 실패했습니다."};
        }
    }
    async updateMemo(){
        try{
            const data = await db.query(`UPDATE PLAN_MEMO SET MEMO = '${this.memo}' WHERE PLAN_ID = '${this.PlanId}'`, { type: QueryTypes.UPDATE });
            return (data.length == 0) ?
                {status: false, reason: "메모가 없습니다."} :
                {status: true, reason: "메모를 수정했습니다."};
        }
        catch(err){
            console.log(err);
            return {status: false, reason: "메모 수정에 실패했습니다."};
        }
    
    }
    async deleteMemo(){
        try{
            const data = await db.query(`DELETE FROM PLAN_MEMO WHERE PLAN_ID = '${this.PlanId}'`, { type: QueryTypes.DELETE });
            return (data.length == 0) ?
                {status: false, reason: "메모가 없습니다."} :
                {status: true, reason: "메모를 삭제했습니다."};
        }
        catch(err){
            console.log(err);
            return {status: false, reason: "메모 삭제에 실패했습니다."};
        }
    }
}

module.exports = Memo;