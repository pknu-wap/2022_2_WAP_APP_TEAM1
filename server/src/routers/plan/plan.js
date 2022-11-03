'use strict';

const { QueryTypes } = require("sequelize");
const db = require("../../util/database");

class Place{
    constructor(param){
        this.PlanId=param.PlanId;
        this.PlaceId=param.PlaceId;
        this.Latitude=param.Latitude;
        this.Longitude=param.Longitude;
        this.Category=param.Category;
        this.AdministrationCode=param.AdministrationCode;
        this.Name=param.Name;
        this.dbsetup = require("../../util/database");
    }
    async createPlace(){
        try{
            const data = await db.query(`SELECT * FROM PLAN_PLACE WHERE LATITUDE = '${this.Latitude}' AND LONGITUDE = '${this.Longitude}'`, { type: QueryTypes.SELECT });
            if(data.length==0){//위도, 경도가 같은 장소가 없을 경우
                const data =await db.query(`INSERT INTO PLACE (PLAN_ID,PLACE_ID,LATITUDE,LONGITUDE,CATEGORY,ADMINISTRATION_CODE,NAME) VALUES('${this.PlanId}','${this.PlaceId}','${this.Latitude}','${this.Longitude}','${this.Category}','${this.AdministrationCode}','${this.Name}')`);
                let PlanId=await db.query(`SELECT PLAN_ID FROM PLACE order by PLAN_ID desc limit 1`,{type:QueryTypes.SELECT});
                if(PlanId.length==0){//planID가 없을 경우
                   console.log("새로운 장소를 생성하는 도중 오류가 발생했습니다.");
                }
                else{//planID가 있을 경우
                    PlanId= await db.query(`INSERT INTO PLAN_PLACE (PLAN_ID) VALUES ('${this.PlanId}')`, { type: QueryTypes.INSERT });
                }
            }
            else{//위도, 경도가 같은 장소가 있을 경우
                let PlaceId=await db.query(`SELECT PLACE_ID FROM PLACE WHERE LATITUDE = '${this.Latitude}' AND LONGITUDE = '${this.Longitude}'`,{type:QueryTypes.SELECT});
                PlaceId= await db.query(`INSERT INTO PLAN_PLACE (Place_ID) VALUES ('${this.PlaceId}')`, { type: QueryTypes.INSERT });
                return (data.length==0) ?//plan_place에 장소가 없을 경우
                    {status: false, reason:"장소정보를 불러오는 도중 오류가 발생했습니다."}:
                    {status: true, reason:"새로운 장소를 생성했습니다."};
            }
        }
        catch(err){
            console.log(err);
            return { status: false,reason:"장소정보를 불러오는 도중 오류가 발생했습니다."};
        }
    }
    async getPlace(){
        try{
            const data= await db.query(`SELECT * FROM PLAN_PLACE WHERE Place_ID = '${this.PlaceId}'`, { type: QueryTypes.SELECT });
            return (data.length==0) ?
                {status: false, reason:"장소정보를 불러오는 도중 오류가 발생했습니다."}:
                {status: true, reason:"새로운 장소를 생성했습니다."};
        }
        catch(err){
            console.log(err);
            return { status: false,reason:"장소정보를 불러오는 도중 오류가 발생했습니다."};
        }
    }
    async deletePlace(){
        try{
            const data= await db.query(`DELETE FROM PLAN_PLACE WHERE Place_ID = '${this.PlaceId}'`, { type: QueryTypes.DELETE });
            return (data.length==0) ?
                {status: false, reason:"장소정보를 불러오는 도중 오류가 발생했습니다."}:
                {status: true, reason:"새로운 장소를 생성했습니다."};
        }
        catch(err){
            console.log(err);
            return { status: false,reason:"장소정보를 불러오는 도중 오류가 발생했습니다."};
        }
    }
    async updatePlace(){
        try{
            const data= await db.query(`UPDATE PLAN_PLACE SET (Plan_ID,Place_ID) VALUES ('${this.PlanId}','${this.PlaceId}')`, { type: QueryTypes.UPDATE });
            return (data.length==0) ?
                {status: false, reason:"장소정보를 불러오는 도중 오류가 발생했습니다."}:
                {status: true, reason:"새로운 장소를 생성했습니다."};
        }
        catch(err){
            console.log(err);
            return { status: false,reason:"장소정보를 불러오는 도중 오류가 발생했습니다."};
        }
    }
};

module.exports = Place;