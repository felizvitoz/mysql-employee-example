# How to Run :
### Run the program :
- go to project home directory (Mysql-Employee-Scheme).
- issue command "mvn -pl controller -am clean spring-boot:run -Dspring-boot.run.profiles={selected-profile}".

#### note : 
- profile that we can choose are "live" and "test".
- we provide the postman API collection on project home, that you can use, if you are not comfortable using swagger, because I didnt customize the api endpoint to make swagger ui easier to operate.

### Test :
to run only test we can follow this command:
- go to project home directory (Mysql-Employee-Scheme).
- issue command "mvn clean test".


# How to Maintain the System :

this important to know how this system is build, so we know how to maintain the system or when 
we want to add something.

### Architecture 

we use clean architecture here, thats why you can find the system is consist of several following modules

#### controller
this module contains controller that can dispatch every request to specific bussiness rules.
we can add numbers of controller as many as we want to here, also in this module we have to implements
presenter using here to follow the Inversion of Control rules.

we can add custom config on config package, we can also using this module to be called from client, if we want to using client as interface for service inter calling.

#### core
in this module where we put the business process, each operation will have their own UseCase class, the important rule here is always use entity class when working in usecase, because it helps this module separate with other module.

in every use case we specify or annotated the class with transactional to start creating the connection to db once we enter the usecase, but we can using different level of propagation, in gateway class (if needed), we dont use custom implementation of transaction 
thats why we use regular spring annotation such as ("component", "service", "repository") in marking each class so the spring creating us the proxy object automatically to support their transaction mechanism (using AoP).

we introduce class RetryAbleExecution here that act as helper if you want your usecase have an ability to retry automatically if something unexpected happen.

#### repository
focus in this module is providing operations against database, the important rule here is to implements gateway interface from core module
that will act as bridges and make core and repository loosely coupling, so we can replace the repository implementation if we need to (in the future).

#### limitation
* we use single project but multi modules, to simplify the development, but if the project is getting larger we recommend to separate every module into different project and start using kind of artifact repository.
* may be currently we don't have control much of the flyway, because for the simplicity of sake, we can install the flyway in our computer, or use flyway maven plugins.
* we should have api gateway that sits before the service controller, its important to have such as authorization and authentication process to control what user can do, but not only that, we also need to check whether the user eligibility to do operation (transaction) that involving other user.
* currently I only create integration test because I think this is small projects, and all component within this project are fully used, but it's important to add unit test as well if we want to focus on smaller component especially if we have component that is not fully used (prepare some code logic to be used in the future).
* we can use RPC to make service interconnection faster than regular rest end point. 
* it is important to create balance between the number of retry count, and backoff (when using RetryAbleExecution), because the retryable is blocking the thread, consider using async mechanism if you want to overcome that limitation.
* for simplicity reason, i don't customize the api documentation (because the time limitation for me to learn, im not used to that), and code formatter (because in java its not as simple in GO hehe, for building api documentation and automatic formatting the code).