var validator = {};

validator.isValidApiRequest = function(body, headers){
	var result = true;

	if(!isValidRequestBody(body)){
		console.log(body);
		result = false;
	}

	if(!isValidRequestHeaders(headers)){
		console.log(headers);
		result = false;
	}

	return result;
}

validator.isValidLoginBody = function(body){
	if(!body.username || !body.password){
		return false;
	}else{
		return true;
	}
};

validator.isValidApiHeader = function(headers) {
	
	if(headers["user-agent"] != "Madrassa-Application"){
		return false;
	}
	return true;
}

validator.isValidApiHeaders = function(headers){
	if(headers["user-agent"] != "Madrassa-Application"){
		return false;
	}

	if(!headers["x-auth-token"]){
		return false;
	}

	return true;
};

module.exports = validator;