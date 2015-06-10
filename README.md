# seleniumMvnScreenshot
Allow your selenium suites to store automatically screenshots taken for every failed test in maven target folder for further usage. For example, you can include the screenshot of a failed test in email sent by your CI-build.

## Usage
1. Just add *FailTestScreenshotListener* to your TestNG class
2. The plugin will find by reflection the webDriver instance you are using and will do all the job.

## Results

