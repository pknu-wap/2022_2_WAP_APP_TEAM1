'use strict';
const router = require('express').Router();
const flightClass = require('../../flight');
// let data = await flight.getInfoByFlightNumber('2022-11-27', 'TW', '702');
//         //let data = await flight.findFlight('CJU', 'GMP', '2022-11-27', '2022-11-24', '', 1, 0, 1);
router.get('/flightNumber', async (req, res) => {
    const {flightDate, airlineCode, flightNum} = req.query;
    const flight = new flightClass();
    let data = await flight.getInfoByFlightNumber(flightDate, airlineCode, flightNum);
    return res.send(data);
});
router.get('/', async (req, res) => {
    const {flyFrom, flyTo, date, returnDate, flightType, adults, children, limit} = req.query;
    const flight = new flightClass();
    let data = await flight.findFlight(flyFrom, flyTo, date, returnDate, flightType, adults, children, limit);
    return res.send(data);
});

module.exports = router;