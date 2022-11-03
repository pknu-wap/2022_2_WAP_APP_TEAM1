'use strict';

const { QueryTypes } = require("sequelize");
const db = require("../../util/database");
const { raw2str } = require("../../util/rawtostr");
const token = require("../../util/jwt");

/**
 * @typedef {Object} User
 * @public
 * @property {string} UserId - 사용자의 고유 ID
 * @property {integer} AccountType - 사용자의 계정 타입
 * @property {string} OauthId - 소셜 로그인일 경우 소셜 로그인 고유 ID
 * @property {string} Username - 일반 로그인일 경우 사용자의 이메일(아이디)
 * @property {string} Password - 일반 로그인일 경우 사용자의 비밀번호
 * @property {string} PhoneNum - 사용자의 전화번호
 * @property {string} Nickname - 사용자의 닉네임
 * @property {string} ProfileImage - 사용자의 프로필 이미지
 * 
 * @private
 * @property {Object} dbsetup - sequelize DB 연결 객체
 * 
 * 이 클래스는 User 테이블에 대한 모델입니다.
*/
class User {
    constructor(param) { //Object.assign(this, param);
        this.UserId = param.UserId;
        this.AccountType = param.AccountType;
        this.OauthId = param.OauthId;
        this.Username = param.Username;
        this.Password = param.Password;
        this.PhoneNum = param.PhoneNum;
        this.Nickname = param.Nickname;
        this.ProfileImage = param.ProfileImage;
        this.dbsetup = require("../../util/database");
    }
    
    async login() {
        try {
            /* 일반 로그인 */
            if (this.AccountType == 0) {
                let data = raw2str(await db.query(`SELECT * FROM DB_USER WHERE USERNAME = '${this.Username}' and PASSWORD = '${this.Password}'`, { type: QueryTypes.SELECT }));
                if (data.length == 0) {
                    return { status: false, reason: "아이디나 비밀번호가 맞지 않습니다." }
                }
                else {
                    let isProfileExists = true;
                    if (data[0].NICKNAME == null || data[0].NICKNAME == "") {
                        isProfileExists = false;
                    }
                    let AccessToken = token.generateAccessToken(data[0].USER_ID);
                    let RefreshToken = token.generateRefreshToken(data[0].USER_ID);
                    return { status: true, reason: "로그인 성공", AccessToken: AccessToken, RefreshToken: RefreshToken, isProfileExists: isProfileExists };
                }
            }
            /* 카카오,네이버 로그인 */
            else if (this.AccountType == 1 || this.AccountType == 2) {
                let query = raw2str(await db.query(`SELECT * FROM DB_USER WHERE OAUTH_ID = '${this.OauthId}'`, { type: QueryTypes.SELECT }));
                if (query.length == 0) {
                    /* 계정이 없을 경우 회원가입 */
                    query = await db.query(`INSERT INTO DB_USER (ACCOUNTTYPE, OAUTH_ID, USERNAME, PASSWORD, PHONENUM, NICKNAME) VALUES ('${this.AccountType}', '${this.OauthId}', '', '', '', '')`, { type: QueryTypes.INSERT });
                    query = raw2str(await db.query(`SELECT * FROM DB_USER WHERE OAUTH_ID = '${this.OauthId}'`, { type: QueryTypes.SELECT }));
                    if (query.length == 0) {
                        return { status: false, reason: "계정 등록에 실패했습니다." };
                    }
                    let isProfileExists = true;
                    if (query[0].NICKNAME == null || query[0].NICKNAME == "") {
                        isProfileExists = false;
                    }
                    let AccessToken = token.generateAccessToken(query[0].USER_ID);
                    let RefreshToken = token.generateRefreshToken(query[0].USER_ID);
                    return { status: true, reason: "로그인 성공", AccessToken: AccessToken, RefreshToken: RefreshToken, isProfileExists: isProfileExists };
                }
                else {
                    let isProfileExists = true;
                    if (query[0].NICKNAME == null || query[0].NICKNAME == "") {
                        isProfileExists = false;
                    }
                    let AccessToken = token.generateAccessToken(query[0].USER_ID);
                    let RefreshToken = token.generateRefreshToken(query[0].USER_ID);
                    return { status: true, reason: "로그인 성공", AccessToken: AccessToken, RefreshToken: RefreshToken, isProfileExists: isProfileExists };
                }

            }
            else {
                return { status: false, reason: "허용되지 않는 로그인 방식입니다." };
            }
        }
        catch (err) {
            console.log('user->login 도중 오류 발생: ', err);
            return { status: false, reason: "계정을 확인하는 도중 오류가 발생했습니다." };
        }
    }

