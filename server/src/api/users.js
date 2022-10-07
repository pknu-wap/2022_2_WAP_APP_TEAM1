"use strict";
const User = require("../model/db_user")
const init = async(body) => {
    return await new User(body);
}
const process = {
    login: async(req, res) => {
        let user = new User(req.body);
        let data = await user.login();
        return res.send(data);
    },
    register: async(req, res) => {
        let user = init(req.body);
        return res.send(await user.register());
    },
    duplicate_id: async(req, res) => {
        let user = init(req.body);
        return res.send(await user.duplicate_id());
    },
    editProfileImage: async(req, res) => {
        let user = init(req.body);
        return res.send(await user.editProfileImage(req.file));
    }
}
module.exports = process;