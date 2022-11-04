const raw2str = (sql) => {
    for (var i = 0; i < sql.length; i++) {
        for (var prop in sql[i]) {
            if (sql[i][prop] instanceof Buffer) {
                sql[i][prop] = sql[i][prop].toString('hex').toUpperCase();
            }
        }
    }
    return sql;
}
exports.raw2str = raw2str;