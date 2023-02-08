const jwt = require("jsonwebtoken");


function generateAccessToken(userId) {
    return jwt.sign({UserId: userId}, process.env.ACCESS_TOKEN_SECRET, {expiresIn: "3d"});
};
function generateRefreshToken(userId) {
    return jwt.sign({UserId: userId}, process.env.REFRESH_TOKEN_SECRET, {expiresIn: "7d"});
}

function authenticateAccessToken(req, res, next) {
    const authHeader = req.headers["authorization"];
    const token = authHeader && authHeader.split(" ")[1];
    if (token == null) return res.sendStatus(401);
    jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, data) => {
        if (err) return res.sendStatus(403);
        console.log(data);
        req.token = data;
        next();
    });
}

function authenticateRefreshToken(req, res, next) {
    const authHeader = req.headers["authorization"];
    const token = authHeader && authHeader.split(" ")[1];
    if (token == null) return res.sendStatus(401);
    jwt.verify(token, process.env.REFRESH_TOKEN_SECRET, (err, data) => {
        if (err) return res.sendStatus(403);
        req.token = data;
        next();
    });
}

module.exports = {
    generateAccessToken,
    generateRefreshToken,
    authenticateAccessToken,
    authenticateRefreshToken
};