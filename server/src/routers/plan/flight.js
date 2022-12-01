'use strict';

const { QueryTypes } = require("sequelize");
const db = require("../../util/database");


class Flight{
    constructor(param){
        this.PlanId=param.PlanId;
        this.FlightId=param.FlightId;
        this.FlightName=param.FlightName;
        this.FlightDeparture=param.FlightDeparture;
        this.FlightArrival=param.FlightArrival;
        this.DepartureAirport=param.DepartureAirport;
        this.ArrivalAirport=param.ArrivalAirport;
        this.dbsetup = require("../../util/database");
    }
    /**
     * @param {string} PlanId
     * @param {string} FlightId
     * @param {string} FlightName
     */

    async findFlight(PlanId, FlightId, FlightName){
        try{
            const data = await db.query(`SELECT * FROM PLAN_FLIGHT WHERE PLAN_ID = '${PlanId}' AND FLIGHT_ID = '${FlightId}' AND FLIGHT_NAME = '${FlightName}'`, { type: QueryTypes.SELECT });
            return (data.length ==0) ?
                { status: false,reason:"비행정보를 불러오는 도중 오류가 발생했습니다."}:
                { status: true,reason:"비행정보를 불러오는 데 성공하였습니다.", data: data };
        }
        catch(err){
            console.log(err);
            return { status: false,reason:"비행정보를 불러오는 도중 오류가 발생했습니다."};
        }
    }
    async createFlight(PlanId, FlightId, FlightName, FlightDeparture, FlightArrival, DepartureAirport, ArrivalAirport){
        try{
            const data = await db.query(`INSERT INTO PLAN_FLIGHT (PLAN_ID, FLIGHT_ID, FLIGHT_NAME, FLIGHT_DEPARTURE, FLIGHT_ARRIVAL, DEPARTURE_Airport, ARRIVAL_Airport) VALUES ('${PlanId}', '${FlightId}', '${FlightName}', '${FlightDeparture}', '${FlightArrival}', '${DepartureAirport}', '${ArrivalAirport}')`, { type: QueryTypes.INSERT });
            return (data.length ==0) ?
                {   status: false, reason: "비행정보를 생성하는 도중 오류가 발생했습니다."}:
                {   status: true,reason:"비행정보를 생성하는 데 성공하였습니다."};
        }
        catch(err){
            console.log(err);
            return { status: false,reason:"비행정보를 생성하는 도중 오류가 발생했습니다."};
        }
    }
    async deleteFlight(PlanId, FlightId){
        try{
            const data = await db.query(`DELETE FROM PLAN_FLIGHT WHERE PLAN_ID = '${PlanId}' AND FLIGHT_ID = '${FlightId}'`, { type: QueryTypes.DELETE });
            return (data.length ==0) ?
                {   status: false, reason: "비행정보를 삭제하는 도중 오류가 발생했습니다."}:
                {   status: true,reason:"비행정보를 삭제하는 데 성공하였습니다."};
        }
        catch(err){
            console.log(err);
            return { status: false,reason:"비행정보를 삭제하는 도중 오류가 발생했습니다."};
        }
    }
    async editFlight(PlanId, FlightId, FlightName, FlightDeparture, FlightArrival, DepartureAirport, ArrivalAirport){
        try{
            const data = await db.query(`UPDATE PLAN_FLIGHT SET(PLAN_ID, FLIGHT_ID, FLIGHT_NAME, FLIGHT_DEPARTURE, FLIGHT_ARRIVAL, DEPARTURE_Airport, ARRIVAL_Airport) VALUES ('${PlanId}', '${FlightId}', '${FlightName}', '${FlightDeparture}', '${FlightArrival}', '${DepartureAirport}', '${ArrivalAirport}')`, { type: QueryTypes.UPDATE });
            return (data.length ==0) ?
                {   status: false, reaon:"비행정보를 수정하는 도중 오류가 발생했습니다."}:
                {   status: true,reason:"비행정보를 수정하는 데 성공하였습니다."};
        }
        catch(err){
            console.log(err);
            return { status: false,reason:"비행정보를 수정하는 도중 오류가 발생했습니다."};
        }
    }
}

module.exports = Flight;