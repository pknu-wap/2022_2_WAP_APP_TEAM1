'use strict';
const models = require("../../models")
const { raw2str } = require("../../util/rawtostr")

module.exports = {
    //Create Trip(PUT)
    async createTrip(req, res) {
        const { UserId } = req.token;
        const { Name, StartDate, EndDate, Region } = req.body;

        if (StartDate === undefined || EndDate === undefined || Name === undefined || Region === undefined) {
            return res.status(400).send({ status: false, reason: `` });
        }
        let transaction = await models.sequelize.transaction();
        try {
            let trip = raw2str(await models.Trip.create({
                OwnerId: UserId,
                StartDate: StartDate,
                EndDate: EndDate,
                Name: Name,
                Region: Region
            }));
            if (trip == null) {
                await transaction.rollback();
                return res.send({ status: false, reason: "계획 생성 도중 오류가 발생했습니다." });
            }
            await transaction.commit();
            return res.send({ status: true, reason: "일정 생성 성공", TripId: trip.TripId });
        } catch (err) {
            await transaction.rollback();
            console.log('createTrip error : ', err);
            res.status(500).send({ status: false, reason: "Internal Server Error" });
        }
    },
    //Delete Trip(DELETE)
    async deleteTrip(req, res) {
        const { UserId } = req.token;
        const { trip } = req;
        if (trip == undefined) {
            return res.send({ status: false, reason: `Trip is undefined` });
        }
        if (trip.OwnerId != UserId) {
            return res.status(403).send({ status: false, reason: "not a owner of trip" });
        }
        let result = await trip.destroy();
        return res.status(200).send({ status: true, reason: "일정 삭제 성공" });
    },
    //Get Trip(GET)
    async getTrip(req, res) {
        const { trip } = req;
        const { UserId } = req.token;

        let tripParticipants = raw2str(await models.TripParticipant.findAll({
            where: {
                TripId: trip.TripId
            },
            include: [{
                model: models.User,
                attributes: ['UserId', 'Nickname', 'ProfileImage']
            }]
        })).map(participant => participant.User);
        let plans = await models.Plan.findAll({
            where: {
                TripId: trip.TripId
            },
            attributes: ['PlanId', 'Day', 'OrderIndex'],
            raw: true
        });
        for (let i = 0; i < plans.length; i++) {
            plans[i].Memo = await models.PlanMemo.findOne({
                where: {
                    TripId: trip.TripId,
                    PlanId: plans[i].PlanId
                },
                attributes: ['Content', 'Created_At', 'Updated_At']
            });
            /*plans[i].Places = raw2str(await models.Place.findAll({
                where: {
                    TripId: trip.TripId,
                    PlanId: plans[i].PlanId
                }
            }));*/
        }
        return res.status(200).send({ status: true, reason: "일정 조회 성공", plans });
    },
    //Get Trip List(GET)
    async getTripList(req, res) {
        const { UserId } = req.token;
        let ownedTrips = raw2str(await models.Trip.findAll({
            where: {
                OwnerId: UserId
            },
            attributes: ['TripId', 'Name', 'StartDate', 'EndDate', 'Region'],
            raw: true
        }));
        let participatedInTrips = raw2str(await models.TripParticipant.findAll({
            where: {
                UserId: UserId
            },
            attributes: ['TripId'],
            include: [{
                model: models.Trip,
                attributes: ['TripId', 'Name', 'StartDate', 'EndDate', 'Region']
            }],
            raw: true,
            nest: true
        }));
        let result = ownedTrips;
        for (let i = 0; i < participatedInTrips.length; i++) {
            result.push(participatedInTrips[i].Trip);
        }
        return res.status(200).send({ status: true, reason: "일정 목록 조회 성공", result });
    }
}
