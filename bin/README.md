# Spring Boot Microservice for Caliber Staging Module
Due to the remote nature work amidst the COVID pandemic, Revature Staging Managers are no longer delegated to managing associates in specific geographical region, as these associates span the entirety of the US. The Caliber Staging Module is an application that manages the assignment of associates to particular Staging Mangers, in order to balance workload and also provide information on those particular associates in the form of a SWOT analysis.  The app's SWOT cabability provides a centralized resource for Staging managers across Revature's national locations to manage and review associates as they prepare for client interviews.

### Team Members:
- Ben Johnston - Scrum Master, Team Lead, Staging Listener
- Jeremy Critchley - Team Lead, Created Associate Controller, Controller and Service Testing, Modal Popup
- Quincy Roman - Team Lead, Devops, SWOT analyses functionalities
- Connor Ryan - Team Lead, Devops, Docker/Jenkins configurations
- Azhya Knox - Team Lead, BatchRetriever, BatchWriter, Firebase Authentication, User UI/UX
- Andrew Curry - Testing & Assigning Associates to Managers
- Mareo Yapp - Viewing all associates assigned to a Manager, Testing and Documentation
- Saleel Bey - Documentation, Styling, Retrieving Managers and associates
- Thomas Morgan - create, view, and modify SWOTs
- Isa Reinert - Create, view, and modify SWOTs
- Patrick Warring - Create, view, and modify SWOTs
- Grayson McClellan - Create, view, and modify SWOTS and CSS development
- Suva Shahria - View recently graduated associates, Testing, Modal Popup
- Young Ha Shin - view component and testing view component
- Saleel Bey - Documentation, Styling, Retrieving Managers and associates
- Max Goncharov - Retrieving managers, dividing associates, styling, and documentation
- Alejandro Garza - Paired Programming, troubleshooting.

#### Manager User Stories
- A manager can view all associates assigned to him.
- A manager can view all associates that are new to staging.
- The system will automatically assign associates to a staging manager.

#### SWOT User Stories
- A manager can create a SWOT analysis for an associate in order to decide actions the associate should prioritize.
- A manager can view previous SWOT.
- A manager can add items to a SWOT.
 
![](./imgs/AD.png)

**Architecture Diagram**

![](./imgs/ERD.PNG)
**ERD Diagram**

Associate status' are enum staging and project. Analysis type are enum strengths, weaknesses, opportunities and threats.

### Technical Used
- Frontend:
  - Angular11
  - AngularFire/Firebase
  - Animate.css Library
  - Jasmine
  - Karma
  - Bootstrap
  - HTML5
  - CSS3
  - Caliber API
- Backend:
  - Spring Boot
  - Spring Data
  - Java8
  - Maven
  - JUnit
  - Mockito
  - PostgreSQL
  - Log4j
- Devops:
  - Jenkins
  - AWS EC2
  - AWS RDS
  - Docker
- Other
  - Git
  - Agile methodologies
