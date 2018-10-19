var router        = require("express").Router();
var validator     = require("../functions/validator.js");
var authFunctions = require("../functions/auth-functions.js");
var middleware    = require("../../middleware/index.js");
var CourseController = require("../../controllers/course-controller.js");


var token = {};

// All courses
router.get("/", middleware.isAuthenticated, (req, res)=>{
	token = req.flash("decoded");

	var response = {
		message: "",
		success: false,
		courses: []
	};

	CourseController.getCoursesDetails().then((courses)=>{
		if(courses != null){
			response.courses = courses;
			response.success = true;
			response.message = "All courses";
			return res.json(response);

		}else{
			response.message = "Failed to get courses";
			return res.json(response);
		}
	}).catch((err)=>{
		// throw err;
		console.log(err);
		response.message = "Error occurred while getting courses";
		return res.json(response);
	});

});

// Display a single course's data
router.get("/:courseId", middleware.isAuthenticated, (req, res)=>{
	token = req.flash("decoded");

	var response = {
		message: "",
		success: false,
		course: null,
		students: null
	};

	Promise.all([
		CourseController.getCourse(req.params.courseId),
		CourseController.getCourseStudents(req.params.courseId)
	])
	.then((values)=>{
		// returns [course, students]

		response.course   = Object.assign({}, values[0][0]);
		response.students = values[1].map(student => Object.assign({}, student));
		response.success  = true;
		response.message  = "success";

		return res.json(response);
	}).catch((err)=>{
		// throw err;
		console.log(err);

		response.message = "Error retrieving course data";
		return res.json(response);
	});

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