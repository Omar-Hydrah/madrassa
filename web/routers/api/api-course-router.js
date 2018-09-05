var router        = require("express").Router();
var validator     = require("../functions/validator.js");
var authFunctions = require("../functions/auth-functions.js");
var middleware    = require("../../middleware/index.js");

var token = {};

// All courses
router.get("/", middleware.isAuthenticated, (req, res)=>{
	token = req.flash("decoded");

	var response = {
		message: "",
		success: false,
		courses: []
	};

	res.json(response);
});

// Display a single course's data
router.get("/:courseId", middleware.isAuthenticated, (req, res)=>{
	token = req.flash("decoded");

	var response = {
		message: "",
		success: false,
		course: null
	};

	res.json(response);
});

router.post("/:courseId/join-course", middleware.isAuthenticated, (req, res)=>{
	token = req.flash("decoded");
	var response = {
		message: "",
		success: false
	};

	res.json(response);
});


module.exports = router;