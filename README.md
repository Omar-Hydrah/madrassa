# Madrassa


##### Courses Management System for universities and schools, with Web and Android clients 

Teachers and students can register to create and join courses respectively.

### Tools
---
- Back-end: Node.js 
- Andorid Client: Java
- Web Client: HTML5, CSS3, Javascript/jQuery
- Database: MySQL with Sequelize as the ORM for Node.js 

For the **Android Client**, Retrofit and RxJava were relied on to handle network operations.

### Courses
---
A Teacher can create courses -through the web interface- for students to join. A Course is registered in the database with the following columns:
    
| column | data type | keys |
| --- | --- | --- |
| course_id | int(11) | Primary Key |
| teacher_id | int(11) | Foreign Key |
| title | varchar(50) | |
| description | text | |
| created_at | datetime | |

All Courses are listed in the url madrassa/course. 
Individual courses are in madrassa/course/7. In a course's page, a student can join the course, and view the list of joined students.

There are a couple of triggers on the courses table. The first is to prevent students from creating courses, and the second is to prevent teachers from joining courses.

### Users (Admins/Teachers/Students)
---
A user is stored in the database table with these columns:
   
| column | data type | keys |
| --- | --- | --- |
| user_id | int(11) | Primary Key
| username | varchar(30) | Unique Key |
| password | varchar(255) |  |
| first_name | varchar(20) | | 
| last_name | varchar(20) | |
| role | enum("admin", teacher, student) | |
| email | varchar(100) | |
| created_at | datetime| |

Passwords are hashed with the npm package; bcrypt-nodejs

# Android Client


There are 4 main activities in the Android client.

- **LoginActivity**
- **HomeActivity**
- **CourseListActivity** - Has a fragment for bigger screens.
- **CourseDetailActivity** - Managed by a fragment.

The Android application relies on the MVVM (Model View ViewModel) architecture to handle its data.

The only source of data is **_AppRepository_**, or "the only source of truth" as described by Google.

**AppRepository** is a simple abstraction layer that talks with both the network data classes, and the local data classes.

Both of the application's data activityes; **CourseListActivity** and **CourseDetailActivity** - rely on a dedicated ViewModel to obtain their data for display in the UI.


### Data
--- 

#### Local Data operations
There's only a single class for Local Data Operations, which is **Preference Handler**.
It does tasks, which save a registered user's state; such as the login token (json web token), username and user id and other information as well.

#### Network Data Operations

Network operations are handled by **Retrofit**, and performed by **RxJava**. There are a couple of Retrofit Service classes, one for authentication; **AuthService**, and another for authenticated users; **Madrassa Service**.
Each of these service classes has a Request class, that's responsible for the creation of it.
**AppRepository** is responsible for the creation of both Retrofit Request classes; **AuthRequest**, and **Madrassa equest**.

#### ViewModels
---
##### CourseListViewModel
**CourseListViewModel** is responsible for obtaining the list of courses, through a method provided by **AppRepository**.

##### CourseViewModel
**CourseViewModel** uses **AppRepository**, and is responsible for:
    - Displaying a course's info and a list of its registered student.
    - Joining a course.
    - Leaving a course.
### User Interface
--- 

#### LoginActivity
Handles user login and uses **AppRepository** to save the data to **SharedPreferences**.


#### HomeActivity
Used to display the number of a user's registered courses, and has a button to access the list of courses

#### CourseListActivity
Displays a list of all the available courses. Its data is obtained through **CourseListViewModel**. 
**CourseListActivity** can be displayed as a fragment, to the left side of the screen, with the selected course being displayed dynamically to the right of the screen.


#### CourseDetailActivity
Displays a single course's details, and also the list of registered students. The course's details are obtained through **CourseViewModel**.
Only for students, there's a button visible at the bottom of the activity, that enables joining a course, or leaving it; in case if someone's already registered.
**CourseDetailActivity** can be displayed as a fragment to the right of the screen.
