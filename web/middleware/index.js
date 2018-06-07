var middleware = {};

middleware.isLoggedIn = function(req, res, next) {
	if(req.isAuthenticated()){
		return next();
	}else{
		res.redirect("/");
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

// If a user is trying to access a protected resource, he should be 
// redirected to that resource after a successful login
/*middleware.isRedirection = function(req, res, next) {
	console.log(req.params);
	next();
}
*/
module.exports = middleware;