"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.makeIndent = exports.makeTableName = exports.recase = exports.singularize = exports.pluralize = exports.qNameJoin = exports.qNameSplit = exports.TableData = void 0;
const lodash_1 = __importDefault(require("lodash"));
const reserved_words_1 = require("reserved-words");
const sequelize_1 = require("sequelize");
class TableData {
    constructor() {
        this.tables = {};
        this.foreignKeys = {};
        this.indexes = {};
        this.hasTriggerTables = {};
        this.relations = [];
    }
}
exports.TableData = TableData;
/** Split schema.table into [schema, table] */
function qNameSplit(qname) {
    if (qname.indexOf(".") > 0) {
        const [schemaName, tableNameOrig] = qname.split(".");
        return [schemaName, tableNameOrig];
    }
    return [null, qname];
}
exports.qNameSplit = qNameSplit;
/** Get combined schema.table name */
function qNameJoin(schema, table) {
    return !!schema ? schema + "." + table : table;
}
exports.qNameJoin = qNameJoin;
/** Uses Inflector via Sequelize, but appends 's' if plural would be the same as singular.
 * Use `Utils.useInflection({ singularize: fn, pluralize: fn2 })` to configure. */
function pluralize(s) {
    let p = sequelize_1.Utils.pluralize(s);
    if (p === sequelize_1.Utils.singularize(s)) {
        p += 's';
    }
    return p;
}
exports.pluralize = pluralize;
/** Uses Inflector via Sequelize.  Use `Utils.useInflection({ singularize: fn, pluralize: fn2 })` to configure. */
function singularize(s) {
    return sequelize_1.Utils.singularize(s);
}
exports.singularize = singularize;
/** Change casing of val string according to opt [c|l|o|p|u]  */
function recase(opt, val, singular = false) {
    if (singular && val) {
        val = singularize(val);
    }
    if (!opt || opt === 'o' || !val) {
        return val || ''; // original
    }
    if (opt === 'c') {
        return lodash_1.default.camelCase(val);
    }
    if (opt === 'k') {
        return lodash_1.default.kebabCase(val);
    }
    if (opt === 'l') {
        return lodash_1.default.snakeCase(val);
    }
    if (opt === 'p') {
        return lodash_1.default.upperFirst(lodash_1.default.camelCase(val));
    }
    if (opt === 'u') {
        return lodash_1.default.snakeCase(val).toUpperCase();
    }
    return val;
}
exports.recase = recase;
const tsNames = ["DataTypes", "Model", "Optional", "Sequelize"];
function makeTableName(opt, tableNameOrig, singular = false, lang = "es5") {
    let name = recase(opt, tableNameOrig, singular);
    if ((0, reserved_words_1.check)(name) || (lang == "ts" && tsNames.includes(name))) {
        name += "_";
    }
    return name;
}
exports.makeTableName = makeTableName;
/** build the array of indentation strings */
function makeIndent(spaces, indent) {
    let sp = '';
    for (let x = 0; x < (indent || 2); ++x) {
        sp += (spaces === true ? ' ' : "\t");
    }
    let space = [];
    for (let i = 0; i < 6; i++) {
        space[i] = sp.repeat(i);
    }
    return space;
}
exports.makeIndent = makeIndent;
//# sourceMappingURL=types.js.map