const raw2str =
{
    sqlswap: function (sql) {
        for (var i = 0; i < sql.length; i++) {
            for (var prop in sql[i]) {
                if (sql[i][prop] instanceof Buffer) {
                    sql[i][prop] = sql[i][prop].toString('hex').toUpperCase();
                }
            }
        }
        return sql;
    }
}
module.exports = raw2str;