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

	var decodedPromise = authFunctions.verifyToken(req.headers);
	var result = {};

	// Promise should resolve immediately
	decodedPromise.then((data)=>{
		result = data;
		// console.log(result);
	}).catch((err)=>{
		return res.status(401).json({
			success: false,
			message: "Failed to decode token"
		});
	});

	if(result.isAuthenticated && result.decoded != null){
		console.log("Processing result");
		req.flash("decoded", result.decoded);
		next();
	}else{
		return res.json({
			success: false,
			message: "Faile to process token"
		});
	}
};

// If a user is trying to access a protected resource, he should be 
// redirected to that resource after a successful login
/*middleware.isRedirection = function(req, res, next) {
	console.log(req.params);
	next();
}
*/
module.exports = middleware;