    async duplicate_id() {
        try {
            const data = await db.query(`SELECT * FROM DB_USER WHERE USERNAME = '${this.Username}'`, { type: QueryTypes.SELECT });
            return (data.length != 0) ?
                { status: false, reason: "이미 존재하는 이메일입니다." } :
                { status: true, reason: "사용 가능한 이메일입니다." };
        } catch (err) {
            console.log('user->duplicate_id 도중 오류 발생: ', err);
            return { status: false, reason: "중복을 확인하는 도중 오류가 발생했습니다." };
        }
    }
    async register() {
        try {
            const data = await db.query(`INSERT INTO DB_USER (ACCOUNTTYPE, OAUTH_ID, USERNAME, PASSWORD, PHONENUM, NICKNAME) VALUES ('0', '', '${this.Username}', '${this.Password}', '', '')`, { type: QueryTypes.INSERT });
            return (data.length == 0) ?
                { status: false, reason: "계정을 생성하는 도중 오류가 발생했습니다." } :
                { status: true, reason: "계정 생성하는 데 성공하였습니다." };
        } catch (err) {
            console.log('user->register 도중 오류 발생: ', err);
            return { status: false, reason: "계정을 생성하는 도중 오류가 발생했습니다." };
        }
    }
    async editInfo(image) {
        try {
            const userData = await db.query(`SELECT * FROM DB_USER WHERE USER_ID = '${this.UserId}'`, { type: QueryTypes.SELECT });
            console.log(userData);
            if (this.Nickname == undefined || this.Nickname == '') {
                this.Nickname = userData[0].NICKNAME;
            }
            if (this.PhoneNum == undefined || this.PhoneNum == '') {
                this.PhoneNum = userData[0].PHONENUM;
            }
            if (this.ProfileImage == undefined || this.ProfileImage == '') {
                this.ProfileImage = userData[0].PROFILEIMAGE;
            }
            if (image != undefined) {
                let image_result = await require("../../util/file")(image.buffer, image.originalname);
                if (image_result.result)
                {
                    this.ProfileImage = image_result.fileName;
                }
                else
                {
                    return { status: false, reason: "프로필 이미지를 업로드하는 도중 오류가 발생했습니다." };
                }
            }
            const data = await db.query(`UPDATE DB_USER SET NICKNAME = '${this.Nickname}', PHONENUM = '${this.PhoneNum}', PROFILEIMAGE = '${this.ProfileImage}' WHERE USER_ID = '${this.UserId}'`, { type: QueryTypes.UPDATE });
            return (data.length == 0) ?
                { status: false, reason: "프로필을 수정하는 도중 오류가 발생했습니다." } :
                { status: true, reason: "프로필을 수정하는 데 성공하였습니다." };       
        }
        catch (err) {
            console.log('user->editProfile 도중 오류 발생: ', err);
            return { status: false, reason: "프로필을 수정하는 도중 오류가 발생했습니다." };
        }
    }
    async getInfo() {
        try {
            let data = await raw2str(await db.query(`SELECT * FROM DB_USER WHERE USER_ID = '${this.UserId}'`, { type: QueryTypes.SELECT }));
            return (data.length == 0) ?
                { status: false, reason: "유저 아이디를 찾을 수 없습니다." } :
                { status: true, reason: "유저 정보 조회 성공", result: data[0] };
        } catch (err) {
            console.log('user->getInfo 도중 오류 발생: ', err);
        }
    }
}
module.exports = User