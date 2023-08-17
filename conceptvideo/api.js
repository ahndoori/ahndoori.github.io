const winston = require('winston');
const logger = winston.createLogger();
const express = require('express');
const app = express();
app.use(express.json());
app.use(express.static('static'));
app.disable('x-powered-by');

const prop = require('./config/properties.json');
console.log(`\x1b[34mBEFORE PROCESS.ENV.NODE_ENV "${process.env.NODE_ENV}"`,'\x1b[0m');
process.env.NODE_ENV = process.env.NODE_ENV ? process.env.NODE_ENV.trim() : 'prd';
console.log(`\x1b[34mAFTER PROCESS.ENV.NODE_ENV "${process.env.NODE_ENV}"`,'\x1b[0m');

/*
const develop = require('./routes/develop');
app.use('/api/v1/develop', develop);
const game = require('./routes/game');
app.use('/api/v1/game', game);
*/

//const routesApi = require('./routes/api');
//app.use('/api', routesApi);
const routesRoot = require('./routes/root');
const routesCommon = require('./routes/common');
const routesModel = require('./routes/model');
const routesBackend = require('./routes/backend');
const routesConceptVideo = require('./routes/conceptvideo');

app.get('/', routesRoot);
app.use('/common', routesCommon);
app.use('/api/v1/contest', routesModel);
app.use('/api/v1/game', routesModel);
app.use('/api', routesBackend);
app.use('/conceptvideo', routesConceptVideo);

app.listen(prop.port, () => {
  console.log(`\x1b[33mhttp://localhost:${prop.port}`,'\x1b[0m');
});
