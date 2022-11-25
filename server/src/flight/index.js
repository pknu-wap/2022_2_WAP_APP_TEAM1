'use strict';
var req = require('superagent');
var dateutil = require('dateutil');
var xml2js = require('xml2js');

var parser = new xml2js.Parser({explicitArray : false});
var parseString = parser.parseString;

function xml2jsParser(res, fn){
    res.text = '';
    res.setEncoding('utf8');
    res.on('data', function(chunk){ res.text += chunk; });
    res.on('end', function(){
        try {
            parseString(res.text, fn);
        } catch (err) {
            fn(err);
        }
    });
};
class flightData {
    constructor() {
        this.KIWI_API_ENDPOINT = 'https://api.tequila.kiwi.com/v2';
        this.KIWI_API_KEY = process.env.KIWI_API_KEY;
        this.AIRPORT_OPENAPI_ENDPOINT = 'http://openapi.airport.co.kr/service/rest/FlightScheduleList/getDflightScheduleList';
        this.AIRPORT_OPENAPI_KEY = process.env.AIRPORT_OPENAPI_KEY;
    }

    async getInfoByFlightNumber(flightDate, airlineCode, flightNum)
    {
        try {
            flightDate = dateutil.format(dateutil.parse(flightDate), "Ymd");
            let res = await req.get(this.AIRPORT_OPENAPI_ENDPOINT)
                .buffer(true)
                .accept('xml')
                .query({ServiceKey: this.AIRPORT_OPENAPI_KEY})
                .query({schAirline: airlineCode})
                .query({schFlightNum: flightNum})
                .query({schDate: flightDate})
                .query({pageNo: 1})
                .parse(xml2jsParser);
            res = res.body.response.body;
            console.log(res.items);
        } catch (e) {
            console.error(e);
        }
    }
    /**
     *
     * @param {string} fly_from 출발공항, 필수
     * @param {string} fly_to 도착공항, 필수
     * @param {string} date 날짜, 필수
     * @param {string} return_date 리턴 날짜, 왕복일 때만 요구
     * @param {string} flight_type 왕복 편도 선택(기본값: oneway), 필수 [oneway, round]
     * @param {integer} adults 성인 인원, 필수
     * @param {integer} children 어린이 인원, 필수
     * @param {integer} limit 검색 갯수 제한, 필수
     */
    async findFlight(fly_from, fly_to, date, return_date, flight_type, adults, children, limit) {
        try {
            date = dateutil.format(dateutil.parse(date), "d/m/Y");
            return_date = dateutil.format(dateutil.parse(return_date), "d/m/Y");
            if (flight_type == '') flight_type = 'oneway';
            let oneway_option = {date_from: date, date_to: date, flight_type: 'oneway'};
            let round_option = {
                date_from: date,
                date_to: date,
                return_from: return_date,
                return_to: return_date,
                flight_type: 'round'
            };
            let res = await req.get(this.KIWI_API_ENDPOINT + '/search')
                .set({'apikey': this.KIWI_API_KEY, Accept: 'application-json'})
                .query({fly_from: fly_from})
                .query({fly_to: fly_to})
                .query((flight_type == 'oneway') ? oneway_option : round_option)
                .query({partner_market: 'kr'})
                .query({curr: 'KRW'})
                .query({locale: 'kr'})
                .query({adults: adults})
                .query({children: children})
                .query({vehicle_type: 'aircraft'})
                .query({limit: limit});
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
            console.log(data);
            return flights;
        } catch (e) {
            console.log(e);
            let error = {error: e};
            return error;
        }
    }
}

module.exports = flightData;