const router = require('express').Router()

const user = require('./user')
router.use('/user', user)   

const trip = require('./trip')
router.use('/trip', trip)

const flight = require('./flight')
router.use('/flight', flight)

module.exports = router