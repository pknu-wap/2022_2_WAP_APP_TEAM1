'use strict';

const { QueryTypes } = require("sequelize");
const db = require("../../util/database");

class Board{
    constructor(param){

        this.BoardId = param.BoardId;
        this.WriterId=param.WriterId;
        this.Content=param.Content;

        this.PlanId = param.PlanId;
        this.PlanDetailId= param.PlanDetailId;
        this.PlaceId =param.PlaceId;
        this.OrderIndex = param.OrderIndex;

        this.PlaceId = param.PlaceId;
        this.Content = param.Content;
        this.CreatedAt = param.CreatedAt;
        this.UpdatedAt=param.UpdatedAt

        this.dbsetup = require("../../util/database");
    }
    async createBoard(){ //detail_db에서 detailID에 해당하는 다른 정보들도 가져오기, 순서에 맞춰서 정렬(작은 것부터)/ 항공권 선택 못하게 막기
        try{
            const planDetail=await db.query(`SELECT * FROM DB_PLAN_PLACE JOIN DB_PLAN_MEMO WHERE PLAN_DETAIL_ID ='${this.PlanDetailId} ORDER BY ORDER INDEX'`,{type: QueryTypes.SELECT});
            if (planDetail==0){
                return {status:false, reason: "아무것도 선택하지 않았습니다."}
            }
            else{
                param.content=planDetail
                const data=await db.query(`INSERT INTO DB_BOARD (CONTENT) VALUES ('${this.Content}')`);
            }
            const data= await db.query(`INSERT INTO DB_BOARD (BOARD_ID, WRITER_ID, CONTENT) VALUES ('${this.BoardId}', '${this.WriterId}','${this.Content}')`, { type: QueryTypes.INSERT });
            return (data.length == 0) ?
                {status: false, reason:"계획 추천하기에 실패했습니다" }:
                {status: true, reason:"계획 추천하기에 성공했습니다" };
            
        }
        catch{
            console.log(err);
            return {status: false, reason: "계획 추천하기에 실패했습니다."};
        }
    }
    async getBoard(){ //board에서 detailID 검색
        try{
            const data = await db.query(`SELECT * FROM DB_BOARD WHERE BOARD_ID = '${this.BoardId}'`, { type: QueryTypes.SELECT });
            return (data.length == 0) ?
                {status: false, reason: "해당 글이 없습니다."} :
                {status: true, reason: "글을 불러왔습니다.", data: data};
        }
        catch{
            console.log(err);
            return {status: false, reason: "게시판 불러오기에 실패했습니다."};
        }
    }
    async updateBoard(){ //board에서 다른 컬럼 다 업뎃 
        try{
            const data= await db.query(`UPDATE DB_BOARD SET WRITER_ID = '${this.WriterId}', CONTENT='${this.Content}' WHERE BOARD_ID = '${this.BoardId}'`, { type: QueryTypes.UPDATE });
            return (data.length == 0) ?
                {status: false, reason:"게시판 수정에 실패했습니다" }:
                {status: true, reason:"게시판 수정에 성공했습니다" };
        }
        catch{
            console.log(err);
            return {status: false, reason: "게시판 수정에 실패했습니다."};
        }
    }
    async deleteBoard(){ //board에서 detailID 지우기
        try{
            const data= await db.query(`DELETE FROM DB_BOARD WHERE BOARD_ID = '${this.BoardId}'`, { type: QueryTypes.DELETE });
            return (data.length == 0) ?
                {status: false, reason:"게시판 삭제에 실패했습니다" }:
                {status: true, reason:"게시판 삭제에 성공했습니다" };
        }
        catch{
            console.log(err);
            return {status: false, reason: "게시판 삭제에 실패했습니다."};
        }
    }
}

module.exports = Board;