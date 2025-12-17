# Zara Test Automation Project

## Technologies

* Java
* Maven
* Selenium WebDriver
* JUnit
* Log4j
* Apache POI (Excel operations)
* Rest-Assured (API tests)

## Project Structure

* Page Object Model (POM) design pattern is used
* OOP principles are applied

## Web Automation Scenario

* Zara Turkey website is opened
* Cookie popup is accepted
* Login page is implemented as Page Object

  > **Note:** Login step is skipped during execution due to real user
  > verification requirements (phone/SMS).
  > Scenario continues as guest user.
* Men → View All navigation
* Search keywords are read from Excel file
* Random product selection
* Product info and price are written to txt file
* Cart price validation
* Quantity increase and cart validation
* Cart cleanup and empty cart verification

## API Automation

* Trello REST API automation using Rest-Assured
* Board and card creation
* Random card selection
* Cleanup operations (delete cards and board)

## Notes

This project is created for automation assignment purposes.
