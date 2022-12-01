const {Model} = require('sequelize');

function iterateModel(model) {
    let obj = model.dataValues;
    for (var prop in obj) {
        if (obj.hasOwnProperty(prop) == false) {
            return;
        }

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
    // ORM Check
    if (sql instanceof Model) {
        iterateModel(sql);
    }
    if (sql instanceof Array) {
        for (var i = 0; i < sql.length; i++) {
            if (sql[i] instanceof Model) {
                iterateModel(sql[i]);
            }
            if (sql[i] instanceof Buffer) {
                sql[i] = sql[i].toString('hex').toUpperCase();
            }
        }
    }
    return sql;
}
exports.raw2str = raw2str;