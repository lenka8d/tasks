tasks
=====
How to run test processes

Process I am here

  This process tests tasks GPS location a SMS location. 
  
  1. To run this test, you need to install Android SDK from http://developer.android.com/sdk/index.html. 
  Download this repository extract it, open Eclipse, which has been installed with android SDK and import project Iamhere from directory "test".
  
  2. If you want to try them on emulator, you need to run two emulator devices. In this process, the nubmer where SMS is sent is 5556, so if none of your devices has this nuber you may need to change it to number of your actual device.
  Make sure you have device with GPS and don´t forget to push it GPS coordination via DDMS view (Emulator Control) after device initialization.
  Then run this process as Android app and start it on device you want to send SMS from. For instance if you have devices no. 5554 and 5556 start it on 5554, so you can see on device 5556 recieved SMS with coordinations.
  
  3. If you want to try this process on real device, plug it via USB, change phone number to real device number starting with +4.. for your country.
  Make sure, you have localization services (as GPS) enabled.And you must have enabled runnig 3rd party apps and developing mode. 
  Then run it as Android app and run it on your device which is between available devices.
  
Process Add event

  1. This process tests task Add Event. Assumig you alredy have Android SDK installed and downloaded and exctracted you open Eclipse and import project AddEvent.
  
  2. To add new event to your calendar you must have at least one account added. If you can´t add it on emulator, you need to test this process on real device.
  
  3. When you have at least one account added to your calendar, you can run this process as it is writen in process before. 
  At first you can see the screen where you must write some basic info about the event. Date maut be in format yyyy/mm/dd. This screen is made only for testing.
  In real processes it should be passed from previous task.
  After submiting there is shown event in calendar.  
