const router = require('express').Router();

router.get('/view', (req, res) => {
    res.send('This is view page');
});
router.get('/add', (req, res) =>{
    res.send('This is add page');
});
router.get('/edit', (req, res) =>{
    res.send('This is edit page');
});
router.get('/', (req, res) =>{
    logger.info('you come in path to '/'')
	res.send("Hello World!");
});
/*
app.get("/", (req, res) => {
	logger.info('you come in path to '/'')
	res.send("Hello World!");
});
*/

module.exports = router;