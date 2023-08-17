
const router = require('express').Router();

router.get('/login', (req, res) => {
    res.send('This is login page');
})
router.get('/logout', (req, res) => {
    res.send('This is logout page');
});
router.get('/register', (req, res) => {
    res.send('This is register page');
});

router.get('/npm', (req, res) => {
    res.send('npm, <img src="../npm.png"/>');
});
/*
app.get('/npm',function(req,res){
	res.send('npm, <img src="./npm.png"/>');
})
*/

module.exports = router;
