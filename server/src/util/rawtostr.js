const { Model } = require('sequelize');

function iterateModel(model) {
    let obj = model.dataValues;
    for (var prop in obj) {
        if (obj.hasOwnProperty(prop) == false) { return; }

        if (obj[prop] instanceof Buffer) {
            obj[prop] = obj[prop].toString('hex').toUpperCase();
        }
        if (obj[prop] instanceof Array) {
            for (var i = 0; i < obj[prop].length; i++) {
                if (obj[prop][i] instanceof Model) {
                    iterateModel(obj[prop][i]);
                }
            }
        }
        if (obj[prop] instanceof Model) {
            iterateModel(obj[prop]);
        }
    }
}
const raw2str = (sql) => {
    // Buffer Check
    if (sql instanceof Buffer) {
        return sql.toString('hex').toUpperCase();
    }
    // SQL Query Check
    for (var i = 0; i < sql.length; i++) {
        for (var prop in sql[i]) {
            if (sql[i][prop] instanceof Buffer) {
                sql[i][prop] = sql[i][prop].toString('hex').toUpperCase();
            }
        }
    }
    // ORM Check
    if (sql instanceof Model) {
        iterateModel(sql);
    }
    return sql;
}
exports.raw2str = raw2str;