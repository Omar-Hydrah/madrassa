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

module.exports = router;