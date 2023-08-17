const router = require('express').Router();
const winston = require('winston');
const logger = winston.createLogger();
const prop = require('../config/properties.json');
const env = require(`../config/environment_${process.env.NODE_ENV}.json`);
console.log(`\x1b[36m../config/environment_${process.env.NODE_ENV}.json`,'\x1b[0m');
const common = require('../common.js');

const errorHeaders={"content-type":"application.json"};
var errorCode=500;

router.get('/', (req, res) =>{
  logger.info('you come in path to '/'')
res.send("Hello World!");
});

router.use('/*',async(req,res)=>{
  let headers=req.headers;
  headers['host']=env.host_backend;
  delete headers['connection'];
  delete headers['content-length'];

  let uri = `${env.protocol_backend}${env.host_backend}${env.port_backend!='80' && env.port_backend!='443' ? ':'+env.port_backend : ''}${req.originalUrl}`;
  console.log('\nROUTES BACKEND '+uri);
  let options = {
    timeout: prop.timeout,
    method:req.method,
    uri:uri,
    headers:headers,
    json: true,
    body:req.body,
  };

  await common.call(options).then(function(response){
    responseHeaders=response.headers;
    delete responseHeaders['content-length'];
    //console.log('responseHeaders',JSON.stringify(responseHeaders));
    res.writeHead(response.statusCode,response.headers);
    res.end(JSON.stringify(response['body']));
  }).catch(error=>{
    if(process.env.NODE_ENV!='prd') console.log('CATCH 😱',error);
    if(error==null) errorCode=500;
    else if(error.code==null) errorCode=500;
    else if(error.code=='ETIMEDOUT') errorCode=500;
    else errorCode=500;
    res.writeHead(errorCode,errorHeaders);
    res.end(JSON.stringify(error));
  });

});

module.exports = router;
