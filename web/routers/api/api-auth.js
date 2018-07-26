var router = require("express").Router();
var authFunctions = require("../functions/auth-functions.js");
var validator     = require("../functions/validator.js");

// Router Documentation
router.get("/", (req, res)=>{
	return res.json({
		"AvailableRoutes": [
			"/register",
			"/login"
		]
	});
});

router.post("/register", (req, res)=>{
	// todo: save to database
	var response = {
		token  : null,
		success: false,
		message: ""
	};

	if(!validator.isValidLoginBody(req.body)){
		response.success = false;
		response.message = "Username and password must be provided";
		res.json(response);
	}

	return res.send("Registering");
	
});

router.post("/login", (req, res)=>{

	var response = {
		token : null,
		success: false,
		message: "",
		user   : {}
	};

	// todo: create jsonwebtoken and send it to the user.
	// var isValidRequest = validator.isValidApiRequest(req.body, req.headers);
	if(!validator.isValidLoginBody(req.body)){
		response.success = false;
		response.message = "Username and password must be provided"; 

		return res.json(response);
	}

	if(!validator.isValidApiHeader(req.headers)){
		response.success = false;
		response.message = "Only android apps are allowed";
		return res.json(response);
	}

	authFunctions.createAuthToken(req.headers, req.body).then((tokenResult)=>{
		response.token   = tokenResult.token;
		response.success = tokenResult.success;
		response.message = tokenResult.message;
		response.user    = tokenResult.user;
		return res.json(response);
		
	}).catch((err)=>{
		console.log(err);
		response.success = false;
		response.message = "Failed to create authentication token"; 
		return res.json(response);
	});

});

router.get("/logout", (req, res)=>{
	// todo: remove the token from the client
	res.send("logout");

});

module.exports = router;
