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

	CourseController.getCoursesDetails().then((result)=>{
		if(result != null){
			response.courses = result.courses;
			response.success = true;
			response.message = "All courses";
			// console.log(response.courses);
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
		students: []
	};

	Promise.all([
		CourseController.getCourse(req.params.courseId),
		CourseController.getCourseStudents(req.params.courseId)
	])
	.then((values)=>{
		// returns [course, students]

		response.course   = Object.assign({}, values[0].course);

		// students from getCourseStudents()
		if(values[1] != null && values[1].students != null){ 
			response.students = values[1].students.map(
				student => Object.assign({}, student));
		}
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
		message: "Joining course",
		success: false
	};

	var courseId = req.params.courseId;

	res.json(response);
});

router.post("/:courseId/leave-course", middleware.isAuthenticated, (req,res)=>{
	token = req.flash("decoded");
	var response = {
		message: "Leaving course",
		success: false
	};

	var courseId = req.params.courseId;

	return res.json(response);
});


module.exports = router;