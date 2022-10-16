const router = require('express').Router()
const user = require('./user')
/**
 * @swagger
 * tags:
 *   name: Users
 *   description: 유저 추가 수정 삭제 조회
 */
router.use('/user', user)   

module.exports = router