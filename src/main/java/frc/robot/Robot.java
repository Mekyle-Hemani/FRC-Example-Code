package frc.robot; //This is the package. Packages are just folders used to organize code better

import edu.wpi.first.wpilibj.Joystick; //This is the library required to use controllers for driving
import edu.wpi.first.wpilibj.TimedRobot; //This is the library required to drive the robot constantly
import frc.robot.subsystems.*; //This allows all subsystem code to be referenced

public class Robot extends TimedRobot {
  //Objects in java are just functions. Declaring them as done below is mandatory
  private RobotDriveSystem m_driveSystem; //This is the drive system object
  private Joystick m_leftStick;
  private Joystick m_rightStick;

  @Override
  public void robotInit() {
    //Initialize drive system and joysticks
    m_driveSystem = new RobotDriveSystem();
    m_leftStick = new Joystick(0);
    m_rightStick = new Joystick(1);
  }

  @Override
  public void teleopPeriodic() {
    //Get joystick inputs
    double leftSpeed = -m_leftStick.getY(); //Get the joystick data from the left
    double rightSpeed = -m_rightStick.getY(); //Get the joystick data from the right

    //Drive the robot using the gathered data from the joysticks
    m_driveSystem.drive(leftSpeed, rightSpeed);
  }
}