var router    = require("express").Router();
var sequelize = require("../config/sequelize-config.js").sequelize;
var Course    = sequelize.import("../models/course.js");
var CourseController = require("../controllers/course-controller.js");
var middleware = require("../middleware/index.js");

router.get("/create-course", middleware.isLoggedIn, (req, res)=>{
	res.render("course/create-course");
});

// This route redirects to /course/all after 
// either failing or succeeding in creating a course. 
// req.flash() is used to send messages.

router.post("/create-course", middleware.isLoggedIn, (req, res)=>{
	// var user = req.session.user;

	if(!req.session.user || !req.session.user.userId){
		req.flash("courseMessage", "No teacher found");

		return res.redirect("/course/all");

	}else{
		if(req.body.title == null || req.body.title.length < 3){
			req.flash("courseMessage", "Invalid course title");
			return res.redirect("/course/all");
		}

		// Prevent non-teachers, and non-admins from creating courses.
		if(req.session.user.role != "teacher" 
			&& req.session.user.role != "admin")
		{
			req.flash("courseMessage", "Only techers can create courses");
			return res.redirect("/course/all");
		}

		CourseController
			.createCourse(req.session.user.userId, 
				req.body.title, 
				req.body.description)

			.then((result)=>{
				req.flash("courseMessage", "Course created successfully.");
				return res.redirect("/course/all");
			}).catch((err)=>{
				req.flash("courseMessage", err);
				return res.redirect("/course/all");
			});
	}

});

router.get("/:courseId/join-course/", middleware.isLoggedIn, (req, res)=>{
	if(!req.session.user || !req.session.user.userId){
		req.flash("courseMessage", "Unable to join course. Student not found");

		return res.redirect("/course/all");
	}else{

		if(req.session.user.role != "student"){
			req.flash("courseMessage", "Only students can access courses");
			return res.redirect("/course/all");
		}

		CourseController.joinCourse(
			req.session.user.userId, req.params.courseId)
			.then((result)=>{
				if(!result){
					req.flash("courseMessage", "Failed to join course");
					return res.redirect("/course/all");
				}else if(result.err != null){
					if(err.name == "SequelizeUniqueConstraintError"){
						req.flash("courseMessage", 
							"You are already registered in this course");
						return res.redirect("/course/all");
					}
				}else{
					req.flash("courseMessage", "Student joined course");
					return res.redirect("/course/all");
				}
			}).catch((err)=>{
				// The user has already joined the course.
				if(err.name == "SequelizeUniqueConstraintError"){
					req.flash("courseMessage", 
						"You are already registered in this course");
					return res.redirect("/course/all");
				}

				req.flash("courseMessage", err.name);
				console.log(err.name);
				return res.redirect("/course/all");
				// console.log(Object.keys(err));

			});

	}
});

router.get("/:courseId/leave-course", middleware.isLoggedIn, (req, res)=>{
	if(!req.session.user || !req.session.user.userId){
		req.flash("courseMessage", "Unable to leave course - no student.");
		res.redirect("/course/all");
	}

	CourseController.leaveCourse(req.session.user.userId, req.params.courseId)
		.then((result)=>{
			// console.log(result);
			if(result.affectedRows == 1){
				req.flash("courseMessage", "Student left course");
			}else{
				req.flash("courseMessage", 
					"Student is not registered in this course.");
			}
			res.redirect("/course/all");
		}).catch((err)=>{
			req.flash("courseMessage", "Failed to leave course");
		});
	
});

router.get("/all", (req, res)=>{

	// If there's a redirection from `course/create-course`, 
	// then there should be a courseMessage flash. 

	var courseMessage = req.flash("courseMessage");
	// Sample data from CourseController.getCoursesDetails:
	// TextRow {
    // title: 'Physics',
    // description: 'An introduction to classic physics.',
    // year: '2018',
    // teacher: 'Hassan Ahmad' }

	CourseController.getCoursesDetails()
		.then((result)=>{
			res.render("course/index", {
				message: courseMessage,
				courses: result.courses
			});

		}).catch((err)=>{
			// TODO: Must inspect further. How might this fail?
			req.flash("courseError", "Unable to get all courses");
			return res.redirect("/home");
		});
});

router.get("/:courseId", (req, res)=>{
	var responseData = {
		course: null,
		students: null,
		displayJoinLink: false,
		displayLeaveLink: false,
		message: null
	};

	Promise.all([
		CourseController.getCourse(req.params.courseId),
		CourseController.getCourseStudents(req.params.courseId)
	]).then((result)=>{
		// result[courseResult, courseStudentsResult]
		var course   = result[0].course;
		var students = result[1].students;

		if(course == null && result[0].message == "fail"){
			console.log(result[0].err);
			return res.render("course/course", responseData);
		}

		if(result[1].err != null && result[1].message == "fail"){
			console.log(result[1].err);
			res.render("course/course", responseData);
		}

		var userJoinedCourse = false;
		if(req.session.user != null && students != null){
			for(var i = 0; i < students.length; i++){
				if(students[i].id == req.session.user.userId){
					userJoinedCourse = true;
				}
			}
		}
		responseData.course   = course;
		responseData.students = students;
		responseData.displayJoinLink  = !userJoinedCourse;
		responseData.displayLeaveLink = userJoinedCourse;

		res.render("course/course", responseData);

	}).catch((err)=>{
		res.render("course/course", {
			course: null,
			students: null,
			displayJoinLink: false,
			displayLeaveLink: false,
			message: err
		});
	});
});

module.exports = router;