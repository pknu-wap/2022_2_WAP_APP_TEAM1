'use strict';
var req = require('superagent');
var dateutil = require('dateutil');
var xml2js = require('xml2js');

var parser = new xml2js.Parser({explicitArray: false});
var parseString = parser.parseString;

function xml2jsParser(res, fn) {
    res.text = '';
    res.setEncoding('utf8');
    res.on('data', function (chunk) {
        res.text += chunk;
    });
    res.on('end', function () {
        try {
            parseString(res.text, fn);
        } catch (err) {
            fn(err);
        }
    });
}

class flightData {
    constructor() {
        this.KIWI_API_ENDPOINT = 'https://api.tequila.kiwi.com/v2';
        this.KIWI_API_KEY = process.env.KIWI_API_KEY;
        this.AIRPORT_OPENAPI_ENDPOINT = 'http://openapi.airport.co.kr/service/rest/FlightScheduleList/getDflightScheduleList';
        this.AIRPORT_OPENAPI_KEY = process.env.AIRPORT_OPENAPI_KEY;
    }

    async getInfoByFlightNumber(flightDate, airlineCode, flightNum) {
        try {
            let res = await req.get(this.AIRPORT_OPENAPI_ENDPOINT)
                .buffer(true)
                .accept('xml')
                .query({ServiceKey: this.AIRPORT_OPENAPI_KEY})
                .query({schAirline: airlineCode})
                .query({schFlightNum: flightNum})
                .query({schDate: dateutil.format(dateutil.parse(flightDate), "Ymd")})
                .query({pageNo: 1})
                .parse(xml2jsParser);
            res = res.body.response.body.items.item;
            if (res == null) {
                console.log('getInfoByFlightNumber: res : ', res);
                return {status: false, result: '항공편 정보를 찾을 수 없습니다.', flight: {}};
            }
            if (res instanceof Array) {
                res = res[0];
            }
            let flightInfo = res;
            let sTime = flightInfo.domesticStartTime;
            let eTime = flightInfo.domesticArrivalTime;
            sTime = sTime.substring(0, 2) + ':' + sTime.substring(2, 4);
            eTime = eTime.substring(0, 2) + ':' + eTime.substring(2, 4);
            let flight = {
                airline: airlineCode,
                flightNo: parseInt(flightNum),
                flyFrom: flightInfo.startcityCode,
                flyTo: flightInfo.arrivalcityCode,
                departure: flightDate + ' ' + sTime,
                arrival: flightDate + ' ' + eTime,
            }
            return {status: true, result: '조회에 성공하였습니다.', flight: flight};
        } catch (e) {
            console.log('getInfoByFlightNumber: error at ', e);
            return {status: false, result: e.message, flight: {}};
        }
    }

    /**
     *
     * @param {string} flyFrom 출발공항, 필수
     * @param {string} flyTo 도착공항, 필수
     * @param {string} date 날짜, 필수
     * @param {string} returnDate 리턴 날짜, 왕복일 때만 요구
     * @param {string} flightType 왕복 편도 선택(기본값: oneway), 필수 [oneway, round]
     * @param {integer} adults 성인 인원, 필수
     * @param {integer} children 어린이 인원, 필수
     * @param {integer} limit 검색 갯수 제한, 필수
     */
    async findFlight(flyFrom, flyTo, date, returnDate, flightType, adults, children, limit) {
        try {
            date = dateutil.format(dateutil.parse(date), "d/m/Y");
            returnDate = dateutil.format(dateutil.parse(returnDate), "d/m/Y");
            if (flightType == '') flightType = 'oneway';
            let oneway_option = {date_from: date, date_to: date, flight_type: 'oneway'};
            let round_option = {
                date_from: date,
                date_to: date,
                return_from: returnDate,
                return_to: returnDate,
                flight_type: 'round'
            };
            let res;
            try {
                res = await req.get(this.KIWI_API_ENDPOINT + '/search')
                    .set({'apikey': this.KIWI_API_KEY, Accept: 'application-json'})
                    .query({fly_from: flyFrom})
                    .query({fly_to: flyTo})
                    .query((flightType == 'oneway') ? oneway_option : round_option)
                    .query({partner_market: 'kr'})
                    .query({curr: 'KRW'})
                    .query({locale: 'kr'})
                    .query({adults: adults})
                    .query({children: children})
                    .query({vehicle_type: 'aircraft'})
                    .query({limit: limit});
            } catch (e) {
                return {status: false, result: `${JSON.parse(e.response.text).error}`};
            }

            let data = JSON.parse(res.text).data;
            let flights = [];
            for (let i = 0; i < data.length; i++) {
                if (data[i].route.length === 1) {
                    let flight = {};
                    flight.flyFrom = data[i].route[0].flyFrom;
                    flight.flyTo = data[i].route[0].flyTo;
                    flight.airline = data[i].route[0].airline;
                    flight.flightNo = data[i].route[0].flight_no;
                    flight.arrival = dateutil.format(dateutil.parse(data[i].route[0].local_arrival), "Y-m-d H:i");
                    flight.departure = dateutil.format(dateutil.parse(data[i].route[0].local_departure), "Y-m-d H:i");

                    flight.price = Math.ceil(data[i].price);
                    flight.seats = data[i].availability.seats;
                    flights.push(flight);
                } else {
                    let roundFlight = [];
                    roundFlight.seats = data[i].availability.seats;
                    roundFlight.price = Math.ceil(data[i].price);
                    for (let j = 0; j < data[i].route.length; j++) {
                        let flight = {};
                        flight.flyFrom = data[i].route[j].flyFrom;
                        flight.flyTo = data[i].route[j].flyTo;
                        flight.airline = data[i].route[j].airline;
                        flight.flightNo = data[i].route[j].flight_no;
                        flight.arrival = dateutil.format(dateutil.parse(data[i].route[j].local_arrival), "Y-m-d H:i");
                        flight.departure = dateutil.format(dateutil.parse(data[i].route[j].local_departure), "Y-m-d H:i");
                        roundFlight.push(flight);
                    }
                    flights.push(roundFlight);
                }
            }
            return {status: true, result: flights};
        } catch (e) {
            console.log(e);
            return {status: false, result: e.message};
        }
    }
}

module.exports = flightData;