"use strict";
const User = require("../model/db_user")

const process = {
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
    editProfileImage: async(req, res) => {
        let user = new User(req.body);
        return res.send(await user.editProfileImage(req.file));
    },
    getInfo: async(req, res) => {
        let user = new User(req.body);
        return res.send(await user.getInfo());
    },
    editNickname: async(req, res) => {
        let user = new User(req.body);
        return res.send(await user.editNickname());
    }
}
module.exports = process;