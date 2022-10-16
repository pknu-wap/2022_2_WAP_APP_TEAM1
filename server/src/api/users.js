"use strict";
const User = require("../model/db_user")

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
<<<<<<< Updated upstream:server/src/api/users.js
    editProfileImage: async(req, res) => {
        req.body.UserId = req.token.userId;
        let user = new User(req.body);
        return res.send(await user.editProfileImage(req.file));
=======
    editInfo: async(req, res) => {
        req.body.UserId = req.token.userId;
        let user = new User(req.body);
        return res.send(await user.editInfo(req.file));
>>>>>>> Stashed changes:server/src/routers/user/user.controller.js
    },
    getInfo: async(req, res) => {
        req.body.UserId = req.token.userId;
        let user = new User(req.body);
        return res.send(await user.getInfo());
    }
}
module.exports = process;