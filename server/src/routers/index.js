const router = require('express').Router()

const user = require('./user')
router.use('/user', user)   

const plan = require('./plan')
router.use('/plan', plan)

module.exports = router