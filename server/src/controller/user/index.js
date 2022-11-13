const models = require("../../models")
const { raw2str } = require("../../util/rawtostr")
const token = require("../../util/jwt")
module.exports = {
    //Login(POST)
    async login(req, res) {
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
            let user = raw2str(await models.User.findOne({
                where: {
                    AccountType: req.body.AccountType,
                    Username: req.body.Username,
                    Password: req.body.Password
                }
            }));

            if (user === null) {
                return res.status(200).send({ status: false, reason: "아이디 또는 비밀번호가 틀렸습니다." });
            }
            let isProfileExists = true;
            if (user.Nickname == null || user.Nickname == "") {
                isProfileExists = false;
            }
            let AccessToken = token.generateAccessToken(user.UserId);
            let RefreshToken = token.generateRefreshToken(user.UserId);
            return res.status(200).send({ status: true, reason: "로그인 성공", AccessToken: AccessToken, RefreshToken: RefreshToken, isProfileExists: isProfileExists });

        }
        //Social Login(Kakao, Naver)
        let user = raw2str(await models.User.findOrCreate({
            where: {
                AccountType: req.body.AccountType,
                Username: req.body.Username
            }
        }));
        let isProfileExists = true;
        if (user.Nickname == null || user.Nickname == "") {
            isProfileExists = false;
        }
        let AccessToken = token.generateAccessToken(user.UserId);
        let RefreshToken = token.generateRefreshToken(user.UserId);
        return res.status(200).send({ status: true, reason: "로그인 성공", AccessToken: AccessToken, RefreshToken: RefreshToken, isProfileExists: isProfileExists });
    },
    //Register(POST)
    async register(req, res) {
        if (req.body.Username === undefined || req.body.Password === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        let user = raw2str(await models.User.create({
            AccountType: 0,
            Username: req.body.Username,
            Password: req.body.Password
        }));
        let AccessToken = token.generateAccessToken(user.UserId);
        let RefreshToken = token.generateRefreshToken(user.UserId);
        return res.status(200).send({
            message: { status: true, reason: "회원가입 성공", AccessToken: AccessToken, RefreshToken: RefreshToken },
        });
    },
    //Duplicate Check(GET)
    async duplicateCheck(req, res) {
        if (req.query.Username === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        let user = await models.User.findByPk(req.query.Username);
        if (user === null) {
            return res.status(200).send({
                message: { status: true, reason: "사용 가능한 아이디입니다." }
            });
        }
        return res.status(200).send({
            message: { status: false, reason: "이미 사용 중인 아이디입니다." }
        });
    },
    //Get Info(GET)
    async getInfo(req, res) {
        let user = raw2str(await models.User.findByPk(req.token.userId))
        if (user === null) {
            return res.status(401).send({
                message: "Unauthorized"
            });
        }
        return res.status(200).send({
            message: { status: true, reason: "정보 조회 성공", user: user }
        });
    },
    //Update Info(PUT)
    async updateInfo(req, res) {
        let user = raw2str(await models.User.findByPk(req.token.userId));
        if (req.body.Nickname !== undefined && req.body.Nickname !== "") {
            user.Nickname = req.body.Nickname;
        }
        if (req.body.PhoneNum !== undefined && req.body.PhoneNum !== "") {
            user.PhoneNum = req.body.PhoneNum;
        }
        if (req.file !== undefined) {
            let image_result = await require("../../util/file")(image.buffer, image.originalname);
            if (image_result.result == false) {
                return res.status(500).send({
                    message: { status: false, reason: "이미지 업로드 실패" }
                });
            }
            user.ProfileImage = image_result.fileName;
        }
        await user.save();
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
