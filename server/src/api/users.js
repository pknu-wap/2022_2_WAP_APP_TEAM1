"use strict";
const User = require("../model/db_user")

const process = {
    login: async(req, res) => {
        return res.send(await new User(req.body).login());
    },
    register: async(req, res) => {
        return res.send(await new User(req.body).register());
    },
    duplicate_id: async(req, res) => {
        let pData = await new User(req.body);
        let response = await pData.duplicate_id();
        return res.send(response);
    },
    editProfileImage: async(req, res) => {
        let pData = await new User(req.body);
        let response = await pData.editProfileImage(req.file);
        return res.send(response);
    }
}
module.exports = process;