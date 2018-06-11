var router    = require("express").Router();
var sequelize = require("../config/sequelize-config.js").sequelize;
var Course    = sequelize.import("../models/course.js");
var CourseController = require("../controllers/course-controller.js");

router.get("/create-course", (req, res)=>{
	res.render("course/create-course");
});

// This route redirects to /course/all after 
// either failing or succeeding in creating a course. 
// req.flash() is used to send messages.

router.post("/create-course", (req, res)=>{
	// var user = req.session.user;

	/*var response = {
		message: "",
		error  : false
	}; */

	if(!req.session.user || !req.session.user.userId){
		// response.error = true;
		// response.message = "No teacher found";
		req.flash("courseMessage", "No teacher found");
		// return res.json(response);
		return res.redirect("/course/all");

	}else{
		if(req.body.title == null || req.body.title.length < 3){
			req.flash("courseMessage", "Invalid course title");
			return res.redirect("course/all");
		}

		CourseController
			.createCourse(req.session.user.userId, 
				req.body.title, 
				req.body.description)

			.then((course)=>{
				req.flash("courseMessage", "Course created successfully.");
				return res.redirect("/course/all");
			}).catch((err)=>{
				req.flash("courseMessage", err);
				return res.redirect("/course/all");
			});
	}

});

router.get("/all", (req, res)=>{

	// If there's a redirection from `course/create-course`, 
	// then there should be a courseMessage flash. 

	var courseMessage = req.flash("courseMessage");
	// console.log("courseMessage", courseMessage);
	// Sample data from CourseController.getCoursesDetails:
	// TextRow {
    // title: 'Physics',
    // description: 'An introduction to classic physics.',
    // year: '2018',
    // teacher: 'Hassan Ahmad' }

	// CourseController.allCourses()
	CourseController.getCoursesDetails()
		.then((courses)=>{
			res.render("course/index", {
				message: courseMessage,
				courses: courses
			});

		}).catch((err)=>{
			// TODO: Must inspect further. How might this fail?
			req.flash("courseError", "Unable to get all courses");
			return res.redirect("/home");
		});
});

module.exports = router;