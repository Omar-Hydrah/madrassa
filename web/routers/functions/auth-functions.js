/*
Functions used to authenticate json web tokens. 
authFunctions.createAuthToken -> 
	returns a result object {success, message, token}

authFunctions.decodeToken ->
	returns a result object {isAuthenticated, message, decoded}
*/

var jwt       = require("jsonwebtoken");
var sequelize = require("../../config/sequelize-config.js").sequelize;
var User      = sequelize.import("../../models/user.js");

var authFunctions = {}; // to be exporetd.

// Used for signing json web tokens
var secret = "thegreatestsupersecretphrase";

// @Param: req.headers,  req.body
authFunctions.createAuthToken = async function(headers, body){
	var result = {
		success: false,
		message: "",
		token  : null,
		user   : {}
	};

	if(headers["user-agent"] == "Madrassa-Application"){
		var username = body.username;
		var password = body.password;

		try{
			var user = await User.findOne({
				where: {username : username}
			});

			if(!user){
				result.success = false;
				result.message = "Failed to find user"; 
				return result;
			}

			if(!user.verifyPassword(password)){
				result.success = false;
				result.message = "Failed to authenticate user " + username;
				return result;
			}else{
				// Creating the payload for the json web token
				var payload = {};
				payload.userId    = user.get("user_id");
				payload.username  = user.get("username");
				payload.firstName = user.get("first_name");
				payload.lastName  = user.get("last_name");
				payload.role      = user.get("role");
				payload.createdAt = user.get("created_at");

				var token = jwt.sign(payload, secret, {
					expiresIn: 60 * 60 * 24 * 3
				});
				result.success = true;
				result.message = "Token created";
				result.token   = token;
				
				// To be returned to the madrassa android client
				result.user = {};
				result.user.id        = user.get("user_id");
				result.user.username  = user.get("username");
				result.user.firstName = user.get("first_name");
				result.user.lastName  = user.get("last_name");
				result.user.role      = user.get("role");
				return result;
			}
		}catch(err){
			console.log(err);
			// throw err;
			result.success = false;
			result.message = "Failed to query database"
			return result;
		}
	}else{
		// throw new Error("Can't create a token for non-android clients");
		// console.log(headers);
		// console.log(body);
		result.success = false;
		result.message = "An attempt to create a token for non-android clients";
		return result;
	}
}

authFunctions.isValidToken = function(){

};

// Verifies that request headers contain "user-agent", and "x-auth-token"
authFunctions.areValidHeaders = function() {

};


// @Param: req.headers
// The token is expected to be in headers["x-auth-token"]
authFunctions.decodeToken = async function(headers){

	var result = {
		isAuthenticated : false,
		message : "",
		decoded   : null
	};

	if(headers["User-Agent"] != "Madrassa-Application"){
		result.isAuthenticated = false;
		result.message         = "Can't verify tokens for non android clients";
		return result;
	}

	if(!headers["x-auth-token"]){
		result.isAuthenticated = false;
		result.message         = "No token provided";
		return result;
	}

	try{
		var decoded = jwt.verify(headers["x-auth-token"], secret);
		if(decoded != null){
			result.isAuthenticated = true,
			result.decoded         = decoded;
			result.message         = "User authenticated";
			return result;
		}
		
		result.isAuthenticated = false;
		result.message = "Token mismatch";
		return result;
		
	}catch(err){
		console.log(err);
		result.isAuthenticated = false;
		result.message         = "Failed to decode token";
		return result;
	}
};



module.exports = authFunctions;