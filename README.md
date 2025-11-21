# nocountry-crm
Endpoints for User CRUD start with "api/v1/user":
* GET ("api/v1/user/{id}"): get user by id
* GET ("api/v1/user"): get all users
* PUT ("api/v1/user/{id}"): update user by id
* DELETE ("api/v1/user/{id}"): delete user by id

Process for creating a user and updating thier image:

1. CUSTOMER_ADMIN creates a user using this endpoint: "POST: /api/v1/auth/register". The information required to create a user is:
* String fullName
* String email
* String password
* String companyCode

Note: no image is uploaded at this point. (Maybe a default image can be set here)

2. USER logs in and updates their own profile using this endpoint: "PUT: api/v1/user/{id}". The information that can be updated is the following:
* String fullName
* String email
* String password
* String companyCode
* RoleCode role (maybe should be removed)
* MultipartFile image

Note: at the moment, only the image link is saved with the User entity. Entity "FileAttachment" isn't used here.
