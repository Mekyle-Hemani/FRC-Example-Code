//Copyright (c) FIRST and other WPILib contributors.
//Open Source Software; you can modify and/or share it under the terms of
//the WPILib BSD license file in the root directory of this project.

package frc.robot; //This is the package declaration. Packages are used to organize code into namespaces to keep it modular and easier to manage.

import edu.wpi.first.wpilibj.RobotBase; //Import the RobotBase class from the WPILib library. This is necessary for initializing and running a robot program.

 /**
  * Do NOT add any static variables to this class, or any initialization at all.
  * Unless you know what you are doing, do not modify this file except to change 
  * the parameter class to the startRobot call.
  */
public final class Main {
  
  //Private constructor to prevent instantiation of the Main class
  private Main() {}

  /**
   * Main initialization function. Do not perform any initialization here.
   *
   * <p>If you change your main robot class, change the parameter type in the call to startRobot.
   */
  public static void main(String... args) {
    //Start the robot. The startRobot method is a WPILib method that initializes the robot program.
    //The Robot::new indicates the class that contains the main robot logic, typically a subclass of RobotBase.
    RobotBase.startRobot(Robot::new); //This starts the robot program by calling the constructor of the Robot class.
  }
}
