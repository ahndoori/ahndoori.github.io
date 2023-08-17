const request = require('request');

module.exports = {
	call:function(options){
		return new Promise((resolve, reject) => {
			request(options, function (error,response) {
				if(!error){
					if(response.statusCode === 200){
						if(process.env.NODE_ENV!='prd') console.log('SUCESS 😎',response);
						resolve(response);
					}else{
						if(process.env.NODE_ENV!='prd') console.log('FAIL 🤢',response);
						resolve(response);
					}
				}else {
					if(process.env.NODE_ENV!='prd') console.log('ERROR 😡',JSON.stringify(error));
					reject(error);
				}
			});
		});
	},
	test:function(a){
		console.log(a);
	}
}