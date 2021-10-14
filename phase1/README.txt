Part 1: Launching Application
====================================
The application can be launched with the Main class. The application will either read from a serialized file
("appstate.ser") if the file is present, or load from a predefined preset if the file is not.

By default, there are 4 users each having different roles.

Username        Role            Full Name           Password        Email
---------       ---------       ---------           ---------       ---------
coordinator     Coordinator     Legal Coordinator   password        coordinator@a
applicant       Applicant       Legal Applicant     password        applicant@a
interviewer     Interviewer     Legal Interviewer   password        interviewer@a
referrer        Referrer        Legal Referrer      password        referrer@a

New users can also be created using the "Signup" button.


Part 2: Coordinator View
====================================

2.1 Viewing current positions

In the "Job Postings" tab, use the "Positions" drop-down menu to select the job positions posted by this company.
To update any field, change the value in the text box and click "Update".

2.2 Creating a new position

Click "Add Position" and a new posting named "New Posting" will appear. Populate the text boxes with the appropriate
data and click "Update".

2.3 Viewing applicants

The HR coordinator is able to view information of every applicant who applied for any job in the current company.
To view details regarding a single applicant, go to the "Applicants" tab and select an applicant from the dropdown.

Here, you can also see the list of applications submitted by the current applicant.

2.4 Updating applicant information

The HR coordinator is also able to update information regarding an applicant. To do so, change the value in the textbox
and click "Update".


Part 3: Applicant View
====================================

3.1 Applying for a position

In the applicant view, click the "Available Positions" tab. In this view, the applicant can view job postings by
selecting it from the dropdown menu.

To apply for the selected job, click on the "Apply" button in this page.

3.2 Viewing personal information

In the "My Information" tab, the applicant can view and alter any personal details as well as see a list of current
applications.


Part 4: Interview View
====================================

4.1 Adding comments to an interview

In the "Interview View", the dropdown menu contains a list of current, upcoming interviews that are assigned to this
interviewer. To add comments, select the correct interview, add the comments and click "Save".

4.2 Completing interview

Once the interview is completed, click on the "Complete Interview" button to conclude the interview. This will also
update the state of the application.


Part 5: Referrer View
====================================

5.1 Adding reference

To add a reference as a referrer, click on "Submit New Reference". In the new window, enter the name of the applicant
being referred exactly. To protect the privacy of applicants, this option is not presented as a list and requires the
referrer to know the applicant's name in advance.

After selecting the position and adding a reference letter, the referrer can click "Submit Reference" to submit.