const router = require('express').Router()

const user = require('./user')
router.use('/user', user)   

const trip = require('./trip')
router.use('/trip', trip)

module.exports = router