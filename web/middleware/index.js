var authFunctions = require("../routers/functions/auth-functions.js");
var middleware = {};

middleware.isLoggedIn = function(req, res, next) {
	// todo: allow for jsonwebtoken authentication

	// If it's the mobile application
	if(req.headers["user-agent"] == "Madrassa-Application"){
		middleware.isAuthenticated(req, res, next);
			
	}else{
	// It's a web browser	

		if(req.isAuthenticated()){
			return next();
		}else{
			res.redirect("/");
		}
		
	}

};

// To prevent logged-in users from visiting /auth/login and /auth/register
middleware.isNotLoggedIn = function(req, res, next) {
	if(req.isAuthenticated()){
		res.redirect("/home");
	}else{
		return next();
	}
};

// Executed only if there's a [x-auth-token] header, and if the 
// User-Agent is "Madrassa-Application"
middleware.isAuthenticated = function(req, res, next) {

	var decodedPromise = authFunctions.decodeToken(req.headers);
	console.log(req.headers);

	// A response will only be sent when failing to authenticate.
	var response = {
		success: false,
		message: ""
	};

	// Promise should resolve immediately
	decodedPromise.then((data)=>{
		// data {isAuthenticated: boolean, message: "string", decoded: {}}
		console.log(data);
		if(data.isAuthenticated){
			req.flash("decoded", data.decoded);
			next();
		}else{
			response.message = "Failed to authenticate token";
			return res.status(401).json(response);
		}

	}).catch((err)=>{
		response.message = "Failed to decode token";
		return res.status(401).json(response);
	});

};

// If a user is trying to access a protected resource, he should be 
// redirected to that resource after a successful login
/*middleware.isRedirection = function(req, res, next) {
	console.log(req.params);
	next();
}
*/
module.exports = middleware;