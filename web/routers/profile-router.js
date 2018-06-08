var router         = require("express").Router();
var UserController = require("../controllers/user-controller.js");

router.get("/", (req, res)=>{

	// Redirect admins to the dashboard
	if(req.session.user.role == "admin"){
		return res.redirect("/profile/admin");
	}
	res.render("profile/index.ejs", {
		user: req.session.user || null
	});
});

router.get("/admin", (req, res)=>{
	// TODO: Display all users, all courses.
	res.render("profile/admin/index", {
		user: req.session.user
	});
});

/*router.post("/set-role", (req, res)=>{
	var allowedRoles = ["teacher", "student"];

	// Confirm that there's a user role, and that it's an allowed role.
	if(req.body.userRole && allowedRoles.indexOf(req.body.userRole) != -1){

		UserController.updateRole(req.session.user.userId, req.body.userRole)
			.then((user)=>{
				// Raw user is sent from the controller.
				console.log(user);
				req.session.user.role = user.role;

				//res.redirect("/profile", {
				//	message: "success"
				//});
				return res.redirect("/profile");
			}).catch((err)=>{
				return res.redirect("/profile");
			});
		return;	
	}

	return res.redirect("/profile");
});
*/
module.exports = router;