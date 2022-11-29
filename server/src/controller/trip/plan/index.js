'use strict';
const models = require("../../../models")
const token = require("../../../util/jwt")
const {raw2str} = require("../../../util/rawtostr")

module.exports = {
    // Commit Plans (POST)
    async commit(req, res) {
        const {OrderIds, DeleteIds, Days} = req.body;
        const {trip} = req;
        let transaction = await models.sequelize.transaction();
        try {
            for (let i = 0; i < DeleteIds.length; i++) {
                await models.Plan.destroy({
                    where: {
                        PlanId: DeleteIds[i]
                    }
                }, {transaction: transaction});
            }
            for (let i = 0; i < Days.length; i++) {
                await models.Plan.update({
                    OrderIndex: i
                }, {
                    where: {
                        TripId: trip.TripId,
                        PlanId: OrderIds[i]
                    }
                }, {transaction: transaction});
            }
            await transaction.commit();
            return res.send({status: true, reason: "일정이 저장되었습니다."});
        } catch (e) {
            await transaction.rollback();
            return res.send({status: false, reason: "Internal Server Error"});
        }
    },
    async addPlace(req, res) {
        let transaction = await models.sequelize.transaction();
        try {
            const {trip} = req;
            const {PlaceId, Latitude, Longitude, Category, Name, RoadAddress} = req.body;
            const {Day} = req.params;

            let place = await models.Place.findOne({
                where: {Latitude: Latitude, Longitude: Longitude}
            });
            if (place == null) {
                place = await models.Place.create({
                    PlaceId: PlaceId,
                    Latitude: Latitude,
                    Longitude: Longitude,
                    Category: Category,
                    Name: Name,
                    RoadAddress: RoadAddress
                }, {transaction: transaction});
            }
            let plan = await models.Plan.create({
                TripId: trip.TripId,
                Day: Day
            }, {transaction: transaction});
            let planPlace = await models.PlanPlace.create({
                TripId: trip.TripId,
                PlanId: plan.PlanId,
                PlaceId: place.PlaceId
            }, {transaction: transaction});

            await transaction.commit();
            return res.send({status: true, reason: "장소 추가 성공", planId: plan.PlanId, planPlace: planPlace});
        } catch (err) {
            await transaction.rollback();
            console.log('addPlace error : ', err);
            res.status(500).send({status: false, reason: "Internal Server Error"});
        }
    },
    //Edit Memo(PATCH)
    async editMemo(req, res) {
        let transaction = await models.sequelize.transaction();
        try {
            const {trip} = req;
            const {Content} = req.body;
            const {PlanId} = req.params;
            let memo = await models.PlanMemo.findOne({
                where: {TripId: trip.TripId, PlanId: PlanId}
            });
            if (memo == null) {
                return res.send({status: false, reason: "PlanId is invalid"});
            }
            memo.Content = Content;
            await memo.save({transaction: transaction});
            await transaction.commit();
            return res.send({status: true, reason: "메모 수정 성공", memo: memo});
        } catch (err) {
            await transaction.rollback();
            console.log('editMemo error : ', err);
            res.status(500).send({status: false, reason: "Internal Server Error"});
        }
    },
    //Add Memo(PUT)
    async addMemo(req, res) {
        let transaction = await models.sequelize.transaction();
        try {
            const {trip} = req;
            const {Content} = req.body;
            const {Day} = req.params;
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
            await transaction.commit();
            return res.send({status: true, reason: "메모 추가 성공", PlanId: plan.PlanId, planMemo: planMemo})
        } catch (err) {
            await transaction.rollback();
            console.log('addMemo error : ', err);
            res.status(500).send({status: false, reason: "Internal Server Error"});
        }

    }
}