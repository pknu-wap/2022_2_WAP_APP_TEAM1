'use strict';
const models = require("../../../models")
const token = require("../../../util/jwt")
const { raw2str } = require("../../../util/rawtostr")

module.exports = {
    async addPlace(req, res) {
        let transaction = await models.sequelize.transaction();
        try {
            //const { Latitude, Longitude, Category,  }
            let place = await models.Place.findOne({
                where: {
                    
                }
            });
        } catch (err) {
            await transaction.rollback();
            console.log('addMemo error : ', err);
            res.status(500).send({ status: false, reason: "Internal Server Error" });
        }
        await transaction.commit();
    },
    //Add Memo(PUT)
    async addMemo(req, res) {
        let transaction = await models.sequelize.transaction();
        try {
            const { trip } = req;
            const { Content } = req.body;
            const { Day } = req.params;
            //계획 생성
            let plan = await models.Plan.create({
                TripId: trip.TripId,
                Day: Day
            });
            // 생성된 계획에 메모 등록
            let planMemo = await models.PlanMemo.create({
                TripId: plan.TripId,
                PlanId: plan.PlanId,
                Content: Content
            });
            
            res.status(200).json({
                planMemo
            });
        } catch (err) {
            await transaction.rollback();
            console.log('addMemo error : ', err);
            res.status(500).send({ status: false, reason: "Internal Server Error" });
        }
        await transaction.commit();
    }
}