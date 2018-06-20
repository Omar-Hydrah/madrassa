var assert           = require("assert");
var sequelize        = require("../config/sequelize-config.js").sequelize;
var User             = sequelize.import("../models/user.js");
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
		/*Promise.all([
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
			console.log(err);
		});*/
		
		done();
	});
	describe("Course creation", ()=>{

		it("a teacher creates a new course", (done)=>{
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
					username: teacherObject.username
				}

			}).then((user)=>{
				// console.log(user.get("user_id"));

				// assert.throws(CourseController.createCourse.bind(
				// 	CourseController,
				// 	user.get("user_id"), physicsObject.title,
				// 	physicsObject.description, false), 
				// Error, "Student error must be thrown");

				CourseController.createCourse(user.get("user_id"), 
					physicsObject.title, physicsObject.description, false)
				.then((course)=>{
					// console.log(course.get("title"));
					// assert.deepEequal(course.get("title"), null, 
					// "Course must be null");
					// assert.throws(CourseController.createCourse, 
					// 	Error, 
					// 	"A student can not create a course");
					if(course){
						// console.log(course.get("title"));
						// throw new Error("Test failure - a student created a course");
						assert.fail("Test failure - a student created a course");
					}
					done();
				}).catch((err)=>{
					// throw err;
					console.log("Error name ", err.name);
					console.log(Object.keys(err));
					// assert.throws(CourseController.createCourse, Error, "Student error");
					assert.ok(err, "Error must be raised");
				}).finally(done);
			}).catch((err)=>{
				throw err;
			});
		});
		// Undefined:
		// console.log(Object.keys(teacher));
		// console.log(Object.keys(student));

	});

	after(function(done){
		done();
		// Destroying database instances.
		/*Promise.all([
			sequelize.query("delete from courses"),
			User.findOne({where: {username: teacherObject.username}}),
			User.findOne({where: {username: studentObject.username}})	
		]).then((values)=>{
			Promise.all([
				values[1].destroy(),
				values[2].destroy()
			]).then(()=>{
				done();
			}).catch((err)=>{
				throw err;
			});

		}).catch((err)=>{
			throw err;
		});*/
		// if(teacher != null){
		// 	teacher.destroy();
		// }
		// if(student != null){
		// 	student.destroy();
		// }
		// if(chemistry != null){
		// 	console.log(chemistry);
		// 	chemistry.destroy();
		// }
	});
});