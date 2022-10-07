'use strict';

const { QueryTypes } = require("sequelize");

//USER_ID = HEXTORAW('${this.USER_ID}')
class User {
    constructor(param) {
        Object.assign(this, param);
        this.dbsetup = require("../config-db");
    }

    login() {
        if (this.Username === undefined || this.Password === undefined || this.AccountType === undefined) {
            return { result: false, reason: "아이디나 비밀번호가 맞지 않습니다." };
        }햐
        let _db = this.dbsetup.getDb();
        _db.authenticate().then(async () => {
            try {
                if (this.AccountType == 0) {
                    const data = await _db.query(`SELECT * FROM DB_USER WHERE USERNAME = ${this.Username} and PASSWORD = ${this.Password}`, { type: QueryTypes.SELECT });
                    return (data.length == 0) ? "ERROR_NOT_MATCH" : "SUCCESS";
                }
                else if (this.AccountType == 1 || this.AccountType == 2) { //Kakao Login
                    const data = await _db.query(`SELECT * FROM DB_USER WHERE OAUTH_ID = ${this.OAUTH_ID}`, { type: QueryTypes.SELECT });
                    return (data.length == 0) ? { result: false, reason: "ERROR_NOT_MATCH" } : { result: true, reason: "SUCCESS" };
                }
                else {
                    return { result: false, reason: "NOT_APPLICATED_ACCOUNT_TYPE" };
                }
            }
            catch (err) { } finally {
                _db.close();
            }
        }).catch((err) => {
            return { result: false, reason: "데이터베이스에 연결할 수 없습니다." };
        }
        );
    }
    async duplicate_id() {
        if (this.Username === undefined) {
            return "ERROR_LACK_OF_DATA";
        }
        let _db = this.dbsetup.getDb();
        try {
            _db.authenticate();
        } catch (error) {
            return { status: false, reason: "데이터베이스 연결 실패" };
        }
        try {
            const data = await _db.query(`SELECT * FROM DB_USER WHERE USERNAME = '${this.Username}'`, { type: QueryTypes.SELECT });
            return (data.length != 0) ? "ERROR_ALREADY_EXISTS" : "SUCCESS";
        } catch (err) {
            console.log('user->register 도중 오류 발생: ', err);
        } finally {
            _db.close();
        }
    }
    register() {
        if (this.Username === undefined || this.Nickname === undefined || this.Password === undefined || this.PhoneNum === undefined || this.AccountType === undefined) {
            return "ERROR_LACK_OF_DATA";
        }
        let _db = this.dbsetup.getDb();
        _db.authenticate().then(async () => {
            try {
                if (this.AccountType == 0) {
                    const data = await _db.query(`INSERT INTO DB_USER (ACCOUNTTYPE, OAUTH_ID, USERNAME, PASSWORD, PHONENUM, NICKNAME) VALUES ('0', '', '${this.Username}', '${this.Password}', '${this.PhoneNum}', '${this.Nickname}')`, { type: QueryTypes.INSERT });
                    console.log('추가 : ', data);
                    return (data.length == 0) ? "ERROR_NOT_MATCH" : "SUCCESS";
                }
            } catch (err) {
                console.log('user->register 도중 오류 발생: ', err);
            } finally {
                _db.close();
            }
        });
    }
    async editProfileImage(image) {
        let _db = this.dbsetup.getDb();
        try {
            _db.authenticate();
        } catch (error) {
            return { status: false, reason: "데이터베이스 연결 실패" };
        }
        let result = await require("../util/file")(image.buffer, image.originalname);
        if (result.result) {
            try {
                const data = await _db.query(`UPDATE DB_USER SET PROFILEIMAGE = '${result.fileName}' WHERE USER_ID = '${this.USER_ID}'`, { type: QueryTypes.UPDATE });
                return (data.length == 0) ? { status: false, reason: "유저 아이디를 찾을 수 없습니다." } : { status: true, reason: "프로필 이미지 변경 성공" };
            } catch (err) {
                console.log('user->editProfileImage 도중 오류 발생: ', err);
            }
        }
        else {
            return { status: false, reason: "파일 저장 실패" };
        }
        _db.close();
    }
}
module.exports = User