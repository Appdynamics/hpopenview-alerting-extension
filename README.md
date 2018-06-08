# AppDynamics Alerting Extension for use with HP OpenView

## Use Case
HP Operations Manager software provides large-scale system and network management of an organization's IT infrastructure. It is also called HP Open View.
This custom action integrates HP Operations Manager with AppDynamics by creating tickets in response to events/alerts in AppDynamics.


## Prerequisites
- You should have the opcmsg executable on the controller machine. For this, please install the HP OM agent on the machine where Controller resides.

## Installation Steps
 1. Run "mvn clean install". 

 2. Find the zip file at 'target/hpopenview-alert.zip' or Download the HP Open View Alerting Extension zip from [AppDynamics Exchange](http://community.appdynamics.com/t5/AppDynamics-eXchange/idb-p/extensions)

 3. Unzip the hpopenview-alert.zip file into <CONTROLLER_HOME_DIR>/custom/actions/ . You should have  <CONTROLLER_HOME_DIR>/custom/actions/hpopenview-alert created.

 4. Check if you have custom.xml file in <CONTROLLER_HOME_DIR>/custom/actions/ directory. If yes, add the following xml to the <custom-actions> element.
 
   ```
      <action>
    		  <type>hpopenview-alert</type>
          <!-- For Linux/Unix *.sh -->
     		  <executable>hpopenview-alert.sh</executable>
          <!-- For windows *.bat -->
     		  <!--<executable>hpopenview-alert.bat</executable>-->
      </action>
  ```
     
   If you don't have custom.xml already, create one with the below xml content
    
      ```
      <custom-actions>
          <action>
      		  <type>hpopenview-alert</type>
            <!-- For Linux/Unix *.sh -->
       		  <executable>hpopenview-alert.sh</executable>
            <!-- For windows *.bat -->
       		  <!--<executable>hpopenview-alert.bat</executable>-->
     	    </action>
        </custom-actions>
      ```
      Uncomment the appropriate executable tag based on windows or linux/unix machine.
    
 5. Update the config.yaml file with path to the "opcmsg" executable.

    ###Note
    Please make sure to not use tab (\t) while editing yaml files. You may want to validate the yaml file using a yaml validator http://yamllint.com/

    	
        ```	
          #complete path to the binary or exe which includes the binary or exe. Use proper separators for Windows and Unix. For windows, escape the "\" char with another "\"
          # For eg. "C:\\HP\\bin\\opcmsg"
          pathToExecutable: ""
          
          #Message group for HP OpenView
          msgGroup: "AppDynamics"
          

        ```        
         
 6. Now you are ready to use this extension as a custom action. In the AppDynamics UI, go to Alert & Respond -> Actions. Click Create Action. Select Custom Action and click OK. In the drop-down menu you can find the action called 'hpopenview-alert'.

## Contributing
Always feel free to fork and contribute any changes directly via [GitHub](https://github.com/Appdynamics/hpopenview-alerting-extension).

## Support
For any questions or feature request, please contact [AppDynamics Support](mailto:help@appdynamics.com).

## Version
Extension Version: 1.0.9

Controller Compatibility: 4.3+

Last updated On: 8th June, 2018