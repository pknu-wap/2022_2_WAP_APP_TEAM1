"use strict";
const User = require("./user.model")
const token = require("../../util/jwt");
const process = {
    /* NO NEED TOKEN */
    /*
        Login(POST)
            req.body.AccountType <= AccountType
            req.body.Username <= Username
            req.body.Password <= Password
    */
    login: async(req, res) => {
        let user = new User(req.body);
        return res.send(await user.login());
    },
    /*
        Register(POST)
            req.body.Username <= Username
            req.body.Password <= Password
    */
    register: async(req, res) => {
        let user = new User(req.body);
        return res.send(await user.register());
    },
    /*
        Duplicate Check(GET)
            req.query.Username <= Username
    */
    duplicate_id: async(req, res) => {
        let user = new User(req.query);
        return res.send(await user.duplicate_id());
    },
    /* NEED TOKEN */
    /*
        Edit Info(POST)
            req.token.userId <= UserId
            req.body.Nickname <= Nickname
            req.body.PhoneNum <= PhoneNum
            req.file <= ProfileImage
    */
    editInfo: async(req, res) => {
        req.body.UserId = req.token.userId;
        let user = new User(req.body);
        return res.send(await user.editInfo(req.file));
    },
    /*
        Get Info(GET)
            req.token.userId <= UserId
    */
    getInfo: async(req, res) => {
        req.body.UserId = req.token.userId;
        let user = new User(req.body);
        return res.send(await user.getInfo());
    },
    /*
        Update Token(GET)
            req.token.userId <= UserId
    */
    updateToken: async (req, res) => {
        let AccessToken = token.generateAccessToken(req.token.userId);
        let RefreshToken = token.generateRefreshToken(req.token.userId);
        res.set('x-access-token', AccessToken);
        res.set('x-refresh-token', RefreshToken);
        return res.sendStatus(200);
    }
}
module.exports = process;