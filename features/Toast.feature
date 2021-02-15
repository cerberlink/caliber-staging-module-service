Feature: Toast Message
	
	#As a user I wish to interact with the page without first clicking on the message
	Scenario Outline: Interacting with the page with active message
	Given User is at the login page
	When user inputs their email <"email">
	And user inputs their password <"password">
	And user clicks log in
	And user clicks view swots
	And user clicks add new item
	And user selects type <"type">
	And user submits item name <"text">
	And user clicks add item
	Then user can click add new item again without clearing the message
	
	Examples:
	| email             | password | type        | text          |
	| test@revature.com | password | Opportunity | just checking |
	| test@revature.com | password | Opportunity | checking again|
	| test@revature.com | password | Opportunity | last time     |
	
	#As a user I wish to see all toasts clearly if there are more than one
	Scenario Outline: Ensuring toasts are clearly visible
	Given User is at the login page
	When user inputs their email <"email">
	And user inputs their password <"password">
	And user clicks log in
	And user clicks view swots
	And user clicks add new item
	And user selects type <"type">
	And user submits item name <"text">
	And user clicks add item
	And user clicks update for recently created item
	And user changes item type <"typetwo">
	And user clicks delete for the same item
	Then the three messages should occupy separate locations on the screen
	
	Examples:
	| email             | password | type        | text          | typetwo  |
	| test@revature.com | password | Opportunity | just checking | Strength |
	| test@revature.com | password | Opportunity | checking again| Strength |
	| test@revature.com | password | Opportunity | last time     | Strength |
	
	# Test ensuring nothing that used to cause alerts brings up alerts
	# (create swot on home page; add new item, update, and delete on view page)
	
	# Could also "then" this by having the user return to the home page and asserting
	# That they reached there safely alerts would not allow the page to page navigation
	# As a user I don't want my page frozen by a javascript alert 
	Scenario Outline: Test ensuring page contains no javascript alerts
	Given User is at the login page
	When user inputs their email <"email">
	And user inputs their password <"password">
	And user clicks log in
	And user clicks create swot
	And user selects type one <"type">
	And user submits item name <"text">
	And user clicks view swots
	And user clicks add new item
	And user selects type two <"typetwo">
	And user submits item name three <"texttwo">
	And user clicks add item
	And user clicks update for recently created item
	And user changes item type <"typethree">
	And user clicks delete for the same item
	Then the user should have four toast messages
	
	Examples:
	| email             | password | type   | text          | typetwo  | texttwo     | typethree |
	| test@revature.com | password | Threat | just checking | Weakness | more checks | Strength  |
	| test@revature.com | password | Threat | checking again| Weakness | testing     | Strength  |
	| test@revature.com | password | Threat | better safe   | Weakness | than sorry  | Strength  |
	
	