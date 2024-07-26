# Assumptions & Thoughts
1. I have added in the caveat that the date format that is input into the GET request is in the format dd/mm/yy rather than sometime being 0d/0m/yy.
2. I have added in the assumption that you do not get charged rent for the tool you have rented on the day you return it.
3. I also thought about adding to the database schema to represent whether to the tool was already rented before you started the rental process. But didn't add it.
4. I should have added unit test specifically for the Service layer but they would be duplicated by the integration test so decided not to.