const models = require("../../models")
const { raw2str } = require("../../util/rawtostr")
const token = require("../../util/jwt")
module.exports = {
    //Login(POST)
    login(req, res) {
        if (req.body.AccountType === undefined || req.body.Username === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        if (req.body.AccountType > 2 || req.body.AccountType < 0) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        //Local login
        if (req.body.AccountType == 0) {
            models.User.findOne({
                where: {
                    AccountType: req.body.AccountType,
                    Username: req.body.Username,
                    Password: req.body.Password
                }
            }).then(user => {
                user = raw2str(user);
                if (user === null) {
                    return res.status(401).send({
                        message: "Unauthorized"
                    });
                }
                let isProfileExists = true;
                if (user.Nickname == null || user.Nickname == "") {
                    isProfileExists = false;
                }
                let AccessToken = token.generateAccessToken(user.UserId);
                let RefreshToken = token.generateRefreshToken(user.UserId);
                return res.status(200).send({
                    message: { status: true, reason: "로그인 성공", AccessToken: AccessToken, RefreshToken: RefreshToken, isProfileExists: isProfileExists },
                });
            }).catch(err => {
                return res.status(500).send({
                    message: err.message
                });
            });
        }
        models.User.findOne({
            where: {
                AccountType: req.body.AccountType,
                Username: req.body.Username
            }
        }).then(user => {
            user = raw2str(user);
            if (user === null) {
                return res.status(401).send({
                    message: "Unauthorized"
                });
            }
            let isProfileExists = true;
            if (user.Nickname == null || user.Nickname == "") {
                isProfileExists = false;
            }
            let AccessToken = token.generateAccessToken(user.UserId);
            let RefreshToken = token.generateRefreshToken(user.UserId);
            return res.status(200).send({
                message: { status: true, reason: "로그인 성공", AccessToken: AccessToken, RefreshToken: RefreshToken, isProfileExists: isProfileExists },
            });
        }).catch(err => {
            return res.status(500).send({
                message: err.message
            });
        });
    },
    //Register(POST)
    register(req, res) {
        if (req.body.Username === undefined || req.body.Password === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        models.User.create({
            AccountType: 0,
            Username: req.body.Username,
            Password: req.body.Password
        }).then(user => {
            user = raw2str(user);
            let AccessToken = token.generateAccessToken(user.UserId);
            let RefreshToken = token.generateRefreshToken(user.UserId);
            return res.status(200).send({
                message: { status: true, reason: "회원가입 성공", AccessToken: AccessToken, RefreshToken: RefreshToken },
            });
        }).catch(err => {
            return res.status(500).send({
                message: err.message
            });
        }
        );
    },
    //Duplicate Check(GET)
    duplicateCheck(req, res) {
        if (req.query.Username === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        models.User.findByPk(req.query.Username).then(user => {
            if (user === null) {
                return res.status(200).send({
                    message: { status: true, reason: "사용 가능한 아이디입니다." }
                });
            }
            return res.status(200).send({
                message: { status: false, reason: "이미 사용 중인 아이디입니다." }
            });
        }).catch(err => {
            return res.status(500).send({
                message: err.message
            });
        });
    },
    //Get Info(GET)
    getInfo(req, res) {
        models.User.findByPk(req.token.userId).then(user => {
            user = raw2str(user);
            if (user === null) {
                return res.status(401).send({
                    message: "Unauthorized"
                });
            }
            return res.status(200).send({
                message: { status: true, reason: "정보 조회 성공", user: user }
            });
        }).catch(err => {
            return res.status(500).send({
                message: err.message
            });
        });
    },
    //Update Info(PUT)
    updateInfo(req, res) {
        models.User.findByPk(req.token.userId).then(user => {
            if (req.body.Nickname !== undefined && req.body.Nickname !== "") {
                user.Nickname = req.body.Nickname;
            }
            if (req.body.PhoneNum !== undefined && req.body.PhoneNum !== "") {
                user.PhoneNum = req.body.PhoneNum;
            }
            if (req.file !== undefined) {
                require("../../util/file")(image.buffer, image.originalname)
                    .then(image_result => {
                        if (image_result.result == false) {
                            return res.status(500).send({
                                message: { status: false, reason: "이미지 업로드 실패" }
                            });
                        }
                    }).catch(err => {
                        return res.status(500).send({
                            message: err.message
                        });
                    });
                user.ProfileImage = image_result.fileName;
            }
            user.save();
        }).catch(err => {
            return res.status(500).send({
                message: err.message
            });
        });
    },
    //Update Token(HEAD)
    updateToken(req, res) {
        let AccessToken = token.generateAccessToken(req.token.userId);
        let RefreshToken = token.generateRefreshToken(req.token.userId);
        res.set('x-access-token', AccessToken);
        res.set('x-refresh-token', RefreshToken);
        return res.sendStatus(200);
    }
}
