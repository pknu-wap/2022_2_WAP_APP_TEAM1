"use strict";
const User = require("./user.model")

const process = {
    /* NO NEED TOKEN */
    login: async(req, res) => {
        let user = new User(req.body);
        return res.send(await user.login());
    },
    register: async(req, res) => {
        let user = new User(req.body);
        return res.send(await user.register());
    },
    duplicate_id: async(req, res) => {
        let user = new User(req.body);
        return res.send(await user.duplicate_id());
    },
    /* NEED TOKEN */
    editInfo: async(req, res) => {
        req.body.UserId = req.token.userId;
        let user = new User(req.body);
        return res.send(await user.editInfo(req.file));
    },
    getInfo: async(req, res) => {
        req.body.UserId = req.token.userId;
        let user = new User(req.body);
        return res.send(await user.getInfo());
    }
}
module.exports = process;