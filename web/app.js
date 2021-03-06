var express  = require("express");
var path     = require("path");
var app      = express();
var morgan   = require("morgan");
var flash    = require("connect-flash");
var session  = require("express-session");
var passport = require("passport");
var bodyParser   = require("body-parser");
var cookieParser = require("cookie-parser");

var middleware   = require("./middleware/index.js");

var sessionStore = new session.MemoryStore();

var sessionSetup = session({
	secret: "wordsecretesuper",
	resave: true,
	saveUninitialized: true, 
	store : sessionStore
});

// Contains a reference to the intialized sequelize object.
var sequelizeConfig = require("./config/sequelize-config.js");
var sequelize = sequelizeConfig.sequelize;

// Synchronizes the ORM with the database.
sequelize.authenticate()
	.then(()=>{
		console.log("Connected to the database");
	}).catch((err)=>{
		throw err;
	});

app.set("view engine", "ejs");
app.use(express.static(path.resolve(__dirname, "public")));
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());
app.use(cookieParser("happyflappycat"));
app.use(morgan("dev"));


// Authentication-related middleware
app.use(sessionSetup);

require("./config/passport-config.js");

app.use(passport.initialize());
app.use(passport.session());
app.use(flash());

// Application Routers:
var authRouter      = require("./routers/auth-router.js");
var homeRouter      = require("./routers/home-router.js");
var profileRouter   = require("./routers/profile-router.js");
var courseRouter    = require("./routers/course-router.js");
var apiAuthRouter   = require("./routers/api/api-auth.js");
var apiCourseRouter = require("./routers/api/api-course-router.js");

app.use("/auth", authRouter);
// Protected routes:
app.use("/home",    middleware.isLoggedIn, homeRouter);
app.use("/profile", middleware.isLoggedIn, profileRouter);
app.use("/course", courseRouter);
app.use("/api/auth", apiAuthRouter);
app.use("/api/course", apiCourseRouter);

app.get("/", (req, res)=>{
	res.render("index");
});

app.get("*", (req, res)=>{
	res.status(404).send("Requested resource not found");
});

var port = process.env.PORT || 80;

app.listen(port, ()=>{
	console.log(`Listening on port 80`);
});	
