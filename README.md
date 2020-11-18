
# ShoppyBot
A bot that purchases items for you!

## Getting Started

1. Download the latest chrome driver [here](https://chromedriver.chromium.org/)
- You can either add the chrome driver and chrome.exe to your PATH or just pass in both of their paths as arguments when constructing a new `ChromeDriver`
	- (Optional) Move the chrome driver where ever you'd like and add it to your PATH environment variable
	- (Optional) Add the path to your chrome.exe to your PATH environment variable
2. Add all of the .jar files in the lib directory to your project's dependencies 
3. Add run configuration to use the Main class and pass in command line arguments

### Arguments
`-name NAME_OF_PRODUCT(STRING) -quantity QUANTITY(INT,DEFAULT=1) -url URL_TO_PRODUCT(STRING, OPTIONAL)`

### Credentials
You must have a file called credentials.txt at the root of the project. The format is as follows:

    amazon_username:USERNAME_HERE
    amazon_password:PASSWORD_HERE
    newegg_username:USERNAME_HERE
    newegg_password:PASSWORD_HERE
