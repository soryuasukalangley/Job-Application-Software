# States
The list below indicates the possible states the application can be in. The states should be stored in an enum in the application.

1. **Applied**
 * Application submitted
    * The job application has been submitted by the applicant along with the resume.
2. **In progress (Normal)**
 * Application received
    * The employer acknowledges receiving the application, indicates the beginning of the hiring process.
 * Selected for interview
    * The employer requests to interview the applicant. This state should also contain a date of the interview.
    * The interviewer and date of interview should also be specified. 
 * Interview completed
    * The applicant completes the interview with the employer.
* Job Offered
3. **In progress (Requesting additional information)**
 * More information requested
    * The employer indicates to the applicant that more information needs to be submitted. Along with this state should also come a message indicating what information the applicant needs to submit.
 * Information submitted
    * The applicant submitted the information
4. **Completed**
 * Accepted
 * Rejected

# Transitions

## Actions

### Applicant

* Submit application (This is the step leading to the initial state. The application begins existing once the user submits it)
  * Requirements: User also need to submit resume
* Submit additional information
  * Requirements:
    * Current state: "More information requested"
    * Requires file to be submitted alongside this action
* Accept Offer
  * Requirements: Current state: "Job Offered"
* Withdraw Offer
  * No requirements, can do this at any stage. Transitions straight to "Rejected"

### Employer

* Acknowledge receiving offer (coordinator)
  * Requirements: Current state: "Application Submitted"
* Select for interview (coordinator)
* Complete interview (interviewer)
  * Requirements: Current state: "Selected for Interview"
  * <u>Note:</u> We might need another state that indicates the date, time, and location of interview.
* Request additional information (coordinator)
* Acknowledge and accept additional information (coordinator)
  * Requirements: Current state: "More information requested"
* Offer position (coordinator)
* Reject applicant (coordinator)
