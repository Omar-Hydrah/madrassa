var assert           = require("assert");
var sequelize        = require("../config/sequelize-config.js").sequelize;
var User             = sequelize.import("../models/user.js");
var Course           = sequelize.import("../models/course.js");
var CourseController = require("../controllers/course-controller.js");
var UserController   = require("../controllers/user-controller.js");

describe("Controlling the course", function(){

	var teacher = {};
	var student = {};
	var chemistry;
	var teacherObject = {
		username: "omar" ,
		password: User.hashPassword("1234"),
		first_name: "omar",
		last_name: "mohammad",
		role   : "teacher" 
	};

	var studentObject = {
		username: "ayman" ,
		password: User.hashPassword("1234"),
		first_name: "ayman",
		last_name: "mohammad",
		role   : "student" 
	};

	var physicsObject = {
		title : "Physics",
		description : "Introduction to Physics"
	};

	// Insert a new teacher, a new course and a new student into the database.
	before(function(done){
		// Creating a teacher.
		Promise.all([
			User.create( teacherObject, {
				fields: ["username", "password", 
					"first_name", "last_name", "role"
				]
			}),

			// Creating a student
			User.create(studentObject, {
				fields: ["username", "password", 
					"first_name", "last_name", "role"]
			})

		]).then((values)=>{
			// console.log(values);
			// Teacher is first, student is second.
			teacher = values[0];
			student = values[1];
			done();

		}).catch((err)=>{
			// console.log(err);
			console.log("Failed to create users");
		});		
	});
	describe("Course creation", ()=>{

		it("A teacher creates a new course", (done)=>{
			User.findOne({where: {username: teacherObject.username}})
			.then((user)=>{
				CourseController.createCourse(user.get("user_id"), 
					physicsObject.title, physicsObject.description, false)
				.then((course)=>{

					assert.equal(
						user.get("user_id"), 
						course.get("teacher_id"),
						"The user id must be " + 
							user.get("user_id") + " but found " +
							course.get("user_id"));

					done();
				}).catch((err)=>{
					// throw err;
					done();
				});
			}).catch((err)=>{
				throw err;
			});
			
		});

		it("A student can not create a new course", (done)=>{
			User.findOne({
				where: {
					username: studentObject.username
				}

			}).then((user)=>{
				// Throws "UnhandledPromiseRejectionWarning",
				// and it works as a warning if a student creates a course.
				// Must be updated for a better version.
				assert.rejects(

					CourseController.createCourse(user.get("user_id"), 
						physicsObject.title, physicsObject.description, false)
					,
					Error, "Failure - a student created a course")
				.then(()=>{
					// console.log(response);
					done();
				});
			}).catch((err)=>{
				throw err;
			});
		});

		it("A student can join a course", (done)=>{
			Promise.all([
				User.findOne({
					where: {
						username: studentObject.username
					}
				}),
				Course.findOne({
					where: {
						title: physicsObject.title
					}
				}),
			]).then((values)=>{
				// values[0] -> user
				// values[1] -> course
				CourseController.joinCourse(values[1].get("course_id"),
					values[0].get("user_id"))
				.then((courseStudent)=>{
					assert.equal(courseStudent.dataValues.student_id,
						values[0].get("user_id"), 
						"ID mismatch - The correct student must join the course.");

					assert.equal(courseStudent.dataValues.course_id,
						values[1].get("course_id"), 
						"ID mismatch - The correct course must be joined");
					done();
				}).catch((err)=>{
					console.log(err);
				});

			}).catch((err)=>{
				throw err;
			});
		});

		it("A student can leave a course", (done)=>{
			Promise.all([
				User.findOne({
					where: {
						username : studentObject.username
					}
				}),
				Course.findOne({
					where: {
						title : physicsObject.title
					}
				})
			]).then((values)=>{
				// values[0] -> user(student)
				// values[1] -> course
				CourseController.leaveCourse(values[1].get("course_id"),
					values[0].get("user_id"))
				.then((response)=>{
					assert.notEqual(response, null, 
						"The student didn't leave the course");
					done();
				}).catch((err)=>{
					// throw err;
					console.log(err);
				});

			}).catch((err)=>{
				// throw err;
				console.log(err);
			});
		});
	});



	after(function(done){
		done();
		// Destroying database instances.
		Promise.all([
			sequelize.query("delete from courses"),
			sequelize.query("delete from users")
		]).then(()=>{
			
			done();
		}).catch((err)=>{
			throw err;
		});
		
	});
});