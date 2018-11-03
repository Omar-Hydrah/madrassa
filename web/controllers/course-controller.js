var sequelize = require("../config/sequelize-config.js").sequelize;
var User      = sequelize.import("../models/user.js");
var Course    = sequelize.import("../models/course.js");
var CourseStudent = sequelize.import("../models/course-student.js");


var CourseController = {};

// The user must have a role of a teacher
CourseController.createCourse = function(teacherId, title, description, 
	plainData = true) 
{

	return new Promise((resolve, reject)=>{
		var result = {
			course: null,
			message: "",
			err :null
		};

		Course.createCourse(teacherId, title, description, plainData)
		.then((res)=>{
			result.course  = res.course;
			result.message = res.message;
			result.err     = res.err;
			resolve(result);

		}).catch((err)=>{
			result.message = "fail";
			resolve(result);
			return;
		});
	});
}

// The user must have a role of a student.
// Should insert in `course_students` table.
CourseController.joinCourse = function(studentId, courseId, plainData = true) {

	return new Promise((resolve, reject)=>{
		var result = {
			courseStudent : null,
			message       : "",
			err           : null
		};

		CourseStudent.joinCourse(studentId, courseId, plainData)
		.then((res)=>{
			result.courseStudent = res.courseStudent;
			result.message       = res.mesage;
			result.err           = res.err;
			resolve(result);
			return;
		}).catch((err)=>{
			result.message = "fail";
			result.err = err;
			resolve(err);
			return;
		});
	});
};

CourseController.leaveCourse = function(studentId, courseId, plainData = true) {
	// Using the Model.destroy(), will cost 2 queries.
	
	return new Promise((resolve, reject)=>{
		var result = {
			affectedRows : 0,
			message      : "",
			err          : null
		};

		CourseStudent.leaveCourse(studentId, courseId, plainData)
		.then((res)=>{
			result.affectedRows = res.affectedRows;
			result.message      = res.message;
			result.err          = res.err;
			resolve(result);
			return;

		}).catch((err)=>{
			result.message = "fail";
			result.err     = err;
			resolve(result);
			return;
		});
	});
};

CourseController.getCoursesDetails = function() {
	return new Promise((resolve, reject)=>{
		var result = {
			courses: null,
			message: "",
			err    : null
		};

		Course.getCourses().then((res)=>{
			result.courses = res.courses;
			result.message = res.message;
			resolve(result);
			return;
		}).catch((err)=>{
			result.message = "fail";
			result.err     = err;
			resolve(result);
			return;
		});
	});
};

CourseController.getCourse = function(courseId) {
	return new Promise((resolve, reject)=>{
		var result = {
			course : null,
			message: "",
			err    : null
		};

		Course.getCourse(courseId).then((res)=>{
			result.course  = res.course;
			result.message = res.message;
			result.err     = res.err;
			resolve(result);
			return;
		}).catch((err)=>{
			result.message = "fail";
			result.err     = err;
			resolve(result);
			return;
		});
	});
};

CourseController.getCourseStudents = function(courseId) {
	return new Promise((resolve, reject)=>{
		var result = {
			students: null,
			message : "",
			err     : null
		};

		CourseStudent.getCourseStudents(courseId)
		.then((res)=>{
			result.students = res.students;
			result.message  = res.message;
			result.err      = res.err;
			resolve(result);
			return;
		}).catch((err)=>{
			result.message = "fail";
			result.err = err;
			resolve(result);
			return;
		});
	});
};


module.exports = CourseController;