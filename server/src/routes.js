var express = require("express")
const router = express.Router()
const session = require("express-session")
const bodyParser = require('body-parser');
const multer = require('multer');
const upload = multer(); //{ dest: './temp/' }
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
router.use(express.json());
router.use(express.urlencoded({extended: true}));

const user = require("./api/users");
router.post("/api/users/login", user.login)
router.post("/api/users/register", user.register)
router.post("/api/users/duplicate", user.duplicate_id)
router.post("/api/users/editProfileImage", upload.single('profile'), user.editProfileImage);
module.exports = router;