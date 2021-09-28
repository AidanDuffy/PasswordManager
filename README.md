# Project: Password Manager

## Description

### Design Goals
I wanted to design a password manager that was simple and easy to use. First, I needed to design a password generator.
Here, I utilized a Memento pattern for a PRNG (pseudorandom number generator) which will randomly select characters
with which to populate a randomly generated password from premade sets (alphanumeric and alphanumeric with symbols),
which will be determined beforehand, and this will also need to know the password length. Further, I will use the
state design pattern for a user using their master password to access all of their logins. Once logged in,
users will have access to all of their logins. The PasswordManager will contain a number of Login objects. It will
implement the Decorator design pattern to execute methods dynamically at runtime, such as access a specific login,
add a login, edit a login, or delete a login. When creating a login, users will select if they want symbols or not
as well as the length of the password, which will then be created in the password attribute of the Login object.
They will also supply the username or email associated with the account. Finally, once all of this is done,
the program will encrypt and hash all of this data to ensure it is secure on the user's machine. In the future,
I would like to create a GUI instead of everything being run through the terminal.

### Flexibility
In terms of flexibility, I want to ensure that this is as flexible as possible, especially as I see this as a more
long-term potential project, as computer security is a passion of mine. I want to ensure that the premade sets for 
password generation can easily be added to or edited, that additional encryption schemes (currently just AES and SHA256)
can be added, and new types of passwords or data, such as credit card numbers could be stored. To achieve this, the 
program will be simple, readable, and easily edited.

### Simplicity and Understandability
I wanted to ensure that the code was modular, was properly marked, well named, and commented so
that it was not cluttered, easy for developers to read, and simple enough to understand. All of the classes will be generalized
enough so that new objects can easily be created. All major object types will be separated into their own package,
such as the Login.java and Password.java in one, the generator in another, and the manager itself in its own.

### Duplication Avoidance
I will ensure that all functionalities are separated into their specific places in order to mitigate overlap, allowing
for fewer opportunities for duplications. I plan to refactor the project in the future and look for any potential mistakes.

### Running the Project
Simply run the Main.java file. Your system requires maven for this to run properly.

### To do:
1. Map out all designs and UML. Integrate further unit testing and DevOps tools.
2. Implement a GUI.
