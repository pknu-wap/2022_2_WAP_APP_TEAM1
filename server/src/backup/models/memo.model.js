'use strict';

const { QueryTypes } = require("sequelize");
const db = require("../../util/database");
const { raw2str } = require("../../util/rawtostr");
const token = require("../../util/jwt");

/**
 * @typedef {Object} PlanMemo
 * @property {string} PlanDetailId 
 * @property {string} PlanId
 */
class PlanMemo {
    constructor(param) {
        this.PlanDetailId = param.PlanDetailId;
        this.PlanId = param.PlanId;
        this.PlaceId = param.PlaceId;
        this.Content = param.Content;
        this.CreatedAt = param.CreatedAt;
        this.UpdatedAt = param.UpdatedAt;
        this.dbsetup = require("../../util/database");
    }
    async create() {
        
}