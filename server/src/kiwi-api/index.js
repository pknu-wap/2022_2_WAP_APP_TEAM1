var req = require('superagent');
var dateutil = require('dateutil');


class kiwi
{
  constructor() {
    this.URL_DOMAIN = 'https://api.tequila.kiwi.com/v2';
    this.API_KEY = process.env.KIWI_API_KEY;
  }
  /**
   * 
   * @param {string} fly_from 출발공항, 필수
   * @param {string} fly_to 
   * @param {string} date_from 
   * @param {string} date_to 
   * @param {string} return_from 
   * @param {string} return_to 
   * @param {string} flight_type 왕복 편도 선택(기본값: oneway), 필수 [oneway, round]
   * @param {integer} adults 성인 인원, 필수
   * @param {integer} children 어린이 인원, 필수
   * @param {integer} limit 검색 갯수 제한, 필수
   */
  async findFlight(fly_from, fly_to, date_from, date_to, return_from, return_to, flight_type, adults, children, limit)
  {
    date_from = dateutil.format(dateutil.parse(date_from), "d/m/Y");
    date_to = dateutil.format(dateutil.parse(date_to), "d/m/Y");
    if (flight_type == '') flight_type = 'oneway';
    req.get(this.URL_DOMAIN + '/search')
    .set({'apikey': this.API_KEY, Accept: 'application-json'})
    .query({fly_from: fly_from})
    .query({fly_to: fly_to})
    .query({date_from: date_from})
    .query({date_to: date_to})
    .query({flight_type: flight_type})
    .query({partner_market: 'kr'})
    .query({curr: 'KRW'})
    .query({locale: 'kr'})
    .query({vehicle_type: 'aircraft'})
    .query({limit: limit})
    .then(res => {
      let data = JSON.parse(res.text).data;
      console.log(data[0]);
    });
  }
}

module.exports = kiwi;