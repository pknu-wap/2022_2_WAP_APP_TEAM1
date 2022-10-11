var express = require("express")
const router = express.Router()
const session = require("express-session")
const bodyParser = require('body-parser');
const multer = require('multer');
const token = require("./util/jwt")
const upload = multer(); 
// 세션값 사용 등록
router.use(
    session({
        secret: "secret key",
        resave: false,
        saveUninitialized: true,
        cookie: {
            httpOnly: true,
            secure: true,
            maxAge: 60000
        },
    })
);
router.use(express.json()); //application/json 타입 사용 등록
router.use(express.urlencoded({extended: true})); //application/x-www-form-urlencoded 타입 사용 등록

// 유저 관련 API
const user = require("./api/users");
router.post("/api/users/login", user.login);
router.post("/api/users/register", user.register);
router.post("/api/users/duplicate", user.duplicate_id);
router.post("/api/users/me/photo", upload.single('profile'), token.authenticateAccessToken, user.editProfileImage); //multipart/form-data 타입으로 파일을 받아옴 (upload.single('profile')은 profile이라는 이름의 파일을 받아옴)
router.get("/api/users/me", token.authenticateAccessToken, user.getInfo);
router.post("/api/users/me/nickname", token.authenticateAccessToken, user.editNickname);
// 계획 관련 API
const plan = require("./api/plans");
router.put("/api/plans", plan.create);
router.delete("/api/plans", plan.delete);
router.post("/api/plans", plan.update);
router.get("/api/plans", plan.getPlan);
router.get("/api/plans/list", plan.getPlanList);
router.put("/api/plans/participants", plan.addParticipant);
router.delete("/api/plans/participants", plan.deleteParticipant);


module.exports = router;