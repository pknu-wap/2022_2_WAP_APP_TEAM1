const models = require("../../models")
const { raw2str } = require("../../util/rawtostr")
const token = require("../../util/jwt")
module.exports = {
    //Login(POST)
    async login(req, res) {
        const { AccountType, Username, Password } = req.body;
        console.log(AccountType, Username, Password);
        if (AccountType == undefined || Username == undefined) {
            return res.send({status: false, reason: "AccountType or Username is undefined"});
        }
       
        if (AccountType > 2 || AccountType < 0) {
            return res.send({status: false, reason: "AccountType is invalid"});
        }
        //Local login
        if (AccountType == 0) {
            let user = raw2str(await models.User.findOne({
                where: {
                    AccountType: 0,
                    Username: Username,
                    Password: Password
                }
            }));

            if (user == null) {
                return res.send({ status: false, reason: "아이디 또는 비밀번호가 틀렸습니다." });
            }
            let isProfileExists = (!(user.Nickname == null || user.Nickname == ""));
            let AccessToken = token.generateAccessToken(user.UserId);
            let RefreshToken = token.generateRefreshToken(user.UserId);
            return res.send({ status: true, reason: "로그인 성공", AccessToken: AccessToken, RefreshToken: RefreshToken, isProfileExists: isProfileExists });

        }
        //Social Login(Kakao, Naver)
        let user = raw2str(await models.User.findOrCreate({
            where: {
                AccountType: AccountType,
                Username: Username
            }
        }));
        let isProfileExists = (!(user.Nickname == null || user.Nickname == ""));
        let AccessToken = token.generateAccessToken(user.UserId);
        let RefreshToken = token.generateRefreshToken(user.UserId);
        return res.send({ status: true, reason: "로그인 성공", AccessToken: AccessToken, RefreshToken: RefreshToken, isProfileExists: isProfileExists });
    },
    //Register(POST)
    async register(req, res) {
        const { Username, Password } = req.body;
        if (Username == undefined || Password == undefined) {
            return res.send({ status: false, reason: "Username or Password is undefined" });
        }
        let user = raw2str(await models.User.create({
            AccountType: 0,
            Username: Username,
            Password: Password
        }));
        let AccessToken = token.generateAccessToken(user.UserId);
        let RefreshToken = token.generateRefreshToken(user.UserId);
        return res.send({ status: true, reason: "회원가입 성공", AccessToken: AccessToken, RefreshToken: RefreshToken });
    },
    //Duplicate Check(GET)
    async duplicateCheck(req, res) {
        const { Username } = req.query;
        if (Username == undefined) {
            return res.send({ status: false, reason: "Username is undefined" });
        }
        let user = await models.User.findByPk(Username);
        if (user == null) {
            return res.send({ status: true, reason: "사용 가능한 아이디입니다." });
        }
        return res.send({status: false, reason: "이미 사용 중인 아이디입니다." });
    },
    //Get Info(GET)
    async getInfo(req, res) {
        const { UserId } = req.token;
        let user = raw2str(await models.User.findByPk(UserId))
        if (user == null) {
            return res.status(401).send("Unauthorized");
        }
        return res.send({status: true, reason: "정보 조회 성공", user: user });
    },
    //Update Info(PUT)
    async updateInfo(req, res) {
        const { UserId } = req.token;
        const { Nickname, PhoneNum, ProfileImage } = req.body;
        const { file } = req;
        let user = raw2str(await models.User.findByPk(UserId));
        if (Nickname !== undefined && Nickname !== "") {
            user.Nickname = Nickname;
        }
        if (PhoneNum !== undefined && PhoneNum !== "") {
            user.PhoneNum = PhoneNum;
        }
        let transaction = await models.sequelize.transaction();
        try {
            if (file !== undefined) {
                let image_result = await require("../../util/file")(file.buffer, file.originalname);
                if (image_result.result == false) {
                    return res.status(500).send({status: false, reason: "이미지 업로드 실패"});
                }
                user.ProfileImage = image_result.fileName;
            }
            await user.save();
            await transaction.commit();
            return res.send({status: true, reason: "정보 수정 성공"});
        } catch (err) {
            await transaction.rollback();
        }
    },
    //Update Token(HEAD)
    updateToken(req, res) {
        const { UserId } = req.token;
        let AccessToken = token.generateAccessToken(UserId);
        let RefreshToken = token.generateRefreshToken(UserId);
        res.set('x-access-token', AccessToken);
        res.set('x-refresh-token', RefreshToken);
        return res.sendStatus(200);
    }
}
