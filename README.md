# Mini-NUIS-Student-Portal
This Java-based Course Management System mimics the National University Information System (NUIS) 
student portal, providing an intuitive way for students to manage their academic progress and track 
school events.

## Project Scope Clarification: 
Although the file is named "finaprojectcircularqueue", it is important to note that the project 
does not implement circular queues as per what was discussed during the 
consultation phase.

### My Flowchart Code Functionality Overview

**1. Term Selection**  
- Users can choose a term, with Term 1 set as the default.  
- If a user attempts to select a term without having submitted grades for the previous term, a pop-up window will alert them that access to that term is restricted until grades have been submitted to the previous term.  

**2. Grade Input Mechanism**  
- Users can input grades for each course through a combo box with the following options:  
  - 4.0 to 1.0  
  - R (Repeat)  
  - Inc (Incomplete)  
  - Drp (Dropped)  

**3. Print Functionality**  
- The system includes a Print button that outputs the student’s flowchart, showcasing their academic progress.  

**4. Interactive Course Boxes**  
- Each course code box features an event listener.  
- When clicked, it opens a small pop-up window titled "Course Information," displaying the course code, grade, and any prerequisite subjects associated with the course.  

**5. NUIS-Inspired Visual Feedback**  
- The system follows the color-coding standards of the NUIS My Flowchart:  
  - **Red**: Courses marked with an 'R' grade.  
  - **Blue**: Courses that have not been taken.  
  - **White**: Passed courses.  
  - **Yellow**: Courses marked as incomplete.  
  - **Orange**: Dropped courses.  

**6. Prerequisite Management**  
- If a course in a selected term has an unpassed prerequisite from a previous term, a warning window will appear.  
- This window notifies the user that grades for that course cannot be entered until the prerequisite is passed.  
- Users must achieve a passing grade (greater than or equal to 1.0) in prerequisite courses to proceed.  

**7. Graduation Notification**  
- When all courses have been passed, a final pop-up window will notify the user that they are eligible for graduation. 
